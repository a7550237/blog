package com.glj.blog.pojo;

import java.util.Date;
import java.util.List;

/**
 * @author guolongjie
 * @since 2019/5/15
 */

public class Comment {

    Integer commentId;
    String name;
    String body;
    Integer hasParent; // 0 没有parent ；  1 有parent
    Integer parentId;
    Date createTime;
    Integer articleId;
    List<Comment> commentChildren;

    public List<Comment> getCommentChildren() {
        return commentChildren;
    }

    public void setCommentChildren(List<Comment> commentChildren) {
        this.commentChildren = commentChildren;
    }

    public Comment() {
    }

    public Comment(String name, String body, Date createTime) {
        this.name = name;
        this.body = body;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", hasParent=" + hasParent +
                ", parentId=" + parentId +
                ", createTime=" + createTime +
                ", articleId=" + articleId +
                ", commentChildren=" + commentChildren +
                '}';
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getHasParent() {
        return hasParent;
    }

    public void setHasParent(Integer hasParent) {
        this.hasParent = hasParent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
