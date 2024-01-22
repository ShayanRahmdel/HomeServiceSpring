package com.shayanr.HomeServiceSpring.service;



import com.shayanr.HomeServiceSpring.entity.business.Order;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Expert;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ExpertService {

    Expert save(Expert expert);

    Expert findById(Integer expertId);

    void deleteById(Integer expertId);

    List<Expert> findAll();

    Expert signUp(Expert expert, File image);

    void changePassword(Integer expertId,String newPassword,String confirmPassword);

    List<Order> seeOrder(Integer expertId);



    void saveImage(String imagePath,Integer expertId);

    WorkSuggestion createSuggest(WorkSuggestion workSuggestion, Integer orderId, Integer expertId);

}
