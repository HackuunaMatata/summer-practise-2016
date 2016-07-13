package Responses.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletAdmin extends HttpServlet {
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
        out.println("<h1>Страница администратора</h1>");
        out.println("<h2>Настройка парамеров:</h2>");
        out.println("<ol>");
        out.println("<li>");
        out.println("<a href=\"ServletAdminFormQuestionaries\">Редактирование анкеты </a><br>");
        out.println("</li>");
        out.println("<li>");
        out.println("<a href=\"ServletAdminAddQuestions\">Добавить вопросы в анкету</a><br>");
        out.println("</li>");
        out.println("<li>");
        out.println("<a href=\"admin_form_getData.html\">Получить данные в doc файле</a><br>");// вставить название сервлета
        out.println("</li>");
        out.println("<li>");
        out.println("<a href=\"admin_form_mail.html\"> Настройка уведомлений на e-mail </a><br>");// вставить название сервлета
        out.println("</li>");
        out.println("</ol>");
        out.println("<a href=\"ServletFormsClient\">Вернуться к анкете</a>");
        out.println("</body>");
        out.println("</html>");
    }
}
