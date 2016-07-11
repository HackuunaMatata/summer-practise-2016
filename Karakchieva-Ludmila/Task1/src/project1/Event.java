package project1;


import java.util.Date;

public class Event {
    Date dateTime_;
    String text_;

    public Event() {
    }

    public Event(String text, Date datetime) {
        text_ = text;
        dateTime_ = new Date(datetime.getTime());
    }

    public String getText() {
        return text_;
    }

    public Date getDate() {
        return dateTime_;
    }

    public void Show() {
        System.out.println("DateTime: " + dateTime_ + " Text: " + text_);
    }
}

