package com.shayanr.HomeServiceSpring.controller;

import com.shayanr.HomeServiceSpring.dto.CustomerRequestDto;
import com.shayanr.HomeServiceSpring.dto.CustomerResponseDto;
import com.shayanr.HomeServiceSpring.dto.PasswordDto;
import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.mapper.CustomerMapper;
import com.shayanr.HomeServiceSpring.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @PutMapping("/change-password/{customerId}")
    public void changePassword(@PathVariable Integer customerId, @RequestBody PasswordDto passwordDto){
        customerService.changePassword(customerId, passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
    }

    @PostMapping("/create-order/{category}/{subduty}/{customerId}")
    public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrder order, @PathVariable Integer category,
                                                     @PathVariable Integer subduty,@PathVariable Integer customerId){
        return new ResponseEntity<>(customerService.createOrder(order,category,subduty,customerId),HttpStatus.CREATED);
    }

    @PostMapping("/create-address/{customerId}/{orderId}")
    public ResponseEntity<Address> createAddress(@RequestBody Address address, @PathVariable Integer customerId
    ,@PathVariable Integer orderId){
        return new ResponseEntity<>(customerService.createAddress(address,customerId,orderId),HttpStatus.CREATED);
    }

    @GetMapping("/see-suggest-by-price/{customerId}")
    public List<WorkSuggestion> seeSuggestByPrice(@PathVariable Integer customerId){
        return customerService.seeSuggestionsByPrice(customerId);
    }

    @GetMapping("/see-suggest-by-score/{customerId}")
    public List<WorkSuggestion> seeSuggestByScore(@PathVariable Integer customerId){
        return customerService.seeSuggestionsByExpertScore(customerId);
    }

    @PutMapping("/accept-suggest/{suggestId}")
    public void acceptSuggest(@PathVariable Integer suggestId){
        customerService.acceptSuggest(suggestId);
    }

    @PutMapping("/update-to-begin/{orderId}/{suggestionId}")
    public void updateToBegin(@PathVariable Integer orderId
            ,@PathVariable Integer suggestionId
            ,@RequestBody LocalDate date){
        customerService.updateOrderToBegin(orderId, suggestionId, date);
    }

    @PutMapping("/update-to-end/{orderId}")
    public void updateToEnd(@PathVariable Integer orderId){
        customerService.updateOrderToEnd(orderId);
    }

    @PostMapping("/payment-by-wallet/{orderId}/{suggestionId}")
    public ResponseEntity<CustomerOrder> payedByWallet(@RequestBody LocalTime doneTime,
                                                       @PathVariable Integer orderId,
                                                       @PathVariable Integer suggestionId){
        return new ResponseEntity<>(customerService.paidByWallet(orderId, suggestionId,doneTime),HttpStatus.OK);
    }
}
