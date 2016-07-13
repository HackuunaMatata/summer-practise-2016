package Responses.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ServletAdminFormMail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println(" <title>HTML Document</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Страница администратора</h1><br>");
        out.println("<h2>Настройка парамеров email:</h2>");
        out.println("<form>");
        out.println("<label for=\"adminEmail\"> Email </label><input type=\"email\" name=\"adminEmail\" id=\"adminEmail\" placeholder=\"Введите email\" required> <br>");
        out.println("<p> Настройки частоты отправления уведомлений: </p>");
        out.println("<label for=\"frequency\"> При каждом новом отзыве </label><input type=\"radio\" name=\"frequency\"/> <br>");
        out.println("<label for=\"frequency\"> Через определенное время </label> ");
        out.println("<select name=\"frequencyPeriod\" id=\"frequencyPeriod\"> ");
        out.println("<option value=\"30m\"> Каждые пол часа </option>");
        out.println("<option value=\"1h\"> Каждый час </option>");
        out.println("<option value=\"3h\"> Каждые 3 часа </option>");
        out.println("<option value=\"5h\"> Каждые 5 часов </option>");
        out.println("<option value=\"10h\"> Каждые 10 часов </option>");
        out.println("<option value=\"24р\"> Каждые 24 часа </option>");
        out.println("</select><input type=\"radio\" name=\"frequency\"/> <br>");
        out.println("<input type=\"submit\" value=\"Настроить\"> <br>");
        out.println("</form>");
        out.println("<a href=\"admin_form.html\"> Основное меню </a><br>");
        out.println("</body >");
        out.println("</html >");
    }
}
