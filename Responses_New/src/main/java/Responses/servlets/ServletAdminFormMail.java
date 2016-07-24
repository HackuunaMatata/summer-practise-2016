package Responses.servlets;

import Responses.dao.AdminDao;
import Responses.dao.DefaultAnswersDao;
import Responses.dbEntities.AdminEntity;
import Responses.dbEntities.DefaultAnswersEntity;
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


public class ServletAdminFormMail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<AdminEntity> admins = AdminDao.getAdmin();
        if (admins.size() == 0) {
            session.beginTransaction();
            AdminEntity adminNew = new AdminEntity();
            adminNew.seteMail(request.getParameter("adminEmail"));
            session.save(adminNew);
            session.getTransaction().commit();
            session.close();
        } else {
            session.beginTransaction();
            AdminEntity adm = (AdminEntity) session.get(AdminEntity.class, admins.get(0).geteMail());
            session.delete(adm);
            adm.seteMail(request.getParameter("adminEmail"));
            session.save(adm);
            session.getTransaction().commit();
        }
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println(" <title>Настройки отправки на email</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1><br>");
        out.println("<h2>Настройка парамеров email:</h2>");
        out.println("<form  method=\"POST\">");
        out.println("<label for=\"adminEmail\"> Email </label><input type=\"email\" name=\"adminEmail\" id=\"adminEmail\" placeholder=\"Введите email\" required> <br>");
        out.println("<input type=\"submit\" value=\"Настроить\"> <br>");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body >");
        out.println("</html >");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println(" <title>Настройки отправки на email</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1><br>");
        out.println("<h2>Настройка парамеров email:</h2>");
        out.println("<form  method=\"POST\">");
        out.println("<label for=\"adminEmail\"> Email </label><input type=\"email\" name=\"adminEmail\" id=\"adminEmail\" placeholder=\"Введите email\" required> <br>");
        out.println("<input type=\"submit\" value=\"Настроить\"> <br>");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body >");
        out.println("</html >");
    }
}
