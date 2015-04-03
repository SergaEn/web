package MVC.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import MVC.persistence.repositories.AccountRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import MVC.persistence.entities.Account;


@Controller
@Deprecated
public class HomeController {
    private static final Logger log = Logger.getLogger(HomeController.class);
    @Autowired
    AccountRepository serviceAccount;

    @RequestMapping("/login")
    public String greeting(Model model) {
        return "login";
    }

    @RequestMapping("/users")
    @PreAuthorize("isAuthenticated()")
    public String showHome(Model model, HttpSession session) {

        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account loggedSpitter = serviceAccount.findByUsername(loggedUsername);
        session.setAttribute("loggedSpitter", loggedSpitter);
        log.info("loggedSpitter  " + loggedSpitter);
        log.info("loggedUsername " + loggedUsername);
        model.addAttribute("loggedUsername", loggedUsername);
        List<Account> users = serviceAccount.findAll();
        log.info("users " + users.toArray());
        if (users.size() != 0) {

            model.addAttribute("users", users);
        }

        return loggedUsername;
    }

}
