package com.example.bootstarJpa.repository;

import com.example.bootstarJpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    <User extends Long> User save(User entity);

    User findByEmail(String email);
}
