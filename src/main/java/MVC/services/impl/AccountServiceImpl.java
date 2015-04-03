package MVC.services.impl;

import MVC.persistence.entities.Account;

import MVC.persistence.entities.Visa;
import MVC.persistence.repositories.AccountRepository;

import MVC.persistence.repositories.VisaRepository;
import MVC.services.AccountService;
import MVC.services.exceptions.AccountDoesNotExistException;
import MVC.services.exceptions.AccountExistsException;
import MVC.services.exceptions.VisaExistsException;
import MVC.util.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Repository

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private VisaRepository visaRepo;



    @Override
    public Account findAccount(Long id) {
        return accountRepo.findOne(id);
    }

    @Transactional
    @Override
    public Account createAccount(Account data) {
        Account account = accountRepo.findByUsername(data.getUsername());
        if(account != null)
        {
            throw new AccountExistsException();
        }

       /* Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        data.setPassword(encoder.encodePassword(account.getPassword(), null));*/
        accountRepo.save(data);
        return data;
    }


/*    @Override
    public List<Visa> findVisasByAccount(Long accountId) {
        Account account = accountRepo.findOne(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }

        return visaRepo.findVisasByAccount(accountId);
    }*/

    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findByUsername(name);
    }
}
