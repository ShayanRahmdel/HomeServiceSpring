package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.business.Wallet;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.WalletRepository;
import com.shayanr.HomeServiceSpring.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findById(Integer id) {
        return walletRepository.findById(id).orElseThrow(() -> new NotFoundException("Wallet not found"));
    }
}
