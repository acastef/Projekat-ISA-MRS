package bioskopi.rs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class MailService {

    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender jms){
        this.mailSender = jms;
    }

}
