package MVC.persistence.repositories;


import MVC.persistence.entities.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by varArg on 31.03.2015.
 */

public interface VisaRepository extends JpaRepository<Visa, Integer> {


    @Query("select b from Visa b where b.cartNumber = :cartNumber")
    Visa findVisaCartNumber(@Param("cartNumber") String cartNumber);

    @Modifying
    @Query("update Visa v set v.summ = ?1 where v.id = ?2")
    int updateVisa(Double summ, Integer id);

}
