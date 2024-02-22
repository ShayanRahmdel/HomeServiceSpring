package com.shayanr.HomeServiceSpring.repositoy.impl;

import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.repositoy.CustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<CustomerOrder> seeOrderByStatus(Integer customerId, String orderStatus) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerOrder> cq = cb.createQuery(CustomerOrder.class);
        Root<CustomerOrder> orderRoot = cq.from(CustomerOrder.class);

        Join<Order, User> userJoin = orderRoot.join("customer");

        cq.where(
                cb.equal(userJoin.get("id"), customerId),
                cb.equal(orderRoot.get("orderStatus"), orderStatus)
        );

        return em.createQuery(cq).getResultList();
    }
}
