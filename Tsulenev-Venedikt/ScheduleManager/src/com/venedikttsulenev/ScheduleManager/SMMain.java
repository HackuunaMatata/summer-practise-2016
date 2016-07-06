package com.venedikttsulenev.ScheduleManager;

import java.text.*;
import java.util.*;

public class SMMain {
    private static final SMScheduler scheduler = new SMScheduler();
    public static void main(String args[]) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        Scanner input = new Scanner(System.in);
        String inp, cmd;
        String arg[] = new String[4];
        while (input.hasNextLine()) {
            inp = input.nextLine();
            StringTokenizer st = new StringTokenizer(inp, "(, )");
            if (st.hasMoreTokens()) {
                cmd = st.nextToken();
                int argc = 0;
                while (argc < 4 && st.hasMoreTokens()) {
                    arg[argc++] = st.nextToken();
                }
                synchronized (scheduler) {
                    try {
                        switch (cmd) {
                            case "ShowInfo":
                                if (argc != 1)
                                    throw new SMWrongArgcException(1);
                                System.out.println(scheduler.getInfo(arg[0]));
                                break;
                            case "CloneEvent":
                                if (argc != 3)
                                    throw new SMWrongArgcException(3);
                                System.out.println(scheduler.cloneEvent(arg[0], arg[1], arg[2]));
                                break;
                            case "AddRandomTimeEvent":
                                if (argc != 4)
                                    throw new SMWrongArgcException(4);
                                Date dateFrom = dateFormat.parse(arg[2]);
                                Date dateTo = dateFormat.parse(arg[3]);
                                System.out.println(scheduler.addRandomTimeEvent(arg[0], arg[1], dateFrom, dateTo));
                                break;
                            case "RemoveEvent":
                                if (argc != 2)
                                    throw new SMWrongArgcException(2);
                                System.out.println(scheduler.removeEvent(arg[0], arg[1]));
                                break;
                            case "AddEvent":
                                if (argc != 3)
                                    throw new SMWrongArgcException(3);
                                Date date = dateFormat.parse(arg[2]);
                                System.out.println(scheduler.addEvent(arg[0], arg[1], date));
                                break;
                            case "Modify":
                                if (argc != 3)
                                    throw new SMWrongArgcException(3);
                                else {
                                    TimeZone tz = TimeZone.getTimeZone(arg[1]);
                                    if (tz != null)
                                        System.out.println(scheduler.modifyUser(arg[0], tz, Boolean.parseBoolean(arg[2])));
                                }
                                break;
                            case "Create":
                                if (argc != 3)
                                    throw new SMWrongArgcException(3);
                                else {
                                    TimeZone tz = TimeZone.getTimeZone(arg[1]);
                                    if (tz != null)
                                        System.out.println(scheduler.addUser(arg[0], tz, Boolean.parseBoolean(arg[2])));
                                }
                                break;
                            default:
                                System.out.println("Unknown command \"" + cmd + '\"');
                        }
                    } catch (ParseException ex) {
                        System.out.println("Error: " + ex.getMessage() + ". Expected date format: DD.MM.YYYY-HH24:Mi:SS.");
                    } catch (SMWrongArgcException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
        }
    }
}
