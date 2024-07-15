package com.aluraforo.challenge.model.repository;

import com.aluraforo.challenge.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    UserDetails findByLogin(String username);
}
