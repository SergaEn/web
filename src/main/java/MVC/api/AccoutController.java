package MVC.api;

import MVC.persistence.entities.Account;
import MVC.persistence.repositories.AccountRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Transactional()
public class AccoutController {
    private final Logger log = Logger.getLogger(AccoutController.class);

    @Autowired
    AccountRepository accountRepository;
    @Transactional(readOnly = true)
    @RequestMapping(value = "/api/accout/", method = POST)
    public ResponseEntity<Account> login(final @RequestBody Account account, final BindingResult bindingResult) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        Account acc = accountRepository.findByUsername(account.getUsername());
        if (acc == null)
            return new ResponseEntity<Account>(HttpStatus.CONFLICT);

        if (acc.getPassword().equals(encoder.encodePassword(account.getPassword(), null))) {
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }

        return new ResponseEntity<Account>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @RequestMapping(value = "/api/register/", method = POST)
    public ResponseEntity<Account> register(final @RequestBody Account account, final BindingResult bindingResult) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        Account acc = accountRepository.findByUsername(account.getUsername());
        if (acc != null)
            return new ResponseEntity<Account>(HttpStatus.CONFLICT);


        if (account.getUsername().length() > 1 && account.getPassword().length() > 4) {
            account.setPassword(encoder.encodePassword(account.getPassword(), null));
            account.setGrantedAuthority("ROLE_USER");
            accountRepository.save(account);
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }

        return new ResponseEntity<Account>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
