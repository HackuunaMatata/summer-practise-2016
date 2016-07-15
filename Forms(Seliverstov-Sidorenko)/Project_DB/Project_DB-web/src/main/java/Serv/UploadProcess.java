/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serv;

import database.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import static javax.servlet.SessionTrackingMode.URL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
//import org.json.JSONArray;

/**
 *
 * @author a1
 */
public class UploadProcess {
    public String upload(int eventId,String title) throws IndexOutOfBoundsException, IOException{
                
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet(title);
 
        // заполняем список какими-то данными
        List<UploadEntity> dataList = fillData(eventId);
 
        // счетчик для строк
        int rowNum = 0;
 
        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Title");
        row.createCell(1).setCellValue("Surname");
        row.createCell(2).setCellValue("Name");
        for(int i = 0; i < dataList.get(0).getuItemAns().size(); i++){
            row.createCell(i+3).setCellValue(dataList.get(0).getuItemAns().get(i).getItem());
        }
 
        // заполняем лист данными
        for (UploadEntity uploadentity : dataList) {
            createSheetHeader(sheet, ++rowNum, uploadentity);
        }
        
        File xlsFile = new File(title+".xls");
        xlsFile.setReadable(true);
        // записываем созданный в памяти Excel документ в файл
        try (FileOutputStream out = new FileOutputStream(xlsFile)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");
        
        return title+".xls";
    }
    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private static void createSheetHeader(HSSFSheet sheet, int rowNum, UploadEntity uploadEntity) {
        Row row = sheet.createRow(rowNum);
 
        row.createCell(0).setCellValue(uploadEntity.getTitle());
        row.createCell(1).setCellValue(uploadEntity.getSurname());
        row.createCell(2).setCellValue(uploadEntity.getName());
        for(int i = 0; i < uploadEntity.getuItemAns().size(); i++){
            row.createCell(i+3).setCellValue(uploadEntity.getuItemAns().get(i).getAnswer());
        }
//        row.createCell(3).setCellValue(uploadEntity.getSalary());
    }
 
    // заполняем список рандомными данными
    // в реальных приложениях данные будут из БД или интернета
    public List<UploadEntity> fillData(int eventId) {
        ArrayList<UploadEntity> uploadEntity = new ArrayList<>();
        UploadEntity tempUE = new UploadEntity();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        //Получили нужный ивент
        Events event = (Events) session.get(Events.class, eventId);
        ArrayList<Users> eUsers = new ArrayList<>();
        ArrayList<Strings> uStrings = new ArrayList<>();
        ArrayList<Numbers> uNumbers = new ArrayList<>();
        ArrayList<Dates> uDates = new ArrayList<>();
        ArrayList<Blobs> uBlobs = new ArrayList<>();
        ArrayList<UploadItemAnswer> uia = new ArrayList<>();
        UploadItemAnswer tempUIA = new UploadItemAnswer();
        
        String sqlUser = "SELECT * FROM Users WHERE idevent="+event.getIdevent();
        SQLQuery query = session.createSQLQuery(sqlUser);
        eUsers = (ArrayList) query.addEntity(Users.class).list();
        
        System.out.println(eUsers);
        
        for(int i = 0; i < eUsers.size(); i++){
            
            String sqlString = "SELECT * FROM Strings WHERE iduser="+eUsers.get(i).getId().getIduser()+
                    " AND idevent="+event.getIdevent();
            query = session.createSQLQuery(sqlString);
            uStrings = (ArrayList) query.addEntity(Strings.class).list();
            
            for(int j = 0; j < uStrings.size(); j++){
                int id = uStrings.get(j).getId().getIditem();
                Questions tempQ = (Questions) session.get(Questions.class, id);
                tempUIA = new UploadItemAnswer(tempQ.getItemname(),uStrings.get(j).getId().getValue());
                uia.add(tempUIA);
            }
            
            String sqlNumber = "SELECT * FROM Numbers WHERE iduser="+eUsers.get(i).getId().getIduser()+
                    " AND idevent="+event.getIdevent();
            query = session.createSQLQuery(sqlNumber);
            uNumbers = (ArrayList) query.addEntity(Numbers.class).list();
            
            for(int j = 0; j < uNumbers.size(); j++){
                int id = uNumbers.get(j).getId().getIditem();
                Questions tempQ = (Questions) session.get(Questions.class, id);
                tempUIA = new UploadItemAnswer(tempQ.getItemname(),String.valueOf(uNumbers.get(j).getId().getValue()));
                uia.add(tempUIA);
            }            
            
            String sqlDate = "SELECT * FROM Dates WHERE iduser="+eUsers.get(i).getId().getIduser()+
                    " AND idevent="+event.getIdevent();
            query = session.createSQLQuery(sqlDate);
            uDates = (ArrayList) query.addEntity(Dates.class).list();
            
            for(int j = 0; j < uDates.size(); j++){
                int id = uDates.get(j).getId().getIditem();
                Questions tempQ = (Questions) session.get(Questions.class, id);
                tempUIA = new UploadItemAnswer(tempQ.getItemname(),uDates.get(j).getValue().toString());
                uia.add(tempUIA);
            }             
            
            String sqlBlob = "SELECT * FROM Blobs WHERE iduser="+eUsers.get(i).getId().getIduser()+
                    " AND idevent="+event.getIdevent();
            query = session.createSQLQuery(sqlBlob);
            uBlobs = (ArrayList) query.addEntity(Blobs.class).list();
            
            for(int j = 0; j < uBlobs.size(); j++){
                int id = uBlobs.get(j).getId().getIditem();
                Questions tempQ = (Questions) session.get(Questions.class, id);
                tempUIA = new UploadItemAnswer(tempQ.getItemname(),uBlobs.get(j).getValue().toString());
                uia.add(tempUIA);
            }     
            
            uploadEntity.add(
                    new UploadEntity(
                            event.getTitle(),
                            eUsers.get(i).getSurname(),
                            eUsers.get(i).getName(),
                            uia
                    )
            );
            uia = new ArrayList();
        }

        session.close();
 
        return uploadEntity;
    }
}
