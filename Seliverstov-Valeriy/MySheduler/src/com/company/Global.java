package com.company;

import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by a1 on 28.06.16.
 */
public class Global {

    static volatile ArrayList<User> users = new ArrayList<>();
    static int genTZ = TimeZone.getDefault().getOffset(System.currentTimeMillis())/3600000;

    public Global() {
    }


}
