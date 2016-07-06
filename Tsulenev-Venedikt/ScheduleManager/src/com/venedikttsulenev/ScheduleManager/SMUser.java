package com.venedikttsulenev.ScheduleManager;

import java.util.*;

public class SMUser {
    private static final int DEFAULT_EVENTS_CAPACITY = 256;
    private TimeZone timeZone;
    private boolean active;
    private final String name;
    private final HashMap<String, SMEvent> events;
    SMUser(String name, TimeZone timeZone, boolean active) {
        this.name = name;
        this.timeZone = timeZone;
        this.active = active;
        this.events = new HashMap<String, SMEvent>(DEFAULT_EVENTS_CAPACITY);
    }
    public static Date convertDateToTimeZone(Date date, TimeZone oldTZ, TimeZone newTZ) {
        return new Date(date.getTime() + newTZ.getRawOffset() - oldTZ.getRawOffset());
    }
    public String getName() {
        return name;
    }
    public void addEvent(SMEvent event) {
        if (!events.containsKey(event.getText()))
            events.put(event.getText(), event);
    }
    public SMEvent getEvent(String text) {
        return events.get(text);
    }
    public void removeEvent(String text) {
        events.remove(text);
    }
    public boolean getActive() {
        return this.active;
    }
    public void modify(TimeZone timeZone, boolean active) {
        for (SMEvent event : events.values())
            event.setDate(convertDateToTimeZone(event.getDate(), this.timeZone, timeZone));
        this.timeZone = timeZone;
        this.active = active;
    }
    public String getInfo() {
        ArrayList<SMEvent> evs = new ArrayList<SMEvent>(events.values());
        Collections.sort(evs);
        String info;
        info = "User: " + name + '\n'
                + "   Time zone: " + timeZone.getID() + '\n'
                + "   " + (active ? "ACTIVE" : "INACTIVE") + '\n';
        if (events.isEmpty())
            info += "   No events for this user";
        else {
            info += "   Events:\n";
            for (SMEvent event : evs)
                info += "      " + event.getDate().toString() + ":   " + event.getText() + '\n';
        }
        return info;
    }
}
