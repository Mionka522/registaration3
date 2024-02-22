package com.example.registration.repositories;


import com.example.registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);


}
