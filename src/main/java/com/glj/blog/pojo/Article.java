package com.glj.blog.pojo;

import java.util.Date;

/**
 * @author guolongjie
 * @since 2019/5/3
 */

public class Article {

    private Integer articleId;
    private String title;
    private String author;
    private String keyWord;
    private Date createTime;
    private String htmlBody;
    private String markDownBody;
    private Tag tag;
    private String coverPhotoPath;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getMarkDownBody() {
        return markDownBody;
    }

    public void setMarkDownBody(String markDownBody) {
        this.markDownBody = markDownBody;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", createTime=" + createTime +
                ", htmlBody='" + htmlBody + '\'' +
                ", markDownBody='" + markDownBody + '\'' +
                ", tag=" + tag +
                ", coverPhotoPath='" + coverPhotoPath + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
