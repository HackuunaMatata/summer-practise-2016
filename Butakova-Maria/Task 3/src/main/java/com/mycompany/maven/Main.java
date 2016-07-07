package com.mycompany.maven;
 
import org.hibernate.Session;
import com.mycompany.maven.City;
import com.mycompany.maven.HibernateSessionFactory;
public class Main {
    
 
    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");
 
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
 
        session.beginTransaction();
 
        City city_con = new City();
 
        city_con.setName("Lisbon");
        city_con.setCountry("Portugal");
        city_con.setArea(100);
        city_con.setPopulation(545245);
        
 
        session.save(city_con);
        session.getTransaction().commit();
 
        session.close();
 
 
    }
}