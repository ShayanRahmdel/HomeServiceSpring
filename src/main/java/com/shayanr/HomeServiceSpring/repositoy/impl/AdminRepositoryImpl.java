package com.shayanr.HomeServiceSpring.repositoy.impl;

import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;

import com.shayanr.HomeServiceSpring.repositoy.AdminRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Expert> searchAdminByExpert(String firstName, String lastName, String email, String expertise, Double minRate, Double maxRate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expert> cq = cb.createQuery(Expert.class);
        Root<Expert> expert = cq.from(Expert.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(cb.lower(expert.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }

        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(cb.lower(expert.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(cb.like(cb.lower(expert.get("email")), "%" + email.toLowerCase() + "%"));
        }

        if (expertise != null && !expertise.isEmpty()) {
            Join<Expert, SubDuty> subDutyJoin = expert.join("subDuties");
            predicates.add(cb.like(cb.lower(subDutyJoin.get("title")), "%" + expertise.toLowerCase() + "%"));
        }

        if (minRate != null && maxRate != null) {
            predicates.add(cb.between(expert.get("overallScore"), minRate, maxRate));
        } else if (minRate != null) {
            predicates.add(cb.greaterThanOrEqualTo(expert.get("overallScore"), minRate));
        } else if (maxRate != null) {
            predicates.add(cb.lessThanOrEqualTo(expert.get("overallScore"), maxRate));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();

    }

    @Override
    public List<Customer> searchAdminByCustomer(String firstName, String lastName, String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> customer = cq.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(cb.lower(customer.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }

        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(cb.lower(customer.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(cb.like(cb.lower(customer.get("email")), "%" + email.toLowerCase() + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
