package Responses.main;

import Responses.dbEntities.AnswersEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Session;


public class AppMain {
    public static void main(String[] args) {

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();

        AnswersEntity answersEntity = new AnswersEntity();

        answersEntity.setId(9);
        answersEntity.setValue("Аналитик");

        session.save(answersEntity);
        session.getTransaction().commit();

        session.close();


    }
}
