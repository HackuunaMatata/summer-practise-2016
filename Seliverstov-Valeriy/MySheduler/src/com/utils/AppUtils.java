package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by a1 on 05.07.16.
 */
public class AppUtils {

    public AppUtils() {
    }

    //separate parameters in function
    //Create(user,timezone,active) (example)
    //result of function String[] = {user,timezone,active}
    //Parameters: String data - (user,timezone,active), num_of_w - number of words
    public String[] separateWords(String data, int num_of_w) throws IndexOutOfBoundsException{

        data.trim();

        String[] res = new String[num_of_w];

        for(int i = 0; i < num_of_w - 1; i++){
            res[i] = data.substring(0,data.indexOf(","));
            data = data.substring(data.indexOf(",")+1);
        }

        res[num_of_w - 1] = data;

        return res;
    }

    public String[] separateEventWords(String data,int num_of_w) throws IndexOutOfBoundsException{

        data.trim();

        String[] res = new String[num_of_w];

        res[0] = data.substring(0,data.indexOf(","));
        data = data.substring(data.indexOf(",")+1);

        if (data.indexOf("\"") == data.lastIndexOf("\"")) throw new IndexOutOfBoundsException();
        res[1] = data.substring(data.indexOf("\""),data.lastIndexOf("\"")+1);
        data = data.substring(data.lastIndexOf("\""));
        data = data.substring(data.indexOf(",")+1);

        String[] date = new String[num_of_w-2];
        date = separateWords(data,num_of_w-2);

        for(int i = 2; i < num_of_w; i++) {
            res[i] = date[i-2];
        }

        return res;
    }

    //separate word with parameters in (...)
    public String сomW_oBkt(String com) throws IllegalStateException,IndexOutOfBoundsException{
        String temp = com.substring(0,com.indexOf("("));
        if (com.contains("("))
            if (!com.contains(")")) throw new IllegalStateException();
        return temp;
    }

    public void printError(String error){
        System.out.println(error);
    }

    //generate random date from dSt to dEnd and in system timezone
    public long getRandDate(String dSt,String dEnd,int ttzone) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        Date d1 = format.parse(dSt);
        Date d2 = format.parse(dEnd);
        long diff = d2.getTime()-d1.getTime();
        if (diff < 0) return -1;
        long randDml = Math.round(Math.random()*(d2.getTime()-d1.getTime())+d1.getTime()+ttzone*3600000);
        return randDml;
    }
}
