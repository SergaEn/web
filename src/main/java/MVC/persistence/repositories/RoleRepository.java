package MVC.persistence.repositories;


import MVC.persistence.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by en on 16.04.2015.
 */
public interface RoleRepository extends JpaRepository<UserRole, Integer> {
}
