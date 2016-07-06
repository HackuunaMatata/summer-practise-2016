package com.java;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.SimpleTimeZone;

/**
 * Created by Vesdet on 28.06.2016.
 */
public class User {
    String name;
    SimpleTimeZone timezone;
    boolean isActive;
    LinkedList<Event> events;

    public User(String name, SimpleTimeZone timezone, boolean isActive) {
        this.name = name;
        this.timezone = timezone;
        this.isActive = isActive;
        events = new LinkedList<>();
    }

    public void addEventToList(Event event) {
        Iterator<Event> iterator = events.iterator();
        Event e;
        while (iterator.hasNext()) {
            e = iterator.next();
            if (event.compareTo(e) < 0) {
                events.add(events.indexOf(e), event);
                return;
            }
        }
        events.addLast(event);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(name + " " +  timezone.getID() + " " + isActive + '\n');
        events.forEach(str::append);
        return str.toString();
    }
}
