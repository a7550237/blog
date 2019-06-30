package com.glj.blog.controller;


import ch.qos.logback.core.util.FileUtil;
import com.github.pagehelper.PageInfo;
import com.glj.blog.pojo.Article;
import com.glj.blog.pojo.Tag;
import com.glj.blog.pojo.User;
import com.glj.blog.service.ArticleService;
import com.glj.blog.service.UserService;
import com.glj.blog.utils.FileUtils;
import com.glj.blog.utils.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



/**
 * @author guolongjie
 * @since 2019/4/27
 */

@Controller
@RequestMapping(value = {"/admin"})
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    final String DEFAULT_COVER_PHOTO = "/static/default.jpg";

    @GetMapping(value = "/index")
    public String index(){
        return "admin/index";
    }

    @GetMapping(value="/login")
    public String login(HttpServletRequest request){
        /*Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            Arrays.stream(cookies).forEach(cookie -> {
                String name = cookie.getName();
                if (name.equals("userName")){
                    request.setAttribute("userName",cookie.getValue());
                    request.setAttribute("isChecked",true);
                }
                if (name.equals("password")) request.setAttribute("password",cookie.getValue());
            });
        }*/
        return "admin/login";
    }

    /*@PostMapping(value = "/login")
    public String aa(){
        return "admin/index";
    }*/

    @GetMapping(value = "/blog-list")
    public String blogList(Model model, @RequestParam(value = "page", required = false) Integer currentPage,
                           @RequestParam(value = "tagId",required = false)Integer tagId){
        if (currentPage==null){
            currentPage = 1;
            tagId = -1;

        }

        ServiceResponse<PageInfo<Article>> articles = articleService.getArticles(currentPage, tagId);
        model.addAttribute("page", articles.getData());
        List<Tag> tags = articleService.getTags();
        model.addAttribute("tags",tags);
        model.addAttribute("tagId",tagId);

        return "admin/blog-list::bloglist";
    }

    /*//登录
    @PostMapping(value="/index")
    public String checkAccount(@RequestParam("userName")String userName,
                                     @RequestParam("password")String password,
                                     @RequestParam(value = "isRemember",required = false)String[] isRemember,
                                     HttpServletRequest request,
                                     HttpServletResponse response){
        User user = new User(userName,password);
        boolean success = userService.checkAccount(user);
        if (success){
            Cookie[] cookies = request.getCookies();
            //anyMatch 集合中有一个true就返回true
            //allMatch 集合中有一个false就返回false
            boolean isExists = false;
            if (cookies!=null){
                isExists = Arrays.stream(cookies).anyMatch((cookie -> {
                    if (cookie.getName().equals("userName")) return true;
                    else return false;
                }));
            }
            if (isRemember!=null){
                if (!isExists){
                    Cookie cookie_userName = new Cookie("userName",userName);
                    Cookie cookie_password = new Cookie("password",password);
                    cookie_userName.setPath("/");
                    cookie_password.setPath("/");
                    cookie_userName.setMaxAge(60*60*24*3);
                    cookie_password.setMaxAge(60*60*24*3);
                    response.addCookie(cookie_userName);
                    response.addCookie(cookie_password);

                }
            }else {
                if (isExists){
                    Cookie cookie_userName = new Cookie("userName",userName);
                    Cookie cookie_password = new Cookie("password",password);
                    cookie_userName.setMaxAge(0);
                    cookie_password.setMaxAge(0);
                    cookie_userName.setPath("/");
                    cookie_password.setPath("/");
                    response.addCookie(cookie_userName);
                    response.addCookie(cookie_password);
                }
            }
            request.getSession().setAttribute("user",user);
            return "admin/index";
        }else {
            request.setAttribute("errorMessage","账号密码错误");
            return "admin/login";
        }

    }*/


    @GetMapping(value = "/error")
    public String error(HttpServletRequest request){
        request.setAttribute("errorMessage","账号密码错误");
        return "admin/login";

    }


    @GetMapping(value="/articles-publish")
    public String form(){
        return "admin/form";
    }

    @GetMapping(value = "/md")
    public String md(@RequestParam(value = "article" ,required = false)String article){

        System.out.println(article);


        return "testmd";
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public HashMap upload(@RequestParam(value = "editormd-image-file",required = false) MultipartFile file){

        HashMap hashMap = new HashMap();
        try {
            String fileName = FileUtils.saveFile(file);
            hashMap.put("success",1);
            hashMap.put("url","/static/"+fileName);
        } catch (IOException e) {
            hashMap.put("success",0);
            hashMap.put("message","上传失败");
        }finally {
            return hashMap;
        }
    }

    @PostMapping("/upload-article")
    @ResponseBody
    public String uploadArticle(HttpServletRequest request,Article article, @RequestParam(value = "coverPhoto",required = false) MultipartFile file){
        boolean b = false;
        if (article.getArticleId()!=-1){
            b=true;
        }

        try {
            if (file!=null && file.getOriginalFilename()!=null && !file.getOriginalFilename().equals("")){
                String fileName = FileUtils.saveFile(file);

                article.setCoverPhotoPath("/static/"+fileName);
            }else {
                // 添加默认封面
                if (b){
                    article.setCoverPhotoPath(null);
                }else {
                    article.setCoverPhotoPath(DEFAULT_COVER_PHOTO);
                }
            }
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            article.setAuthor(username);
            if (b){
                articleService.updateArticle(article);
            }else {
                articleService.saveArticle(article);
            }

            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @GetMapping(value = {"/write-blog","/updateBlog"})
    public String writeBlog(Model model,@RequestParam(value = "articleId",required = false)Integer articleId){
        if (articleId != null){
            Article article = articleService.getArticleById(articleId);
            model.addAttribute("article",article);
        }
        List<Tag> tags =  articleService.getTags();
        model.addAttribute("tags",tags);
        return "admin/write-blog";
    }


    @GetMapping(value = "deleteBlog")
    public String deleteBlog(Integer articleId){
        articleService.deleteArticleById(articleId);
        return "redirect:/admin/blog-list";
    }

}
