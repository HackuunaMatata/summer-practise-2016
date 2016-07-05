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
    private Date date;   // Date event (for server)


    /**
     * DISC: Create object for event.
     *
     * @param text Event text
     * @param date Event date
     */
    public Event (String text, Date date)
    {
        this.text = (text != null) ? text : "null-text";
        if (date == null)
        {
            System.err.println("Date is null. Set current date.");
            this.date = new Date();
        }
        else
        {
            this.date = date;
        }
    }


    /**
     * DISC: return event date.
     *
     * @return Date of event
     */
    public Date getDate ()
    {
        return date;
    }


    /**
     * DISC: return event text.
     *
     * @return Text of event
     */
    public String getText()
    {
        return text;
    }


    /**
     * DISC: return clone for current event.
     *
     * @return Clone for current event
     */
    public Event clone()
    {
        return new Event(this.text, this.date);
    }
}
