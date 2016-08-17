import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String args[]) {
        LinkedList<User> arrayUsers = new LinkedList<User>();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(System.out, true);
        GregorianCalendar Time = new GregorianCalendar();
        output.println(Time.get(Calendar.DATE) + "." + Time.get(Calendar.MONTH) + "." +
                Time.get(Calendar.YEAR) + "-" + Time.get(Calendar.HOUR) + ":" + Time.get(Calendar.MINUTE) + ":" +
                Time.get(Calendar.SECOND) + " DD.MM.YYYY-HH.MM.SS");
        output.println("now GMT = " + (Time.get(Calendar.ZONE_OFFSET)/1000/60/60));
        String cString = "";
        String[] nameCommand = {
                "create", //0
                "modify", //1
                "addEvent", //2
                "removeEvent", //3
                "addRandomTimeEvent", //4
                "showInfo", //5
                "cloneEvent", //6
                "startScheduling"//7
        };

        while (true) {
            try {
                cString = input.readLine();
                String[] arrayString = cString.split(",|\\(|\\)", 2);
                String command = arrayString[0].replaceAll(" ", "");
                int index = 0;
                for (index = 0; index < nameCommand.length; index++) {
                    if (nameCommand[index].equals(command)) {
                        break;
                    }
                }
                switch (index) {
                    case 0:
                        create(arrayUsers, arrayString[1]);
                        break;
                    case 1:
                        modify(arrayUsers, arrayString[1]);
                        break;
                    case 2:
                        addEvent(arrayUsers, arrayString[1]);
                        break;
                    case 3:
                        removeEvent(arrayUsers, arrayString[1]);
                        break;
                    case 4:
                        addRandomTimeEvent(arrayUsers, arrayString[1]);
                        break;
                    case 5:
                        showInfo(arrayUsers, arrayString[1]);
                        break;
                    case 6:
                        cloneEvent(arrayUsers, arrayString[1]);
                        break;
                    case 7:
                        new Thread(new informThread(arrayUsers)).start();
                        output.println("Вывод событий включен");
                        break;
                    default:
                        output.println("Команду невозможно распознать");
                        break;
                }
            } catch (RuntimeException k) {
                System.out.println(k.getMessage());
            } catch (IOException e) {
                System.out.println("Произошла ошибка считывания строки");
            }
        }
    }
    private static void cloneEvent(LinkedList<User> arrayUsers, String str) throws RuntimeException {
        try {
            String[] arrayStr = str.split(",|\\(|\\)");
            int indexFrom = find(arrayUsers, arrayStr[0]);
            if (indexFrom < 0) throw (new RuntimeException("Пользователь не найден"));
            int indexTo = find(arrayUsers, arrayStr[2]);
            if (indexTo < 0) throw (new RuntimeException("Пользователь не найден"));
            GregorianCalendar temp = new GregorianCalendar();
            for (Event event : arrayUsers.get(indexFrom).getArrayEvents()) {
                if (event.getText().equals(arrayStr[1])) {
                    temp = event.getDate();
                    break;
                }
            }
            arrayUsers.get(indexTo).getArrayEvents().add(new Event(temp, arrayStr[1]));
            System.out.println("Событие скопировано и добавлено");
        }
        catch(RuntimeException e) {
            throw e;
        }
    }
    private static void showInfo(LinkedList<User> arrayUsers, String str)  throws RuntimeException {
        try {
            String []arrayStr = str.split(",|\\(|\\)");
            int index = find(arrayUsers, arrayStr[0]);
            if (index < 0) throw (new RuntimeException("Пользователь не найден"));
            System.out.println(arrayUsers.get(index).getName() + " " + arrayUsers.get(index).getSimpleTimeZone() + " " + arrayUsers.get(index).getStatus());
            boolean flag = false;
            while (!flag) {
                flag = true;
                for (int i = 0; i < (arrayUsers.get(index).getArrayEvents().size()-1); i++) {
                    GregorianCalendar temp1 = new GregorianCalendar();
                    temp1 = arrayUsers.get(index).getArrayEvents().get(i).getDate();
                    GregorianCalendar temp2 = new GregorianCalendar();
                    temp2 = arrayUsers.get(index).getArrayEvents().get(i + 1).getDate();
                    if (temp1.before(temp2)) {
                        flag = false;
                        GregorianCalendar tempTime = arrayUsers.get(index).getArrayEvents().get(i).getDate();
                        String tempText = arrayUsers.get(index).getArrayEvents().get(i).getText();
                        arrayUsers.get(index).getArrayEvents().get(i).setDate(arrayUsers.get(index).getArrayEvents().get(i + 1).getDate());
                        arrayUsers.get(index).getArrayEvents().get(i).setText(arrayUsers.get(index).getArrayEvents().get(i + 1).getText());
                        arrayUsers.get(index).getArrayEvents().get(i + 1).setDate(tempTime);
                        arrayUsers.get(index).getArrayEvents().get(i + 1).setText(tempText);
                    }
                }
            }
            for (Event event : arrayUsers.get(index).getArrayEvents()) {
                GregorianCalendar temp = new GregorianCalendar();
                temp = event.getDate();
                System.out.println(temp.get(Calendar.DATE) + "." + temp.get(Calendar.MONTH) + "." +
                                    temp.get(Calendar.YEAR)+ "-" + temp.get(Calendar.HOUR) + ":" +
                                    temp.get(Calendar.MINUTE) + ":" + temp.get(Calendar.SECOND) + " " + event.getText());
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
    private static void addRandomTimeEvent(LinkedList<User> arrayUsers, String str) throws RuntimeException { //name, text, dateFrom, dateTo
        try {
            String[] arrayStr = str.split(",|\\(|\\)");
            GregorianCalendar time = new GregorianCalendar();
            GregorianCalendar dateFrom = new GregorianCalendar();
            dateFrom = stringToCalendar(arrayStr[2]);
            GregorianCalendar dateTo = new GregorianCalendar();
            dateTo = stringToCalendar(arrayStr[3]);
            time = dateFrom;
            if (dateFrom.get(Calendar.YEAR) != dateTo.get(Calendar.YEAR))
                time.set(Calendar.YEAR, dateTo.get(Calendar.YEAR));
            else if (dateFrom.get(Calendar.MONTH) != dateTo.get(Calendar.MONTH))
                time.set(Calendar.MONTH, dateTo.get(Calendar.MONTH));
            else if (dateFrom.get(Calendar.DATE) != dateTo.get(Calendar.DATE))
                time.set(Calendar.DATE, dateTo.get(Calendar.DATE));
            else if (dateFrom.get(Calendar.HOUR) != dateTo.get(Calendar.HOUR))
                time.set(Calendar.HOUR, dateTo.get(Calendar.HOUR));
            else if (dateFrom.get(Calendar.MINUTE) != dateTo.get(Calendar.MINUTE))
                time.set(Calendar.MINUTE, dateTo.get(Calendar.MINUTE));
            else if (dateFrom.get(Calendar.SECOND) != dateTo.get(Calendar.SECOND))
                time.set(Calendar.SECOND, dateTo.get(Calendar.SECOND));
            int index = find(arrayUsers, arrayStr[0]);
            if (index < 0) throw  (new RuntimeException("Пользователь не найден"));
            arrayUsers.get(index).getArrayEvents().add(new Event(time, arrayStr[1]));
            arrayUsers.get(index).getArrayBoolean().add(new Boolean(false));
            System.out.println("Событие из интервала добавлено");
        }
        catch(RuntimeException e) {
            throw e;
        }


    }
    private static void addEvent (LinkedList<User> arrayUsers, String str) throws RuntimeException {
        try {
            //DD.MM.YYYY-HH24:Mi:SS
            String[] arrayStr = str.split(",|\\(|\\)");
            int index = find(arrayUsers, arrayStr[0]);
            if (index < 0) throw (new RuntimeException("Пользователь не найден"));
            GregorianCalendar time = new GregorianCalendar();
            time = stringToCalendar(arrayStr[2]);
            arrayUsers.get(index).getArrayEvents().add(new Event(time, arrayStr[1]));
            arrayUsers.get(index).getArrayBoolean().add(new Boolean(false));
            System.out.println("Событие добавлено");
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
    private static GregorianCalendar stringToCalendar (String dString) {
        int date = Integer.parseInt(dString.substring(0,2));
        int month = Integer.parseInt(dString.substring(3,5));
        int year = Integer.parseInt(dString.substring(6,10));
        int hour = Integer.parseInt(dString.substring(11,13));
        int minute = Integer.parseInt(dString.substring(14,16));
        int second = Integer.parseInt(dString.substring(17));
        GregorianCalendar temp = new GregorianCalendar(year,month,date,hour,minute,second);
        return temp;
    }
    private static void modify (LinkedList<User> arrayUsers, String str) throws RuntimeException {
        try {
            String[] arrayStr = str.split(",|\\(|\\)");
            Matcher m = Pattern.compile("-?\\d+").matcher(arrayStr[1]);
            int gmt = 0;
            while (m.find()) {
                gmt = Integer.parseInt(arrayStr[1].substring(m.start(), m.end()));
            }
            Matcher p = Pattern.compile("online|offline", Pattern.CASE_INSENSITIVE & Pattern.UNICODE_CASE).matcher(arrayStr[2]);
            String status = "";
            while (p.find()) {
                status = arrayStr[2].substring(p.start(), p.end());
            }
            int index = find(arrayUsers, arrayStr[0]);
            if (index < 0) throw (new RuntimeException("Пользователь не найден"));
            arrayUsers.get(index).setSimpleTimeZone(gmt);
            arrayUsers.get(index).setStatus(status);
            System.out.println("Данные пользователя изменены");
        }
        catch (RuntimeException e) {
            throw e;
        }

    }
    private static void removeEvent(LinkedList<User> arrayUsers, String str) throws RuntimeException {
        try {
            String[] arrayStr = str.split(",|\\(|\\)");
            int index = find(arrayUsers, arrayStr[0]);
            if (index < 0) throw (new RuntimeException(("Пользователь не найден")));
            int size = arrayUsers.get(index).getArrayEvents().size();
            for (int i = 0; i < size; i++) {
                if (arrayUsers.get(index).getArrayEvents().get(i).getText().equals(arrayStr[1])) {
                    arrayUsers.get(index).getArrayEvents().remove(i);
                    break;
                }
            }
            System.out.println("Событие удалено");
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
    private static int find(LinkedList<User> arrayUsers, String name) {
        for (int i=0; i < arrayUsers.size(); i++) {
            User user = arrayUsers.get(i);
            if (user.getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private static void create (LinkedList<User> arrayUsers, String str) {
        String []arrayStr = str.split(",|\\(|\\)");

        Matcher m = Pattern.compile("-?\\d+").matcher(arrayStr[1]);
        int gmt = 0;
        while (m.find()) {
            gmt = Integer.parseInt(arrayStr[1].substring(m.start(), m.end()));
        }
        Matcher p = Pattern.compile("online|offline", Pattern.CASE_INSENSITIVE & Pattern.UNICODE_CASE).matcher(arrayStr[2]);
        String status ="";
        while (p.find()) {
            status = arrayStr[2].substring(p.start(), p.end());
        }
        arrayUsers.add(new User(arrayStr[0],gmt,status));
        System.out.println("Пользователь добавлен");
    }
}
