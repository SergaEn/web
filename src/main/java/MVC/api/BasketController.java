package MVC.api;

import MVC.persistence.entities.Account;
import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.AccountRepository;
import MVC.persistence.repositories.PhoneRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Created by en on 16.04.2015.
 */

@RestController
@RequestMapping(value = "/api/basket")
@Transactional()
public class BasketController {
    private final Logger log = Logger.getLogger(BasketController.class);
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PhoneRepository phoneRepository;


    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(method = POST)
    public ResponseEntity<List<Phone>> getAllPhonesToBasket(final @RequestParam(value = "name") String name, final @RequestBody List<String> listId, final BindingResult bindingResult) {
        final Set<String> integers = new HashSet<String>();
        final List<Phone> phones = new ArrayList<Phone>();
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        Account account = accountRepository.findByUsername(name);
        if (account == null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountRepository.findByUsername(details.getUsername());
            if (loggedIn.getId() != account.getId()) return new ResponseEntity(HttpStatus.FORBIDDEN);

            integers.addAll(listId);
            for (String str : integers) {
                phones.add(phoneRepository.findOne(Integer.parseInt(str)));
            }
            return new ResponseEntity<List<Phone>>(phones, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


}
