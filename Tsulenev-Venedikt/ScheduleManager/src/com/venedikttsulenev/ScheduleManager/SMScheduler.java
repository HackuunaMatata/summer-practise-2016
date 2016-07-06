package com.venedikttsulenev.ScheduleManager;

import java.util.*;

public class SMScheduler {
    private static final int DEFAULT_USERS_CAPACITY = 256;
    private static final int DEFAULT_EVENTS_CAPACITY = 1024;
    private static final TimeZone LOCAL_TIME_ZONE = TimeZone.getDefault();
    private final HashMap<String, SMUser> users;
    private final ArrayList<SMEvent> sortedEvents;
    private final Random random;
//    private volatile int sortedEventsOffset;
    SMScheduler() {
        users = new HashMap<String, SMUser>(DEFAULT_USERS_CAPACITY);
        sortedEvents = new ArrayList<SMEvent>(DEFAULT_EVENTS_CAPACITY);
        random = new Random();
//        sortedEventsOffset = 0;
        SMSchedulingThread schedThr = new SMSchedulingThread(sortedEvents, 100);
        schedThr.start();
    }
    synchronized public String addUser(String name, TimeZone timeZone, boolean active) {
        users.put(name, new SMUser(name, timeZone, active));
        return "User \"" + name + "\" was created (" + timeZone.getID() + ", " + (active ? "active" : "inactive") + ')';
    }
    synchronized private void addSorted(SMEvent event) {
        int i = 0;
        while (i < sortedEvents.size() && sortedEvents.get(i).compareTo(event) < 0)
            ++i;
        sortedEvents.add(i, event);
    }
    synchronized public String modifyUser(String name, TimeZone timeZone, boolean active) {
        SMUser usr = users.get(name);
        if (usr == null)
            return "There is no user \"" + name + '\"';
        boolean wasActive = usr.getActive();
        usr.modify(timeZone, active);
        Collections.sort(sortedEvents); /* Переупорядочить с новой таймзоной и активностью */
        return "User \"" + name + "\" was modified (" + timeZone.getID() + ", " + active + ')';
    }
    synchronized public String addEvent(String name, String text, Date date) {
        SMUser usr = users.get(name);
        if (usr == null)
            return "There is no user \"" + name + '\"';
        SMEvent newEvent = new SMEvent(usr, text, date);
        usr.addEvent(newEvent);
        this.addSorted(newEvent);
        return "Event \"" + text + "\" at " + date.toString() + " was created for user \"" + name + '\"';
    }
    synchronized public String removeEvent(String name, String text) {
        SMUser usr = users.get(name);
        if (usr == null)
            return "There is no user \"" + name + '\"';
        SMEvent event = usr.getEvent(text);
        if (event == null)
            return "There is no such event \"" + text + "\" for user \"" + name + '\"';
        usr.removeEvent(text);
        sortedEvents.remove(event);
        return "Event \"" + text + "\" removed";
    }
    synchronized public String addRandomTimeEvent(String name, String text, Date dateFrom, Date dateTo) {
        long from = dateFrom.getTime();
        long to = dateTo.getTime();
        long newOne = random.nextLong() % Math.abs(to - from) + from;
        Date date = new Date(newOne);
        this.addEvent(name, text, date);
        return "Event \"" + text + "\" at" + date.toString() + " created for user \"" + name + '\"';
    }
    synchronized public String cloneEvent(String name, String text, String nameTo) {
        SMUser usrFrom = users.get(name);
        if (usrFrom != null) {
            SMUser usrTo = users.get(nameTo);
            if (usrTo != null) {
                SMEvent newEvent = new SMEvent(usrTo, text, usrFrom.getEvent(text).getDate());
                usrTo.addEvent(newEvent);
            }
        }
        return "Event \"" + text + "\" was copied from user \"" + name + "\" to user \"" + nameTo + '\"';
    }
    synchronized public String getInfo(String name) {
        SMUser usr = users.get(name);
        if (usr != null)
            return usr.getInfo();
        else
            return "There is no user with name \"" + name + '\"';
    }
}
