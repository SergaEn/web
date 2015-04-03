package MVC.persistence.repositories;

import java.util.Collection;

import MVC.persistence.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
  
  /* // @Query("select b from account b where b.username = :username")
    Collection<Account> findByUsername( String username);
    */
    @Query("select b from Account b where b.username = :username")
    Account findByUsername(@Param("username") String username);


}
