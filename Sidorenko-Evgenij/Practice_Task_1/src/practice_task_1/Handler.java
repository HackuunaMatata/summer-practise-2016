package practice_task_1;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler extends Thread {

    private String[] cmd = {"Create", //0
        "Modify", //1
        "ShowInfo", //2
        "AddEvent", //3
        "RemoveEvent", //4
        "AddRandomTimeEvent", //5
        "CloneEvent"};                                          //6

    protected String[] messages = {"[USER ALREADY EXISTS]", //0
        "[WRONG STATUS]", //1
        "[USER CREATED]", //2
        "[USER MODIFYED]", //3
        "[NO SUCH USER]", //4    
        "[NO EVENT(S) FOR CURRENT USER]", //5
        "[TEXT IS NOT UNIQUE]", //6
        "[WRONG DATE FORMAT]", //7
        "[EVENT CREATED]", //8
        "[EVENT REMOVED]", //9
        "[BAD DATE SEQUENCE]", //10 
        "[EXIT]", //11
        "[NO SUCH COMMAND]", //12
        "[EVENT CLONED]"};                                  //13
    volatile boolean keepRunning = true;
    String command;
    Date date;

    public Handler() {
    }

    public void run() {
        while (keepRunning) {
            try {
                Sort();
                int i = CheckOnEvents();
                if (i >= 0) {
                    MainClass.print.printevent(i);
                    MainClass.events.get(i).RemoveEvent(MainClass.events.get(i).name, MainClass.events.get(i).text);
                }
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
        }
        MainClass.print.print(11);
    }

    public void ParseCmd(String c) throws ParseException {
        String[] params;
        int msg;
        boolean flag = false;
        for (int i = 0; i < cmd.length; i++) {
            if (c.contains(cmd[i])) {
                flag = true;
                params = c.split("[(),]" + "|" + cmd[i]);
                for (int j = 0; j < params.length; j++) {
                    params[j] = params[j].trim();
                }
                switch (i) {
                    case 0:
                        MainClass.users.add(new User());
                        msg = MainClass.users.get(MainClass.users.size() - 1).Create(params[2], params[3], params[4]);
                        MainClass.print.print(msg);
                        break;
                    case 1:
                        if (MainClass.users.size() - 1 >= 0) {
                            msg = MainClass.users.get(MainClass.users.size() - 1).Modify(params[2], params[3], params[4]);
                            MainClass.print.print(msg);
                        } else {
                            MainClass.print.print(4);
                        }
                        break;
                    case 2:
                        if (MainClass.users.size() - 1 >= 0) {
                            msg = MainClass.print.ShowInfo(params[2]);
                            //msg = MainClass.users.get(MainClass.users.size() - 1).ShowInfo(params[2]);
                            MainClass.print.print(msg);
                        } else {
                            MainClass.print.print(4);
                        }
                        break;
                    case 3:
                        MainClass.events.add(new UserEvent());
                        msg = MainClass.events.get(MainClass.events.size() - 1).AddEvent(params[2], params[3], params[4]);
                        MainClass.print.print(msg);
                        break;
                    case 4:
                        if (MainClass.events.size() - 1 >= 0) {
                            msg = MainClass.events.get(MainClass.events.size() - 1).RemoveEvent(params[2], params[3]);
                            MainClass.print.print(msg);
                        } else {
                            MainClass.print.print(4);
                        }
                        break;
                    case 5:
                        MainClass.events.add(new UserEvent());
                        msg = MainClass.events.get(MainClass.events.size() - 1).AddRandomTimeEvent(params[2], params[3], params[4], params[5]);
                        MainClass.print.print(msg);
                        break;
                    case 6:
                        if (MainClass.events.size() - 1 >= 0) {
                            msg = MainClass.events.get(MainClass.events.size() - 1).CloneEvent(params[2], params[3], params[4]);
                            MainClass.print.print(msg);
                        } else {
                            MainClass.print.print(4);
                        }
                        break;
                    default:
                        MainClass.print.print(12);
                        break;
                }
            }
        }
        if (!flag) {
            MainClass.print.print(12);
        }
    }

    public int CheckOnEvents() {
        date = new Date();
        for (int i = 0; i < MainClass.events.size(); i++) {
            if (date.after(MainClass.events.get(i).date) && MainClass.users.get(i).status) {
                //System.out.println("[EVENT FOR USER " + MainClass.events.get(i).name + "; TITLE: " + MainClass.events.get(i).text + "; TIME: " + date.toLocaleString());
                return i;
            } else if (date.after(MainClass.events.get(i).date) && !MainClass.users.get(i).status) {
                MainClass.events.get(i).RemoveEvent(MainClass.events.get(i).name, MainClass.events.get(i).text);
            }
        }
        date = null;
        return -1;
    }

    private void Sort() {
        Date d;
        String t;
        String name;
        for (int i = 0; i < MainClass.events.size() - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < MainClass.events.size() - i - 1; j++) {
                //System.out.println(MainClass.events.get(j).date.after(MainClass.events.get(j + 1).date));
                if (MainClass.events.get(j).date.equals(MainClass.events.get(j + 1).date)) {
                    if (MainClass.events.get(j).text.compareTo(MainClass.events.get(j + 1).text) > 0) {
                        d = MainClass.events.get(j).date;
                        t = MainClass.events.get(j).text;
                        name = MainClass.events.get(j).name;
                        MainClass.events.get(j).date = MainClass.events.get(j + 1).date;
                        MainClass.events.get(j).name = MainClass.events.get(j + 1).name;
                        MainClass.events.get(j).text = MainClass.events.get(j + 1).text;
                        MainClass.events.get(j + 1).date = d;
                        MainClass.events.get(j + 1).name = name;
                        MainClass.events.get(j + 1).text = t;
                        swapped = true;
                    }
                } else if (MainClass.events.get(j).date.after(MainClass.events.get(j + 1).date)) {
                    d = MainClass.events.get(j).date;
                    t = MainClass.events.get(j).text;
                    name = MainClass.events.get(j).name;
                    MainClass.events.get(j).date = MainClass.events.get(j + 1).date;
                    MainClass.events.get(j).name = MainClass.events.get(j + 1).name;
                    MainClass.events.get(j).text = MainClass.events.get(j + 1).text;
                    MainClass.events.get(j + 1).date = d;
                    MainClass.events.get(j + 1).name = name;
                    MainClass.events.get(j + 1).text = t;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

}
