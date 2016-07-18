package Responses.dao;

import Responses.dbEntities.FormsEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormsDao {

//    @Autowired
//    private SessionFactory sessionFactory;

    public static List<FormsEntity> getForms() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(FormsEntity.class);
        List<FormsEntity> forms = criteria.list();
        session.close();
        return forms;
    }

//    public static FormsEntity getFormById(Integer formId) {
//        Session session = HibernateSessionFactory.getSessionFactory().openSession();
//        Criteria criteria = session.createCriteria(FormsEntity.class);
//        criteria.add(Restrictions.eq("id", formId));
//        FormsEntity form = (FormsEntity) criteria.uniqueResult();
//        session.close();
//        return form;
//    }
//
//    public static void deleteForm(Integer answerId) {
//        Session session = HibernateSessionFactory.getSessionFactory().openSession();
//        FormsEntity form = new FormsEntity();
//        form.setId(answerId);
//        session.delete(form);
//        session.getTransaction().commit();
//    }
}
