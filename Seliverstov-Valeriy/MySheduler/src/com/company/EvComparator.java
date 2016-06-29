package com.company;

import java.util.Comparator;

/**
 * Created by a1 on 28.06.16.
 */

//Comparator for events
public class EvComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        if (((MyEvent) o1).dat.after(((MyEvent) o2).dat)) return 1;
        if (((MyEvent) o1).dat.equals(((MyEvent) o2).dat)) {
            if (((MyEvent) o2).info.compareTo(((MyEvent) o1).info) > 0) return 1;
            else {
                if (((MyEvent) o2).info.compareTo(((MyEvent) o1).info) < 0) return -1;
                else return 0;
            }
        }
        if (((MyEvent) o1).dat.before(((MyEvent) o2).dat)) return -1;
        return 0;
    }
}