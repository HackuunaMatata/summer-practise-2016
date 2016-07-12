package Responses.dao;

import Responses.dbEntities.FormsEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class FormsDao {
    private SessionFactory sessionFactory;

    public List<FormsEntity> getForms() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(FormsEntity.class);
        List<FormsEntity> forms = criteria.list();
        session.close();
        return forms;
    }

    public FormsEntity getQuestionById(Integer formId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(FormsEntity.class);
        criteria.add(Restrictions.eq("formId", formId));
        FormsEntity form = (FormsEntity) criteria.uniqueResult();
        session.close();
        return form;
    }
}
