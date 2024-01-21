package com.shayanr.HomeServiceSpring.repositoy;


import com.example.homeservicespringdata.entity.users.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert,Integer> {


}
