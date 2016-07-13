package Responses.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletAdminFormGetData extends HttpServlet {
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
        out.println("<h2> Получение данных </h2><br>");
        out.println("<form action=\"\">");
        out.println("<input type=\"radio\" name=\"data\" id=\"allData\">");
        out.println("<label for=\"data\"> Получить все данные </label><br><br>");
        out.println("<input type=\"radio\" name=\"data\" id=\"getData\">");
        out.println("<label for=\"data\"> Получить данные по параметру:  </label><br><br>");
        out.println("<script>");
        out.println("function a() {");
        out.println("if (document.getElementById(\"getData\").checked ) {");
        out.println("document.getElementById(\"projectNameBox\").disabled = false;");
        out.println("document.getElementById(\"userPostBox\").disabled = false;");
        out.println("}");
        out.println("else {");
        out.println("document.getElementById(\"projectNameBox\").disabled = true;");
        out.println("document.getElementById(\"userPostBox\").disabled = true;");
        out.println("}");
        out.println("}");
        out.println("</script>");
        out.println("<input type=\"checkbox\" name=\"projectNameBox\" id=\"projectNameBox\" onClick=\"a()\" >");
        out.println("<label for=\"projectName\"> Проект </label>");
        out.println("<select name=\"projectName\" id=\"projectName\"> ");
        out.println("<option value=\"project1\"> Проект 1</option>");
        out.println("<option value=\"project2\"> Проект 2</option>");
        out.println("<option value=\"project3\"> Проект 3</option>");
        out.println("<option value=\"project4\"> Проект 4</option>");
        out.println("<option value=\"project5\"> Проект 5</option>");
        out.println("</select><br>");
        out.println("<input type=\"checkbox\" name=\"userPostBox\" id=\"userPostBox\" onClick=\"a()\">");
        out.println("<label for=\"userPost\"> Должность </label>");
        out.println("<select name=\"userPost\" id=\"userPost\"  > ");
        out.println("<option value=\"project1\"> Менеджер </option>");
        out.println("<option value=\"project2\"> Руководитель отдела </option>");
        out.println("<option value=\"project3\"> Руководитель проекта </option>");
        out.println("<option value=\"project4\"> Программист </option>");
        out.println("<option value=\"project5\"> Аналитик </option>");
        out.println("</select><br><br>");
        out.println("<input type=\"submit\" value=\"Получить\">");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body >");
        out.println("</html >");
    }
}
