package Responses.servlets;

import Responses.dao.QuestionsDao;
import Responses.dbEntities.QuestionsEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServletAdminAddQuestions extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        QuestionsDao questionsDao = new QuestionsDao();
        List<QuestionsEntity> questions = questionsDao.getQuestions();
        int countQuestion = questions.size();
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        QuestionsEntity questionsEntity = new QuestionsEntity();
        questionsEntity.setId(countQuestion);
        questionsEntity.setValue(request.getParameter("questionName"));
        System.out.println(request.getParameter("questionName"));
        questionsEntity.setIsActive(true);
        questionsEntity.setIsRequired(false);
        session.save(questionsEntity);
        session.getTransaction().commit();
        session.close();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Добавление вопроса</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Добавление вопросов в анкету </h2>");
        out.println("<form action=\"ServletAdminAddQuestions\" method=\"POST\">");
        out.println("<p> Название вопроса </p>");
        out.println("<input type=\"text\" name=\"questionName\" id=\"questionName\" placeholder=\"Введите название вопроса\" > <br>");
        out.println("<input type=\"submit\" value=\"Добавить вопрос\">");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Добавление вопроса</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Добавление вопросов в анкету </h2>");
        out.println("<form action=\"ServletAdminAddQuestions\" method=\"POST\">");
        out.println("<p> Название вопроса </p>");
        out.println("<input type=\"text\" name=\"questionName\" id=\"questionName\" placeholder=\"Введите название вопроса\" > <br>");
        out.println("<input type=\"submit\" value=\"Добавить вопрос\">");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body>");
        out.println("</html>");
    }
}
