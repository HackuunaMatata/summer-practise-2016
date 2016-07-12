package task.one;
import java.util.*;
public class User {
    private String name;
    private int timeZone;
    private boolean isActive;

    private static ArrayList<Event> events = new ArrayList<Event>();

    public User(String name, int timeZone, boolean isActive) {
        this.name = name;
        this.timeZone = timeZone;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeZone() {
        return timeZone;
    }



    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addEvent(Event event){
        events.add(event);
        sortEvents();
    }

    public boolean removeEvent(String text){
        boolean isRemoved = false;
        for(int i=0;i<events.size();i++)
        {
            if(events.get(i).getText().equals(text))
            {
                events.remove(i);
                isRemoved=true;
            }
        }
        return isRemoved;
    }

    public Event getEvent(String text){
        for(int i=0;i<events.size();i++)
        {
            if(events.get(i).getText().equals(text))
            {
                return events.get(i);
            }
        }
        return null;
    }
    private void sortEvents(){
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event event1, Event event2)
            {
                return event1.getDate().compareTo(event2.getDate());
            }
        });
    }
    public int getEventsCount(){
        return events.size();
    }
    public Event getEventByIndex(int index){
        return events.get(index);
    }

}
