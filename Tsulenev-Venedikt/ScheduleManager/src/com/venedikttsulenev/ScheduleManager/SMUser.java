package com.venedikttsulenev.ScheduleManager;

import java.util.*;

public class SMUser {
    private static final int DEFAULT_EVENTS_CAPACITY = 256;
    private TimeZone timeZone;
    private boolean active;
    private String name;
    private HashMap<String, SMEvent> events;
    SMUser(String name, TimeZone timeZone, boolean active) {
        this.name = name;
        this.timeZone = timeZone;
        this.active = active;
        this.events = new HashMap<String, SMEvent>(DEFAULT_EVENTS_CAPACITY);
    }
    public int compareTo(SMUser usr) {
        return this.name.compareTo(usr.getName());
    }
    public String getName() {
        return name;
    }
    public Collection<SMEvent> getEvents() {
        return events.values();
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
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    public boolean getActive() {
        return this.active;
    }
    public void modify(TimeZone timeZone, boolean active) {
        for (SMEvent event : events.values()) {
            Date newDate = new Date(event.getDate().getTime() + timeZone.getRawOffset() - this.timeZone.getRawOffset());
            event.setDate(newDate);
        }
        this.timeZone = timeZone;
        this.active = active;
    }
    public String getInfo() {
        String info;
        info = "User: " + name + '\n'
                + "   Time zone: " + timeZone.getID() + '\n'
                + "   " + (active ? "ACTIVE" : "INACTIVE") + '\n';
        if (events.isEmpty())
            info += "   No events for this user";
        else {
            info += "   Events:\n";
            for (SMEvent event : events.values())
                info += event.getDate().toString() + ":   " + event.getText() + '\n';
        }
        return info;
    }
}
