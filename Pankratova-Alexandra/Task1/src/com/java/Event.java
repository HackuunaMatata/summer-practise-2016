package com.java;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vesdet on 28.06.2016.
 */
public class Event implements Comparable<Event> {
    public String text;
    public Date datetime;

    public Event(String text, Date datetime) {
        this.text = text;
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(datetime) + " - " + text + '\n';
    }

    @Override
    public int compareTo(Event event) {

        if (this.datetime.getTime() > event.datetime.getTime()) return 1;
        if (this.datetime.getTime() < event.datetime.getTime()) return -1;

        if (this.text.compareTo(event.text) > 0) return 1;
        if (this.text.compareTo(event.text) < 0) return -1;

        return 0;
    }
}
