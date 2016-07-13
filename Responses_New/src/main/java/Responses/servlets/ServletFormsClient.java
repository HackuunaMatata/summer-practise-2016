package Responses.servlets;

import Responses.dao.AnswersDao;
import Responses.dao.DefaultAnswersDao;
import Responses.dao.FormsDao;
import Responses.dao.QuestionsDao;
import Responses.dbEntities.AnswersEntity;
import Responses.dbEntities.DefaultAnswersEntity;
import Responses.dbEntities.FormsEntity;
import Responses.dbEntities.QuestionsEntity;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class ServletFormsClient extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Date date = new Date();
        FormsDao formsDao = new FormsDao();
        List<FormsEntity> forms = formsDao.getForms();
        DefaultAnswersDao defaultAnswersDao = new DefaultAnswersDao();
        List<DefaultAnswersEntity> defAnswers = defaultAnswersDao.getAnswers();
        int newId = 0;
        if (forms.size() != 0) {
            newId = Integer.valueOf(forms.get(forms.size() - 1).getId()) + 1;
        }
        QuestionsDao questionsDao = new QuestionsDao();
        List<QuestionsEntity> questions = questionsDao.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getIsActive() == true) {
                Session session = HibernateSessionFactory.getSessionFactory().openSession();
                session.beginTransaction();

                FormsEntity formsEntity = new FormsEntity();
                formsEntity.setId(newId);
                formsEntity.setQuestionId(i);
                formsEntity.setDateSent(date);

                AnswersDao answersDao = new AnswersDao();
                List<AnswersEntity> answers = answersDao.getAnswers();
                Integer answerId = answers.size();
                String answer = request.getParameter(String.valueOf(questions.get(i).getId()));

                if (answer != "" && answer != null) {
                    AnswersEntity answerEntity = new AnswersEntity();
                    answerEntity.setId(answerId);
                    if ((i != 0) && (i != 4)) {
                        answerEntity.setValue(answer);
                    } else {
                        answerEntity.setValue(defAnswers.get(Integer.valueOf(answer)).getValue());
                    }
                    formsEntity.setAnswerId(answerId);
                    session.save(formsEntity);
                    session.save(answerEntity);
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

        DefaultAnswersDao answersDao = new DefaultAnswersDao();
        List<DefaultAnswersEntity> answers = answersDao.getAnswers();


        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getIsActive() == true) {
                out.println("<li><a href=\"#" + questions.get(i).getId() + "\">" + questions.get(i).getValue() + "</a> <br>");
            }
        }
        out.println("</ol>");
        out.println("</fieldset>");
        out.println("</p>");
        out.println("<form action=\"\" onsubmit=\"return empty_form()\" method=\"POST\">");
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getIsActive() == true) {
                if ((i == 0) || (i == 4)) {
                    out.println("<select name=\"" + questions.get(i).getId() + "\" id=\"" +
                            questions.get(i).getId() + "\"> ");
                    if (i == 4) {
                        out.println("<option value=\"\">  </option>");
                    }
                    for (int j = 0; j < 5; j++) {
                        if (i == 0) {
                            out.println("<option value=\"" + String.valueOf(j + i) +
                                    "\">" + answers.get(j + i).getValue() + "</option>");
                        } else {
                            out.println("<option value=\"" + String.valueOf(j + i + 1) +
                                    "\">" + answers.get(j + i + 1).getValue() + "</option>");
                        }
                    }
                    out.println("</select><br>");
                } else if ((i > 4) && (i < 9)) {
                    out.println("<p>" + questions.get(i).getValue() + ": </p><textarea name=\"" +
                            questions.get(i).getId() + "\" id=\"" + questions.get(i).getId() +
                            "\" cols=\"80\" rows=\"10\" maxlength=\"4096\" placeholder=\"Место для отзыва\" spellcheck=\"true\"></textarea><br>");
                } else {
                    out.println("<label for=\"" + questions.get(i).getId() + "\">" +
                            questions.get(i).getValue() + "</label><input type=\"text\" name=\"" +
                            questions.get(i).getId() + "\" id=\"" + questions.get(i).getId() +
                            "\" placeholder=\"" + questions.get(i).getValue() + "\" ");
                    if(questions.get(i).getIsRequired() == true){
                        out.println(" required ");
                    }
                    out.println("> <br>");
                }
                out.println("<script>");
                out.println("function empty_form () {");
                out.println("var txt1 = document.getElementById('5').value;");
                out.println("var txt2 = document.getElementById('6').value;");
                out.println("var txt3 = document.getElementById('7').value;");
                out.println("var txt4 = document.getElementById('8').value;");
                out.println(" if((txt1 == '')&&(txt2 == '')&&(txt3 == '')&&(txt4 == '')){");
                out.println("alert('Напишите хотя бы один отзыв!');");
                out.println("return false;}");
                out.println("return true;}");
                out.println("</script>");
            }
        }
        out.println("<input type=\"submit\" value=\"Отправить отзыв\">");
        // out.println("<input type=\"reset\" value=\"Очистить анкету\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}