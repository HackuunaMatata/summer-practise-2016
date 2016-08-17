import java.util.*;


public class User {
    private LinkedList<Event> arrayEvents_;
    private LinkedList<Boolean> arrayBoolean_;
    private SimpleTimeZone simpleTimeZone_;
    private String name_;
    private String status_;

    User(String name, int timeZone, String status) {
        arrayEvents_ = new LinkedList<Event>();
        arrayBoolean_ = new LinkedList<Boolean>();
        name_ = name;
        simpleTimeZone_ = new SimpleTimeZone(timeZone*1000*60*60, TimeZone.getDefault().getID()); //параметры в скобках
        status_ = status;
    }
    String getName() {
        return name_;
    }
    synchronized void setStatus(String status) {
        status_ = status;
    }
    synchronized void setSimpleTimeZone(int timeZone) {
        simpleTimeZone_.setRawOffset(timeZone*1000*60*60);
    }
    LinkedList<Event> getArrayEvents() {
        return arrayEvents_;
    }
    int getSimpleTimeZone(){
        return simpleTimeZone_.getRawOffset()/1000/60/60;
    }
    String getStatus() {
        return status_;
    }
    LinkedList<Boolean> getArrayBoolean() { return arrayBoolean_; }
}
