package MVC.service;

import MVC.persistence.entities.Order;
import MVC.util.AppearanceLetters;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * Created by en on 23.04.2015.
 */
@Service
public class Receiver {
    private final Logger log = Logger.getLogger(Receiver.class);

    @Autowired
    ApplicationMailer applicationMailer;

    @Autowired
    AppearanceLetters appearanceLetters;

    @JmsListener(destination = "mailbox-destination", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(final Order order) {
        log.info("Received <" + order.toString() + ">");

        try {
            applicationMailer.sendMail(order.getEmail().trim(), "WebShop", appearanceLetters.sendMailToUser(order));
        } catch (Exception e) {
            log.warn(e);
        }
    }
}
