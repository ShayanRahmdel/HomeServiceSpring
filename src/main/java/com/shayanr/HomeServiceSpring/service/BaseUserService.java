package com.shayanr.HomeServiceSpring.service;

import com.shayanr.HomeServiceSpring.entity.users.User;

import java.util.Optional;

public interface BaseUserService <T extends User>{

    void signUp(T user);

    Optional<T> findById(Integer id);

    void deleteById(Integer id);


    void changePassword(String email, String oldPassword,String newPassword);
}
