package com.venedikttsulenev.Task3;

import org.hibernate.Session;

public class Main {
    public static void main(String args[]) {
        Session session = Task3SessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        CityEntity cityOFCh = new CityEntity();
        cityOFCh.setName("Chelyabinskkkk");
        cityOFCh.setArea(100);
        cityOFCh.setPopulation(500000);
        cityOFCh.setCountry("Russia");
        session.save(cityOFCh);
        session.getTransaction().commit();
        session.close();
    }
}
