package com.shayanr.HomeServiceSpring.controller;

import com.shayanr.HomeServiceSpring.dto.CustomerRequestDto;
import com.shayanr.HomeServiceSpring.dto.CustomerResponseDto;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.mapper.CustomerMapper;
import com.shayanr.HomeServiceSpring.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto>registerCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto){
        Customer customer = CustomerMapper.INSTANCE.requestDtoToModel(customerRequestDto);
        customerService.signUp(customer);
        CustomerResponseDto customerResponseDto = CustomerMapper.INSTANCE.modelToResponse(customer);
        return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
    }
}
