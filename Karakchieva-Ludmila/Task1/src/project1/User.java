package project1;

import java.util.*;


enum Status {
    ACTIVE,
    PASSIVE
}

public class User {
    ArrayList<Event> events_;
    int count_;
    String name_;
    TimeZone timeZone_;
    Status status_;

    public User() {
        name_ = "";
        events_ = new ArrayList<Event>();
        timeZone_ = null;
        status_ = Status.ACTIVE;
    }

    public User(String name, TimeZone timezone, Status status) {
        name_ = name;
        events_ = new ArrayList<Event>();
        timeZone_ = timezone;
        status_ = status;
    }

    public String getName() {
        return name_;
    }

    public void ModifyUser(TimeZone timezone, Status status) {
        timeZone_ = timezone;
        status_ = status;
    }

    public void AddEventUser(String text, Date date) {
        boolean unique = isUnique(text);
        if (unique == true) {
            System.out.println("Text isn't unique");
        } else {
            TimeZone timezoneSystem = TimeZone.getDefault();
            Event event = new Event(text, new Date(date.getTime() + (timezoneSystem.getRawOffset() - timeZone_.getRawOffset())));
            events_.add(event);
            Collections.sort(events_, new Comparator<Event>() {
                @Override
                public int compare(Event o1, Event o2) {
                    return o1.getText().compareTo(o2.getText());
                }
            });
            Collections.sort(events_, new Comparator<Event>() {
                @Override
                public int compare(Event o1, Event o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            count_++;
        }
    }

    public void RemoveEventUser(String text) {
        int index = 0;
        boolean flag = false;
        while (index < count_) {
            if (events_.get(index).getText().equals(text)) {
                events_.remove(index);
                count_--;
                index = count_;
                flag = true;
            }
            index++;
        }
        if (flag == false) {
            System.out.println("Unknown event");
        }
    }

    public Date getDateEventUser(String text) {
        int index = 0;
        boolean flag = false;
        Date date = new Date();
        while (index < count_) {
            if (events_.get(index).getText().equals(text)) {
                date = events_.get(index).getDate();
                index = count_;
                flag = true;
            }
            index++;
        }
        if (flag == false) {
            System.out.println("Unknown event");
        }
        return date;
    }

    public void ShowInfoUser() {
        int index = 0;
        System.out.println("INFORMATION ABOUT USER");
        System.out.println("Name: " + name_);
        System.out.println("Timezone: " + timeZone_.getID());
        System.out.println("Status: " + status_);
        System.out.println("Events:");
        while (index < count_) {
            events_.get(index).Show();
            index++;
        }
    }

    public boolean isUnique(String text) {
        int index = 0;
        boolean flag = false;
        while (index < count_) {
            if (events_.get(index).getText().equals(text)) {
                index = count_;
                flag = true;
            }
            index++;
        }
        return flag;
    }
}
