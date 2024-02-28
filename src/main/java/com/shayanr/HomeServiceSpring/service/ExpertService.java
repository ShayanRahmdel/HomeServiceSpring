package com.shayanr.HomeServiceSpring.service;




import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.util.List;


public interface ExpertService {

    Expert save(Expert expert);

    Expert findById(Integer expertId);

    void deleteById(Integer expertId);

    List<Expert> findAll();

    void signUp(Expert expert);

    void changePassword(Integer expertId,String newPassword,String confirmPassword);

    List<CustomerOrder> seeOrder(Integer expertId);

    void saveImage(String imagePath,Integer expertId) throws IOException;

    WorkSuggestion createSuggest(WorkSuggestion workSuggestion, Integer orderId, Integer expertId);

    Integer seeScoreOrder(Integer orderId);

    Integer expertCategory(Integer id);

    Double seeAmountWallet(Integer expertId);

    List<CustomerOrder> seeOrdersByStatus(Integer expertId);

    void deleteAll();

}
