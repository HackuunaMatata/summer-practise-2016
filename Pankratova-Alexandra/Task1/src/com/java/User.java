package com.java;

import java.util.ArrayList;
import java.util.SimpleTimeZone;

/**
 * Created by Vesdet on 28.06.2016.
 */
public class User {
    public String name;
    public SimpleTimeZone timezone;
    public boolean isActive;
    public ArrayList<Event> events;

    public User(String name, SimpleTimeZone timezone, boolean isActive) {
        this.name = name;
        this.timezone = timezone;
        this.isActive = isActive;
        events = new ArrayList<>();
    }
}
