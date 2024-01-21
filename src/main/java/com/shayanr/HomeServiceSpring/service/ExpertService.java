package com.shayanr.HomeServiceSpring.service;



import com.shayanr.HomeServiceSpring.entity.business.Order;
import com.shayanr.HomeServiceSpring.entity.users.Expert;

import java.io.File;
import java.util.List;

public interface ExpertService {

    Expert signUp(Expert expert, File image);

    void changePassword(String email, String oldPassword,String newPassword);

    List<Order> seeOrder(Integer expertId);

    Order acceptOrder(Integer orderId);

    void saveImage(String imagePath,Integer expertId);

}
