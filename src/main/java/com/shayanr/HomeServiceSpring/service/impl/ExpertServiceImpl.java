package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.repositoy.ExpertRepository;
import com.shayanr.HomeServiceSpring.service.ExpertService;
import com.shayanr.HomeServiceSpring.service.OrderService;
import com.shayanr.HomeServiceSpring.service.WorkSuggestionService;
import com.shayanr.HomeServiceSpring.util.Validate;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;

    private final OrderService orderService;

    private final WorkSuggestionService workSuggestionService;

    @Override
    @Transactional
    public Expert save(Expert expert) {
        try {
            expertRepository.save(expert);
        } catch (NullPointerException | PersistenceException e) {
            System.out.println("Error saving Expert");
        }
        return expert;
    }

    @Override
    public Expert findById(Integer expertId) {
        return expertRepository.findById(expertId).orElseThrow(() -> new NullPointerException("no id found"));

    }

    @Override
    @Transactional
    public void deleteById(Integer expertId) {
        if (expertId == null) {
            throw new NullPointerException("cant find expert");
        }
        expertRepository.deleteById(expertId);
    }

    @Override
    public List<Expert> findAll() {
        return expertRepository.findAll();
    }

    @Override
    @Transactional
    public Expert signUp(Expert expert, File image) throws IOException {
        if (expert == null) {
            throw new NullPointerException("null expert");
        }
        if (!Validate.nameValidation(expert.getFirstName()) ||
                !Validate.nameValidation(expert.getLastName()) ||
                !Validate.emailValidation(expert.getEmail()) ||
                !Validate.passwordValidation(expert.getPassword())) {
            throw new PersistenceException("wrong validation");
        }
        String extension = FilenameUtils.getExtension(image.getName());
        if (extension.equalsIgnoreCase("video") || extension.equalsIgnoreCase("mp4")) {
            throw new PersistenceException("Invalid image format. Only image files are allowed.");
        }
        expert.setConfirmation(Confirmation.NEW);

        byte[] imageInbytes = setImageToByte(image);
        expert.setImage(imageInbytes);
        expertRepository.save(expert);
        return expert;

    }

    @Override
    public Expert changePassword(Integer expertId, String newPassword, String confirmPassword) {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NullPointerException("Could not find expert"));

        if (!Validate.passwordValidation(newPassword) || !newPassword.equals(confirmPassword)) {
            throw new PersistenceException("Password not valid");
        }
        expert.setPassword(newPassword);
        expertRepository.save(expert);
        return expert;


    }

    @Override
    public List<CustomerOrder> seeOrder(Integer expertId) {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NullPointerException("customerId not found"));
        if (expert.getConfirmation().equals(Confirmation.NEW)) {
            throw new PersistenceException("You cant see Orders yet");
        }
        return orderService.seeOrders(expertId);

    }


    @Override
    public void saveImage(String imagePath, Integer expertId) throws IOException {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NullPointerException("null id"));
        if (imagePath.isEmpty()) {
            throw new NullPointerException();
        }
        FileOutputStream fos = new FileOutputStream(imagePath);
        fos.write(expert.getImage());
        System.out.println("Image saved successfully.");
    }

    @Override
    public WorkSuggestion createSuggest(WorkSuggestion workSuggestion, Integer orderId, Integer expertId) {
        List<CustomerOrder> customerOrders = seeOrder(expertId);
        CustomerOrder customerOrder = orderService.findById(orderId);
        Expert expert = expertRepository.findById(expertId).orElse(null);

        if (customerOrder == null || expert == null) {
            throw new NullPointerException("wrong expert id or empty order");
        }
        if (workSuggestion.getSuggestedPrice() < customerOrder.getSubDuty().getBasePrice()) {
            throw new PersistenceException("your suggestion price is low");
        }
        if (!Validate.isValidDateAndTimeForSuggest(workSuggestion.getSuggestedDate(), workSuggestion.getSuggestedBeginTime(), customerOrder)) {
            throw new PersistenceException("not vaild date");
        }
        workSuggestion.setExpert(expert);
        workSuggestion.setCustomerOrder(customerOrder);
        workSuggestionService.save(workSuggestion);
        customerOrder.setOrderStatus(OrderStatus.WAITING_EXPERT_SELECTION);
        orderService.saveOrder(customerOrder);
        return workSuggestion;
    }

    @Override
    @Transactional
    public void deleteAll() {
        expertRepository.deleteAll();
    }


    private byte[] setImageToByte(File imageFile) throws IOException {
        return Files.readAllBytes(imageFile.toPath());
    }
}

