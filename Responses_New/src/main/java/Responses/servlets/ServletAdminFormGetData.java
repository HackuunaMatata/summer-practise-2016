package Responses.servlets;

import Responses.dao.DefaultAnswersDao;
import Responses.dbEntities.DefaultAnswersEntity;
import Responses.utils.DocxWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServletAdminFormGetData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int project = -1, post = -1;
        String name = "Forms";
        if ("on".equals(request.getParameter("data"))) {
            if ("on".equals(request.getParameter("projectNameBox"))) {
                System.out.println();
                project = Integer.parseInt(request.getParameter("projectName"));
                name += project;
            }
            if ("on".equals(request.getParameter("userPostBox"))) {
                post = Integer.parseInt(request.getParameter("userPost"));
                name += post;
            }
        }
        name += ".docx";
        String path = getServletContext().getRealPath("/") + name;
        if (project == -1 && post == -1)
            DocxWriter.WriteForms(path);
        else
            DocxWriter.WriteFormsByProjectAndPost(path, project, post);
        response.sendRedirect(request.getContextPath() + name);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println(" <title>Получение данных</title>");
        out.println("</head>");
        out.println("<body onLoad=\"a()\">");
        out.println("<h1>Страница администратора</h1><br>");
        out.println("<h2> Получение данных </h2><br>");
        out.println("<form action=\"\" method=\"post\">");
        out.println("<input type=\"radio\" name=\"data\" id=\"allData\" onChange=\"a()\">");
        out.println("<label for=\"data\"> Получить все данные </label><br><br>");
        out.println("<input type=\"radio\" name=\"data\" id=\"getData\" onChange=\"a()\">");
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
        DefaultAnswersDao answersDao = new DefaultAnswersDao();
        List<DefaultAnswersEntity> answers = answersDao.getAnswers();
        int id = answers.get(0).getQuestionId();
        int j = 0;
        while(j < answers.size() && id == answers.get(j).getQuestionId()){
            out.println("<option value=\"" + String.valueOf(j) + "\">" + answers.get(j).getValue() + " </option>");
            j++;
        }
        out.println("</select><br>");
        out.println("<input type=\"checkbox\" name=\"userPostBox\" id=\"userPostBox\" onClick=\"a()\">");
        out.println("<label for=\"userPost\"> Должность </label>");
        out.println("<select name=\"userPost\" id=\"userPost\"  > ");
        id = answers.get(j).getQuestionId();
        while(j < answers.size() && id == answers.get(j).getQuestionId()){
            out.println("<option value=\"" + String.valueOf(j) + "\">" + answers.get(j).getValue() + " </option>");
            j++;
        }
        out.println("</select><br><br>");
        out.println("<input type=\"submit\" value=\"Получить\">");
        out.println("</form>");
        out.println("<a href=\"ServletAdmin\"> Основное меню </a><br>");
        out.println("</body >");
        out.println("</html >");
    }
}
