package com.shayanr.HomeServiceSpring.service;



import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Expert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ExpertService {

    Expert save(Expert expert);

    Expert findById(Integer expertId);

    void deleteById(Integer expertId);

    List<Expert> findAll();

    Expert signUp(Expert expert, File image) throws IOException;

    Expert changePassword(Integer expertId,String newPassword,String confirmPassword);

    List<CustomerOrder> seeOrder(Integer expertId);

    void saveImage(String imagePath,Integer expertId) throws IOException;

    WorkSuggestion createSuggest(WorkSuggestion workSuggestion, Integer orderId, Integer expertId);


    void deleteAll();

}
