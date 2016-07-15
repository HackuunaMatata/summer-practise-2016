/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;

/**
 *
 * @author a1
 */
public class UploadEntity {
    private String title;
    private String surname;
    private String name;
    private ArrayList<UploadItemAnswer> uItemAns;

    public UploadEntity(String title, String surname, String name, ArrayList<UploadItemAnswer> uItemAns) {
        this.title = title;
        this.surname = surname;
        this.name = name;
        this.uItemAns = uItemAns;
    }


    public UploadEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<UploadItemAnswer> getuItemAns() {
        return uItemAns;
    }

    public void setuItemAns(ArrayList<UploadItemAnswer> uItemAns) {
        this.uItemAns = uItemAns;
    }
    
    
    
    
}
