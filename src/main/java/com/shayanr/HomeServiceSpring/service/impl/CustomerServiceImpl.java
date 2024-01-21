package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.repositoy.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl {

    private CustomerRepository customerRepository;

    private DutyCategoryServiceImpl dutyCategoryServiceImpl;
    private SubDutyServiceImpl subDutyServiceImpl;
    private OrderServiceImpl orderServiceImpl;
    private AddressServiceImpl addressServiceImpl;




    public Customer signUp(Customer customer) {
        try {
            if (Validate.nameValidation(customer.getFirstName()) &&
                    Validate.nameValidation(customer.getLastName()) &&
                    Validate.emailValidation(customer.getEmail()) &&
                    Validate.passwordValidation(customer.getPassword())) {
                repository.saveOrUpdate(customer);
                return customer;
            }
        }catch (PersistenceException | NullPointerException e){
            System.out.println("Error saving customer");
        }
        return customer;
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        try {
            if (Validate.passwordValidation(newPassword)){
                repository.changePassword(email, oldPassword, newPassword);
            }else throw new IllegalArgumentException();
        }catch (IllegalArgumentException e){
            System.out.println("Error: check password");
        }

    }

    public Order createOrder(Order order, Integer category, Integer subDutyId,Integer customerId) {
        Collection<DutyCategory> allDutyCategory = dutyCategoryServiceImpl.findAll();
        System.out.println(allDutyCategory);
        List<SubDuty> subDuties = subDutyServiceImpl.seeSubDutyByCategory(category);
        System.out.println(subDuties);
        try {
            SubDuty subDuty = subDutyServiceImpl.findById(subDutyId).orElseThrow(PersistenceException::new);
            if (isValidDateAndTime(order.getWorkDate(), order.getTimeDate())) {
                assert subDuty != null;
                if (isValidPrice(subDuty,order.getProposedPrice())) {
                    order.setSubDuty(subDuty);
                    order.setComment(null);
                    order.setOrderStatus(OrderStatus.Waiting_Expert_Sugestion);
                    order.setAddress(null);
                    orderServiceImpl.saveOrUpdate(order);

                }
            }
            return order;
        }catch (PersistenceException e) {
            System.out.println("Error: saving order:");
        }
        return order;

        }

    public Address createAddress(Address address,Integer customerId,Integer orderId) {
       try {
           Customer customer = repository.findById(customerId).orElseThrow(()->new PersistenceException("cant find customer"));
           Order order = orderServiceImpl.findById(orderId).orElseThrow(()->new PersistenceException("Cant find order"));
           if (Validate.cityValidation(address.getCity())&& Validate.postalValidation(address.getPostalCode())
                   && Validate.isValidCity(address.getState())) {
               address.setCustomer(customer);
               addressService.saveOrUpdate(address);

               assert order != null;
               order.setAddress(address);
               orderServiceImpl.saveOrUpdate(order);
               order.setAddress(address);
               orderServiceImpl.saveOrUpdate(order);
           }
           return address;
       }catch (PersistenceException e){
           System.out.println(e.getMessage());
       }
       return address;
    }



    boolean isValidDateAndTime(LocalDate date, LocalTime time){
        if (date.isBefore(LocalDate.now()) || time.isBefore(LocalTime.now())) {
            System.out.println("Your date is before");
            return false;
        }
        return true;
    }

    boolean isValidPrice(SubDuty subDuty,Double price){
        Double basePrice = subDuty.getBasePrice();
        if (price< basePrice){
            System.out.println("Your price is less than BasePrice");
            return false;
        }
        return true;
    }
}

