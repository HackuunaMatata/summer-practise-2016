package Responses.dao;


import Responses.dbEntities.QuestionsEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionsDao {

//    @Autowired
//    private SessionFactory sessionFactory;

    public static List<QuestionsEntity> getQuestions() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(QuestionsEntity.class);
        List<QuestionsEntity> questions = criteria.list();
        session.close();
        return questions;
    }

    public static QuestionsEntity getQuestionById(Integer questionId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(QuestionsEntity.class);
        criteria.add(Restrictions.eq("id", questionId));
        QuestionsEntity question = (QuestionsEntity) criteria.uniqueResult();
        session.close();
        return question;
    }
//    public static void deleteQuestion(Integer answerId) {
//        Session session = HibernateSessionFactory.getSessionFactory().openSession();
//        QuestionsEntity question = new QuestionsEntity();
//        question.setId(answerId);
//        session.delete(question);
//        session.getTransaction().commit();
//    }
}
