package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.repositoy.AddressRepository;
import com.shayanr.HomeServiceSpring.service.AddressService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;


    @Override
    @Transactional
    public Address saveAddress(Address address) {
        try {
            addressRepository.save(address);
        }catch (NullPointerException | PersistenceException e){
            System.out.println("Error saving address");
        }
        return address;
    }

    @Override
    public void deleteAll() {
        addressRepository.deleteAll();
    }

}
