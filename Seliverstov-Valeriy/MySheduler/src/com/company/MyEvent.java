package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by a1 on 28.06.16.
 */
public class MyEvent{

    Date dat;
    String info;

    //constructor for date in String
    public MyEvent(String iinfo,String ddat,int tzone) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        dat = new Date(format.parse(ddat).getTime()+tzone*3600000);

        info = iinfo;
    }

    //constructor for event with date in milliseconds
    public MyEvent(String iinfo,long ddat) throws ParseException {

        dat = new Date(ddat);

        info = iinfo;
    }

}
