package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.Order;
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
    public Order saveOrder(Order order) {
        try {
            orderRepository.save(order);
        }catch (NullPointerException | PersistenceException e){
            System.out.println("Error saving order");
        }
        return order;
    }

    @Override
    public Order findById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order==null){
            throw new NullPointerException("cant find order");
        }
        return order;

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
    public List<Order> seeOrders(Integer expertId) {
        if (expertId==null){
            throw new NullPointerException("cant find order");
        }
       return orderRepository.seeOrders(expertId);
    }
}
