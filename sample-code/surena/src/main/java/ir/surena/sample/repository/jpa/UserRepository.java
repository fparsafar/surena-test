package ir.surena.sample.repository.jpa;

import ir.surena.sample.domain.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByExternalId(String externalId);



}
