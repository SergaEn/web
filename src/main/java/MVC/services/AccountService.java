package MVC.services;


import MVC.persistence.entities.Account;

import MVC.persistence.entities.Visa;

import java.util.List;

public interface AccountService {
    public Account findAccount(Long id);
    public Account createAccount(Account data);
 /*   public Visa createVisa(Long accountId, Visa visa);*/
   /* public List<Visa> findVisasByAccount(Long accountId);*/
    public List<Account> findAllAccounts();
    public Account findByAccountName(String name);
}
