package com.shayanr.HomeServiceSpring.service.impl;


import com.shayanr.HomeServiceSpring.entity.business.Comment;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.exception.IsEmptyFieldException;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import com.shayanr.HomeServiceSpring.repositoy.ExpertRepository;
import com.shayanr.HomeServiceSpring.service.ExpertService;
import com.shayanr.HomeServiceSpring.service.OrderService;
import com.shayanr.HomeServiceSpring.service.WorkSuggestionService;
import com.shayanr.HomeServiceSpring.util.Validate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
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
        return expertRepository.save(expert);

    }

    @Override
    public Expert findById(Integer expertId) {
      return expertRepository.findById(expertId).orElseThrow(()-> new NotFoundException("Expert not found"));
    }

    @Override
    @Transactional
    public void deleteById(Integer expertId) {
        expertRepository.deleteById(expertId);
    }

    @Override
    public List<Expert> findAll() {
        return expertRepository.findAll();
    }

    @Override
    @Transactional
    public Expert signUp(Expert expert) {
        if (expert == null) {
            throw new NotFoundException("null expert");
        }

        expert.setConfirmation(Confirmation.NEW);
        expertRepository.save(expert);
        return expert;

    }

    @Override
    public Expert changePassword(Integer expertId, String newPassword, String confirmPassword) {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NotFoundException("Could not find expert"));

        if (!Validate.passwordValidation(newPassword) || !newPassword.equals(confirmPassword)) {
            throw new ValidationException("Password not valid");
        }
        expert.setPassword(newPassword);
        expertRepository.save(expert);
        return expert;


    }

    @Override
    public List<CustomerOrder> seeOrder(Integer expertId) {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NotFoundException("customerId not found"));
        if (expert.getConfirmation().equals(Confirmation.NEW)) {
            throw new ValidationException("You cant see Orders yet");
        }
        return orderService.seeOrders(expertId);

    }


    @Override
    public void saveImage(String imagePath, Integer expertId){
        Expert expert = findById(expertId);
        if (imagePath.isEmpty()) {
            throw new IsEmptyFieldException("Path must not be empty");
        }

        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            fos.write(expert.getImage());
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save the image.");
        }

    }

    @Override
    public WorkSuggestion createSuggest(WorkSuggestion workSuggestion, Integer orderId, Integer expertId) {
        CustomerOrder customerOrder = orderService.findById(orderId);
        Expert expert = findById(expertId);


        if (workSuggestion.getSuggestedPrice() < customerOrder.getSubDuty().getBasePrice()) {
            throw new ValidationException("your suggestion price is low");
        }
        if (!Validate.isValidDateAndTimeForSuggest(workSuggestion.getSuggestedDate(), workSuggestion.getSuggestedBeginTime(), customerOrder)) {
            throw new ValidationException("not vaild date");
        }
        workSuggestion.setWorkduration(workSuggestion.getSuggestedBeginTime().plusHours(workSuggestion.getWorkduration().getHour()));
        workSuggestion.setExpert(expert);
        workSuggestion.setCustomerOrder(customerOrder);
        workSuggestionService.save(workSuggestion);
        customerOrder.setOrderStatus(OrderStatus.WAITING_EXPERT_SELECTION);
        orderService.saveOrder(customerOrder);
        return workSuggestion;
    }

    @Override
    public Integer seeScoreOrder(Integer orderId) {
        CustomerOrder order = orderService.findById(orderId);
        Comment comment = order.getComment();
        return comment.getScore();
    }

    @Override
    public Integer expertCategory(Integer id) {
        return expertRepository.expertCategory(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        expertRepository.deleteAll();
    }



}

