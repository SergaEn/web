package MVC.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import MVC.services.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import MVC.persistence.entities.Account;


@Controller

public class HomeController {
	private static final Logger log = Logger.getLogger(HomeController.class);
	@Autowired
    AccountService serviceAccount;

	  @RequestMapping("/login")
	    public String greeting( Model model) {
	        return "login";
	    }
	  
		@RequestMapping("/users")
        @PreAuthorize("isAuthenticated()")
		public String showHome(Model model, HttpSession session) {
			
			//Start up. Get number of followers and following and store in model.
		
					
			//Populate the session object with a currently logged Spitter.
			String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
			Account loggedSpitter = serviceAccount.findByAccountName(loggedUsername);
			session.setAttribute("loggedSpitter", loggedSpitter);
			log.info("loggedSpitter  "+loggedSpitter);
			log.info("loggedUsername "+loggedUsername);
			model.addAttribute("loggedUsername", loggedUsername);
			//Spittle, so as the user can create a new message.
		//	model.addAttribute("spittle", new Spittle());
			
			//Get recent spittles from people you follow.
			List<Account> users = serviceAccount.findAllAccounts();
		log.info("users "+users.toArray());
			if(users.size() != 0) {
				
					model.addAttribute("users", users);
			}
			
			return "hello";
		}

}
