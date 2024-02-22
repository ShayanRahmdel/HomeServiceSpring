package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.repositoy.UserRepository;
import com.shayanr.HomeServiceSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }




}
