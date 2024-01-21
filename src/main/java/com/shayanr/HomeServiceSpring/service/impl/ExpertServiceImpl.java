package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.repositoy.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl {

    private ExpertRepository expertRepository;


    public Expert signUp(Expert expert,File image) {
        try {
            if (Validate.nameValidation(expert.getFirstName()) &&
                    Validate.nameValidation(expert.getLastName()) &&
                    Validate.emailValidation(expert.getEmail()) &&
                    Validate.passwordValidation(expert.getPassword())) {
                expert.setConfirmation(Confirmation.New);
                byte[] imageInbytes = setImageToByte(image);
                expert.setImage(imageInbytes);
                repository.saveOrUpdate(expert);
                return expert;

            }
        }catch (PersistenceException | NullPointerException e){
            System.out.println("Error saving Expert");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expert;
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        try {
            if (Validate.passwordValidation(newPassword)){
                repository.changePassword(email, oldPassword, newPassword);
            }else throw new IllegalArgumentException();
        }catch (IllegalArgumentException e){
            System.out.println("Error: check password");
        }

    }

    public List<Order> seeOrder(Integer expertId) {
        try {
            Expert expert = repository.findById(expertId).orElseThrow(()-> new PersistenceException("Could not find expert"));
            assert expert != null;
            if (expert.getConfirmation().equals(Confirmation.New)){
                System.out.println("You cant see Orders yet");
            }
            return repository.seeOrder(expertId);
        }catch (PersistenceException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Order acceptOrder(Integer orderId) {
        try {
            Order order = orderService.findById(orderId).orElseThrow(()->new PersistenceException("Could not find Order"));
            assert order != null;
            order.setOrderStatus(OrderStatus.Work_Being);
            orderService.saveOrUpdate(order);
            return order;
        }catch (PersistenceException e){
            System.out.println(e.getMessage());
        }
        return null;
    }



    public void saveImage(String imagePath, Integer expertId) {
        Expert expert = repository.findById(expertId).orElse(null);
        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            assert expert != null;
            fos.write(expert.getImage());
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save the image: " + e.getMessage());
        }catch (NullPointerException e){
            System.out.println("cant find id");
        }
    }

    private byte[] setImageToByte(File imageFile) throws IOException {
        return Files.readAllBytes(imageFile.toPath());
    }

}

