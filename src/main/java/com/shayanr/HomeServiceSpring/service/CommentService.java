package com.shayanr.HomeServiceSpring.service;


import com.shayanr.HomeServiceSpring.entity.business.Comment;



public interface CommentService {

    Comment save(Comment comment);


    Comment findById(Integer id);
}
