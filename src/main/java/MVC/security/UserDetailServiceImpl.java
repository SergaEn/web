package MVC.security;

import MVC.persistence.entities.Account;
import MVC.persistence.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository service;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = service.findByUsername(name);
        if (account == null) {
            throw new UsernameNotFoundException("no user found with " + name);
        }

        return new AccountUserDetails(account);
    }
}
