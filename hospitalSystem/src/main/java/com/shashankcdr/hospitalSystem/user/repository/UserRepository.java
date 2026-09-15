package com.shashankcdr.hospitalSystem.user.repository;

import com.shashankcdr.hospitalSystem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}