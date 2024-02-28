package com.shayanr.HomeServiceSpring.service;

import com.shayanr.HomeServiceSpring.entity.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService<T extends User> extends UserDetailsService {

    Optional<T> findByEmail(String email);

    void save(T t);


    void sendEmail(String emailAddress);

    ResponseEntity<?> confirmEmail(String confirmationToken);

    Double seeAmountWallet(Integer id);

}
