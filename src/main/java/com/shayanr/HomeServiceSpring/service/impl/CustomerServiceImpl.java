package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
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
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer signUp(Customer customer) {

        if (!Validate.nameValidation(customer.getFirstName()) ||
                !Validate.nameValidation(customer.getLastName()) ||
                !Validate.emailValidation(customer.getEmail()) ||
                !Validate.passwordValidation(customer.getPassword())) {
            throw new IllegalArgumentException();
        }
        customerRepository.save(customer);
        return customer;

    }

    @Override
    @Transactional
    public Customer changePassword(Integer customerId, String newPassword, String confirmPassword) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NullPointerException("Could not find customer"));
        if (!Validate.passwordValidation(newPassword) || !newPassword.equals(confirmPassword)) {
            throw new PersistenceException("Password not valid");
        }
        customer.setPassword(newPassword);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    @Transactional
    public CustomerOrder createOrder(CustomerOrder customerOrder, Integer category, Integer subDutyId, Integer customerId) {
        SubDuty subDuty = subDutyService.findById(subDutyId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NullPointerException("Could not find customer"));
        if (dutyCategoryService.findById(category) == null) {
            throw new NullPointerException();
        }

        if (!Validate.isValidDateAndTime(customerOrder.getWorkDate(), customerOrder.getTimeDate())) {
            throw new PersistenceException();
        }
        if (!isValidPrice(subDuty, customerOrder.getProposedPrice())) {
            throw new PersistenceException();
        }

        customerOrder.setSubDuty(subDuty);
        customerOrder.setCustomer(customer);
        customerOrder.setOrderStatus(OrderStatus.WAITING_EXPERT_SUGESTION);
        return orderService.saveOrder(customerOrder);
    }

    @Override
    @Transactional
    public Address createAddress(Address address, Integer customerId, Integer orderId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        CustomerOrder customerOrder = orderService.findById(orderId);
        if (!Validate.cityValidation(address.getCity()) || !Validate.postalValidation(address.getPostalCode())
                || !Validate.isValidCity(address.getState())) {
            throw new PersistenceException("not a valid address");
        }
        address.setCustomer(customer);
        addressService.saveAddress(address);

        assert customerOrder != null;
        customerOrder.setAddress(address);
        orderService.saveOrder(customerOrder);
        return address;

    }

    @Override
    public List<WorkSuggestion> seeSuggestionsByPrice(Integer customerId) {

        customerRepository.findById(customerId).orElseThrow(() -> new NullPointerException("Customer not found"));
        return customerRepository.seeSuggestionsByPrice(customerId);
    }

    @Override
    public List<WorkSuggestion> seeSuggestionsByExpertScore(Integer customerId) {

        customerRepository.findById(customerId).orElseThrow(() -> new NullPointerException("Customer not found"));
        return customerRepository.seeSuggestionsByExpertScore(customerId);

    }

    @Override
    @Transactional
    public CustomerOrder acceptSuggest(Integer suggestId) {
        WorkSuggestion workSuggestion = workSuggestionService.findById(suggestId);
        assert workSuggestion != null;
        CustomerOrder customerOrder = workSuggestion.getCustomerOrder();
        customerOrder.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME);
        orderService.saveOrder(customerOrder);
        return customerOrder;

    }

    @Override
    @Transactional
    public CustomerOrder updateOrderToBegin(Integer orderId, Integer suggestionId, LocalDate date) {
        CustomerOrder customerOrder = orderService.findById(orderId);
        WorkSuggestion workSuggestion = workSuggestionService.findById(suggestionId);

        assert workSuggestion != null;
        if (date.isBefore(workSuggestion.getSuggestedDate())) {
            throw new IllegalArgumentException("Date is before " + workSuggestion.getSuggestedDate());
        }
        customerOrder.setOrderStatus(OrderStatus.WORK_BEGIN);
        orderService.saveOrder(customerOrder);
        return customerOrder;

    }

    @Override
    @Transactional
    public CustomerOrder updateOrderToEnd(Integer orderId) {
        CustomerOrder customerOrder = orderService.findById(orderId);
        customerOrder.setOrderStatus(OrderStatus.WORK_DONE);
        orderService.saveOrder(customerOrder);
        return customerOrder;
    }

    @Override
    @Transactional
    public void deleteAll() {
        customerRepository.deleteAll();
    }


    boolean isValidPrice(SubDuty subDuty, Double price) {
        Double basePrice = subDuty.getBasePrice();
        if (price < basePrice) {
            System.out.println("Your price is less than BasePrice");
            return false;
        }
        return true;
    }
}

