package com.glj.blog.dao;

import com.glj.blog.pojo.Comment;

import java.util.List;

public interface CommentDao {

    void addComment(Comment comment);

    List<Comment> getComments(Integer id);

}
