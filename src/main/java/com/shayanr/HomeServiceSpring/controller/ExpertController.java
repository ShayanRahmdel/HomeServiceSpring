package com.shayanr.HomeServiceSpring.controller;

import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private ExpertService expertService;
//    @PostMapping("/register")
//    public void register(@RequestBody Expert expert,@RequestParam("image")MultipartFile image) throws IOException {
//        expertService.signUp(expert,image);
//    }
}
