package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import com.utils.*;

/**
 * Created by a1 on 28.06.16.
 */

//Syntax of user's commands in CommandLine:
//Create(name, timezone, active)
//Modify(name, timezone, active)
//AddEvent(name, "text", datetime)
//RemoveEvent(name, "text")
//CloneEvent(name, "text", nameTo)
//ShowInfo(name)
//StartScheduling
//exit

public class WorkwsUser {

    private String command;
    private AppUtils appUtils;

    public WorkwsUser() {
        appUtils = new AppUtils();
    }

    //while(true) with reading Lines and analysing command
    public void work(){

        String onlyCom = ""; //command without (...)
        Scanner scan = new Scanner(System.in);

        while(true){

            command = scan.nextLine();

            try {
                onlyCom = appUtils.сomW_oBkt(command);
                process(onlyCom);
            } catch (IndexOutOfBoundsException e) {
                onlyCom = command;
                process(onlyCom);
            } catch(IllegalStateException ge) {
                appUtils.printError("Keep up syntax right!");
            }

        }
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
                appUtils.printError("Unknown command!");
                break;
        }
    }

    //Create user (check for format of timezone (int from -12 to 12), check for unique name)
    void create(String data){

        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3]; //number in [] depends on number of words in (...)

        try {
            info = appUtils.separateWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            appUtils.printError("Keep up syntax right!");
            return;
        }

        int num = User.searchUsr(info[0],Global.users);

        if (num != -1) {
            appUtils.printError("User already exists!");
            return;
        }

        User usr = null;

        try {
            usr = new User(info[0],info[1],info[2]);
        } catch (NumberFormatException e) {
            appUtils.printError("Bad format of timezone! It's from -12 to 12.");
            return;
        }

        Global.users.add(usr);

        System.out.println("Success! New user:\n"+
                Global.users.get(Global.users.size()-1).getName()+"\n"+
                Global.users.get(Global.users.size()-1).getTzone()+"\n"+
                Global.users.get(Global.users.size()-1).getStatus());

        Global.users.sort(new UsComparator());

        System.out.println("-------------------------");
    }

    //working like void create(String data) but just update user
    private void modify(String data){

        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3];//number in [] depends on number of words in (...)

        try {
            info = appUtils.separateWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            appUtils.printError("Keep up syntax right!");
            return;
        }

        int num = User.searchUsr(info[0],Global.users);

        if (num == -1) {
            appUtils.printError("Unknown user!");
        }
        else {
            User usr = null;
            try {
                usr = new User(info[0],info[1],info[2]);
            } catch (NumberFormatException e) {
                appUtils.printError("Bad format of timezone! It's from -12 to 12");
                return;
            }
            Global.users.set(num,usr);

            System.out.println("Success! user "+info[0]+" now:\n"+
                    Global.users.get(num).getName()+"\n"+
                    Global.users.get(num).getTzone()+"\n"+
                    Global.users.get(num).getStatus());
            System.out.println("-------------------------");
        }
    }

    //add event for user
    void addEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3];//number in [] depends on number of words in (...)

        try {
            info = appUtils.separateEventWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            appUtils.printError("Keep up syntax right!");
            return;
        }

        int num = User.searchUsr(info[0],Global.users);

        if (num == -1) {
            appUtils.printError("Unknown user!");
            return;
        }

        if ((!Global.users.get(num).isEmptyEvents()) &&
                (!Global.users.get(num).uniqEvent(info[1]))) {
            appUtils.printError("Not uniq EventText!");
            return;
        }

        MyEvent mev = null;

        try {
            mev = new MyEvent(info[1],info[2],Global.users.get(num).getTzone() - Global.genTZ);
        } catch (ParseException e) {
            appUtils.printError("Bad format of data!");
            return;
        }

        Global.users.get(num).addEvent(mev);
        int currentEv = Global.users.get(num).sizeEvents()-1;

        System.out.println("Success! New event for "+Global.users.get(num).getName()+":\n"+
                Global.users.get(num).getEvent(currentEv).getInfo()+"\n"+
                Global.users.get(num).getEvent(currentEv).getDate().toString());

        Global.users.get(num).sortEvents();

        System.out.println("-------------------------");
    }

    //working like previous func but create random date in needed interval
    void addRandTimeEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[4];

        try {
            info = appUtils.separateEventWords(data,4);
        } catch (IndexOutOfBoundsException e) {
            appUtils.printError("Keep up syntax right!");
            return;
        }

        int num = User.searchUsr(info[0],Global.users);

        if (num == -1) {
            appUtils.printError("Unknown user!");
            return;
        }

        if ((!Global.users.get(num).isEmptyEvents()) &&
                (!Global.users.get(num).uniqEvent(info[1]))) {
            appUtils.printError("Not uniq EventText!");
            return;
        }

        MyEvent mev = null;

        try {
            long time = appUtils.getRandDate(info[2],info[3],Global.users.get(num).getTzone() - Global.genTZ);
            if (time == -1) {
                appUtils.printError("Replace dates!");
                return;
            }
            mev = new MyEvent(info[1],time);
        } catch (ParseException e) {
            appUtils.printError("Bad format of data!");
            return;
        }

        Global.users.get(num).addEvent(mev);
        int currentEv = Global.users.get(num).sizeEvents()-1;

        System.out.println("Success! New event for "+Global.users.get(num).getName()+":\n"+
                Global.users.get(num).getEvent(currentEv).getInfo()+"\n"+
                Global.users.get(num).getEvent(currentEv).getDate().toString());

        Global.users.get(num).sortEvents();

        System.out.println("-------------------------");
    }

    //just remove event
    void removeEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[2];

        try {
            info = appUtils.separateEventWords(data,2);
        } catch (IndexOutOfBoundsException e) {
            appUtils.printError("Keep up syntax right!");
            return;
        }

        int numU = User.searchUsr(info[0],Global.users);

        if (numU == -1) {
            appUtils.printError("Unknown user!");
            return;
        }

        int numE = Global.users.get(numU).searchEv(info[1]);

        if (numE == -1) {
            appUtils.printError("Unknown event!");
            return;
        }

        Global.users.get(numU).removeEvent(numE);

        System.out.println("Event removed!");

        System.out.println("-------------------------");
    }

    //clone event
    void cloneEvent(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[3];

        try {
            info = appUtils.separateEventWords(data,3);
        } catch (IndexOutOfBoundsException e) {
            appUtils.printError("Keep up syntax right!");
            return;
        }

        int numUfrom = User.searchUsr(info[0],Global.users); // number of user-source

        if (numUfrom == -1) {
            appUtils.printError("Unknown user from!");
            return;
        }

        int numE = Global.users.get(numUfrom).searchEv(info[1]);

        if (numE == -1) {
            appUtils.printError("Unknown event!");
            return;
        }

        int numUto = User.searchUsr(info[2],Global.users);; // number of user-destination

        if (numUto == -1) {
            appUtils.printError("Unknown user to!");
            return;
        }

        Global.users.get(numUto).addEvent(
                Global.users.get(numUfrom).getEvent(numE)
        );

        int currentEv = Global.users.get(numUto).sizeEvents()-1;

        System.out.println("Success! New event for "+Global.users.get(numUto).getName()+":\n"+
                Global.users.get(numUto).getEvent(currentEv).getInfo()+"\n"+
                Global.users.get(numUto).getEvent(currentEv).getDate().toString());

        Global.users.get(numUto).sortEvents();

        System.out.println("-------------------------");
    }

    //show info for user
    void showInfo(String data){
        data = data.substring(1,data.indexOf(")"));

        String[] info = new String[1];

        try {
            info = appUtils.separateWords(data,1);
        } catch (IndexOutOfBoundsException e) {
            appUtils.printError("Keep up syntax right!");
            return;
        }

        int num = User.searchUsr(info[0],Global.users);

        if (num == -1) {
            appUtils.printError("Unknown user!");
            return;
        }

        Global.users.get(num).printInfo();

        Global.users.get(num).printEvents();

    }

}
