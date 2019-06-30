package com.glj.blog.service;

import com.github.pagehelper.PageInfo;
import com.glj.blog.pojo.Article;

import com.glj.blog.pojo.Image;

import com.glj.blog.pojo.ONE;
import com.glj.blog.pojo.Tag;
import com.glj.blog.utils.ServiceResponse;

import java.util.List;

public interface ArticleService {
    void saveArticle(Article article);

    ServiceResponse<PageInfo<Article>> getArticles(int currentPage,int tagId);

    Article getArticleById(Integer id);

    List<ONE> ONE();

    List<Image> getImgs(String category,String type,int pageNumber,int pageSize);

    List<Tag> getTags();

    void updateArticle(Article article);

    void deleteArticleById(Integer articleId);

    List<Tag> getHotTags(int size);

}
