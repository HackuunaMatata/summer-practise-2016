package practice_task_1;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class UserEvent {

    protected String name;
    protected String text;
    protected Date date;

    public UserEvent() {
    }

    public int AddEvent(String n, String t, String d) throws ParseException {
        boolean flag = false;
        for (int i = 0; i < MainClass.events.size() - 1; i++) {
            if (MainClass.events.get(i).text.equals(t) && MainClass.events.get(i).name.equals(n)) {
                MainClass.events.remove(MainClass.events.size() - 1);
                flag = true;
                break;
            }
        }
        if (!flag) {
            flag = false;
            for (int i = 0; i < MainClass.users.size(); i++) {
                if (MainClass.users.get(i).name.equals(n)) {
                    name = n;
                    text = t;
                    date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-H:mm:ss");
                    format.setTimeZone(TimeZone.getTimeZone(MainClass.users.get(i).timezone.getID()));
                    date = format.parse(d);
                    flag = true;
                    if (!d.equals(format.format(date))) {
                        date = null;
                        MainClass.events.remove(MainClass.events.size() - 1);
                        return 7;
                    } else {
                        return 8;
                    }
                }
            }
            if (!flag) {
                MainClass.events.remove(MainClass.events.size() - 1);
                return 4;
            }
        }
        return 6;
    }

    public int RemoveEvent(String n, String t) {
        boolean flag = false;
        for (int i = 0; i < MainClass.events.size(); i++) {
            if (MainClass.events.get(i).name.equals(n) && MainClass.events.get(i).text.equals(t)) {
                MainClass.events.remove(i);
                flag = true;
                break;
            }
        }
        if (!flag) {
            return 4;
        }
        return 9;
    }

    public int AddRandomTimeEvent(String n, String t, String df, String dt) throws ParseException {
        boolean flag = false;
        for (int i = 0; i < MainClass.events.size() - 1; i++) {
            if (MainClass.events.get(i).text.equals(t)) {
                MainClass.events.remove(MainClass.events.size() - 1);
                flag = true;
                break;
            }
        }
        if (!flag) {
            flag = false;
            for (int i = 0; i < MainClass.users.size(); i++) {
                if (MainClass.users.get(i).name.equals(n)) {
                    name = n;
                    text = t;
                    Date date1 = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-H:mm:ss");
                    format.setTimeZone(TimeZone.getTimeZone(MainClass.users.get(i).timezone.getID()));
                    date1 = format.parse(df);
                    flag = true;
                    if (!df.equals(format.format(date1))) {
                        date1 = null;
                        MainClass.events.remove(MainClass.events.size() - 1);
                        return 7;
                    } else {
                        Date date2 = new Date();
                        date2 = format.parse(dt);
                        if (!dt.equals(format.format(date2))) {
                            MainClass.events.remove(MainClass.events.size() - 1);
                            return 7;
                        } else if (date1.after(date2)) {
                            return 10;
                        } else {
                            Random r = new Random(System.currentTimeMillis());
                            Timestamp a = new Timestamp(date1.getTime());
                            Timestamp b = new Timestamp(date2.getTime());
                            long offset = a.getTime();
                            long end = b.getTime();
                            long diff = end - offset;
                            Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
                            date = new Date();
                            date.setTime(rand.getTime());
                            return 8;
                        }
                    }
                }
            }
            if (!flag) {
                MainClass.events.remove(MainClass.events.size() - 1);
                return 4;
            }
        }
        return 6;
    }

    public int CloneEvent(String n, String t, String nt) throws ParseException {
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        int i, j, k;
        for (i = 0; i < MainClass.users.size(); i++) {
            if (MainClass.users.get(i).name.equals(n)) {
                flag1 = true;
                for (j = 0; j < MainClass.users.size(); j++) {
                    if (MainClass.users.get(j).name.equals(nt)) {
                        flag2 = true;
                        for (k = 0; k < MainClass.events.size(); k++) {
                            if (MainClass.events.get(k).text.equals(t) && MainClass.events.get(k).name.equals(nt)) {
                                flag3 = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        if (!flag1) {
            return 4;
        } else if (!flag2) {
            return 4;
        } else if (flag3) {
            return 6;
        }
        if (MainClass.events.get(i).date.getHours() == 0) {
            MainClass.events.get(i).date.setHours(00);
        }
        String date = MainClass.events.get(i).date.toLocaleString().replace(' ', '-');
        MainClass.events.add(new UserEvent());
        MainClass.events.get(MainClass.events.size() - 1).AddEvent(nt, t, date);
        return 13;
    }
}
