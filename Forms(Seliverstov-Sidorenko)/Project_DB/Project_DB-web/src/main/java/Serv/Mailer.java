package Serv;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

    String to = "mylllket@yandex.ua";
    String from = "myscappy2012@gmail.com";
    String host = "localhost";
    final String username = "myscappy2012@gmail.com";
    final String password = "QwErTy28121994";

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public Mailer() {
    }

    public void SendMessage() throws MessagingException {
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("mylllket@yandex.ua"));
        //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
        generateMailMessage.setSubject("Greetings from Crunchify..");
        String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        Transport transport = getMailSession.getTransport("smtp");

        transport.connect("smtp.gmail.com", "myscappy2012@gmail.com", "QwErTy28121994");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();

    }

    public void SendMessage2() throws MessagingException {

        String to = "mylllket@yandex.ua";

        String from = "myscappy2012@gmail.com";

        String host = "195.208.253.8";

        boolean sessionDebug = false;
        Properties props = System.getProperties();
        props.put("mail.host", host);
        props.put("mail.transport.protocol", "smtp");

        Session session = Session.getDefaultInstance(props, null);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject("Subject");
        msg.setSentDate(new Date());
        msg.setText("Hello");

        Transport.send(msg);

        System.out.println("Sent message successfully....");
    }
}
