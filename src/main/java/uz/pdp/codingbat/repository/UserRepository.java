package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
}
