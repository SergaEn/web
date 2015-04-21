package MVC.api;

import MVC.persistence.entities.Account;
import MVC.persistence.entities.UserRole;
import MVC.persistence.repositories.AccountRepository;
import MVC.persistence.repositories.RoleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Transactional()
public class AccoutController {
    private final Logger log = Logger.getLogger(AccoutController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/api/accounts/", method = POST)
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

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/api/accounts/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccount(final @PathVariable Integer accountId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountRepository.findByUsername(details.getUsername());

            if (loggedIn.getId() == accountId) {
                return new ResponseEntity<Account>(loggedIn, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/api/accounts/", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        if (accounts != null) {

            return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/api/authedaccounts", method = RequestMethod.GET)
    public ResponseEntity<Account> getAuthorizedAccount() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountRepository.findByUsername(details.getUsername());

            if (loggedIn == null) {
                return new ResponseEntity<Account>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<Account>(loggedIn, HttpStatus.OK);
            }
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
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

            UserRole role = new UserRole(account, "USER");
            accountRepository.save(account);
            roleRepository.save(role);

            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }

        return new ResponseEntity<Account>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
