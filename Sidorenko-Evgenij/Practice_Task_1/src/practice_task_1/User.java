package practice_task_1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class User {

    protected String name;
    protected TimeZone timezone;
    protected boolean status;

    User() {
    }

    public int Create(String n, String t, String s) {
        boolean flag = false;
        for (int i = 0; i < MainClass.users.size() - 1; i++) {
            if (MainClass.users.get(i).name.equals(n)) {
                MainClass.users.remove(MainClass.users.size() - 1);
                flag = true;
                break;
            }
        }
        if (flag == false) {
            name = n;
            timezone = TimeZone.getTimeZone(t);
            if (s.toLowerCase().equals("active")) {
                status = true;
            } else if (s.toLowerCase().equals("passive")) {
                status = false;
            } else {
                return 1;
            }
            return 2;
        }
        return 0;
    }

    public int Modify(String n, String t, String s) throws ParseException {
        boolean flag = false;
        if (!MainClass.users.isEmpty()) {
            for (int i = 0; i < MainClass.users.size(); i++) {
                if (MainClass.users.get(i).name.equals(n)) {
                    if (s.toLowerCase().equals("active")) {
                        MainClass.users.get(i).status = true;
                    } else if (s.toLowerCase().equals("passive")) {
                        MainClass.users.get(i).status = false;
                    } else {
                        return 1;
                    }
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-H:mm:ss");
                    MainClass.users.get(i).timezone = TimeZone.getTimeZone(t);
                    format.setTimeZone(TimeZone.getTimeZone(MainClass.users.get(i).timezone.getID()));
                    for (int j = 0; j < MainClass.events.size(); j++) {
                        if (MainClass.users.get(i).name.equals(MainClass.events.get(j).name)) {
                            date = format.parse(MainClass.events.get(j).date.toLocaleString().replace(' ', '-'));
                            MainClass.events.get(j).date = date;
                        }
                    }
                    flag = true;
                    break;
                }
            }
        } else {
            return 4;
        }
        if (!flag) {
            return 4;
        }
        return 3;
    }
}
