package MVC.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import MVC.persistence.entities.Account;
import MVC.persistence.repositories.AccountRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@org.springframework.web.bind.annotation.RestController
public class AccoutController {

    @Autowired AccountRepository accountRepository;

	@RequestMapping(value = "/api/accaunt/{id:\\d+}",  method = GET)
	String getAccount(final @PathVariable Long id){
        Account account = accountRepository.findOne(id);
		return account.getUsername();
	}



}
