package Responses.dao;


import Responses.dbEntities.QuestionsEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class QuestionsDao {

    private SessionFactory sessionFactory;

    public List<QuestionsEntity> getQuestions() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(QuestionsEntity.class);
        List<QuestionsEntity> questions = criteria.list();
        session.close();
        return questions;
    }

    public QuestionsEntity getQuestionById(Integer questionId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(QuestionsEntity.class);
        criteria.add(Restrictions.eq("questionId", questionId));
        QuestionsEntity question = (QuestionsEntity) criteria.uniqueResult();
        session.close();
        return question;
    }
}
