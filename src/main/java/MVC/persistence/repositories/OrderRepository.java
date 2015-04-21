package MVC.persistence.repositories;

import MVC.persistence.entities.Account;
import MVC.persistence.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by en on 20.04.2015.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findOrderByUuid(String uuid);

    List<Order> findOrderByAccount(Account account);
}
