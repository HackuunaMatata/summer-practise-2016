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
public class QuestionWithAnswers {
    Questions question;
    ArrayList<String> answers;

    public QuestionWithAnswers() {
        this.question = new Questions();
        this.answers = new ArrayList<String>();
    }

    public QuestionWithAnswers(Questions question, ArrayList<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
    
}
