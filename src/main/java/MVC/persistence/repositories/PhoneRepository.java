package MVC.persistence.repositories;


import MVC.persistence.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by varArg on 31.03.2015.
 */
public interface PhoneRepository extends JpaRepository<Phone, Integer> {


}
