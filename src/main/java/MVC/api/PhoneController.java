package MVC.api;

import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.PhoneRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by varArg on 31.03.2015.
 */
@RestController
@RequestMapping(value = "/api/phones")
public class PhoneController {
    private static final Logger log = Logger.getLogger(PhoneController.class);
    @Autowired
    private PhoneRepository phoneRepository;

    @RequestMapping(value = "/{id:\\d+}", method = GET)
   /* @PreAuthorize("isAuthenticated()")*/
    Phone getPhones(final @PathVariable Integer id) {
        final Phone phone = phoneRepository.findOne(id);
        log.info(phone.toString());
        return phone;
    }

    @RequestMapping(method = GET)
    /*@PreAuthorize("isAuthenticated()")*/
    List<Phone> getPhones() {
        List<Phone> phone = phoneRepository.findAll();
        return phone;
    }

    @RequestMapping(method = POST)
    public List<Phone> getAllPhonesToCart(final @RequestBody List<String> setId, final BindingResult bindingResult) {
        Phone phone;
        final List<Phone> phones = new ArrayList<Phone>();
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        Set<Integer> integers = new HashSet<Integer>();
        for (String str : setId) {
            Integer i = Integer.parseInt(str);
            integers.add(i);
        }

        for (Integer i : integers) {
            phone = phoneRepository.findOne(i);
            phones.add(phone);
        }


        return phones;
    }



}
