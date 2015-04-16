package MVC.persistence.repositories;


import MVC.persistence.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select b from Account b where b.username = :username")
    Account findByUsername(@Param("username") String username);


}
