package Responses.servlets;

import Responses.dao.*;
import Responses.dbEntities.*;
import Responses.utils.HibernateSessionFactory;
import org.hibernate.Session;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ServletFormsClient extends HttpServlet {
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

                if (answer.length() != 0 && answer != null) {
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

        // Отправка на email - не работает
        AdminDao adminDao = new AdminDao();
        List<AdminEntity> admins = adminDao.getAdmin();
        if (admins.size() != 0){
            String to = admins.get(0).geteMail();
            System.out.println(to);
            String from = "testowaya.p@yandex.ru";
            Properties properties = new Properties();
                    //System.getProperties();
            properties.put("mail.smtp.host", "smtp.yandex.ru");
            properties.put("mail.smtp.socketFactory.port", 465);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", 465);
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties,

                    new javax.mail.Authenticator(){

                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication("testowaya.p@yandex.ru", "12345pochta");
                        }
                    }
            );
            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));
                message.setSubject("Новый отзыв");
                message.setText("Вы получили новый отзыв");
                Transport.send(message);
            }catch (MessagingException mex) {
                mex.printStackTrace();
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

        int countQwID_0 = 0;
        while (answers.get(countQwID_0).getQuestionId() == 0) {
            countQwID_0++;
        }
        int countQwID_4 = answers.size() - countQwID_0;


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
                    if (i == 0) {
                        for (int j = 0; j < countQwID_0; j++) {
                            out.println("<option value=\"" + String.valueOf(j + i) +
                                    "\">" + answers.get(j + i).getValue() + "</option>");
                        }
                    } else {
                        out.println("<option value=\"\">  </option>");
                        for (int j = 0; j < countQwID_4; j++) {
                            out.println("<option value=\"" + String.valueOf(j + countQwID_0) +
                                    "\">" + answers.get(j + countQwID_0).getValue() + "</option>");
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
                if (questions.get(i).getIsRequired() == true) {
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