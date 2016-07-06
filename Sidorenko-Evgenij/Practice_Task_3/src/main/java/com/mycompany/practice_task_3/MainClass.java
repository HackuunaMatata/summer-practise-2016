package com.mycompany.practice_task_3;

import org.hibernate.Session;

public class MainClass {
    
    public static void main(String[] args) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        City[] city = new City[2];
        CityId[] cityid = new CityId[2];
        
        
        session.beginTransaction();
        city[0] = new City();
        cityid[0] = new CityId();
        cityid[0].setName("Norilsk");
        cityid[0].setCountry("Russia");
        city[0].setId(cityid[0]);
        city[0].setArea(23);
        city[0].setPopulation(176251);
               
//        city[1] = new City();
//        cityid[1] = new CityId();
//        cityid[1].setName("Symy");
//        cityid[1].setCountry("Ukraine");
//        city[1].setId(cityid[1]);
//        city[1].setArea(145);
//        city[1].setPopulation(268409);
        session.save(city[0]);
        session.getTransaction().commit();
        

        session.close();

    }
}
