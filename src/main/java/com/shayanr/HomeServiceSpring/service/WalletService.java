package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Wallet;

import java.util.Optional;

public interface WalletService{

    Wallet save(Wallet wallet);

    Optional<Wallet> findById(Integer id);


}
