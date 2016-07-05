package com.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by Vesdet on 28.06.2016.
 */
public class TaskReader {
    public ArrayList<User> users = new ArrayList<>();

    public User create(String name, SimpleTimeZone timeZone, boolean isActive) {
        User user = new User(name, timeZone, isActive);
        users.add(user);
        System.out.println("User is created");
        return user;
    }

    public boolean modify(String name, SimpleTimeZone timeZone, boolean isActive) {
        for (User user : users) {
            if (user.name.equals(name)) {
                user.timezone = timeZone;
                user.isActive = isActive;
                System.out.println("User is changed");
                return true;
            }
        }
        System.out.println("User is not founded");
        return false;
    }

    public Event addEvent(String name, String text, Date datetime) {
        User person = null;
        for (User user : users) {
            if (user.name.equals(name)) {
                person = user;
            }
        }
        if (person == null) {
            System.out.println("User is not founded");
            return null;
        }

        for (Event event : person.events) {
            if (event.text.equals(text)) {
                System.out.println("Event with this text was created earlier");
                return null;
            }
        }
        Event event = new Event(text, datetime);
        person.addEventToList(event);
        TaskWriter.addEventToCalendar(person, event);
        System.out.println("Event is added");
        return event;
    }

    public boolean removeEvent(String name, String text) {
        User person = null;
        for (User user : users) {
            if (user.name.equals(name)) {
                person = user;
            }
        }
        if (person == null) {
            System.out.println("User is not founded");
            return false;
        }

        for (Event event : person.events) {
            if (event.text.equals(text)) {
                person.events.remove(event);
                System.out.println("Event is removed");
                return true;
            }
        }
        System.out.println("Event is not founded");
        return false;
    }

    public boolean cloneEvent(String name, String text, String nameTo) {
        User person = null;
        for (User user : users) {
            if (user.name.equals(name)) {
                person = user;
            }
        }
        if (person == null) {
            System.out.println("User is not founded");
            return false;
        }

        for (Event event : person.events) {
            if (event.text.equals(text)) {
                return addEvent(nameTo, text, event.datetime) != null;
            }
        }
        System.out.println("Event is not founded");
        return false;
    }

    public boolean addRandomTimeEvent(String name, String text, Date dateFrom, Date dateTo) {
        return addEvent(name, text,
                new Date((long) (dateFrom.getTime() + Math.random() * (dateTo.getTime() - dateFrom.getTime())))) != null;
    }

    public void showInfo(String name) {
        for (User user : users) {
            if (user.name.equals(name)) {
                System.out.println(user);
                return;
            }
        }
        System.out.println("User is not founded");
    }

}
