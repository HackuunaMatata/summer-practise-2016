package Responses.servlets;

import Responses.dbEntities.DefaultAnswersEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletAdminAddProject extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        DefaultAnswersEntity answerEntity = new DefaultAnswersEntity();
        answerEntity.setQuestionId(0);
        answerEntity.setValue(request.getParameter("projectName"));
        session.save(answerEntity);
        session.getTransaction().commit();
        session.close();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Добавление проекта</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Добавление проекта </h2>");
        out.println("<form action=\"ServletAdminAddProject\" method=\"POST\">");
        out.println("<p> Название проекта </p>");
        out.println("<input type=\"text\" name=\"projectName\" id=\"projectName\" placeholder=\"Введите название проекта\" > <br>");
        out.println("<input type=\"submit\" value=\"Добавить проект\">");
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
        out.println("<title>Добавление проекта</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Добавление проекта </h2>");
        out.println("<form action=\"ServletAdminAddProject\" method=\"POST\">");
        out.println("<p> Название проекта </p>");
        out.println("<input type=\"text\" name=\"projectName\" id=\"projectName\" placeholder=\"Введите название проекта\" > <br>");
        out.println("<input type=\"submit\" value=\"Добавить проект\">");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body>");
        out.println("</html>");
    }
}
