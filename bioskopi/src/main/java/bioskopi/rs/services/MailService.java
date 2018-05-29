package bioskopi.rs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;


    public  void sendNotification(String subject, String message) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("666.null.null.null@gmail.com");
        mail.setFrom("danijelradakovic1996@gmail.com");
        mail.setSubject(subject);
        mail.setText(message);

        mailSender.send(mail);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("666.null.null.null@gmail.com");
        mailSender.setPassword("666nullnull");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
