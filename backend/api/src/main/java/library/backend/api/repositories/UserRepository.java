package library.backend.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import library.backend.api.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrPhoneNo(String email, String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNo(String phoneNo);
}
