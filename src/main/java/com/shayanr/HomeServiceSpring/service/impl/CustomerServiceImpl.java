package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.Order;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.repositoy.CustomerRepository;
import com.shayanr.HomeServiceSpring.service.*;
import com.shayanr.HomeServiceSpring.util.Validate;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final WorkSuggestionService workSuggestionService;


    private final DutyCategoryService dutyCategoryService;
    private final SubDutyService subDutyService;
    private final OrderService orderService;
    private final AddressService addressService;


    @Override
    public Optional<Customer> findById(Integer customerId) {
        if (customerId == null) {
            throw new NullPointerException("customerId cannot be null");
        }
        return customerRepository.findById(customerId);
    }

    @Override
    @Transactional
    public void deleteById(Integer customerId) {
        if (customerId == null) {
            throw new NullPointerException("customerId cannot be null");
        }
        customerRepository.deleteById(customerId);

    }

    @Override
    public List<Customer> findAll() {
      return   customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer signUp(Customer customer) {
        try {
            if (Validate.nameValidation(customer.getFirstName()) &&
                    Validate.nameValidation(customer.getLastName()) &&
                    Validate.emailValidation(customer.getEmail()) &&
                    Validate.passwordValidation(customer.getPassword())) {
                customerRepository.save(customer);
                return customer;
            }
        }catch (PersistenceException | NullPointerException e){
            System.out.println("Error saving customer");
        }
        return customer;

    }

    @Override
    @Transactional
    public void changePassword(Integer customerId,String newPassword,String confirmPassword) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        try {
            if (customer == null){
                throw new NullPointerException("customerId not found");
            }

            if (!Validate.passwordValidation(newPassword) || !newPassword.equals(confirmPassword)){
                throw new PersistenceException("Password not valid");
            }
            customer.setPassword(newPassword);
            customerRepository.save(customer);
        }catch (NullPointerException | PersistenceException e){
            System.out.println(e.getMessage());
        }


    }

    @Override
    @Transactional
    public Order createOrder(Order order, Integer category, Integer subDutyId, Integer customerId) {
        SubDuty subDuty = subDutyService.findById(subDutyId);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        try {
            if (dutyCategoryService.findById(category) == null){
                throw new NullPointerException();
            }

            if (!Validate.isValidDateAndTime(order.getWorkDate(), order.getTimeDate())) {
                throw new PersistenceException();
            }
                if (!isValidPrice(subDuty,order.getProposedPrice())) {
                    throw new PersistenceException();
                }

            order.setSubDuty(subDuty);
            order.setCustomer(customer);
            order.setComment(null);
            order.setOrderStatus(OrderStatus.WAITING_EXPERT_SUGESTION);
            order.setAddress(null);
            orderService.saveOrder(order);

        }catch (PersistenceException | NullPointerException e) {
            System.out.println("Error: saving order:");
        }
        return order;

    }

    @Override
    @Transactional
    public Address createAddress(Address address, Integer customerId, Integer orderId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Order order = orderService.findById(orderId);
        try {
            if (!Validate.cityValidation(address.getCity()) || !Validate.postalValidation(address.getPostalCode())
                    || !Validate.isValidCity(address.getState())) {
                throw new PersistenceException("not a valid address");
            }
            address.setCustomer(customer);
            addressService.saveAddress(address);

            assert order != null;
            order.setAddress(address);
            orderService.saveOrder(order);
            return address;
        }catch (PersistenceException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return address;
    }

    @Override
    public List<WorkSuggestion> seeSuggestionsByPrice(Integer customerId) {
        try {
            return customerRepository.seeSuggestionsByPrice(customerId);
        }catch (NullPointerException | InvalidDataAccessApiUsageException e){
            System.out.println("Error:");
        }
        return null;

    }

    @Override
    public List<WorkSuggestion> seeSuggestionsByExpertScore(Integer customerId) {
        try {
            return customerRepository.seeSuggestionsByExpertScore(customerId);
        }catch (NullPointerException | InvalidDataAccessApiUsageException e){
            System.out.println("Error:");
        }
        return null;
    }

    @Override
    @Transactional
    public void acceptSuggest(Integer suggestId) {
        try {
            WorkSuggestion workSuggestion = workSuggestionService.findById(suggestId).orElse(null);
            assert workSuggestion != null;
            Order order = workSuggestion.getOrder();
            order.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME);
            orderService.saveOrder(order);
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateOrderToBegin(Integer orderId, Integer suggestionId, LocalDate date) {
        Order order = orderService.findById(orderId);
        WorkSuggestion workSuggestion = workSuggestionService.findById(suggestionId).orElse(null);
        try {
            if (order == null) {
                throw new NullPointerException("orderId cannot be null");
            }
            assert workSuggestion != null;
            if (date.isBefore(workSuggestion.getSuggestedDate())){
                throw new IllegalArgumentException("Date is before " + workSuggestion.getSuggestedDate());
            }
            order.setOrderStatus(OrderStatus.WORK_BEING);
            orderService.saveOrder(order);
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    @Transactional
    public void updateOrderToEnd(Integer orderId) {
        Order order = orderService.findById(orderId);
        try {
            if(order==null){
                throw new NullPointerException("orderId cannot be null");
            }
            order.setOrderStatus(OrderStatus.WORK_DONE);
            orderService.saveOrder(order);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

    }



    boolean isValidPrice(SubDuty subDuty, Double price){
        Double basePrice = subDuty.getBasePrice();
        if (price< basePrice){
            System.out.println("Your price is less than BasePrice");
            return false;
        }
        return true;
    }
}

