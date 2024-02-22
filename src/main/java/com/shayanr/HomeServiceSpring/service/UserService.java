package com.shayanr.HomeServiceSpring.service;

import com.shayanr.HomeServiceSpring.entity.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);

}
