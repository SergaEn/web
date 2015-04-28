package MVC.api;

import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.PhoneRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by varArg on 31.03.2015.
 */
@RestController
@RequestMapping(value = "/api")
public class PhoneController {
    private static final Logger log = Logger.getLogger(PhoneController.class);
    @Autowired
    private PhoneRepository phoneRepository;


    @RequestMapping(value = "/phone/{id:\\d+}")
    Phone getPhonesById(final @PathVariable Integer id) {
        final Phone phone = phoneRepository.findOne(id);
        log.info(phone.toString());
        return phone;
    }

    @RequestMapping(value = "/phones/{pageNumber}")
    Page<Phone> getPhones(final @PathVariable Integer pageNumber) {
        log.info("Количество : " + phoneRepository.countPhone());
        Page<Phone> phone = phoneRepository.findAll(new PageRequest(pageNumber - 1, 8, Sort.Direction.ASC, "id"));
        return phone;
    }

    @RequestMapping()
    Page<Phone> getPhones() {
        Page<Phone> phone = phoneRepository.findAll(new PageRequest(0, 8, Sort.Direction.ASC, "id"));
        return phone;
    }


}
