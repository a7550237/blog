package com.glj.blog.service.impl;

import com.glj.blog.dao.CommentDao;
import com.glj.blog.pojo.Comment;
import com.glj.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author guolongjie
 * @since 2019/5/15
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(new Date());
        commentDao.addComment(comment);
    }

    @Override
    public List<Comment> getComments(Integer id) {
        List<Comment> comments = commentDao.getComments(id);
        Iterator<Comment> iterator = comments.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getHasParent() == 1){
                iterator.remove();
            }
        }
        return comments;
    }
}
