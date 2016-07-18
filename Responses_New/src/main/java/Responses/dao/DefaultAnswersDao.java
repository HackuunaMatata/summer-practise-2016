package Responses.dao;

import Responses.dbEntities.DefaultAnswersEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultAnswersDao {

//    @Autowired
//    private SessionFactory sessionFactory;

    public static List<DefaultAnswersEntity> getAnswers() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DefaultAnswersEntity.class);
        List<DefaultAnswersEntity> defaultAnswers = criteria.list();
        session.close();
        return defaultAnswers;
    }
/*
    public DefaultAnswersEntity getAnswerByQuestionId(Integer answerId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(DefaultAnswersEntity.class);
        criteria.add(Restrictions.eq("questionId", answerId));
        DefaultAnswersEntity answer = (DefaultAnswersEntity) criteria.uniqueResult();
        session.close();
        return answer;
    }
*/
}
