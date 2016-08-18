import java.util.GregorianCalendar;

public class Event {
    GregorianCalendar date_;
    String text_;
    Event(GregorianCalendar date, String text) {
        date_ = date;
        text_ = text;
    }
    String getText() {
        return text_;
    }
    GregorianCalendar getDate() {
        return date_;
    }
    synchronized void setDate (GregorianCalendar date) {
        date_ = date;
    }
    synchronized void setText (String text) {
        text_ = text;
    }
}
