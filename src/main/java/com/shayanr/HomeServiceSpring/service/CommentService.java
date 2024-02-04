package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Comment;

import java.util.Optional;

public interface CommentService {

    Comment save(Comment comment);


    Optional<Comment> findById(Integer id);
}
