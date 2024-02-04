package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.OrderRepository;
import com.shayanr.HomeServiceSpring.service.OrderService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public CustomerOrder saveOrder(CustomerOrder customerOrder) {
           return orderRepository.save(customerOrder);

    }

    @Override
    public Optional<CustomerOrder> findById(Integer orderId) {
        Optional<CustomerOrder> customerOrder = orderRepository.findById(orderId);
        if (customerOrder.isEmpty()){
            throw new NullPointerException("cant find order");
        }
        return customerOrder;

    }

    @Override
    @Transactional
    public void deleteById(Integer orderId) {
        CustomerOrder customerOrder = orderRepository.findById(orderId).orElse(null);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<CustomerOrder> seeOrders(Integer expertId) {
        if (expertId==null){
            throw new NotFoundException("cant expert");
        }
       return orderRepository.seeOrders(expertId);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();;
    }
}
