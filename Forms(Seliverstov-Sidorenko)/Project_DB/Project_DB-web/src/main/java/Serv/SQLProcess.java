/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serv;

import database.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
//import org.json.JSONArray;

/**
 *
 * @author a1
 */
public class SQLProcess {
    private ArrayList<Events> ev;
    private ArrayList<Questions> questions;
    private Set<Questions> questionsSet;


    public ArrayList<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Questions> questions) {
        this.questions = questions;
    }
    
    public SQLProcess() {
        this.ev = new ArrayList<>();
    }

    public ArrayList<Events> getEv() {
        return ev;
    }

    public void setEv(ArrayList<Events> ev) {
        this.ev = ev;
    }
    
    public String getEventTitle(int eventId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Events event = (Events) session.get(Events.class, eventId);
        return event.getTitle();
    }
    
    public void eventsFromDB(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM Events";
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> results = query.addEntity(Events.class).list();
        System.out.println(results);
        
        ev = (ArrayList) results;

        session.close();
    }
    
    public Boolean checkUniqueEvent(String title){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM Events WHERE title='"+title+"'";
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> results = new ArrayList<>();
        results = query.addEntity(Events.class).list();
        System.out.println(results);
        
        if(!results.isEmpty()) {
            session.close();

            return false;
        }
//        for(int i = 0; i < results.size(); i++){
//            if(results.get(i).getTitle().equals(title)){
//                session.close();
//                return false;
//            }
//        }

        session.close();
        return true;
    }
    
    public Boolean checkUniqueQuestion(int eventId,String itemname){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Events tempEvent = new Events();
        String sql = "SELECT * FROM Questions " //убрать сурнаме и наме
                + "WHERE itemname ='"+itemname+"'"
                + " AND idevent="+eventId;
        SQLQuery query = session.createSQLQuery(sql);
        List<Questions> results = new ArrayList<>();
        results = query.addEntity(Questions.class).list();
        System.out.println(results);
        
        if(!results.isEmpty()) {
            session.close();

            return false;
        }
        
        session.close();
        return true;
    }
    
    public void renameEvent(String oldTitle,String title){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "SELECT * FROM Events WHERE title='"+oldTitle+"'";
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> results = query.addEntity(Events.class).list();
        
        Events tempEvent = new Events();
        
        tempEvent = results.get(0);
        
        tempEvent.setTitle(title);
        
        session.update(tempEvent);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void changeImage(int eventId,byte[] img){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Events tempEvent = (Events) session.get(Events.class, eventId);
        
        tempEvent.setImage(img);
        
        session.update(tempEvent);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void changeActive(int eventId,ArrayList<String> itemnames){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Events tempEvent = (Events) session.get(Events.class, eventId);
        
        String sql = "SELECT * FROM Questions " //убрать сурнаме и наме
                + "WHERE itemname <> 'Surname' "
                + "AND itemname <> 'Name' "
                + "AND idevent="+eventId;
        SQLQuery query = session.createSQLQuery(sql);
        ArrayList<Questions> results = (ArrayList) query.addEntity(Questions.class).list();
        
        Questions question = new Questions();
        for(int i = 0; i < results.size(); i++){
            question =results.get(i);
            
            if (itemnames.contains(question.getItemname())){
                String sqlupdate = "UPDATE Questions SET isActive=1 WHERE idevent="+eventId
                        + " AND itemname = '"+question.getItemname()+"'";
                query = session.createSQLQuery(sqlupdate);
                query.addEntity(Questions.class).executeUpdate();
            }
            else {
                String sqlupdate = "UPDATE Questions SET isActive=0 WHERE idevent="+eventId
                        + " AND itemname = '"+question.getItemname()+"'";
                query = session.createSQLQuery(sqlupdate);
                query.addEntity(Questions.class).executeUpdate();
            }
            session.save(question);
        }
        
        session.getTransaction().commit();
        session.close();
    }

    public void questionsFromDB(int eventId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM Questions "
                + "WHERE itemname <> 'Surname' "
                + "AND itemname <> 'Name' "
                + "AND idevent="+eventId;
        SQLQuery query = session.createSQLQuery(sql);
        ArrayList<Questions> results = (ArrayList) query.addEntity(Questions.class).list();
        System.out.println(results);
        
        questions = results;

        session.close();
        
    }
    
    public QuestionWithAnswers oneQuestionFromDB(int eventId,String itemname){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        String sql = "SELECT * FROM Questions "
                + "WHERE itemname = '"+itemname+"'"
                + "AND idevent="+eventId;
        SQLQuery query = session.createSQLQuery(sql);
        ArrayList<Questions> results = (ArrayList) query.addEntity(Questions.class).list();
        
        System.out.println(results);
        
        Questions tempQ = results.get(0);

        sql = "SELECT * FROM Answers "
                + "WHERE iditem="+tempQ.getIditem();
        query = session.createSQLQuery(sql); 
        ArrayList<Answers> answers = (ArrayList) query.addEntity(Answers.class).list();
        
        QuestionWithAnswers qwa = new QuestionWithAnswers();
        
        qwa.setQuestion(tempQ);
                
        for(int i = 0; i < answers.size(); i++) {
            qwa.getAnswers().add(answers.get(i).getId().getAnswer());
        }
        
        session.close();
        
        return qwa;
        
    }
    
    public void addEventtoDB(String title,byte[] img){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        
        Events newEvent = new Events();
        Questions tempQ = new Questions();
        Answers tempAns = new Answers();
        AnswersId tempAnsId = new AnswersId();
        
        //-------------Add event $title------------
        
        newEvent.setTitle(title);
        newEvent.setImage(img);
        
        session.save(newEvent);
        
        //-------------Add Question "Surname"------------
        
        tempQ = addQuestion(newEvent,"Surname","input","text","Введите фамилию",true);
        
        session.save(tempQ);
        
        tempQ = new Questions();
        tempAns = new Answers();
        tempAnsId = new AnswersId();
        
        //-------------Add Question "Name"------------
        
        tempQ = addQuestion(newEvent,"Name","input","text","Введите имя",true);
        
        session.save(tempQ);
        
        tempQ = new Questions();
        tempAns = new Answers();
        tempAnsId = new AnswersId();
        
        //-------------Add Question "Telephone"------------
        
        tempQ = addQuestion(newEvent,"Phone","input","text","Введите телефон",true);
        
        session.save(tempQ);
        
        tempQ = new Questions();
        tempAns = new Answers();
        tempAnsId = new AnswersId();
        
        //-------------Add Question "Email"------------
        
        tempQ = addQuestion(newEvent,"Email","input","text","Введите e-mail",true);
        
        session.save(tempQ);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void addQuestiontoDB(int eventId,
            String itemname,
            String tag,
            String type, 
            String desc,
            String[] answers){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Events qEvent = (Events) session.get(Events.class, eventId);
        Questions tempQ = addQuestion(qEvent,itemname,tag,type,desc,true);
        
        session.save(tempQ);
        
        Answers tempAns = new Answers();
        
        for(int i = 0; i < answers.length; i++){
            tempAns = addAnswer(tempQ.getIditem(),answers[i],tempQ);
            
            session.save(tempAns);
        }
        
        session.getTransaction().commit();
        
        session.close();
        
    }
    
    public void deleteEventFromDB(int eventId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Events deletingEvent = new Events();
        
        deletingEvent = (Events) session.get(Events.class, eventId);
        
        session.delete(deletingEvent);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void updateQuestionInDB(int eventId,
            String itemname,
            String tag,
            String type, 
            String desc,
            String[] answers){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        String sql = "SELECT * FROM Questions "
                + "WHERE itemname = '"+itemname+"'"
                + "AND idevent="+eventId;
        SQLQuery query = session.createSQLQuery(sql);
        ArrayList<Questions> results = (ArrayList) query.addEntity(Questions.class).list();
        
        System.out.println(results);
        
        Questions tempQ = results.get(0);        
        
        session.beginTransaction();
        
        sql = "SELECT * FROM Answers "
                + "WHERE iditem="+tempQ.getIditem();
        query = session.createSQLQuery(sql); 
        ArrayList<Answers> answersQ = (ArrayList) query.addEntity(Answers.class).list();
        
        for(int i = 0; i < answersQ.size(); i++){
            session.delete(answersQ.get(i));
        }
        
        tempQ.setItemname(itemname);
        tempQ.setTag(tag);
        tempQ.setType(type);
        tempQ.setDescription(desc);
        
        session.update(tempQ);
        
        Answers tempAns = new Answers();
        
        for(int i = 0; i < answers.length; i++){
            tempAns = addAnswer(tempQ.getIditem(),answers[i],tempQ);
            
            session.save(tempAns);
        }
        
        session.getTransaction().commit();
        session.close();
    }
    
    public void deleteQuestionFromDB(int eventId,String itemname){
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        String sql = "SELECT * FROM Questions "
                + "WHERE itemname = '"+itemname+"'"
                + "AND idevent="+eventId;
        SQLQuery query = session.createSQLQuery(sql);
        ArrayList<Questions> results = (ArrayList) query.addEntity(Questions.class).list();
        
        System.out.println(results);
        
        Questions tempQ = results.get(0);
        
        session.beginTransaction();
        
        session.delete(tempQ);
        
        session.getTransaction().commit();
        session.close();
    }
    
    public Questions addQuestion(
            Events event,
            String itemname,
            String tag,
            String type, 
            String desc,
            boolean isActive){
        Questions tempQ = new Questions();
        tempQ.setEvents(event);
        tempQ.setIdevent(event.getIdevent());
        tempQ.setItemname(itemname);
        tempQ.setTag(tag);
        tempQ.setType(type); 
        tempQ.setDescription(desc);
        tempQ.setIsActive(isActive);
        
        return tempQ;
    }
    
    public Answers addAnswer(int iditem, String answer,Questions question){
        Answers tempAns = new Answers();
        
        tempAns.setId(new AnswersId(iditem,answer));
        tempAns.setQuestions(question);
        
        return tempAns;
    }
    
    public void clean(){
        ev = new ArrayList<>();
    }
}
