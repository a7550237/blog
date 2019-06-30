package com.glj.blog.service;

import com.glj.blog.pojo.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    List<Comment> getComments(Integer id);
}
