package com.venedikttsulenev.ScheduleManager;

import java.text.*;
import java.util.*;

public class SMMain {
    public static void main(String args[]) {
        SMScheduler scheduler = new SMScheduler();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        Scanner input = new Scanner(System.in);
        String inp;
        String cmd, arg1, arg2, arg3, arg4;
        while (input.hasNextLine()) {
            inp = input.nextLine();
            StringTokenizer st = new StringTokenizer(inp, "(, )");
            cmd = st.nextToken();
            arg1 = arg2 = arg3 = arg4 = null;
            if (st.hasMoreTokens())
                arg1 = st.nextToken();
            if (st.hasMoreTokens())
                arg2 = st.nextToken();
            if (st.hasMoreTokens())
                arg3 = st.nextToken();
            if (st.hasMoreTokens())
                arg4 = st.nextToken();
            if (cmd.equals("ShowInfo")) {
                if (arg1 != null)
                    System.out.println(scheduler.getInfo(arg1));
            }
            else if (cmd.equals("CloneEvent")) {
                if (arg1 != null && arg2 != null && arg3 != null)
                    System.out.println(scheduler.cloneEvent(arg1, arg2, arg3));
            }
            else if (cmd.equals("AddRandomTimeEvent")) {
                if (arg1 != null && arg2 != null && arg3 != null && arg4 != null) {
                    try {
                        Date dateFrom = dateFormat.parse(arg3);
                        Date dateTo = dateFormat.parse(arg4);
                        System.out.println(scheduler.addRandomTimeEvent(arg1, arg2, dateFrom, dateTo));
                    }
                    catch (ParseException ex) {
                        System.out.println("Could not parse date (should be: DD.MM.YYYY-HH24:Mi:SS)");
                    }
                }
            }
            else if (cmd.equals("RemoveEvent")) {
                if (arg1 != null && arg2 != null)
                    System.out.println(scheduler.removeEvent(arg1, arg2));
            }
            else if (cmd.equals("AddEvent")) {
                if (arg1 != null && arg2 != null && arg3 != null) {
                    try {
                        Date date = dateFormat.parse(arg3);
                        System.out.println(scheduler.addEvent(arg1, arg2, date));
                    }
                    catch (ParseException ex) {
                        System.out.println("Could not parse date (should be: DD.MM.YYYY-HH24:Mi:SS)");
                    }
                }
            }
            else if (cmd.equals("Modify")) {
                if (arg1 != null && arg2 != null && arg3 != null) {
                    TimeZone tz = TimeZone.getTimeZone(arg2);
                    if (tz != null)
                        System.out.println(scheduler.modifyUser(arg1, tz, Boolean.parseBoolean(arg3)));
                }
            }
            else if (cmd.equals("Create")) {
                if (arg1 != null && arg2 != null && arg3 != null) {
                    TimeZone tz = TimeZone.getTimeZone(arg2);
                    if (tz != null)
                        System.out.println(scheduler.addUser(arg1, tz, Boolean.parseBoolean(arg3)));
                }
            }
        }
    }
}
