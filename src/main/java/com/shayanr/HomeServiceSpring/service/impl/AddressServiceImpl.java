package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.repositoy.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl {
    private AddressRepository addressRepository;


}
