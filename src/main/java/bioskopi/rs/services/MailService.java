package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    public void sendUserActivation(RegisteredUser regUser) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(regUser.getEmail());
        mail.setFrom("666.null.null.null@gmail.com");
        mail.setSubject("Account activation");
        mail.setText("Click: http://localhost:8080/signup/"+regUser.getUsername());
        mailSender.send(mail);
    }

    public void sendInvitation(UserDTO user, String projId, String seatId) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("666.null.null.null@gmail.com");
        mail.setSubject("Invitation for projection");
        mail.setText("To accept, click on link: http://localhost:8080/tickets/invitation/"
                        + user.getId() + "+" + projId + "+" + seatId);
        mailSender.send(mail);
    }

    public void sendFriendRequest(List<UserDTO> users){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(users.get(1).getEmail());
        mail.setFrom("666.null.null.null@gmail.com");
        mail.setSubject("Friend request");
        mail.setText(users.get(0).getName() + " " + users.get(0).getSurname() + " wants to be friend with you. To accept" +
                "request, click: http://localhost:8080/friends/addFriend/" + users.get(0).getId() + "+" + users.get(1).getId());
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
