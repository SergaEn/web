package MVC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Created by en on 22.04.2015.
 */
@Service("mailService")
public class ApplicationMailer {
    @Autowired
    private JavaMailSender mailSender;


    public void sendMail(final String to, final String subject, final String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject(subject);
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setText(body, true);
        mailSender.send(message);

    }

}