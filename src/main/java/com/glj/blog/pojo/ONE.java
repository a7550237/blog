package com.glj.blog.pojo;


import java.io.Serializable;

/**
 * @author guolongjie
 * @since 2019/5/7
 */

public class ONE implements Serializable {
    String imgUrl;
    String article;

    public ONE(String imgUrl, String article) {
        this.imgUrl = imgUrl;
        this.article = article;
    }

    public ONE() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
