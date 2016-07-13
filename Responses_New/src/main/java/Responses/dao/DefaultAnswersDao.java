package Responses.dao;

import Responses.dbEntities.AnswersEntity;
import Responses.dbEntities.DefaultAnswersEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultAnswersDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<DefaultAnswersEntity> getAnswers() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DefaultAnswersEntity.class);
        List<DefaultAnswersEntity> defaultAnswers = criteria.list();
        session.close();
        return defaultAnswers;
    }

    public DefaultAnswersEntity getAnswerById(Integer answerId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DefaultAnswersEntity.class);
        criteria.add(Restrictions.eq("defaultAnswerId", answerId));
        DefaultAnswersEntity answer = (DefaultAnswersEntity) criteria.uniqueResult();
        session.close();
        return answer;
    }

}
