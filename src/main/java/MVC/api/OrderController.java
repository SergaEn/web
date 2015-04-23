package MVC.api;

import MVC.persistence.entities.Account;
import MVC.persistence.entities.Order;
import MVC.persistence.entities.Phone;
import MVC.persistence.repositories.AccountRepository;
import MVC.persistence.repositories.OrderRepository;
import MVC.service.JmsMessageSender;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by en on 20.04.2015.
 */

@RestController
@RequestMapping(value = "/api/order")
@Transactional()
public class OrderController {
    private final HashMap<Integer, Integer> countPhone = new HashMap();
    private final Map<Integer, Integer> synmap = Collections.synchronizedMap(countPhone);
    private final Logger log = Logger.getLogger(OrderController.class);
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JmsMessageSender jmsMessageSender;


    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOrder(final @RequestParam(value = "name") String name, final @RequestBody String orderPhones) {

        log.info("JSON : " + orderPhones);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JsonNode node = null;
        try {
            node = mapper.readTree(orderPhones);
        } catch (IOException e) {
            log.warn(e, e);
        }

        Phone[] phones = mapper.convertValue(node.get("phones"), Phone[].class);
        Order order = mapper.convertValue(node.get("order"), Order.class);
        UUID orderUUid = UUID.randomUUID();

        Account account = accountRepository.findByUsername(name);
        if (account == null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            Account loggedIn = accountRepository.findByUsername(details.getUsername());
            if (loggedIn == null || loggedIn.getId() != account.getId())
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            List<Phone> list = Arrays.asList(phones);
            for (Phone phone : list)
                synmap.put(phone.getId(), phone.getCount());
            order.setUuid(orderUUid.toString());
            order.setPhoneList(list);
            order.setAccount(account);

            jmsMessageSender.send("save-order", order);
            jmsMessageSender.send("mailbox-destination", order);


            return new ResponseEntity(HttpStatus.OK);
        } else {
            new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Order>> getAllOrdersByAccount(final @RequestParam(value = "name") String name) {
        Account account = accountRepository.findByUsername(name);
        if (account == null) return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Order> orders = orderRepository.findOrderByAccount(account);
            for (Order order : orders)
                log.info("ORDER: " + order.toString());
            return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Phone>> getAllPhonesByOrder(final @PathVariable("uuid") String uuid) {

            Order order = orderRepository.findOrderByUuid(uuid);

        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
            Account account = order.getAccount();

            List<Phone> phones = order.getPhoneList();
        for (Phone phone : phones) {
            phone.setCount(synmap.get(phone.getId()));
            log.info("phone.getCount() :" + phone.getCount());
        }

            return new ResponseEntity<List<Phone>>(phones, HttpStatus.OK);

    }


}
