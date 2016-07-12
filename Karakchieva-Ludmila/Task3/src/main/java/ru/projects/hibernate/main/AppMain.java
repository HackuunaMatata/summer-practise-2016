package ru.projects.hibernate.main;

import org.hibernate.Session;
import ru.projects.hibernate.dao.CityEntity;
import ru.projects.hibernate.utils.HibernateSessionFactory;


public class AppMain {
    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();

        CityEntity cityEntity = new CityEntity();

        cityEntity.setName("New-York");
        cityEntity.setPopulation(8405837);
        cityEntity.setArea(789);
        cityEntity.setCountry("USA");

        session.save(cityEntity);
        session.getTransaction().commit();

        session.close();


    }
}
