package MVC.persistence.repositories;

import MVC.persistence.entities.Account;
import MVC.persistence.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by VarArg on 19.04.2015.
 */
public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Basket findCartByAccount(Account account);

}
