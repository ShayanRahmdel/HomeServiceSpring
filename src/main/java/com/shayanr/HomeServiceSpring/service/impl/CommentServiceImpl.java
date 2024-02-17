package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.business.Comment;
import com.shayanr.HomeServiceSpring.exception.NotFoundException;
import com.shayanr.HomeServiceSpring.repositoy.CommentRepository;
import com.shayanr.HomeServiceSpring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElseThrow(()->new NotFoundException("Not found comment"));
    }
}

