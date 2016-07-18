package Responses.dao;

import Responses.dbEntities.AdminEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<AdminEntity> getAdmin() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(AdminEntity.class);
        List<AdminEntity> admin = criteria.list();
        session.close();
        return admin;
    }
}
