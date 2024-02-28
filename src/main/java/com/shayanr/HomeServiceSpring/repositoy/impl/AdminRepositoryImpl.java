package com.shayanr.HomeServiceSpring.repositoy.impl;

import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.repositoy.AdminRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<User> searchAdminByUser(String firstName, String lastName, String email,
                                        String expertise, Double minRate, Double maxRate, LocalTime registrationTime,
                                        LocalTime registrationTimeTo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }

        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("email")), "%" + email.toLowerCase() + "%"));
        }

        if (expertise != null && !expertise.isEmpty()) {
            Join<Expert, SubDuty> subDutyJoin = user.join("subDuties");
            predicates.add(cb.like(cb.lower(subDutyJoin.get("title")), "%" + expertise.toLowerCase() + "%"));
        }

        if (minRate != null && maxRate != null) {
            predicates.add(cb.between(user.get("overallScore"), minRate, maxRate));
        } else if (minRate != null) {
            predicates.add(cb.greaterThanOrEqualTo(user.get("overallScore"), minRate));
        } else if (maxRate != null) {
            predicates.add(cb.lessThanOrEqualTo(user.get("overallScore"), maxRate));
        }

        if (registrationTime != null && registrationTimeTo != null) {
            predicates.add(cb.between(user.get("signUpTime"), registrationTime, registrationTimeTo));
        } else if (registrationTime != null) {
            predicates.add(cb.greaterThanOrEqualTo(user.get("signUpTime"), registrationTime));
        } else if (registrationTimeTo != null) {
            predicates.add(cb.lessThanOrEqualTo(user.get("signUpTime"), registrationTimeTo));
        }


        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();

    }

    @Override
    public List<WorkSuggestion> searchWorkSuggestionByName(String firstName, String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<WorkSuggestion> cq = cb.createQuery(WorkSuggestion.class);
        Root<WorkSuggestion> workSuggestionRoot = cq.from(WorkSuggestion.class);

        Join<WorkSuggestion, Expert> expertJoin = workSuggestionRoot.join("expert");


        cq.where(
                cb.equal(expertJoin.get("firstName"), firstName),
                cb.equal(expertJoin.get("lastName"), lastName)
        );

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<CustomerOrder> searchOrders(LocalDate startDate, LocalDate endDate, OrderStatus orderStatus,
                                            String category, String subDuty) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerOrder> cq = cb.createQuery(CustomerOrder.class);
        Root<CustomerOrder> order = cq.from(CustomerOrder.class);
        List<Predicate> predicates = new ArrayList<>();

        if (startDate != null && endDate != null) {
            predicates.add(cb.between(order.get("workDate"), startDate, endDate));
        }

        if (orderStatus != null) {
            predicates.add(cb.equal(order.get("orderStatus"), orderStatus));
        }


        if (category != null) {
            Join<CustomerOrder, SubDuty> subDutyJoin = order.join("subDuty");
            Join<SubDuty, DutyCategory> dutyCategoryJoin = subDutyJoin.join("dutyCategory");
            predicates.add(cb.like(cb.lower(dutyCategoryJoin.get("title")), "%" + category.toLowerCase() + "%"));
        }


        if (subDuty != null) {
            Join<CustomerOrder, SubDuty> subDutyJoin = order.join("subDuty");
            predicates.add(cb.like(cb.lower(subDutyJoin.get("title")), "%" + subDuty.toLowerCase() + "%"));
        }

        cq.select(order);
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Expert> searchExpertByCountSuggest(Integer desiredCount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expert> cq = cb.createQuery(Expert.class);
        Root<Expert> expertRoot = cq.from(Expert.class);

        Join<Expert, WorkSuggestion> workSuggestionJoin = expertRoot.join("workSuggestions", JoinType.LEFT);
        cq.select(expertRoot);
        cq.groupBy(expertRoot.get("id"));
        cq.having(cb.equal(cb.count(workSuggestionJoin), desiredCount));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Customer> searchCustomerByCountOrder(Integer desiredCount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> customerRoot = cq.from(Customer.class);

        Join<Customer, CustomerOrder> customerOrderJoin = customerRoot.join("customerOrders", JoinType.LEFT);
        cq.select(customerRoot);
        cq.groupBy(customerRoot.get("id"));
        cq.having(cb.equal(cb.count(customerOrderJoin), desiredCount));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<CustomerOrder> seeOrdersByFullName(String firstName, String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerOrder> cq = cb.createQuery(CustomerOrder.class);
        Root<CustomerOrder> customerOrderRoot = cq.from(CustomerOrder.class);

        Join<CustomerOrder, Customer> customerJoin = customerOrderRoot.join("customer");


        cq.where(
                cb.equal(customerJoin.get("firstName"), firstName),
                cb.equal(customerJoin.get("lastName"), lastName)
        );

        return em.createQuery(cq).getResultList();
    }
}
