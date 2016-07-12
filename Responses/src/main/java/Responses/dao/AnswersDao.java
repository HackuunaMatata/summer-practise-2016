package Responses.dao;

import Responses.dbEntities.AnswersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class AnswersDao {

    private SessionFactory sessionFactory;

    public List<AnswersEntity> getAnswers() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AnswersEntity.class);
        List<AnswersEntity> answers = criteria.list();
        session.close();
        return answers;
    }

    public AnswersEntity getAnswerById(Integer answerId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AnswersEntity.class);
        criteria.add(Restrictions.eq("answerId", answerId));
        AnswersEntity answer = (AnswersEntity) criteria.uniqueResult();
        session.close();
        return answer;
    }
}
