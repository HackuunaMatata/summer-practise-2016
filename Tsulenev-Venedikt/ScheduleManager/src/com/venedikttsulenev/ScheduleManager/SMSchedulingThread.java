package com.venedikttsulenev.ScheduleManager;

import java.util.ArrayList;
import java.util.Date;

public class SMSchedulingThread extends Thread {
    private final ArrayList<SMEvent> eventList;
    private int checkInterval;
    private boolean scheduling;
    SMSchedulingThread(ArrayList<SMEvent> evList, int checkIntervalMillis) {
        this.eventList = evList;
        this.checkInterval = checkIntervalMillis;
        this.scheduling = true;
    }
//    public void pauseScheduling() {
//        scheduling = false;
//    }
//    public void resumeScheduling() {
//        scheduling = true;
//    }
    public void run() {
        long time = (new Date()).getTime();
        long prevtime = time - checkInterval;
        while (scheduling) {
            synchronized (eventList) {
                if (!eventList.isEmpty()) {
                    int i = 0;
                    SMEvent event = eventList.get(0);
                    while (i < eventList.size()
                            && event.getDate().getTime() < time
                            && event.getDate().getTime() > prevtime
                            && event.getUser().getActive())
                    {
                        System.out.println(
                                "EVENT: \n"
                                        + "   " + event.getDate().toString() + '\n'
                                        + "   " + event.getText() + '\n'
                        );
                        ++i;
                        if (i < eventList.size())
                            event = eventList.get(i);
                    }
                }
            }
            try {
                Thread.sleep(checkInterval);
            } catch (InterruptedException e) {
                System.out.println("Error: scheduling thread was interrupted.");
            }
            prevtime = time;
            time += checkInterval;
        }
    }
}
