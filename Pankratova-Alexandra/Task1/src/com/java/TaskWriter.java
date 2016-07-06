package com.java;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Vesdet on 05.07.2016.
 */
public class TaskWriter implements Runnable {
    static LinkedList<Record> calendar = new LinkedList<>();

    public static void addEventToCalendar(User user, Event event){
        if(event.datetime.getTime() < System.currentTimeMillis()) return;
        Record record = new Record(user, event);

        Iterator<Record> iterator = calendar.iterator();
        Record e;
        while (iterator.hasNext()) {
            e = iterator.next();
            if (record.event.datetime.getTime() < e.event.datetime.getTime()) {
                calendar.add(calendar.indexOf(e), record);
                return;
            }
            if (record.event.datetime.getTime() == e.event.datetime.getTime()) {
                if (record.user.name.compareTo(e.user.name) < 0) {
                    calendar.add(calendar.indexOf(e), record);
                    return;
                }
                if (record.user.name.equals(e.user.name)) {
                    if (record.event.text.compareTo(e.event.text) < 0) {
                        calendar.add(calendar.indexOf(e), record);
                        return;
                    }
                }
            }
        }
        calendar.addLast(record);
    }


    @Override
    public void run() {
        Iterator<Record> iterator = calendar.iterator();
        Record e;
        while (iterator.hasNext()) {
            e = iterator.next();
            if (e.event.datetime.getTime() < System.currentTimeMillis()) calendar.remove(e);
            else break;
        }
        while (true) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            if (calendar.isEmpty()) continue;
            e = calendar.get(0);
            if (e.event == null) {
                calendar.remove(e);
                continue;
            }
            if (e.event.datetime.getTime() / 60_000 == System.currentTimeMillis() / 60_000) {
                if (!e.user.isActive) {
                    calendar.remove(e);
                    continue;
                }
                System.out.println(e.user.name + " - " + e.event);
                calendar.remove(e);
            }
        }
    }
}
