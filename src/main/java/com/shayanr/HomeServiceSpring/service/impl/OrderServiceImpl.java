package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.OrderRepository;
import com.shayanr.HomeServiceSpring.service.OrderService;
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
           return orderRepository.save(customerOrder);

    }

    @Override
    public CustomerOrder findById(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new NotFoundException("Order not found"));
    }

    @Override
    @Transactional
    public void deleteById(Integer orderId) {
        findById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<CustomerOrder> seeOrders(Integer expertId) {
       return orderRepository.seeOrders(expertId);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
