package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

}
