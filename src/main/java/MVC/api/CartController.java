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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Created by en on 16.04.2015.
 */

@RestController
@RequestMapping(value = "/api/cart")
@Transactional()
public class CartController {
    private final Logger log = Logger.getLogger(CartController.class);
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    OrderRepository orderRepository;


    @Deprecated
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


    @RequestMapping(method = POST)
    public ResponseEntity<List<Phone>> getAllPhonesToCart(final @RequestBody List<String> listId, final BindingResult bindingResult) {
        Phone phone;
        final List<Phone> phones = new ArrayList<Phone>();
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        Set<Integer> integers = new HashSet<Integer>();
        for (String str : listId) {
            Integer i = Integer.parseInt(str);
            integers.add(i);
        }

        for (Integer i : integers) {
            phone = phoneRepository.findOne(i);
            phones.add(phone);
        }


        return new ResponseEntity<List<Phone>>(phones, HttpStatus.MULTI_STATUS.OK);
    }


}
