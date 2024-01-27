package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.repositoy.OrderRepository;
import com.shayanr.HomeServiceSpring.service.OrderService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public CustomerOrder saveOrder(CustomerOrder customerOrder) {
        try {
            orderRepository.save(customerOrder);
        }catch (NullPointerException | PersistenceException e){
            System.out.println("Error saving order");
        }
        return customerOrder;
    }

    @Override
    public CustomerOrder findById(Integer orderId) {
        CustomerOrder customerOrder = orderRepository.findById(orderId).orElse(null);
        if (customerOrder ==null){
            throw new NullPointerException("cant find order");
        }
        return customerOrder;

    }

    @Override
    @Transactional
    public void deleteById(Integer orderId) {
        if (orderId==null){
            throw new NullPointerException("cant delete order");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<CustomerOrder> seeOrders(Integer expertId) {
        if (expertId==null){
            throw new NullPointerException("cant find order");
        }
       return orderRepository.seeOrders(expertId);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();;
    }
}
