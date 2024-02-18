package com.shayanr.HomeServiceSpring.controller;

import com.shayanr.HomeServiceSpring.dto.*;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import com.shayanr.HomeServiceSpring.mapper.ExperMapperCustom;
import com.shayanr.HomeServiceSpring.mapper.OrderMapper;
import com.shayanr.HomeServiceSpring.mapper.SuggestionMapper;
import com.shayanr.HomeServiceSpring.service.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;
    private final ExperMapperCustom experMapperCustom;

    @PostMapping("/register")
    public ResponseEntity<ExpertResponseCustomDto> register(@Valid @ModelAttribute ExpertRequestDto requestDto) throws IOException {
        validateImage(requestDto.getImage());
        Expert expert =experMapperCustom.requestDtoToModel(requestDto);
        return new ResponseEntity<>(experMapperCustom.modelToResponseCustom(expertService.signUp(expert)),HttpStatus.CREATED);
    }

    @PutMapping("/change-password/{customerId}")
    public void changePassword(@PathVariable Integer customerId, @RequestBody PasswordDto passwordDto) {
        expertService.changePassword(customerId, passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
    }


    @GetMapping("/see-orders/{expertId}")
    public List<OrderResponseDto> seeOrders(@PathVariable Integer expertId) {
        List<CustomerOrder> customerOrders = expertService.seeOrder(expertId);
        return OrderMapper.INSTANCE.listModelToResponse(customerOrders);
    }

    @PostMapping("/create-suggestion/{orderId}/{expertId}")
    public ResponseEntity<SuggestionResponseDto> createSuggestion(@RequestBody WorkSuggestion suggestion,
                                                                  @PathVariable Integer orderId,
                                                                  @PathVariable Integer expertId) {
        WorkSuggestion suggest = expertService.createSuggest(suggestion, orderId, expertId);
        SuggestionResponseDto suggestionResponseDto = SuggestionMapper.INSTANCE.modelToResponse(suggest);
        return new ResponseEntity<>(suggestionResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/see-score/{orderId}")
    public ResponseEntity<Integer> seeScoreWork(@PathVariable Integer orderId) {
        Integer score = expertService.seeScoreOrder(orderId);
        if (score != null) {
            return ResponseEntity.ok(score);
        }
        return ResponseEntity.notFound().build();
    }


    private void validateImage(MultipartFile image) throws IOException {
        if (image == null) {
            throw new ValidationException("Image is required.");
        }

        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".jpg")) {
            throw new ValidationException("Only JPEG images with the .jpg extension are allowed.");
        }

        if (image.getSize() > 300 * 1024) {
            throw new ValidationException("Image size cannot exceed 300KB.");
        }
    }

}
