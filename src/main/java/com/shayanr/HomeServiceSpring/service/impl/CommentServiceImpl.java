package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.repositoy.CommentRepository;
import com.shayanr.HomeServiceSpring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
}
