package MVC.api;

import MVC.persistence.entities.Account;
import MVC.persistence.entities.Cart;
import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.AccountRepository;
import MVC.persistence.repositories.CartRepository;
import MVC.persistence.repositories.OrderRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * Created by en on 16.04.2015.
 */

@RestController
@RequestMapping(value = "/api/cart")

public class CartController {
    private final Logger log = Logger.getLogger(CartController.class);
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;


    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<List<Phone>> savaParametrs(final BindingResult bindingResult, final @RequestParam(value="name") String name, final @RequestBody List<Phone> list) {

        if (bindingResult.hasErrors() || name==null) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        Account account = accountRepository.findByUsername(name);
        if(account == null) return  new ResponseEntity(HttpStatus.FORBIDDEN);

  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountRepository.findByUsername(details.getUsername());
            if (loggedIn==null || loggedIn.getId() != account.getId()) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

            Cart cart = new Cart(list, loggedIn);
            entityManager.merge(cart);
            return new ResponseEntity<List<Phone>> (HttpStatus.OK);
        }else{new ResponseEntity(HttpStatus.FORBIDDEN);}




        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @Transactional(readOnly = true)
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<Phone>> getPhonesToCart(final BindingResult bindingResult,final @RequestParam(value="name") String name) {
        if (bindingResult.hasErrors() || name==null) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
         Account account = accountRepository.findByUsername(name);
        if(account == null) return  new ResponseEntity(HttpStatus.FORBIDDEN);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountRepository.findByUsername(details.getUsername());
            if (loggedIn.getId() != account.getId()) return new ResponseEntity(HttpStatus.FORBIDDEN);

          Cart cart = cartRepository.findCartByAccount(loggedIn);
            List<Phone> phones = cart.getPhones();
            for(Phone phone: phones)
            log.info(phone);
         return new ResponseEntity<List<Phone>>(phones , HttpStatus.OK);
        }else{new ResponseEntity(HttpStatus.FORBIDDEN);}

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


}
