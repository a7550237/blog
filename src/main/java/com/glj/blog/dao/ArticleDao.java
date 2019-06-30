package com.glj.blog.dao;


import com.glj.blog.pojo.Article;
import com.glj.blog.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface ArticleDao {

    void saveArticle(Article article);

    ArrayList<Article> getArticles(@Param("id") Integer id);

    Article getArticleById(Integer id);

    List<Tag> getTags();

    void updateArticle(Article article);

    void deleteArticleById(@Param("articleId") Integer articleId);

    List<Tag> getHotTags(@Param("size")int size);
}
