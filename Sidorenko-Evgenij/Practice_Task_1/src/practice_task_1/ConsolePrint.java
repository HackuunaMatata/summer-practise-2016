package practice_task_1;

import java.util.Date;

public class ConsolePrint extends Handler {

    public ConsolePrint() {
    }

    public void print(int i) {
        if (i >= 0 && i < messages.length) {
            System.out.println(messages[i]);
        }
    }

    public void printevent(int i) {
        Date date = new Date();
        System.out.println("[EVENT FOR USER] " + MainClass.events.get(i).name + "; TITLE: " + MainClass.events.get(i).text + "; TIME: " + date.toLocaleString());
        date = null;
    }

    public int ShowInfo(String n) {
        boolean flag = false;
        boolean flag2 = false;
        Date d;
        String t;
        String name;
        if (!MainClass.users.isEmpty()) {
            String status;
            for (int i = 0; i < MainClass.users.size(); i++) {
                if (MainClass.users.get(i).name.equals(n)) {
                    flag2 = true;
                    System.out.print(MainClass.users.get(i).name + " " + MainClass.users.get(i).timezone.getID() + " ");
                    if (MainClass.users.get(i).status) {
                        System.out.println("Active");
                    } else {
                        System.out.println("Passive");
                    }
                    for (int j = 0; j < MainClass.events.size(); j++) {
                        if (MainClass.users.get(i).name.equals(MainClass.events.get(j).name)) {
                            flag = true;
                            System.out.println(MainClass.events.get(j).date + " " + MainClass.events.get(j).text);
                        }
                    }
                    if (!flag) {
                        return 5;
                    }
                }
            }
            if (!flag2) {
                return 4;
            }
        } else {
            return 4;
        }
        return 503;
    }
}
