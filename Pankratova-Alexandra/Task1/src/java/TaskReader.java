package java;

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
        return user;
    }

    public boolean modify(String name, SimpleTimeZone timeZone, boolean isActive) {
        for (User user : users) {
            if (user.name.equals(name)) {
                user.timezone = timeZone;
                user.isActive = isActive;
                return true;
            }
        }
        return false;
    }

    public boolean addEvent(String name, String text, Date datetime) {
        User person = null;
        for (User user : users) {
            if (user.name.equals(name)) {
                person = user;
            }
        }
        if (person == null) return false;

        for (Event event : person.events) {
            if (event.text.equals(text)) {
                return false;
            }
        }
        person.events.add(new Event(text, datetime));
        return true;
    }

    public boolean removeEvent(String name, String text) {
        User person = null;
        for (User user : users) {
            if (user.name.equals(name)) {
                person = user;
            }
        }
        if (person == null) return false;

        for (Event event : person.events) {
            if (event.text.equals(text)) {
                person.events.remove(event);
                return true;
            }
        }
        return false;
    }

    public boolean cloneEvent(String name, String text, String nameTo) {
        User person = null;
        for (User user : users) {
            if (user.name.equals(name)) {
                person = user;
            }
        }
        if (person == null) return false;

        for (Event event : person.events) {
            if (event.text.equals(text)) {
                return addEvent(nameTo, text, event.datetime);
            }
        }
        return false;
    }

    public boolean addRandomTimeEvent(String name, String text, Date dateFrom, Date dateTo) {
        return addEvent(name, text,
                new Date((long) (dateFrom.getTime() + Math.random() * (dateTo.getTime() - dateFrom.getTime()))));
    }
}
