package com.shayanr.HomeServiceSpring.repositoy;


import com.shayanr.HomeServiceSpring.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface UserRepository <T extends User> extends JpaRepository<T, Integer> {

    Optional<T> findByEmail(String email);
    @Query("select w.amount from User u join u.wallet w WHERE u.id=:id")
    Double seeAmountWallet(Integer id);


}
