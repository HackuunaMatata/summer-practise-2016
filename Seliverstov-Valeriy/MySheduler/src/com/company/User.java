package com.company;

import java.util.ArrayList;

/**
 * Created by a1 on 28.06.16.
 */
public class User {

    String name;
    int tzone = 0;
    boolean stat;
    ArrayList<MyEvent> events;

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

}
