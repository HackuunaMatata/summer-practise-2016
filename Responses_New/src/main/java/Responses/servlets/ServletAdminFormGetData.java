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
        String project = null, post = null;
        String path = getServletContext().getRealPath("/") + "temp.docx";
        if ("on".equals(request.getParameter("data"))) {
            if ("on".equals(request.getParameter("projectNameBox")))
                project = request.getParameter("projectName");
            if ("on".equals(request.getParameter("userPostBox")))
                post = request.getParameter("userPost");
        }
        if (project == null && post == null)
            DocxWriter.WriteForms("path");
        else
            DocxWriter.WriteFormsByProjectAndPost(path, project, post);
//        response.setContentType("text/html;charset=UTF-8");
//        response.getWriter().println("<html><body>" + request.getContextPath() + "/temp.docx</body></html>");
        response.sendRedirect(request.getContextPath() + "/temp.docx");
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
        while(id == answers.get(j).getQuestionId()){
            out.println("<option value=\"" + String.valueOf(j) + "\">" + answers.get(j).getValue() + " </option>");
            j++;
        }
        out.println("</select><br>");
        out.println("<input type=\"checkbox\" name=\"userPostBox\" id=\"userPostBox\" onClick=\"a()\">");
        out.println("<label for=\"userPost\"> Должность </label>");
        out.println("<select name=\"userPost\" id=\"userPost\"  > ");
        j = 0;
        id = answers.get(j).getQuestionId();
        while(id == answers.get(j).getQuestionId()){
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
