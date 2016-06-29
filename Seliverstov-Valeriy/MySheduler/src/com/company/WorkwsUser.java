package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by a1 on 28.06.16.
 */

//Syntax of user's commands in CommandLine:
//Create(name, timezone, active)
//Modify(name, timezone, active)
//AddEvent(name, text, datetime)
//RemoveEvent(name, text)
//CloneEvent(name, text, nameTo)
//ShowInfo(name)
//StartScheduling
//exit

public class WorkwsUser {

    private String command;

    public WorkwsUser() {

    }

    //while(true) with reading Lines and analysing command
    void work(){

        String onlyCom = ""; //command without (...)
        Scanner scan = new Scanner(System.in);

        while(true){

            command = scan.nextLine();

            try {
                onlyCom = сomW_oBkt(command);
                process(onlyCom);
            } catch (IndexOutOfBoundsException e) {
                onlyCom = command;
                process(onlyCom);
            } catch(IllegalStateException ge) {
                System.out.println("Keep up syntax right!");
            }

        }
    }

    //separate word with parameters in (...)
    String сomW_oBkt(String com) throws IllegalStateException,IndexOutOfBoundsException{
        String temp = com.substring(0,com.indexOf("("));
        if (com.contains("("))
            if (!com.contains(")")) throw new IllegalStateException();
        return temp;
    }

    //Switch with cases for every user's request
    void process(String onlyCom){
        switch(onlyCom){
            case "Create":
                if (command.contains("("))
                    create(command.substring(command.indexOf("(")));
                break;
            case "Modify":
                if (command.contains("("))
                    modify(command.substring(command.indexOf("(")));
                break;
            case "AddEvent":
                if (command.contains("("))
                    addEvent(command.substring(command.indexOf("(")));
                break;
            case "RemoveEvent":
                if (command.contains("("))
                    removeEvent(command.substring(command.indexOf("(")));
                break;
            case "AddRandomTimeEvent":
                if (command.contains("("))
                    addRandTimeEvent(command.substring(command.indexOf("(")));
                break;
            case "CloneEvent":
                if (command.contains("("))
                    cloneEvent(command.substring(command.indexOf("(")));
                break;
            case "ShowInfo":
                if (command.contains("("))
                    showInfo(command.substring(command.indexOf("(")));
                break;
            case "StartSheduling":
                System.out.println("It started!");
                Shedule_thr st = new Shedule_thr();
                st.start();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Unknown command!");
                break;
        }
    }

    //Create user (check for format of timezone (int from -12 to 12), check for unique name)
    void create(String data){

        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3]; //number in [] depends on number of words in (...)

        try {
            info = separateWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Keep up syntax right!");
            return;
        }

        int num = searchUsr(info[0]);

        if (num != -1) {
            System.out.println("User already exists!");
            return;
        }

        User usr = null;
        try {
            usr = new User(info[0],info[1],info[2]);
        } catch (NumberFormatException e) {
            System.out.println("Bad format of timezone! It's from -12 to 12.");
            return;
        }
        Global.users.add(usr);

        System.out.println("Success! New user:\n"+
                Global.users.get(Global.users.size()-1).name+"\n"+
                Global.users.get(Global.users.size()-1).tzone+"\n"+
                Global.users.get(Global.users.size()-1).stat);
        Global.users.sort(new UsComparator());
        System.out.println("-------------------------");
    }

    //separate parameters in function
    //Create(user,timezone,active)
    //result of function String[] = {user,timezone,active}
    //Parameters: String data - (user,timezone,active), num_of_w - number of words
    private String[] separateWords(String data, int num_of_w) throws IndexOutOfBoundsException{

        data.trim();

        String[] res = new String[num_of_w];

        for(int i = 0; i < num_of_w - 1; i++){
            res[i] = data.substring(0,data.indexOf(","));
            data = data.substring(data.indexOf(",")+1);
        }

        res[num_of_w - 1] = data;

        return res;
    }

    //working like void create(String data) but just update user
    private void modify(String data){

        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3];//number in [] depends on number of words in (...)

        try {
            info = separateWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Keep up syntax right!");
            return;
        }

        int num = searchUsr(info[0]);

        if (num == -1) {
            System.out.println("Unknown user!");
        }
        else {
            User usr = null;
            try {
                usr = new User(info[0],info[1],info[2]);
            } catch (NumberFormatException e) {
                System.out.println("Bad format of timezone! It's from -12 to 12");
                return;
            }
            Global.users.set(num,usr);

            System.out.println("Success! user "+info[0]+" now:\n"+
                    Global.users.get(num).name+"\n"+
                    Global.users.get(num).tzone+"\n"+
                    Global.users.get(num).stat);
            System.out.println("-------------------------");
        }
    }

    //add event for user
    void addEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3];//number in [] depends on number of words in (...)

        try {
            info = separateWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Keep up syntax right!");
            return;
        }

        int num = searchUsr(info[0]);

        if (num == -1) {
            System.out.println("Unknown user!");
            return;
        }

        if ((!Global.users.get(num).events.isEmpty()) && (!uniqEvent(info[1],num))) {
            System.out.println("Not uniq EventText!");
            return;
        }

        MyEvent mev = null;

        try {
            mev = new MyEvent(info[1],info[2],Global.users.get(num).tzone - Global.genTZ);
        } catch (ParseException e) {
            System.out.println("Bad format of data!");
            return;
        }

        Global.users.get(num).events.add(mev);
        int currentEv = Global.users.get(num).events.size()-1;

        System.out.println("Success! New event for "+Global.users.get(num).name+":\n"+
                Global.users.get(num).events.get(currentEv).info+"\n"+
                Global.users.get(num).events.get(currentEv).dat.toString());
        Global.users.get(num).events.sort(new EvComparator());
        System.out.println("-------------------------");
    }

    //working like previous func but create random date in needed interval
    void addRandTimeEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[4];

        try {
            info = separateWords(data,4);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Keep up syntax right!");
            return;
        }

        int num = searchUsr(info[0]);

        if (num == -1) {
            System.out.println("Unknown user!");
            return;
        }

        if ((!Global.users.get(num).events.isEmpty()) && (!uniqEvent(info[1],num))) {
            System.out.println("Not uniq EventText!");
            return;
        }

        MyEvent mev = null;

        try {
            long time = getRandDate(info[2],info[3],Global.users.get(num).tzone - Global.genTZ);
            if (time == -1) {
                System.out.println("Replace dates!");
                return;
            }
            mev = new MyEvent(info[1],time);
        } catch (ParseException e) {
            System.out.println("Bad format of data!");
            return;
        }

        Global.users.get(num).events.add(mev);
        int currentEv = Global.users.get(num).events.size()-1;

        System.out.println("Success! New event for "+Global.users.get(num).name+":\n"+
                Global.users.get(num).events.get(currentEv).info+"\n"+
                Global.users.get(num).events.get(currentEv).dat.toString());
        Global.users.get(num).events.sort(new EvComparator());
        System.out.println("-------------------------");
    }

    //just remove event
    void removeEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[2];

        try {
            info = separateWords(data,2);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Keep up syntax right!");
            return;
        }

        int numU = searchUsr(info[0]);

        if (numU == -1) {
            System.out.println("Unknown user!");
            return;
        }

        int numE = searchEv(info[1],numU);

        if (numE == -1) {
            System.out.println("Unknown event!");
            return;
        }

        Global.users.get(numU).events.remove(numE);
        System.out.println("Event removed!");
        System.out.println("-------------------------");
    }

    //check text of event for uniqueness
    boolean uniqEvent(String text,int numU){
        int num_of_ev = -1;

        int i = 0;
        try {
            while(!Global.users.get(numU).events.get(i++).info.equals(text));
            num_of_ev = i;
        } catch (IndexOutOfBoundsException e) {}

        return num_of_ev == -1;
    }

    //search event for user with number numU in ArrayList<User>
    int searchEv(String text,int numU){
        int num_of_ev = -1;

        int i = 0;
        try {
            while(!Global.users.get(numU).events.get(i++).info.equals(text));
            num_of_ev = i-1;
        } catch (IndexOutOfBoundsException e) {}

        return num_of_ev;
    }

    //clone event
    void cloneEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3];

        try {
            info = separateWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Keep up syntax right!");
            return;
        }

        int numUfrom = searchUsr(info[0]); // number of user-source

        if (numUfrom == -1) {
            System.out.println("Unknown user from!");
            return;
        }

        int numE = searchEv(info[1],numUfrom);

        if (numE == -1) {
            System.out.println("Unknown event!");
            return;
        }

        int numUto = searchUsr(info[2]); // number of user-destination

        if (numUto == -1) {
            System.out.println("Unknown user to!");
            return;
        }

        Global.users.get(numUto).events.add(Global.users.get(numUfrom).events.get(numE));
        int currentEv = Global.users.get(numUto).events.size()-1;

        System.out.println("Success! New event for "+Global.users.get(numUto).name+":\n"+
                Global.users.get(numUto).events.get(currentEv).info+"\n"+
                Global.users.get(numUto).events.get(currentEv).dat.toString());
        Global.users.get(numUto).events.sort(new EvComparator());
        System.out.println("-------------------------");
    }

    //show info for user
    void showInfo(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[1];

        try {
            info = separateWords(data,1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Keep up syntax right!");
            return;
        }

        int num = searchUsr(info[0]);

        if (num == -1) {
            System.out.println("Unknown user!");
            return;
        }
        System.out.println("User:\n"+
                Global.users.get(num).name+"\n"+
                Global.users.get(num).tzone+"\n"+
                Global.users.get(num).stat);
        printEvents(num);

    }

    //print all events for user num
    void printEvents(int num){
        for(int i = 0; i < Global.users.get(num).events.size(); i++){
            System.out.println("Event "+i+":\n"+
                    Global.users.get(num).events.get(i).info+"\n"+
                    Global.users.get(num).events.get(i).dat.toString());
        }
    }

    //generate random date from dSt to dEnd and in system timezone
    long getRandDate(String dSt,String dEnd,int ttzone) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        Date d1 = format.parse(dSt);
        Date d2 = format.parse(dEnd);
        long diff = d2.getTime()-d1.getTime();
        if (diff < 0) return -1;
        long randDml = Math.round(Math.random()*(d2.getTime()-d1.getTime())+d1.getTime()+ttzone*3600000);
        return randDml;
    }

    //search user with name in ArrayList<User>
    int searchUsr(String name){
        int num_of_usr = -1;

        int i = 0;
        try {
            while(!Global.users.get(i++).name.equals(name));
            num_of_usr = i-1;
        } catch (IndexOutOfBoundsException e) {
        }

        return num_of_usr;
    }

}
