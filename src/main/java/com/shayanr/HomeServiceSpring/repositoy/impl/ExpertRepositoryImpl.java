package com.shayanr.HomeServiceSpring.repositoy.impl;

import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.repositoy.ExpertRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class ExpertRepositoryImpl implements ExpertRepositoryCustom {
    private final EntityManager em;
    @Override
    public List<CustomerOrder> seeOrdersByStatus(Integer expertId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerOrder> cq = cb.createQuery(CustomerOrder.class);
        Root<CustomerOrder> orderRoot = cq.from(CustomerOrder.class);

        Join<CustomerOrder, SubDuty> subDutyJoin = orderRoot.join("subDuty");
        Join<SubDuty, Expert> subDutyExpertJoin = subDutyJoin.join("experts");


        cq.where(
                cb.equal(subDutyExpertJoin.get("id"), expertId),
                cb.or(
                        cb.equal(orderRoot.get("orderStatus"), OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME),
                        cb.equal(orderRoot.get("orderStatus"), OrderStatus.WORK_BEGIN),
                        cb.equal(orderRoot.get("orderStatus"), OrderStatus.WORK_DONE),
                        cb.equal(orderRoot.get("orderStatus"), OrderStatus.PAID)
                )
        );

        return em.createQuery(cq).getResultList();
    }
}
