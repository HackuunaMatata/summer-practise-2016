import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class informThread implements Runnable {
    LinkedList<User> arrayUsers_;
    informThread (LinkedList<User> arrayUsers) {
        arrayUsers_ = arrayUsers;
    }
    @Override
    public void run() {
        while (true) {
            GregorianCalendar nowTime = new GregorianCalendar();
            int timeZoneNow = nowTime.get(Calendar.ZONE_OFFSET) / 1000 / 60 / 60;
            LinkedList<String> arrayTimeEvent = new LinkedList<String>();
            for (int i = 0; i < arrayUsers_.size(); i++) {
                User user = arrayUsers_.get(i);
                if (user.getStatus().equals("online")) {
                    int different = timeZoneNow - user.getSimpleTimeZone();
                    for (int j = 0; j < user.getArrayEvents().size(); j++) {
                        GregorianCalendar currentTime = new GregorianCalendar(user.getArrayEvents().get(j).getDate().get(Calendar.YEAR),
                                user.getArrayEvents().get(j).getDate().get(Calendar.MONTH), user.getArrayEvents().get(j).getDate().get(Calendar.DATE),
                                user.getArrayEvents().get(j).getDate().get(Calendar.HOUR), user.getArrayEvents().get(j).getDate().get(Calendar.MINUTE),
                                user.getArrayEvents().get(j).getDate().get(Calendar.SECOND));
                        currentTime.add(Calendar.HOUR, different);
                        try {
                            if ((user.getArrayBoolean().get(j) == false) && (equal(currentTime, nowTime))) {
                                user.getArrayBoolean().set(j, true);
                                arrayTimeEvent.add((user.getName() + " " + user.getArrayEvents().get(j).getText()));
                            }
                        }
                        catch (IndexOutOfBoundsException e) {
                        }
                    }
                }
            }
            if (!arrayTimeEvent.isEmpty()) {
                boolean flag = false;
                while (!flag) {
                    flag = true;
                    for (int l = 0; l < (arrayTimeEvent.size() - 1); l++) {
                        if (arrayTimeEvent.get(l).compareToIgnoreCase(arrayTimeEvent.get(l + 1)) == 1) {
                            flag = false;
                            String tempString = arrayTimeEvent.get(l);
                            arrayTimeEvent.set(l, arrayTimeEvent.get(l + 1));
                            arrayTimeEvent.set(l + 1, tempString);
                        }
                    }
                }
                String str = nowTime.get(Calendar.DATE) + "." + nowTime.get(Calendar.MONTH) + "." +
                        nowTime.get(Calendar.YEAR) + "-" + nowTime.get(Calendar.HOUR) + ":" + nowTime.get(Calendar.MINUTE) + ":" +
                        nowTime.get(Calendar.SECOND);
                for (String x : arrayTimeEvent) {
                    System.out.println(str + " " + x);
                }
            }
        }
    }
    public boolean equal(GregorianCalendar one, GregorianCalendar two) {
        if ((one.get(Calendar.DATE) == two.get(Calendar.DATE)) &&
            (one.get(Calendar.MONTH) == two.get(Calendar.MONTH)) &&
            (one.get(Calendar.YEAR) == two.get(Calendar.YEAR)) &&
            (one.get(Calendar.HOUR) == two.get(Calendar.HOUR)) &&
            (one.get(Calendar.MINUTE) == two.get(Calendar.MINUTE)) &&
            (one.get(Calendar.SECOND) == two.get(Calendar.SECOND)))
            return true;
        else return false;

    }
}
