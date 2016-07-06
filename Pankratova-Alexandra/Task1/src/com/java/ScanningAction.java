package com.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.SimpleTimeZone;

/**
 * Created by Vesdet on 02.07.2016.
 */
public class ScanningAction {

    private static TaskReader taskReader = new TaskReader();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            String action = in.nextLine();
            try {
                parseAction(action);
            } catch (StringIndexOutOfBoundsException ex) {
                System.out.println("Illegal command");
            }
        }
    }

    private static void parseAction(String action) throws StringIndexOutOfBoundsException {
        String name, timeZone, isActive, text, datetime;
        Integer offsetTimeZone;
        //User user;
        String nameOfAction = action.substring(0, action.indexOf("("));

        switch (nameOfAction){
            case "Create":
                name = action.substring(action.indexOf("(")+1, action.indexOf(","));

                action = action.replaceFirst(nameOfAction + "\\(" + name + ", ", "");
                timeZone = action.substring(0, action.indexOf(","));
                offsetTimeZone = new Integer(timeZone.substring(3, timeZone.length()));
                if (offsetTimeZone > 12 || offsetTimeZone < -12) throw new StringIndexOutOfBoundsException();

                isActive = action.substring(action.indexOf(",") + 2, action.indexOf(")"));

                taskReader.create(name, new SimpleTimeZone(offsetTimeZone, timeZone), Boolean.valueOf(isActive));
                break;
            case "Modify":
                name = action.substring(action.indexOf("(")+1, action.indexOf(","));

                action = action.replaceFirst(nameOfAction + "\\(" + name + ", ", "");
                timeZone = action.substring(0, action.indexOf(","));
                offsetTimeZone = new Integer(timeZone.substring(3, timeZone.length()));
                if (offsetTimeZone > 12 || offsetTimeZone < -12) throw new StringIndexOutOfBoundsException();

                isActive = action.substring(action.indexOf(",") + 2, action.indexOf(")"));

                taskReader.modify(name, new SimpleTimeZone(offsetTimeZone, timeZone), Boolean.valueOf(isActive));
                break;
            case "AddEvent":
                name = action.substring(action.indexOf("(")+1, action.indexOf(","));

                action = action.replaceFirst(nameOfAction + "\\(" + name + ", ", "");
                text = action.substring(0, action.indexOf(","));

                datetime = action.substring(action.indexOf(",") + 2, action.indexOf(")"));
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");

                try {
                    //System.out.println(format.parse(datetime));
                    //System.out.println((format.parse(datetime)).getTime());
                    taskReader.addEvent(name, text, format.parse(datetime));
                } catch (ParseException e) {
                    throw new StringIndexOutOfBoundsException();
                }
                break;
            case "RemoveEvent":
                name = action.substring(action.indexOf("(")+1, action.indexOf(","));

                action = action.replaceFirst(nameOfAction + "\\(" + name + ", ", "");
                text = action.substring(0, action.indexOf(")"));

                taskReader.removeEvent(name, text);
                break;
            case "AddRandomTimeEvent":
                name = action.substring(action.indexOf("(")+1, action.indexOf(","));

                action = action.replaceFirst(nameOfAction + "\\(" + name + ", ", "");
                text = action.substring(0, action.indexOf(","));

                action = action.replaceFirst(text + ", ", "");
                String dateFrom = action.substring(0, action.indexOf(","));
                SimpleDateFormat formatFrom = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                String dateTo = action.substring(action.indexOf(",") + 2, action.indexOf(")"));
                SimpleDateFormat formatTo = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                try {
                    //System.out.println(format.parse(datetime));
                    taskReader.addRandomTimeEvent(name, text, formatFrom.parse(dateFrom), formatTo.parse(dateTo));
                } catch (ParseException e) {
                    throw new StringIndexOutOfBoundsException();
                }
                break;
            case "CloneEvent":
                name = action.substring(action.indexOf("(")+1, action.indexOf(","));

                action = action.replaceFirst(nameOfAction + "\\(" + name + ", ", "");
                text = action.substring(0, action.indexOf(","));

                action = action.replaceFirst(text + ", ", "");
                String nameTo = action.substring(0, action.indexOf(")"));

                taskReader.cloneEvent(name, text, nameTo);
                break;
            case "ShowInfo":
                name = action.substring(action.indexOf("(")+1, action.indexOf(")"));
                taskReader.showInfo(name);
                break;
            case "Start":
                System.out.println("Scheduling is started");
                Thread thread = new Thread(new TaskWriter());
                thread.start();
                break;
        }
    }
}
