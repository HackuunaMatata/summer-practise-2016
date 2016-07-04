package sheduler;

import java.util.Date;

/**
 * Class for description user's event. <br>
 * Every event has unique text message with description
 * of event's reason and date when text message needs
 * to be displayed.
 */
public class Event {
    private String text; // Description text of event
    private Date date;   // Date event

    public Event (String text, long date) {
        this.text = (text != null) ? text : "null-text";
        this.date = new Date(date);
    }
}
