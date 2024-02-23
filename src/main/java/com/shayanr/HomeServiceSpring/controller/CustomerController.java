package com.shayanr.HomeServiceSpring.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.shayanr.HomeServiceSpring.dto.*;
import com.shayanr.HomeServiceSpring.entity.business.Address;
import com.shayanr.HomeServiceSpring.entity.business.Comment;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.users.Customer;

import com.shayanr.HomeServiceSpring.exception.ValidationException;
import com.shayanr.HomeServiceSpring.mapper.CommentMapper;
import com.shayanr.HomeServiceSpring.mapper.CustomerMapper;
import com.shayanr.HomeServiceSpring.mapper.OrderMapper;
import com.shayanr.HomeServiceSpring.mapper.SuggestionMapper;
import com.shayanr.HomeServiceSpring.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;
    private final DefaultKaptcha captchaProducer;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto> registerCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        Customer customer = CustomerMapper.INSTANCE.requestDtoToModel(customerRequestDto);
        customer.setPassword(passwordEncoder.encode(customerRequestDto.getPassword()));
        customerService.signUp(customer);
        CustomerResponseDto customerResponseDto = CustomerMapper.INSTANCE.modelToResponse(customer);
        return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return customerService.confirmEmail(confirmationToken);
    }

    @PutMapping("/change-password/{customerId}")
    public void changePassword(@PathVariable Integer customerId, @RequestBody PasswordDto passwordDto) {
        customerService.changePassword(customerId, passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
    }

    @PostMapping("/create-order/{category}/{subduty}/{customerId}")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CustomerOrder order, @PathVariable Integer category,
                                                        @PathVariable Integer subduty, @PathVariable Integer customerId) {
        CustomerOrder order1 = customerService.createOrder(order, category, subduty, customerId);

        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToResponse(order1), HttpStatus.CREATED);
    }

    @PostMapping("/create-address/{customerId}/{orderId}")
    public ResponseEntity<Address> createAddress(@RequestBody Address address, @PathVariable Integer customerId
            , @PathVariable Integer orderId) {
        return new ResponseEntity<>(customerService.createAddress(address, customerId, orderId), HttpStatus.CREATED);
    }

    @GetMapping("/see-suggest-by-price/{customerId}")
    public List<SuggestionResponseDto> seeSuggestByPrice(@PathVariable Integer customerId) {
        List<WorkSuggestion> workSuggestions = customerService.seeSuggestionsByPrice(customerId);
        return SuggestionMapper.INSTANCE.listModeltoResponse(workSuggestions);
    }

    @GetMapping("/see-suggest-by-score/{customerId}")
    public List<SuggestionResponseDto> seeSuggestByScore(@PathVariable Integer customerId) {
        List<WorkSuggestion> workSuggestions = customerService.seeSuggestionsByExpertScore(customerId);
        return SuggestionMapper.INSTANCE.listModeltoResponse(workSuggestions);
    }

    @PutMapping("/accept-suggest/{suggestId}")
    public ResponseEntity<OrderResponseDto> acceptSuggest(@PathVariable Integer suggestId) {
        CustomerOrder customerOrder = customerService.acceptSuggest(suggestId);
        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToResponse(customerOrder), HttpStatus.OK);
    }

    @PutMapping("/update-to-begin/{orderId}/{suggestionId}")
    public ResponseEntity<OrderResponseDto> updateToBegin(@PathVariable Integer orderId
            , @PathVariable Integer suggestionId
            , @RequestBody LocalDate date) {
        CustomerOrder customerOrder = customerService.updateOrderToBegin(orderId, suggestionId, date);
        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToResponse(customerOrder), HttpStatus.OK);
    }

    @PutMapping("/update-to-end/{orderId}/{suggestionId}")
    public ResponseEntity<OrderResponseDto> updateToEnd(@PathVariable Integer orderId,@PathVariable Integer suggestionId,@RequestBody LocalTime doneTime) {
        CustomerOrder customerOrder = customerService.updateOrderToEnd(orderId,suggestionId,doneTime);
        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToResponse(customerOrder), HttpStatus.OK);
    }

    @PostMapping("/payment-by-wallet/{orderId}/{suggestionId}")
    public ResponseEntity<OrderResponseDto> payedByWallet(@PathVariable Integer orderId,
                                                       @PathVariable Integer suggestionId) {
        CustomerOrder customerOrder = customerService.paidByWallet(orderId, suggestionId);
        return new ResponseEntity<>(OrderMapper.INSTANCE.modelToResponse(customerOrder), HttpStatus.OK);
    }

    @PostMapping("/online-payment")
    public PaymentDto processPayment(@Valid @RequestBody PaymentDto paymentDto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Invalid payment details");
        }
        System.out.println("successful");
        return paymentDto;
    }

    @PostMapping("/create-comment/{orderId}/{suggestionId}")
    public CommentDto createComment(@PathVariable Integer orderId,
                                    @PathVariable Integer suggestionId,
                                    @Valid @RequestBody CommentDto dto){
        Comment comment = customerService.createComment(orderId, dto.getScore(), dto.getComment(), suggestionId);
        return CommentMapper.INSTANCE.modelToDto(comment);

    }
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] captchaBytes = baos.toByteArray();
        response.getOutputStream().write(captchaBytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @GetMapping("/see-amount-wallet/{customerId}")
    public Double seeAmountWallet(@PathVariable Integer customerId){
        return customerService.seeAmountWallet(customerId);
    }

    @GetMapping("/see-orders-by-status/{customerId}")
    public List<CustomerOrder> seeOrdersByStatus(@PathVariable Integer customerId, @RequestBody String orderStatus){
     return customerService.seeOrderByStatus(customerId, orderStatus);
    }

}

