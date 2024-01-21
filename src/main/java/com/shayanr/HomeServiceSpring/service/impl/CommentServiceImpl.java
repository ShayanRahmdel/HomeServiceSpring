package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.repositoy.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {
    private CommentRepository commentRepository;
}
