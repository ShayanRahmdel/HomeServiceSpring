package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.repositoy.WalletRepository;
import com.shayanr.HomeServiceSpring.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
}
