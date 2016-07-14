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

public class ServletAdminAddPosition extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        DefaultAnswersEntity answerEntity = new DefaultAnswersEntity();
        answerEntity.setQuestionId(4);
        answerEntity.setValue(request.getParameter("positionName"));
        session.save(answerEntity);
        session.getTransaction().commit();
        session.close();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Добавление должности</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Добавление должности </h2>");
        out.println("<form action=\"ServletAdminAddPosition\" method=\"POST\">");
        out.println("<p> Наименование должности </p>");
        out.println("<input type=\"text\" name=\"positionName\" id=\"positionName\" placeholder=\"Введите наименование должности\" > <br>");
        out.println("<input type=\"submit\" value=\"Добавить должность\">");
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
        out.println("<title>Добавление должности</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2> Добавление должности </h2>");
        out.println("<form action=\"ServletAdminAddPosition\" method=\"POST\">");
        out.println("<p> Наименование должности </p>");
        out.println("<input type=\"text\" name=\"positionName\" id=\"positionName\" placeholder=\"Введите наименование должности\" > <br>");
        out.println("<input type=\"submit\" value=\"Добавить должность\">");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body>");
        out.println("</html>");
    }
}
