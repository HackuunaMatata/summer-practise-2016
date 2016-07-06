package com.company;

import java.util.Comparator;

/**
 * Created by a1 on 28.06.16.
 */

//Comparator for events
public class EvComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        if (((MyEvent) o1).getDate().after(((MyEvent) o2).getDate())) return 1;
        if (((MyEvent) o1).getDate().equals(((MyEvent) o2).getDate())) {
            if (((MyEvent) o2).getInfo().compareTo(((MyEvent) o1).getInfo()) > 0) return 1;
            else {
                if (((MyEvent) o2).getInfo().compareTo(((MyEvent) o1).getInfo()) < 0) return -1;
                else return 0;
            }
        }
        if (((MyEvent) o1).getDate().before(((MyEvent) o2).getDate())) return -1;
        return 0;
    }
}