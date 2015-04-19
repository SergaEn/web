package MVC.api;

import MVC.persistence.entities.Phone;
import MVC.persistence.entities.Visa;
import MVC.persistence.repositories.PhoneRepository;
import MVC.persistence.repositories.VisaRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
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

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by varArg on 02.04.2015.
 */
@RestController
@Transactional()
public class VisaController {
    private static final Logger log = Logger.getLogger(VisaController.class);

    @Autowired
    private VisaRepository visaRepository;
    @Autowired
    private PhoneRepository phoneRepository;


    @RequestMapping(value = "/api/buyPhone/{id:\\d+}", method = POST)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Phone> buyVisa(final @PathVariable Integer id, final @RequestBody Visa buyVisa, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }



        log.info(buyVisa.toString());
        ResponseEntity<Phone> rv;
        Visa visa = visaRepository.findVisaCartNumber(buyVisa.getCartNumber());
        log.info(visa);
        if (visa == null)
            return new ResponseEntity<Phone>(HttpStatus.CONFLICT);
        if (visa.getSumm() < buyVisa.getSumm()) {
            return new ResponseEntity<Phone>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (visa.getFirstName().equals(buyVisa.getFirstName())
                && visa.getLastName().equals(buyVisa.getLastName())
                && visa.getCartName().equals(buyVisa.getCartName())
                && DateUtils.isSameDay(visa.getExpirationDate(), buyVisa.getExpirationDate())
                && visa.getCvv().equals(buyVisa.getCvv())) {

            log.info("Проверка карты " + visa.toString() + " прошла успешно");
            Double result = visa.getSumm() - buyVisa.getSumm();

            try {
                visaRepository.updateVisa(result, visa.getId());
            } catch (Exception e) {
                log.warn(e.getMessage());
            }

            visa.setSumm(result);
            Phone phone = phoneRepository.findOne(id);
            log.info(phone.toString());
            phone.setCount(phone.getCount() - 1);

            try {
                phoneRepository.saveAndFlush(phone);
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
            return new ResponseEntity<Phone>(phone, HttpStatus.OK);


        } else return new ResponseEntity<Phone>(HttpStatus.CONFLICT);


    }

    @RequestMapping(value = "/api/addVisa/", method = POST)
    public ResponseEntity<Visa> addVisa(final @RequestBody Visa buyVisa, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        log.info(buyVisa.toString());
        ResponseEntity<Phone> rv;
        Visa visa = visaRepository.findVisaCartNumber(buyVisa.getCartNumber());
        log.info(visa);
        if (visa != null)
            return new ResponseEntity<Visa>(HttpStatus.CONFLICT);
        if (buyVisa.getCvv() == null || buyVisa.getCartNumber() == null || buyVisa.getExpirationDate() == null || buyVisa.getCartName() == null
                || buyVisa.getFirstName() == null || buyVisa.getLastName() == null) {
            return new ResponseEntity<Visa>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            visaRepository.save(buyVisa);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        log.info("Виза добавлена: " + buyVisa.toString());

        return new ResponseEntity<Visa>(buyVisa, HttpStatus.OK);


    }
}
