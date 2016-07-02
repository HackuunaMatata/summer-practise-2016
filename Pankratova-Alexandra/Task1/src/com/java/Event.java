package com.java;

import java.util.Date;

/**
 * Created by Vesdet on 28.06.2016.
 */
public class Event {
    public String text;
    public Date datetime;

    public Event(String text, Date datetime) {
        this.text = text;
        this.datetime = datetime;
    }
}
