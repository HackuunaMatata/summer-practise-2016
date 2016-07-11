package project1;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SheduleCoordinator extends Thread {

    public SheduleCoordinator() {
        super();
        setDaemon(true);
    }

    ;

    @Override
    public void run() {

        Date currentTime = null;

        while (true) {
            currentTime = new Date();
            for (int i = 0; i < ConsoleCommands.users_.size(); i++)
                if (ConsoleCommands.users_.get(i).status_.equals(Status.ACTIVE))
                    for (int j = 0; j < ConsoleCommands.users_.get(i).events_.size(); j++) {
                        if ((ConsoleCommands.users_.get(i).events_.get(j).dateTime_.before(new Date(currentTime.getTime() + 250)) &&
                                (ConsoleCommands.users_.get(i).events_.get(j).dateTime_.after(new Date(currentTime.getTime() - 250))))) {
                            System.out.println("------NEW EVENT------");
                            System.out.println("Time: " + currentTime.toString());
                            System.out.println("User: " + ConsoleCommands.users_.get(i).name_);
                            System.out.println("Text: " + ConsoleCommands.users_.get(i).events_.get(j).text_);
                            System.out.println("---------------------");
                            //System.out.print("command>");
                        }
                        if (ConsoleCommands.users_.get(i).events_.get(j).dateTime_.before(new Date(currentTime.getTime()))) {
                            ConsoleCommands.users_.get(i).RemoveEventUser(ConsoleCommands.users_.get(i).events_.get(j).getText());
                            Collections.sort(ConsoleCommands.users_, new Comparator<User>() {
                                @Override
                                public int compare(User o1, User o2) {
                                    return o1.name_.compareTo(o2.name_);
                                }
                            });
                        }
                    }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
