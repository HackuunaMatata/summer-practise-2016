package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

 static ArrayList<User> users = new ArrayList<>();
  
    public static void main(String[] args) throws ParseException {

        
       /* users.add(new User());
        users.get(0).Create("a", "4", "active");
        users.add(new User());
        users.get(1).Create("b", "0", "active");
        users.get(0).AddEvent("b", "hello", "5.07.2016-21:52:00");
        users.get(0).AddEvent("b", "hello2", "11.04.2012-12:12:00");
        users.get(0).ShowInfo("b");
        users.get(0).AddEvent("b", "hello122", "12.04.2012-7:12:00");
        users.get(0).AddRandomTimeEvent("b", "hello3", "10.06.2014-16:00:00", "10.06.2014-18:00:00");
        users.get(0).RemoveEvent("b", "hello");
        users.get(0).ShowInfo("a");
        users.get(0).ShowInfo("b");*/
      
        while(true){
            try {
                redaction();
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void redaction() throws ParseException
    {
        String command;
        int size_us;
        String name_us;
        Scanner in = new Scanner(System.in);
        System.out.print("Введите команду: \n");
        
        String line = in.nextLine();  
        StringTokenizer str = new StringTokenizer(line);
        command = str.nextToken();
        
       switch(command) {
           case "Create":
              users.add(new User());
              size_us = users.size();
              users.get(size_us-1).Create(str.nextToken(), str.nextToken(), str.nextToken());
            break;
            
           case "Modify":
              size_us = users.size();
              if(size_us == 0) 
              {
                  System.out.println("ПОльзователи отсутствуют!"); 
                  break;
              }
              users.get(size_us - 1).Modify(str.nextToken(), str.nextToken(), str.nextToken());
            break;
            
            case "ShowInfo":
              size_us = users.size();
              if(size_us == 0) 
              {
                  System.out.println("ПОльзователи отсутствуют!"); 
                  break;
              }
              users.get(size_us-1).ShowInfo(str.nextToken());
            break;

            case "AddEvent":
               size_us = users.size();
              if(size_us == 0) 
              {
                  System.out.println("ПОльзователи отсутствуют!"); 
                  break;
              }
              users.get(size_us - 1).AddEvent(str.nextToken(), str.nextToken(), str.nextToken());
             break;
             
             case "RemoveEvent":
               size_us = users.size();
              if(size_us == 0) 
              {
                  System.out.println("ПОльзователи отсутствуют!"); 
                  break;
              }
              users.get(size_us - 1).RemoveEvent(str.nextToken(), str.nextToken());
             break;
             
             case "CloneEvent":
               size_us = users.size();
              if(size_us == 0) 
              {
                  System.out.println("ПОльзователи отсутствуют!"); 
                  break;
              }
              users.get(size_us - 1).CloneEvent(str.nextToken(), str.nextToken(),str.nextToken());
             break;
             
             case "AddRandomTimeEvent":
                 size_us = users.size();
              if(size_us == 0) 
              {
                  System.out.println("ПОльзователи отсутствуют!"); 
                  break;
              }
              users.get(size_us - 1).AddRandomTimeEvent(str.nextToken(), str.nextToken(),
                      str.nextToken(),str.nextToken());
              break;
              
             case "StartScheduling":
                Scheduling sh = new Scheduling();
                sh.run();
            break;
             default:
                 System.out.println("Команда некорректна.");
                 break;
               
               
       }
           
    }
    
}
