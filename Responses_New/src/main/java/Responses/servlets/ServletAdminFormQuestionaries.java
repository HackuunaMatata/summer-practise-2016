package Responses.servlets;

import Responses.dao.QuestionsDao;
import Responses.dbEntities.QuestionsEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServletAdminFormQuestionaries extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        QuestionsDao questionsDao = new QuestionsDao();
        List<QuestionsEntity> questions = questionsDao.getQuestions();
        PrintWriter out = response.getWriter();
        for(int i = 0; i < questions.size(); i++){
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            QuestionsEntity ques = (QuestionsEntity) session.load(QuestionsEntity.class, i);
            tx.commit();
            ques.setId(questions.get(i).getId());
            ques.setValue(questions.get(i).getValue());
            ques.setIsRequired(questions.get(i).getIsRequired());
            Transaction tx7 = session.beginTransaction();
            String active = request.getParameter(String.valueOf(questions.get(i).getId()) + "Active");
            if("on".equals(active)){
                ques.setIsActive(true);
            }
            else{
                ques.setIsActive(false);
            }
            session.update(ques);
            tx7.commit();
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Включение вопросов</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Редактирование анкеты</h2>");
        out.println("<form action=\"ServletAdminFormQuestionaries\" method=\"POST\">");
        out.println(" <p>Какие пункты будут заполняться в анкете? (Отметьте галочкой)</p>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th> Пункты анкеты </th><th> Включен ли вопрос в анкету </th>");
        out.println("</tr>");
        for (int i = 0; i < questions.size(); i++) {
            out.println("<tr>");
            out.println("<td>" + questions.get(i).getValue() + "</td><td><input type=\"checkbox\" name=\""+ questions.get(i).getId() + "Active\" checked=\"checked\"></td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("<input type = \"submit\" value = \"Изменить\" >");
        out.println("</form >");
        out.println("<a href = \"ServletAdmin\" > Основное меню</a ><br >");
        out.println("</body >");
        out.println("</html >");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        QuestionsDao questionsDao = new QuestionsDao();
        List<QuestionsEntity> questions = questionsDao.getQuestions();


        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Включение вопросов</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Редактирование анкеты</h2>");
        out.println("<form action=\"ServletAdminFormQuestionaries\" method=\"POST\">");
        out.println(" <p>Какие пункты будут заполняться в анкете? (Отметьте галочкой)</p>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th> Пункты анкеты </th><th> Включен ли вопрос в анкету </th>");
        out.println("</tr>");
        for (int i = 0; i < questions.size(); i++) {
            out.println("<tr>");
            out.println("<td>" + questions.get(i).getValue() + "</td><td><input type=\"checkbox\" name=\""+ questions.get(i).getId() + "Active\" checked=\"checked\"></td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("<input type = \"submit\" value = \"Изменить\" >");
        out.println("</form >");
        out.println("<a href = \"ServletAdmin\" > Основное меню</a ><br >");
        out.println("</body >");
        out.println("</html >");
    }
}
