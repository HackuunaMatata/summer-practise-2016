package ru.practice_lab.hibernate.main;

import org.hibernate.Session;
import ru.practice_lab.hibernate.dao.CityEntity;
import ru.practice_lab.hibernate.utils.HibernateSessionFactory;

public class AppMain {

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();

        CityEntity cityEntity = new CityEntity();

        cityEntity.setName("Naryan-Mar");
        cityEntity.setPopulation(26000);
        cityEntity.setArea(48);

        session.save(cityEntity);
        session.getTransaction().commit();

        session.close();


    }
}