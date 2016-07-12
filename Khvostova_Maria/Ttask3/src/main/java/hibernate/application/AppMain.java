package hibernate.application;

import hibernate.factory.HibernateSessionFactory;
import hibernate.factory.HobbyEntity;
import hibernate.factory.ContactEntity;
import hibernate.factory.ContactHobbyDetailEntity;
import hibernate.factory.ContactHobbyDetailEntityPK;
import hibernate.factory.ContactTelDetailEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AppMain {

    public static void main(String[] args) throws  Throwable{
//        System.out.println("Hibernate tutorial");
        SessionFactory sessionFactory= HibernateSessionFactory.getSessionFactory();


            System.out.println("Opening session");
            Session session = sessionFactory.openSession();

        System.out.println("Begining transaction");
        session.beginTransaction();

        HobbyEntity hobbyEntity = new HobbyEntity();

       hobbyEntity.setHobbyId("1234");

        session.save(hobbyEntity);
        System.out.println("Comitting");
      session.getTransaction().commit();

        System.out.println("Close");
       session.close();
        HibernateSessionFactory.shutdown();

    }
}
