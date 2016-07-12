package task.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

class EventNotifier implements Runnable
{
    private boolean isActiveThread = true;
    public void stop()
    {
        this.isActiveThread = false;
    }

    public void run()
    {
        try {
            Date d1 = new Date();
            while (isActiveThread) {
                sleep(1000);
                Date d2 = new Date();
                String dateString = d2.toString();
                //System.out.println(date.getTime());
                Scheudle sh = new Scheudle();
                for(int i = 0; i<sh.users.size();i++)
                {
                    User user = sh.users.get(i);
                    for (int j = 0;j < user.getEventsCount();j++)
                    {
                       Date userDate = user.getEventByIndex(j).getDate();
                        if(userDate.before(d2)&& userDate.after(d1)) {

                                if (user.isActive() == true) {
                                    System.out.println("Event is beginning: ");
                                    Event ev = user.getEventByIndex(j);
                                    System.out.print(ev.getText() + " " + ev.getDate().toString());
                                }

                        }
                    }
                }
                d1=d2;
            }
        }
        catch(InterruptedException e){}
    }
}

public class Scheudle {
    public static ArrayList<User> users = new ArrayList<User>();

    public static void main(String[] args) {
        try {
            EventNotifier eventNotifier = new EventNotifier();
            Thread myThready = new Thread(eventNotifier);	//Создание потока "myThready"
            myThready.start();

            boolean isActive = true;
            boolean isFound = false;
            while (isActive) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String[] command = reader.readLine().split(" ");
                switch (command[0])
                {
                    case "Create":
                        users.add(new User(command[1], Integer.parseInt(command[2]), command[3] == "false" ? false : true));
                        System.out.println("Added successfully");
                        break;
                    case "Modify":
                        isFound = false;
                        for(int i = 0; i < users.size(); i++)
                        {
                            if( users.get(i).getName().equals(command[1]) )
                            {
                                User modifiedUser = users.get(i);
                                modifiedUser.setTimeZone(Integer.parseInt(command[2]));
                                modifiedUser.setActive(command[3].equals("false") ? false : true);
                                users.set(i, modifiedUser);
                                isFound = true;
                                System.out.println("Modified successfully");
                                break;
                            }
                        }
                        if(!isFound)
                        {
                            System.out.println("User does not exist");
                        }
                        break;
                    case "AddEvent":
                        isFound = false;
                        for(int i = 0; i < users.size(); i++)
                        {
                            if( users.get(i).getName().equals(command[1]) )
                            {
                                User modifiedUser = users.get(i);
                                for(int j=0;j<modifiedUser.getEventsCount();j++)
                                {
                                    if(modifiedUser.getEventByIndex(j).getText().equals(command[2])){
                                        System.out.println("There are similar text of events!");
                                        break;
                                    }
                                }
                                SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyyy-hh:mm:ss");
                                Calendar c = GregorianCalendar.getInstance();
                                Date dateUser= dt.parse(command[3]);//получили дату пользователя в его часовом поясе
                                long systemTZ = c.getTimeZone().getOffset(new Date().getTime())/3600000;//TimeZone системы
                                long difTZ=systemTZ-modifiedUser.getTimeZone();//разность TimeZone системы и пользователя
                                dateUser.setTime(dateUser.getTime()+difTZ*3600000);//установили время для пользователя с учетом разности часовых поясов
                                Event ev= new Event(dateUser, command[2]);
                                modifiedUser.addEvent(ev);
                                isFound = true;
                                System.out.println("Event added successfully");
                                break;
                            }
                        }
                        if(!isFound)
                        {
                            System.out.println("User does not exist");
                        }
                        break;
                    case "RemoveEvent":
                        isFound = false;
                        for(int i = 0; i < users.size(); i++)
                        {
                            if( users.get(i).getName().equals(command[1]) )
                            {
                                User modifiedUser = users.get(i);
                                isFound =modifiedUser.removeEvent(command[2]);
                                users.set(i, modifiedUser);
                                System.out.println("Event removed successfully");
                                break;
                            }
                        }
                        if(!isFound)
                        {
                            System.out.println("Event does not exist");
                        }
                        break;

                    case "AddRandomTimeEvent":
                        isFound = false;
                        for(int i = 0; i < users.size(); i++)
                        {
                            if( users.get(i).getName().equals(command[1]) )
                            {
                                User modifiedUser = users.get(i);
                                SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyyy-hh:mm:ss");
                                Random r = new Random(System.currentTimeMillis());
                                long time = r.nextInt((int)(dt.parse(command[4]).getTime() - dt.parse(command[3]).getTime()));
                                Calendar c = GregorianCalendar.getInstance();
                                Date dateUser= new Date(dt.parse(command[3]).getTime() + time);//получили дату пользователя в его часовом поясе
                                long systemTZ = c.getTimeZone().getOffset(new Date().getTime())/3600000;//TimeZone системы
                                long difTZ=systemTZ-modifiedUser.getTimeZone();//разность TimeZone системы и пользователя
                                dateUser.setTime(dateUser.getTime()+difTZ*3600000);//установили время для пользователя с учетом разности часовых поясов
                                Event ev= new Event(dateUser, command[2]);
                                modifiedUser.addEvent(ev);
                                users.set(i, modifiedUser);
                                isFound = true;
                                System.out.println("Event added successfully");
                                break;
                            }
                        }
                        if(!isFound)
                        {
                            System.out.println("User does not exist");
                        }
                        break;
                    case "CloneEvent":
                        isFound = false;
                        Event e = null;
                        int nameTo = -1;
                        for(int i = 0; i < users.size(); i++)
                        {
                            if( users.get(i).getName().equals(command[1]) )
                            {
                                e = users.get(i).getEvent(command[2]);
                            }
                            if(users.get(i).getName().equals(command[3]))
                            {
                                nameTo = i;
                            }
                            if(e != null && nameTo != -1)
                            {
                                User modifiedUser = users.get(nameTo);
                                modifiedUser.addEvent(e);
                                users.set(nameTo, modifiedUser);
                                System.out.println("Event clones successfully");
                                isFound = true;
                                break;
                            }
                        }
                        if(!isFound)
                        {
                            System.out.println("Event or user do not exist");
                        }
                        break;
                    case "ShowInfo":
                        isFound = false;
                        for(int i = 0; i < users.size(); i++)
                        {
                            if( users.get(i).getName().equals(command[1]) )
                            {
                                User modifiedUser = users.get(i);
                                System.out.println(modifiedUser.getName() + " "
                                        + modifiedUser.getTimeZone() + " "
                                        + (modifiedUser.isActive()? "true": "false"));
                                for (int j=0;j<modifiedUser.getEventsCount();j++)
                                {
                                    Event ev = modifiedUser.getEventByIndex(j);
                                    System.out.println(" " + j + ": " + ev.getText() + " " + ev.getDate().toString());
                                }
                                isFound = true;
                                break;
                            }
                        }
                        if(!isFound)
                        {
                            System.out.println("User does not exist");
                        }
                        break;
                    case "Exit":
                        isActive = false;
                        break;
                    default:
                        System.out.println("Wrong command!");
                }
            }
            eventNotifier.stop();
        }
        catch (IOException e){}
        catch (ParseException e) {}
    }
}