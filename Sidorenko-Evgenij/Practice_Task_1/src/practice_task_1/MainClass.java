package practice_task_1;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class MainClass {

    static ArrayList<User> users = new ArrayList<User>();
    static ArrayList<UserEvent> events = new ArrayList<UserEvent>();
    static Scanner s = new Scanner(System.in);
    static ConsolePrint print = new ConsolePrint();

    public static void main(String[] args) throws ParseException {

        
        Handler x = new Handler();
        Thread cmd = new Thread(x);
        cmd.start();
        String cmds = "";
        while (!cmds.equals("stop")) {
            cmds = s.nextLine();
            if (cmds != null) {
                x.ParseCmd(cmds);
            }
        }
        x.keepRunning = false;
        cmd.interrupt();

//        Create(a, Asia/Krasnoyarsk, active)
//        AddEvent(a, Hello World!, 29.06.2016-22:18:00)
//        Create(b, America/Los_Angeles, passive)
//        Create(c, America/Los_Angeles, passive)
//        AddRandomTimeEvent(a, 1, 28.12.2020-12:35:22, 28.12.2030-14:35:24)
//        AddRandomTimeEvent(b, hello2, 28.12.1994-14:35:22, 28.12.2000-14:35:24)
//        AddRandomTimeEvent(b, hello3, 28.12.1994-14:35:22, 28.12.2032-14:35:24)
//        CloneEvent(a, Hello World!, b)
//        CloneEvent(b, hello3, c)
//        CloneEvent(b, hello1, c)
//        CloneEvent(b, hello2, a)
//        ShowInfo(a);
//        ShowInfo(b);
//        ShowInfo(c);
    }

}
