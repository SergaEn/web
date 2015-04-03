package MVC.api;

import java.util.Collection;
import java.util.GregorianCalendar;

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

import MVC.persistence.entities.Account;
import MVC.persistence.repositories.AccountRepository;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccoutController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(value = "/api/accout/", method = POST)
    public ResponseEntity<Account> login(final @RequestBody Account account, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        Account acc = accountRepository.findByUsername(account.getUsername());
        if (acc == null)
            return new ResponseEntity<Account>(HttpStatus.CONFLICT);

        if (acc.getPassword().equals(account.getPassword())) {
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }

        return new ResponseEntity<Account>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @RequestMapping(value = "/api/register/", method = POST)
    @Transactional(readOnly = true)
    public ResponseEntity<Account> register(final @RequestBody Account account, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        Account acc = accountRepository.findByUsername(account.getUsername());
        if (acc != null)
            return new ResponseEntity<Account>(HttpStatus.CONFLICT);


        if (account.getUsername().length() > 1 && account.getPassword().length() > 4) {
            accountRepository.save(account);
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }

        return new ResponseEntity<Account>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
