package sheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for description users. <br>
 * Every user has name, timezone and pool of events.
 * User can be active or passive. Scheduler handles the pool
 * of events just for active users.
 */
public class User {
    private String name   = null;  // User name
    private int timezone  = 0;     // User timezone
    private boolean state = false; // true - active, false - passive
    private List eventPool = null; // List of events (ArrayList class)

    public User(String name, int timezone, boolean state)
    {
        this.name     = name;
        this.timezone = timezone;
        this.state    = state;
        eventPool = new ArrayList(0);
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public String getName() {

        return name;
    }

    public int getTimezone() {
        return timezone;
    }

    public boolean isState() {
        return state;
    }

    public List getEventPool() {
        return eventPool;
    }
}
