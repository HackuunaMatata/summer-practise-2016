package com.company;

import java.util.Comparator;

/**
 * Created by a1 on 28.06.16.
 */

//Comparator for users
public class UsComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (((User) o1).name.compareTo(((User) o2).name) < 0) return 1;
        if (((User) o1).name.compareTo(((User) o2).name) > 0) return 1;
        return 0;
    }
}
