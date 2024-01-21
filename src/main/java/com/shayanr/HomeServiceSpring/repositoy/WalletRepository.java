package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.business.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Integer> {
}
