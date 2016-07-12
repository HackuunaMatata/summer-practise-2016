package Responses.servlets;

import Responses.dao.AnswersDao;
import Responses.dao.FormsDao;
import Responses.dao.QuestionsDao;
import Responses.dbEntities.AnswersEntity;
import Responses.dbEntities.FormsEntity;
import Responses.dbEntities.QuestionsEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ServletFormsClient extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        FormsDao formsDao = new FormsDao();
        List<FormsEntity> forms = formsDao.getForms();
        int newId = forms.size();
        QuestionsDao questionsDao = new QuestionsDao();
        List<QuestionsEntity> questions = questionsDao.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getIsActive() == true) {
                Session session = HibernateSessionFactory.getSessionFactory().openSession();
                session.beginTransaction();

                FormsEntity formsEntity = new FormsEntity();
                formsEntity.setId(newId);
                formsEntity.setQuestionId(i);

                AnswersDao answersDao = new AnswersDao();
                List<AnswersEntity> answers = answersDao.getAnswers();
                Integer answerId = answers.size();
                String answer = request.getParameter(String.valueOf(questions.get(i).getId()));


                if ((i != 0) && (i != 4)) {
                    AnswersEntity answerEntity = new AnswersEntity();
                    answerEntity.setId(answerId);
                    answerEntity.setValue(answer);
                    formsEntity.setAnswerId(answerId);
                    session.save(formsEntity);
                    session.save(answerEntity);
                }
                else{
                    formsEntity.setAnswerId(Integer.parseInt(answer));
                    session.save(formsEntity);
                }

                session.getTransaction().commit();
                session.close();
            }
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Success</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2> Ваш отзыв принят, спасибо :) </h2>");
        out.println("<a href=\"ServletFormsClient\">Написать новый отзыв </a><br>");
        out.println("</body>");
        out.println("</html>");
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
        out.println("<a href=\"ServletAdmin\"> Настройки администратора </a> <br>");
        out.println("<p>");
        out.println("<fieldset>");
        out.println("<legend> Содержание анкеты: </legend>");
        out.println("<ol>");


        QuestionsDao questionsDao = new QuestionsDao();
        List<QuestionsEntity> questions = questionsDao.getQuestions();

        AnswersDao answersDao = new AnswersDao();
        List<AnswersEntity> answers = answersDao.getAnswers();


        for (int i = 0; i < questions.size(); i++) {
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
                        if (i == 0) {
                            out.println("<option value=\"" + answers.get(j + i).getId() +
                                    "\">" + answers.get(j + i).getValue() + "</option>");
                        } else {
                            out.println("<option value=\"" + answers.get(j + i + 1).getId() +
                                    "\">" + answers.get(j + i + 1).getValue() + "</option>");
                        }
                    }
                    out.println("</select><br>");
                } else if (i > 4) {
                    out.println("<p>" + questions.get(i).getValue() + ": </p><textarea name=\"" +
                            questions.get(i).getId() + "\" id=\"" + questions.get(i).getId() +
                            "\" cols=\"80\" rows=\"10\" maxlength=\"4096\" placeholder=\"Место для отзыва\" spellcheck=\"true\"></textarea><br>");
                } else {
                    out.println("<label for=\"" + questions.get(i).getId() + "\">" +
                            questions.get(i).getValue() + "</label><input type=\"text\" name=\"" +
                            questions.get(i).getId() + "\" id=\"" + questions.get(i).getId() +
                            "\" placeholder=\"" + questions.get(i).getValue() + "\" > <br>");
                }
            }
        }
        out.println("<input type=\"submit\" value=\"Отправить отзыв\">");
        // out.println("<input type=\"reset\" value=\"Очистить анкету\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}