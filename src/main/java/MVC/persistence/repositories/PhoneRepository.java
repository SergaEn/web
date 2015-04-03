package MVC.persistence.repositories;


import MVC.persistence.entities.Account;
import MVC.persistence.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

/**
 * Created by varArg on 31.03.2015.
 */
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

 /*   @Query("Select b from Phone b where b.cart.id = :cartId")
    List<Phone> findPhonesByCart(@Param("cartId") Integer cartId);
*/
}
