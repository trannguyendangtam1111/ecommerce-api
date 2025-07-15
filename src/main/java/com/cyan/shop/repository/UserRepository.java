package com.cyan.shop.repository;

import com.cyan.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByName(String name);
    //    void deleteByEmail(String email);
}
