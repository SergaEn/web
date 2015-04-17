package MVC.api;

import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by en on 16.04.2015.
 */

@RestController
@RequestMapping(value = "/api/carts")
public class CartController {

    @Autowired
    AccountRepository accountRepository;


    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public List<Phone> getAllPhoneByAccountId(final @RequestBody List<?> list, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }


        return null;
    }

     /*   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountRepository.findByUsername(details.getUsername());
            log.info(loggedIn.toString());
            if (loggedIn==null) return new ResponseEntity<Phone>(HttpStatus.UNAUTHORIZED);
        }else{new ResponseEntity<Phone>(HttpStatus.UNAUTHORIZED);}*/

}
