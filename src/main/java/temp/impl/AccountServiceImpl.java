package temp.impl;

import MVC.persistence.entities.Account;

import MVC.persistence.repositories.AccountRepository;

import MVC.persistence.repositories.VisaRepository;
import MVC.services.exceptions.AccountExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



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


    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findByUsername(name);
    }
}
