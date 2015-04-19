package MVC.persistence.repositories;

import MVC.persistence.entities.Account;
import MVC.persistence.entities.Cart;
import MVC.persistence.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by VarArg on 19.04.2015.
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByAccount(Account account);

}
