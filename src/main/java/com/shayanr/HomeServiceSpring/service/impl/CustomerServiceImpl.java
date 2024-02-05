package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.*;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import com.shayanr.HomeServiceSpring.repositoy.CustomerRepository;
import com.shayanr.HomeServiceSpring.service.*;
import com.shayanr.HomeServiceSpring.util.Validate;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final WorkSuggestionService workSuggestionService;

    private final CommentService commentService;


    private final WalletService walletService;
    private final SubDutyService subDutyService;
    private final OrderService orderService;
    private final AddressService addressService;



    @Override
    public Optional<Customer> findById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()){
            throw new NotFoundException("Customer not found");
        }
        return customer;
    }

    @Override
    @Transactional
    public void deleteById(Integer customerId) {
        if (customerId == null) {
            throw new NotFoundException("customerId cannot be null");
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
        customerRepository.save(customer);
        Wallet wallet = new Wallet();
        wallet.setAmount(0.0);
        customer.setWallet(wallet);
        return customer;

    }

    @Override
    @Transactional
    public Customer changePassword(Integer customerId, String newPassword, String confirmPassword) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Could not find customer"));
        if (!Validate.passwordValidation(newPassword) || !newPassword.equals(confirmPassword)) {
            throw new ValidationException("Password not valid");
        }
        customer.setPassword(newPassword);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    @Transactional
    public CustomerOrder createOrder(CustomerOrder customerOrder, Integer category, Integer subDutyId, Integer customerId) {
        SubDuty subDuty = subDutyService.findById(subDutyId).orElseThrow(()-> new NotFoundException("SubDuty not found"));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Could not find customer"));


        if (!Validate.isValidDateAndTime(customerOrder.getWorkDate(), customerOrder.getTimeDate())) {
            throw new ValidationException("check date");
        }
        if (!isValidPrice(subDuty, customerOrder.getProposedPrice())) {
            throw new ValidationException("check price");
        }

        customerOrder.setSubDuty(subDuty);
        customerOrder.setCustomer(customer);
        customerOrder.setOrderStatus(OrderStatus.WAITING_EXPERT_SUGESTION);
        return orderService.saveOrder(customerOrder);
    }

    @Override
    @Transactional
    public Address createAddress(Address address, Integer customerId, Integer orderId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new NotFoundException("Customer not found"));
        CustomerOrder customerOrder = orderService.findById(orderId).orElseThrow(()->new NotFoundException("Order not found"));
        if (!Validate.cityValidation(address.getCity()) || !Validate.postalValidation(address.getPostalCode())
                || !Validate.isValidCity(address.getState())) {
            throw new ValidationException("not a valid address");
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

        customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerRepository.seeSuggestionsByPrice(customerId);
    }

    @Override
    public List<WorkSuggestion> seeSuggestionsByExpertScore(Integer customerId) {

        customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerRepository.seeSuggestionsByExpertScore(customerId);

    }

    @Override
    @Transactional
    public CustomerOrder acceptSuggest(Integer suggestId) {
        WorkSuggestion workSuggestion = workSuggestionService.findById(suggestId).orElseThrow(() -> new NotFoundException("not found suggestion"));
        CustomerOrder customerOrder = workSuggestion.getCustomerOrder();
        customerOrder.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME);
        orderService.saveOrder(customerOrder);
        return customerOrder;

    }

    @Override
    @Transactional
    public CustomerOrder updateOrderToBegin(Integer orderId, Integer suggestionId, LocalDate date) {
        CustomerOrder customerOrder = orderService.findById(orderId).orElseThrow(()-> new NotFoundException("Order not found"));
        WorkSuggestion workSuggestion = workSuggestionService.findById(suggestionId).orElseThrow(()-> new NotFoundException("Suggestion not found"));

        if (date.isBefore(workSuggestion.getSuggestedDate())) {
            throw new ValidationException("Date is before " + workSuggestion.getSuggestedDate());
        }
        customerOrder.setOrderStatus(OrderStatus.WORK_BEGIN);
        orderService.saveOrder(customerOrder);
        return customerOrder;

    }

    @Override
    @Transactional
    public CustomerOrder updateOrderToEnd(Integer orderId) {
        CustomerOrder customerOrder = orderService.findById(orderId).orElseThrow(()-> new NotFoundException("order not found"));
        customerOrder.setOrderStatus(OrderStatus.WORK_DONE);
        orderService.saveOrder(customerOrder);
        Comment comment = new Comment();
        commentService.save(comment);
        customerOrder.setComment(comment);

        return customerOrder;
    }

    @Override
    @Transactional
    public CustomerOrder paidByWallet(Integer orderId, Integer workSuggestId, LocalTime doneTime) {
        WorkSuggestion workSuggestion = workSuggestionService.findById(workSuggestId).orElseThrow(() -> new NotFoundException("not found work suggestion"));
        CustomerOrder order = orderService.findById(orderId).orElseThrow(() -> new NotFoundException("not found order"));
        LocalTime workduration = workSuggestion.getWorkduration();
        Expert expert = workSuggestion.getExpert();
        Wallet expertWallet = expert.getWallet();
        Customer customer = order.getCustomer();
        Wallet customerWallet = customer.getWallet();
        if (customerWallet.getAmount() < workSuggestion.getSuggestedPrice()+50){
            throw new ValidationException("Your amount not enough to pay");
        }
        customerWallet.setAmount(customerWallet.getAmount()-workSuggestion.getSuggestedPrice());
        Double expertMoney =  workSuggestion.getSuggestedPrice() * 0.7;
        expertWallet.setAmount(expertMoney);
        walletService.save(customerWallet);
        walletService.save(expertWallet);
        if (!IsWorkDoneInRightTime(doneTime,workduration)){
            int extraWorkHour = doneTime.minusHours(workduration.getHour()).getHour();
            expert.setOverallScore(expert.getOverallScore()-(double) extraWorkHour);
            if (validScoreForExpert(expert)){
                expert.setConfirmation(Confirmation.INACTIVE);
            }

        }
        order.setOrderStatus(OrderStatus.PAID);
        orderService.saveOrder(order);
        return order;
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

    boolean IsWorkDoneInRightTime(LocalTime doneTime,LocalTime suggestTime) {
        return !doneTime.isAfter(suggestTime);
    }

    Boolean validScoreForExpert(Expert expert){
        return expert.getOverallScore() <= 0;
    }
}

