package MVC.persistence.repositories;


import MVC.persistence.entities.Phone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Created by varArg on 31.03.2015.
 */
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    @Query("select count(p) from Phone p")
    Long countPhone();

    @Query("select p from Phone p")
    List<Phone> findAllPhone(Pageable pageable);
}
