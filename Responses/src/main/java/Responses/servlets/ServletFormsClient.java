package Responses.servlets;

import Responses.dao.AnswersDao;
import Responses.dao.QuestionsDao;
import Responses.dbEntities.AnswersEntity;
import Responses.dbEntities.QuestionsEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServletFormsClient extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Форма для пользователя</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> Анкета для отзывов </h1>");
        out.println("<a href=\"../../java/ServletAdmin\"> Настройки администратора </a> <br>");
        out.println("<p>");
        out.println("<fieldset>");
        out.println("<legend> Содержание анкеты: </legend>");
        out.println("<ol>");

        QuestionsDao questionsDao = new QuestionsDao();
        List<QuestionsEntity> questions = questionsDao.getQuestions();

        AnswersDao answersDao = new AnswersDao();
        List<AnswersEntity> answers = answersDao.getAnswers();

        for (int i = 0; i < questions.size() ; i++) {
            if (questions.get(i).getIsActive() == true) {
                out.println("<li><a href=\"#" + questions.get(i).getId() + "\">" + questions.get(i).getValue() + "</a> <br>");
            }
        }
        out.println("</ol>");
        out.println("</fieldset>");
        out.println("</p>");
        out.println("<form action=\"\" method=\"POST\">");
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getIsActive() == true) {
                if ((i == 0) || (i == 4)) {
                    out.println("<select name=\"" + questions.get(i).getId() + "\" id=\"" +
                            questions.get(i).getId() + "\"> ");
                    for (int j = 0; j < 5; j++) {
                        out.println("<option value=\"" + answers.get(j + i).getId() +
                                "\">" + answers.get(j + i).getValue() + "</option>");
                    }
                    out.println("</select><br>");
                } else if ((i > 0) && (i < 4)) {
                    out.println("<label for=\"" + questions.get(i).getId() + "\">" +
                            questions.get(i).getValue() + "</label><input type=\"text\" name=\"" +
                            questions.get(i).getId() + "\" id=\"" + questions.get(i).getId() +
                            "\" placeholder=\"" + questions.get(i).getValue() + "\" > <br>");
                } else {
                    out.println("<p>" + questions.get(i).getValue() + ": </p><textarea name=\"" +
                            questions.get(i).getId() + "\" id=\"" + questions.get(i).getId() +
                            "\" cols=\"80\" rows=\"10\" maxlength=\"4096\" placeholder=\"Место для отзыва\" spellcheck=\"true\"></textarea><br>");
                }
            }
        }
        out.println("<input type=\"submit\" value=\"Отправить отзыв\">");
        out.println("<input type=\"reset\" value=\"Очистить анкету\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}