package com.company;

import java.util.ArrayList;

/**
 * Created by a1 on 28.06.16.
 */
public class User {

    private String name;
    private int tzone = 0;
    private boolean stat;
    private ArrayList<MyEvent> events;

    public User(String nname,String ttzone,String sstat) throws NumberFormatException{
        name = nname;
        tzone = Integer.parseInt(ttzone);
        if ((tzone > 12) || (tzone < -12)) throw new NumberFormatException();
        if ((sstat.equals("1"))
            || (sstat.equals("true"))
                || (sstat.equals("active"))
                    || (sstat.equals("True"))
                        || (sstat.equals("TRUE"))) stat = true;
        else stat = false;
        events = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public void setName(String nname){
        name = nname;
    }

    public int getTzone(){
        return this.tzone;
    }

    public void setTzone(int ttzone){
        tzone = ttzone;
    }

    public boolean getStatus(){
        return this.stat;
    }

    public void setStatus(boolean status){
        stat = status;
    }

    public MyEvent getEvent(int numEvent){
        return events.get(numEvent);
    }

    public void addEvent(MyEvent event){
        events.add(event);
    }

    public void removeEvent(int numEvent){
        events.remove(numEvent);
    }

    public void sortEvents(){
        events.sort(new EvComparator());
    }

    public int sizeEvents(){
        return events.size();
    }

    public boolean isEmptyEvents(){
        return events.isEmpty();
    }

    public void printInfo(){
        System.out.println("User:\n"+
                this.getName()+"\n"+
                this.getTzone()+"\n"+
                this.getStatus());
    }

    //search user with name in ArrayList<User>
    public static int searchUsr(String name, ArrayList<User> usersList){
        int num_of_usr = -1;

        int i = 0;
        try {
            while(!usersList.get(i++).name.equals(name));
            num_of_usr = i-1;
        } catch (IndexOutOfBoundsException e) {
        }

        return num_of_usr;
    }

    //check text of event for uniqueness
    public boolean uniqEvent(String text){
        int num_of_ev = -1;

        int i = 0;
        try {
            while(!events.get(i++).getInfo().equals(text));
            num_of_ev = i;
        } catch (IndexOutOfBoundsException e) {}

        return num_of_ev == -1;
    }

    //search event for user with number numU in ArrayList<User>
    public int searchEv(String text){
        int num_of_ev = -1;

        int i = 0;
        try {
            while(!events.get(i++).getInfo().equals(text));
            num_of_ev = i-1;
        } catch (IndexOutOfBoundsException e) {}

        return num_of_ev;
    }

    //print all events for user num
    public void printEvents(){
        for(int i = 0; i < this.events.size(); i++){
            System.out.println("Event "+i+":\n"+
                    this.events.get(i).getInfo()+"\n"+
                    this.events.get(i).getDate().toString());
        }
    }

}
