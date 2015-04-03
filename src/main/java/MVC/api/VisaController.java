package MVC.api;

import MVC.persistence.entities.Phone;

import MVC.persistence.entities.Visa;
import MVC.persistence.repositories.PhoneRepository;
import MVC.persistence.repositories.VisaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.GregorianCalendar;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by varArg on 02.04.2015.
 */
@RestController
@Transactional(readOnly = true)
public class VisaController {


    @Autowired
    private VisaRepository visaRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    @RequestMapping(value = "/api/payToVisa/{id:\\d+}", method = POST)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Phone> buyVisa(final @PathVariable Integer id, final @RequestBody Visa buyVisa, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        ResponseEntity<Phone> rv;
        Visa visa = visaRepository.findVisaCartNumber(buyVisa.getCartNumber());
        if (visa == null)
            return new ResponseEntity<Phone>(HttpStatus.CONFLICT);
        if (visa.getSumm() < buyVisa.getSumm()) {
            return new ResponseEntity<Phone>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (visa.getFirstName().equals(buyVisa.getFirstName())
                && visa.getLastName().equals(buyVisa.getLastName())
                && visa.getCartName().equals(buyVisa.getCartName())
                && ((GregorianCalendar) visa.getExpirationDate()).toZonedDateTime().getDayOfYear() == (((GregorianCalendar) buyVisa.getExpirationDate()).toZonedDateTime().getDayOfYear())
                && visa.getCvv().equals(buyVisa.getCvv())) {

            Double result = visa.getSumm() - buyVisa.getSumm();

            visaRepository.updateVisa(result, visa.getId());
            visa.setSumm(result);
            Phone phone = phoneRepository.findOne(id);
            phone.setCount(1);
            phoneRepository.saveAndFlush(phone);

            return new ResponseEntity<Phone>(phone, HttpStatus.OK);


        } else return new ResponseEntity<Phone>(HttpStatus.CONFLICT);


    }
}
