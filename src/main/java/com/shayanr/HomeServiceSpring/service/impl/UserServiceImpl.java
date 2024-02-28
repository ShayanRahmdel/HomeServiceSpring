package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.users.ConfirmationToken;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.exception.NotFoundEmail;
import com.shayanr.HomeServiceSpring.repositoy.ConfirmationTokenRepository;
import com.shayanr.HomeServiceSpring.repositoy.UserRepository;
import com.shayanr.HomeServiceSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl<T extends User> implements UserService<T> {

    private final UserRepository<T> userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;
    @Override
    public Optional<T> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(T t) {
        userRepository.save(t);
    }

    @Override
    public void sendEmail(String emailAddress) {
        User user = findByEmail(emailAddress).orElseThrow(()-> new NotFoundEmail("not found email address"));

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("shayan.rahmdel@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Complete Registration!");
        String userPath = "";
        if (user instanceof Expert) {
            userPath = "expert";
        } else if (user instanceof Customer) {
            userPath = "customer";
        }
        mailMessage.setText("To confirm your account, please click here : " + "http://localhost:8080/"+userPath+"/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            T t = findByEmail(token.getUser().getEmail()).orElseThrow(()-> new NotFoundEmail(""));

            t.setEnabled(true);
            save(t);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    @Override
    public Double seeAmountWallet(Integer id) {
        return userRepository.seeAmountWallet(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }

}
