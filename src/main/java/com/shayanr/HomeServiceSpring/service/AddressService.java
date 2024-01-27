package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Address;

public interface AddressService  {


    Address saveAddress(Address address);

    void deleteAll();
}
