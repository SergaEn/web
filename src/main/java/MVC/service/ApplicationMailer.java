package MVC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by en on 22.04.2015.
 */
@Service("mailService")
public class ApplicationMailer {
    @Autowired
    private MailSender mailSender;


    public void sendMail(final String to, final String subject, final String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

}