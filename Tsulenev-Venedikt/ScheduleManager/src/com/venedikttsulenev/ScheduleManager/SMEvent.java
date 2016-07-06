package com.venedikttsulenev.ScheduleManager;

import java.util.Date;

public class SMEvent implements Comparable<SMEvent> {
    private final String text;
    private Date date;
    private final SMUser user;
    SMEvent(SMUser user, String text, Date date) {
        this.user = user;
        this.text = text;
        this.date = date;
    }
    public SMUser getUser() { return user; }
    public String getText() { return text; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SMEvent oEvent = (SMEvent) obj;
        return text.equals(oEvent.getText())
                && date.equals(oEvent.getDate())
                && user.equals(oEvent.getUser());
    }
    public int compareTo(SMEvent otherEvent) {
        if (this.user.getActive() != otherEvent.getUser().getActive())
            return this.user.getActive() ? -1 : 1;
        if (this.date.getTime() < otherEvent.getDate().getTime())
            return -1;
        if (this.date.getTime() > otherEvent.getDate().getTime())
            return 1;
        int cmp = otherEvent.getText().compareTo(this.text);
        return cmp != 0 ? cmp : otherEvent.getUser().getName().compareTo(this.user.getName());
    }
}
