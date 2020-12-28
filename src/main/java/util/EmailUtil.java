package util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import java.util.Properties;

/**
 * This is simple servlet that demonstrates sending email using package javax.mail.*
 *
 * @author Eugene Suleimanov
 */

public class EmailUtil {

    // need special google admin account
    private final String SENDER_EMAIL_ADDRESS = "...";
    private final String SENDER_EMAIL_PASSWORD = "...";
    private final String SENDER_HOST = "smtp.gmail.com";
    private final String SENDER_PORT = "587";
    private final String RECEIVER_EMAIL_ADDRESS = "...";

    public void sendSimpleMail(String subject, String text) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SENDER_HOST);
        properties.put("mail.smtp.port", SENDER_PORT);
        properties.put("mail.from", SENDER_EMAIL_ADDRESS);
        properties.put("mail.smtp.password", SENDER_EMAIL_PASSWORD);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL_ADDRESS, SENDER_EMAIL_PASSWORD);
                    }
                });


        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(RECEIVER_EMAIL_ADDRESS));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);

    }
}