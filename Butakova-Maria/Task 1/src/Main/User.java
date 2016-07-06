package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User {
    
    String name;   // имя пользователя
    int timezone;   // таймзона
    boolean status;     // 0 - пассивный / 1 - активен
    ArrayList<Event> us_event = new ArrayList<>();  // события для пользователя
    
    public User () {}
    
    // создание нового пользователя
    public int Create (String nm_us, String tm_us, String st_us) {
        for ( int i = 0; i < Main.users.size() - 1; i++)
        {
            if(nm_us.equals(Main.users.get(i).name)) 
            {
                System.out.println("Пользователь с данным именем уже существует!\n");
                Main.users.remove(Main.users.size() - 1);
                return 0;
            }
        }
        if(st_us.equals("active")) status = true;
        else if (st_us.equals("passive")) status = false;
        else  { 
            System.out.println("Статус некорректен!"); 
            Main.users.remove(Main.users.size() - 1);
            return 0; 
        }
        if(Integer.valueOf(tm_us)>12 || Integer.valueOf(tm_us)<-12)
        {
            System.out.println("Таймзона некорректна!"); 
            Main.users.remove(Main.users.size() - 1);
            return 0; 
        }
        
        name = nm_us;
        timezone = Integer.valueOf(tm_us);
       
        System.out.println("Пользователь добавлен!\n");
        return 1;
        
    }
    
    // изменение пользователя
    public int Modify (String nm_us, String tm_us, String st_us) {
        boolean flag = false;
        for ( int i = 0; i < Main.users.size(); i++)
        {
            if(nm_us.equals(Main.users.get(i).name)) 
            {
                 if(st_us.equals("active")) Main.users.get(i).status = true;
                else if (st_us.equals("passive")) Main.users.get(i).status = false;
                    else  
                {
                    System.out.println("Статус некорректен!Операция не выполнена.");
                    return 0;
                } 
                if(Integer.valueOf(tm_us)>12 || Integer.valueOf(tm_us)<-12)
                {
                    System.out.println("Таймзона некорректна! Операция не выполнена.");
                    return 0;
                }
                 Main.users.get(i).timezone = Integer.valueOf(tm_us);      
                 flag = true;      
            }
        }
        if (flag == true)
            System.out.println("Данные изменены.");
        else
            System.out.println("Такого пользователя не существует.");
        
          return 1;
    }

     //AddEventдобавление события для пользователя. 
    public int AddEvent(String nm_us, String txt_ev, String dt_us ) throws ParseException
    {

        for ( int i = 0; i < Main.users.size(); i++)
        {
         if(nm_us.equals(Main.users.get(i).name)) 
         {
             for (Event us_event1 : Main.users.get(i).us_event) {
                 if(us_event1.text.equals(txt_ev))
                 {
                     System.out.println("Текст не уникален!");
                     return 0;
                 }
             }
             SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
             Date date = new Date(format.parse(dt_us).getTime()+Main.users.get(i).timezone*3600000);
             
             
            
             for (int j = 0; j < Main.users.get(i).us_event.size(); j++) {
                 
                 if(Main.users.get(i).us_event.get(j).time.getTime() > date.getTime()){
                     
                  Main.users.get(i).us_event.add(new Event());
                  for(int k = Main.users.get(i).us_event.size()-1; k > j; k--)
                  {
                      Main.users.get(i).us_event.get(k).text = Main.users.get(i).us_event.get(k-1).text;
                      Main.users.get(i).us_event.get(k).time = Main.users.get(i).us_event.get(k-1).time;
                  }
                  
                 Main.users.get(i).us_event.get(j).text = txt_ev;
                 Main.users.get(i).us_event.get(j).time = date;
                 return 0;
                 
                 }
             }
             Main.users.get(i).us_event.add(new Event());
             int s = Main.users.get(i).us_event.size() - 1;
             Main.users.get(i).us_event.get(s).text = txt_ev;
             Main.users.get(i).us_event.get(s).time = date;
             

             return 0;
           }
         }
        System.out.println("ПОльзователя с таким именем не существует.");
        return 0;

     }
    
    public int RemoveEvent(String nm_us, String txt_ev)
    {
     for ( int i = 0; i < Main.users.size(); i++)
        {
         if(nm_us.equals(Main.users.get(i).name)) 
         {
             for(int j = 0; j < Main.users.get(i).us_event.size(); j++)
             {
                 if(Main.users.get(i).us_event.get(j).text.equals(txt_ev))
                     Main.users.get(i).us_event.remove(j);
                 System.out.println("Событие удалено.");
                 return 0;
             }
             System.out.println("Такого события не существует.");
             return 0;
         }
        }
        System.out.println("ПОльзователя с таким именем не существует.");
        return 0;
    }
    
    public int AddRandomTimeEvent(String nm_us, String txt_ev, String dt_us_fr, 
            String dt_us_to) throws ParseException
    {
      for ( int i = 0; i < Main.users.size(); i++)
        {
         if(nm_us.equals(Main.users.get(i).name)) 
         {
             for (Event us_event1 : Main.users.get(i).us_event) {
                 if(us_event1.text.equals(txt_ev))
                 {
                     System.out.println("Текст не уникален!");
                     return 0;
                 }
             }
             
             
             SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
             Date date_fr = new Date(format.parse(dt_us_fr).getTime()+Main.users.get(i).timezone*3600000);
             Date date_to = new Date(format.parse(dt_us_to).getTime()+Main.users.get(i).timezone*3600000);
             
             long t = (long) (date_fr.getTime() + Math.random()*(date_to.getTime() - date_fr.getTime()));
             Date date = new Date(t);
             
            
             for (int j = 0; j < Main.users.get(i).us_event.size(); j++) {
                 
                 if(Main.users.get(i).us_event.get(j).time.getTime() > date.getTime()){
                     
                  Main.users.get(i).us_event.add(new Event());
                  for(int k = Main.users.get(i).us_event.size()-1; k > j; k--)
                  {
                      Main.users.get(i).us_event.get(k).text = Main.users.get(i).us_event.get(k-1).text;
                      Main.users.get(i).us_event.get(k).time = Main.users.get(i).us_event.get(k-1).time;
                  }
                  
                 Main.users.get(i).us_event.get(j).text = txt_ev;
                 Main.users.get(i).us_event.get(j).time = date;
                 return 0;
                 
                 }
             }
             Main.users.get(i).us_event.add(new Event());
             int s = Main.users.get(i).us_event.size() - 1;
             Main.users.get(i).us_event.get(s).text = txt_ev;
             Main.users.get(i).us_event.get(s).time = date;

             return 0;
           }
         }
        System.out.println("ПОльзователя с таким именем не существует.");
        return 0;
    }
    public int CloneEvent(String nm_us, String tx_even, String nm_us_to)
    {
        int ind_nm = -1, ind_nm_to = -1;
        for ( int i = 0; i < Main.users.size(); i++)
        {
         if(nm_us.equals(Main.users.get(i).name)) ind_nm = i;
         if(nm_us_to.equals(Main.users.get(i).name)) ind_nm_to = i;
        }
        if(ind_nm == -1 || ind_nm_to == -1)
        {
            System.out.println("Ошибка в имени пользователя.");
            return 0;
        }
         for (Event us_event1 : Main.users.get(ind_nm).us_event) {
             if(us_event1.text.equals(tx_even))
             {
                 Main.users.get(ind_nm_to).us_event.add(new Event());
                 int size =  Main.users.get(ind_nm_to).us_event.size();
                 Main.users.get(ind_nm_to).us_event.get(size-1).text = us_event1.text;
                 Main.users.get(ind_nm_to).us_event.get(size-1).time = us_event1.time;
                 System.out.println("Событие добавлено.");
                 return 0;
             }
         }
         System.out.println("Такого события не существует.");
         return 0;
    }
            
    //показывает информацию о данном пользователе
     public void ShowInfo(String nm_us) {
         boolean flag = false;
         for ( int i = 0; i < Main.users.size(); i++)
        { 
            if(Main.users.get(i).name.equals(nm_us)) 
            {
                System.out.println(Main.users.get(i).name+'\t'+ 
                   Main.users.get(i).timezone+'\t'+Main.users.get(i).status);
                
                for (Event us_event1 : Main.users.get(i).us_event) {
                    System.out.println(us_event1.text + '\t' + us_event1.time);
                }
               flag = true;
            }
        }
         if(!flag) System.out.println("Такого пользователя не существует!\n");
         
     }        
    

}
