package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.repositoy.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl {
    private WalletRepository walletRepository;
}
