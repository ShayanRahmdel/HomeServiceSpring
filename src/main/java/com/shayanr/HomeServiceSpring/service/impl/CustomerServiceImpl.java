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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


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
    public Customer findById(Integer customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    @Transactional
    public void deleteById(Integer customerId) {
        findById(customerId);
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
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
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
        SubDuty subDuty = subDutyService.findById(subDutyId);
        Customer customer = findById(customerId);


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
        Customer customer = findById(customerId);
        CustomerOrder customerOrder = orderService.findById(orderId);

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
        findById(customerId);
        return customerRepository.seeSuggestionsByPrice(customerId);
    }

    @Override
    public List<WorkSuggestion> seeSuggestionsByExpertScore(Integer customerId) {
        findById(customerId);
        return customerRepository.seeSuggestionsByExpertScore(customerId);

    }

    @Override
    @Transactional
    public CustomerOrder acceptSuggest(Integer suggestId) {
        WorkSuggestion workSuggestion = workSuggestionService.findById(suggestId);
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

        if (date.isBefore(workSuggestion.getSuggestedDate())) {
            throw new ValidationException("Date is before " + workSuggestion.getSuggestedDate());
        }
        customerOrder.setOrderStatus(OrderStatus.WORK_BEGIN);
        orderService.saveOrder(customerOrder);
        return customerOrder;

    }

    @Override
    @Transactional
    public CustomerOrder updateOrderToEnd(Integer orderId, Integer workSuggestId, LocalTime doneTime) {
        CustomerOrder customerOrder = orderService.findById(orderId);
        customerOrder.setOrderStatus(OrderStatus.WORK_DONE);
        WorkSuggestion workSuggestion = workSuggestionService.findById(workSuggestId);
        LocalTime workduration = workSuggestion.getWorkduration();
        Expert expert = workSuggestion.getExpert();

        if (!IsWorkDoneInRightTime(doneTime, workduration)) {
            int extraWorkHour = doneTime.minusHours(workduration.getHour()).getHour();
            expert.setOverallScore(expert.getOverallScore() - (double) extraWorkHour);
            if (validScoreForExpert(expert)) {
                expert.setConfirmation(Confirmation.INACTIVE);
            }
        }
            orderService.saveOrder(customerOrder);
            Comment comment = new Comment();
            commentService.save(comment);
            customerOrder.setComment(comment);

            return customerOrder;

    }

        @Override
        @Transactional
        public CustomerOrder paidByWallet (Integer orderId, Integer workSuggestId){
            WorkSuggestion workSuggestion = workSuggestionService.findById(workSuggestId);
            CustomerOrder order = orderService.findById(orderId);
            Expert expert = workSuggestion.getExpert();

            Wallet expertWallet = expert.getWallet();
            Customer customer = order.getCustomer();
            Wallet customerWallet = customer.getWallet();
            if (customerWallet.getAmount() < workSuggestion.getSuggestedPrice() + 50) {
                throw new ValidationException("Your amount not enough to pay");
            }
            customerWallet.setAmount(customerWallet.getAmount() - workSuggestion.getSuggestedPrice());
            Double expertMoney = workSuggestion.getSuggestedPrice() * 0.7;
            expertWallet.setAmount(expertMoney);
            walletService.save(customerWallet);
            walletService.save(expertWallet);



        order.setOrderStatus(OrderStatus.PAID);
        orderService.saveOrder(order);
        return order;
    }

    @Override
    public Comment createComment(Integer orderId, Integer score, String massage, Integer suggestionId) {
        CustomerOrder order = orderService.findById(orderId);
        Customer customer = order.getCustomer();
        WorkSuggestion workSuggestion = workSuggestionService.findById(suggestionId);
        Expert expert = workSuggestion.getExpert();
        expert.setOverallScore((double) score);
        Comment comment = order.getComment();
        comment.setCustomer(customer);
        comment.setComment(massage);
        comment.setScore(score);
        commentService.save(comment);
        return comment;
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

    boolean IsWorkDoneInRightTime(LocalTime doneTime, LocalTime suggestTime) {
        return !doneTime.isAfter(suggestTime);
    }

    Boolean validScoreForExpert(Expert expert) {
        return expert.getOverallScore() <= 0;
    }
}

