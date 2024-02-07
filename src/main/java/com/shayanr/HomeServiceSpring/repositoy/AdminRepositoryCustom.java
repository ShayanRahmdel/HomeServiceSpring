package com.shayanr.HomeServiceSpring.repositoy;

import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;

import java.util.List;

public interface AdminRepositoryCustom {
    List<Expert> searchAdminByExpert(String name, String lastName, String email, String expertise, Double minRate, Double maxRate);

    List<Customer> searchAdminByCustomer(String name, String lastName, String email);
}
