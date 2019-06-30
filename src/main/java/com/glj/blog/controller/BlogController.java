package com.glj.blog.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.glj.blog.pojo.*;
import com.glj.blog.service.ArticleService;
import com.glj.blog.service.CommentService;
import com.glj.blog.utils.RandomValidateCodeUtil;
import com.glj.blog.utils.ServiceResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author guolongjie
 * @since 2019/5/5
 */
@Controller
@RequestMapping(value = {"/blog","/"})
public class BlogController {

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;


    @GetMapping(value = {"/index","/"})
    public String value(Model model, @RequestParam(value = "page", required = false) Integer currentPage,
                            @RequestParam(value = "tagId",required = false)Integer tagId) {
        String url = "blog/index::content";
        List<ONE> ones = articleService.ONE();
        model.addAttribute("ones", ones);
        if (currentPage == null) {
            currentPage = 1;
            tagId = -1;
            url = "blog/index";
        }



        ServiceResponse<PageInfo<Article>> serviceResponse = articleService.getArticles(currentPage,tagId);
        model.addAttribute("page", serviceResponse.getData());
        List<Tag> tags = articleService.getHotTags(5);
        model.addAttribute("tags",tags);
        model.addAttribute("tagId",tagId);
        return url;
    }

    @GetMapping(value = "/article")
    public ModelAndView article(HttpServletRequest request, @RequestParam(value = "id", required = false) Integer id) {
        Article article = articleService.getArticleById(id);
        ModelAndView mav = new ModelAndView("blog/article-fullwidth");
        List<Comment> comments = commentService.getComments(id);
        mav.addObject("article", article);
        mav.addObject("comments",comments);
        mav.addObject("success",true);
        return mav;
    }

    @GetMapping(value = {"/img"})
    public String img(Model model, HttpServletResponse response,@RequestParam(value = "pageNumber",required = false)Integer pageNumber) {
        List<Image> list = articleService.getImgs("cos", "hot", 0, 2);
        model.addAttribute("items",list);
        return "blog/img";
    }

    @GetMapping(value = "/getMoreImg")
    @ResponseBody
    public HashMap<String,Object> getMoreImg(@RequestParam(value = "pageNumber")Integer pageNumber){
        HashMap<String,Object> map = new HashMap<>();
        List<Image> list = articleService.getImgs("cos", "hot", pageNumber, 2);
        map.put("imgs",list);
        return map;
    }



    @PostMapping(value = "/addComment")
    public String addComment(Model model, Comment comment, @RequestParam(value = "verify",required = false) String verify, HttpSession session){
        commentService.addComment(comment);
        List<Comment> comments = commentService.getComments(comment.getArticleId());
        model.addAttribute("comments",comments);
        return "blog/article-fullwidth::comment";
    }

    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            //输出验证码图片方法
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping(value = "checkVerify")
    @ResponseBody
    public HashMap<String,Object> checkVerify(@RequestParam(value = "verify",required = false)String verify,HttpSession session){
        HashMap<String,Object> map = new HashMap<>();
        String val = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
        if (val.equalsIgnoreCase(verify)){
            map.put("success",true);

        }else {
            map.put("success",false);
            map.put("errorMsg","验证码错误");
        }
        return map;
    }


}
