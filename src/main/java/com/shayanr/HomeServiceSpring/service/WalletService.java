package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Wallet;



public interface WalletService{

    Wallet save(Wallet wallet);

    Wallet findById(Integer id);


}
