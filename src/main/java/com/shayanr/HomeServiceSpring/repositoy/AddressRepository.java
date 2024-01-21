package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.business.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {

}
