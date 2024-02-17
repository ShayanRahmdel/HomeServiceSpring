package com.shayanr.HomeServiceSpring.controller;

import com.shayanr.HomeServiceSpring.dto.*;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import com.shayanr.HomeServiceSpring.mapper.ExpertMapper;
import com.shayanr.HomeServiceSpring.mapper.OrderMapper;
import com.shayanr.HomeServiceSpring.mapper.SuggestionMapper;
import com.shayanr.HomeServiceSpring.service.ExpertService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @PostMapping("/register")
    public ResponseEntity<ExpertResponseDto> register(@Valid @ModelAttribute ExpertRequestDto requestDto) throws IOException {
        Expert expert = ExpertMapper.INSTANCE.requestDtoToModel(requestDto);
        expert.setImage(requestDto.getImage().getBytes());
        ExpertResponseDto expertResponseDto = ExpertMapper.INSTANCE.modelToResponse(expertService.signUp(expert));
        return new ResponseEntity<>(expertResponseDto,HttpStatus.CREATED);

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


    private byte[] setPath(String path) throws IOException {
        if (path.isEmpty()) {
            throw new NotFoundException("Not found path");
        }
        if (!path.toLowerCase().endsWith("jpg")) {
            throw new ValidationException("check format");
        }
        byte[] image = Files.readAllBytes(Paths.get(path));
        if (image.length > 300 * 1024) {
            throw new ValidationException("your picture is too large");
        }
        return image;
    }

}
