package MVC.api;

import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.PhoneRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by varArg on 31.03.2015.
 */
@RestController
public class PhoneController {
    private static final Logger log = Logger.getLogger(PhoneController.class);
    @Autowired
    PhoneRepository phoneRepository;

    @RequestMapping(value = "/api/phones/{id:\\d+}", method = GET)
   /* @PreAuthorize("isAuthenticated()")*/
    Phone getPhones(final @PathVariable Integer id) {
        final Phone phone = phoneRepository.findOne(id);
        log.info(phone.toString());
        return phone;
    }

    @RequestMapping(value = "/api/phones", method = GET)
    /*@PreAuthorize("isAuthenticated()")*/
    List<Phone> getPhones() {
        List<Phone> phone = phoneRepository.findAll();
        return phone;
    }

}
