package MVC.persistence.repositories;

import MVC.persistence.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by en on 20.04.2015.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findOrderByUuid(String uuid);
}
