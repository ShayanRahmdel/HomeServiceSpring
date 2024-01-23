package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.Order;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
        Expert expert = expertRepository.findById(expertId).orElse(null);
        if (expert == null) {
            throw new NullPointerException("cant find expert");
        }
        return expert;
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
    public Expert signUp(Expert expert, File image) {
        try {
            if (Validate.nameValidation(expert.getFirstName()) &&
                    Validate.nameValidation(expert.getLastName()) &&
                    Validate.emailValidation(expert.getEmail()) &&
                    Validate.passwordValidation(expert.getPassword())) {
                expert.setConfirmation(Confirmation.NEW);
                String extension = FilenameUtils.getExtension(image.getName());
                if (extension.equalsIgnoreCase("video") || extension.equalsIgnoreCase("mp4")) {
                    throw new PersistenceException("Invalid image format. Only image files are allowed.");
                }
                byte[] imageInbytes = setImageToByte(image);
                expert.setImage(imageInbytes);
                expertRepository.save(expert);
                return expert;

            }
        } catch (PersistenceException | NullPointerException e) {
            System.out.println("Error saving Expert");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return expert;
    }

    @Override
    public void changePassword(Integer expertId, String newPassword, String confirmPassword) {
        Expert expert = expertRepository.findById(expertId).orElse(null);

        try {
            if (expert == null) {
                throw new NullPointerException("customerId not found");
            }

            if (!Validate.passwordValidation(newPassword) || !newPassword.equals(confirmPassword)) {
                throw new PersistenceException("Password not valid");
            }
            expert.setPassword(newPassword);
            expertRepository.save(expert);
        } catch (PersistenceException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Order> seeOrder(Integer expertId) {
        try {
            Expert expert = expertRepository.findById(expertId).orElse(null);
            assert expert != null;
            if (expert.getConfirmation().equals(Confirmation.NEW)) {
                System.out.println("You cant see Orders yet");
            }
            return orderService.seeOrders(expertId);
        } catch (PersistenceException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public void saveImage(String imagePath, Integer expertId) {
        Expert expert = expertRepository.findById(expertId).orElse(null);
        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            assert expert != null;
            fos.write(expert.getImage());
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save the image: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("cant find id");
        }

    }

    @Override
    public WorkSuggestion createSuggest(WorkSuggestion workSuggestion, Integer orderId, Integer expertId) {
        List<Order> orders = seeOrder(expertId);
        Order order = orderService.findById(orderId);
        Expert expert = expertRepository.findById(expertId).orElse(null);

        try {
            if (orders.isEmpty() || expert == null) {
                throw new NullPointerException("wrong expert id or empty order");
            }
            if (workSuggestion.getSuggestedPrice() < order.getSubDuty().getBasePrice()) {
                throw new PersistenceException("your suggestion price is low");
            }
            if (!Validate.isValidDateAndTime(workSuggestion.getSuggestedDate(), workSuggestion.getSuggestedBeginTime())) {
                throw new PersistenceException("not vaild date");
            }
            workSuggestion.setExpert(expert);
            workSuggestion.setOrder(order);
            workSuggestionService.save(workSuggestion);
            order.setOrderStatus(OrderStatus.WAITING_EXPERT_SELECTION);
            orderService.saveOrder(order);
        } catch (NullPointerException | PersistenceException e) {
            System.out.println(e.getMessage());
        }
        return workSuggestion;
    }


    private byte[] setImageToByte(File imageFile) throws IOException {
        return Files.readAllBytes(imageFile.toPath());
    }
}

