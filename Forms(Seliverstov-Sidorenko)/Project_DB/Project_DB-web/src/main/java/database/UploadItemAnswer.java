/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author a1
 */
public class UploadItemAnswer {
    private String item;
    private String answer;

    public UploadItemAnswer(String item, String answer) {
        this.item = item;
        this.answer = answer;
    }

    public UploadItemAnswer() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    
}
