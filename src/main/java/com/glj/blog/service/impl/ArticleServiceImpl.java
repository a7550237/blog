package com.glj.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.glj.blog.dao.ArticleDao;
import com.glj.blog.pojo.Article;
import com.glj.blog.pojo.Image;

import com.glj.blog.pojo.ONE;
import com.glj.blog.pojo.Tag;
import com.glj.blog.service.ArticleService;
import com.glj.blog.utils.FileUtils;
import com.glj.blog.utils.ParseTestUtils;
import com.glj.blog.utils.ServiceResponse;

import net.sf.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author guolongjie
 * @since 2019/5/3
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Transient
    @Override
    public void saveArticle(Article article) {
        try {
            article.setCreateTime(new Date());
            articleDao.saveArticle(article);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("DB error");
        }
    }


    @Override
    public ServiceResponse<PageInfo<Article>> getArticles(int currentPage,int tagId) {


        ServiceResponse<PageInfo<Article>> serviceResponse = new ServiceResponse<>();

        PageHelper.startPage(currentPage,3);
        ArrayList<Article> articles = articleDao.getArticles(tagId);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        pageInfo.getList().stream().forEach(article -> {
            article.setText(ParseTestUtils.parseHtmlForText(article.getHtmlBody(),150));
        });
        serviceResponse.setData(pageInfo);
        return serviceResponse;
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleDao.getArticleById(id);
    }

    @Override
    public List<ONE> ONE() {
        List<ONE> ones = null;
        if (stringRedisTemplate.hasKey("one")){
            String onestr =stringRedisTemplate.opsForValue().get("one");
            ones = JSON.parseArray(onestr,ONE.class);
        }else {
            ones=new ArrayList<>();
            try {
                Document document = Jsoup.connect("http://wufazhuce.com/").get();
                Elements imgs = document.select("div.carousel-inner div.item img.fp-one-imagen");
                Elements articles = document.select("div.carousel-inner div.item div.fp-one-cita a");
                for(int i=0;i<5;i++) {
                    Element img = imgs.get(i);
                    Element article = articles.get(i);
                    String attr = img.attr("src");
                    String text = article.text();
                    ones.add(new ONE(attr,text));
                }
                String s = JSON.toJSONString(ones);
                System.out.println("===="+s);
                stringRedisTemplate.opsForValue().set("one",s,60*60, TimeUnit.SECONDS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ones;
    }

    @Override
    public List<Image> getImgs(String category,String type,int pageNumber,int pageSize) {
        List<Image> imgs = null;
        if (stringRedisTemplate.hasKey("imgs")){
            String imgsstr = stringRedisTemplate.opsForValue().get("imgs");
            imgs = JSON.parseArray(imgsstr,Image.class);
        }else {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://api.vc.bilibili.com/link_draw/v2/Photo/list?category="+category+"&type="+type+"&page_num="+pageNumber+"&page_size="+pageSize).build();
            try {
                Response execute = client.newCall(request).execute();
                String json = execute.body().string();
                JSONObject jsonObject = JSONObject.fromObject(json);
                JSONObject data = jsonObject.getJSONObject("data");
                Iterator items = data.getJSONArray("items").iterator();
                imgs = new ArrayList<>();
                while (items.hasNext()){
                    JSONObject next = (JSONObject) items.next();
                    Iterator pictures = next.getJSONObject("item").getJSONArray("pictures").iterator();
                    String title = (String) next.getJSONObject("item").get("title");
                    while (pictures.hasNext()){
                        Image image = new Image();
                        JSONObject next1 = (JSONObject) pictures.next();
                        String img_src = (String) next1.get("img_src");
                        image.setSrc(img_src);
                        image.setTitle(title);
                        imgs.add(image);
                    }
                }
                String jsonString = JSON.toJSONString(imgs);
                stringRedisTemplate.opsForValue().set("imgs",jsonString,60*60, TimeUnit.SECONDS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgs;
    }

    @Override
    public List<Tag> getTags() {
        return articleDao.getTags();
    }

    @Transient
    @Override
    public void updateArticle(Article article) {
        Article oldArticle = getArticleById(article.getArticleId());
        if (article.getCoverPhotoPath() != null){
            FileUtils.deleteFile(oldArticle.getCoverPhotoPath());
        }else {
            article.setCoverPhotoPath(oldArticle.getCoverPhotoPath());
        }
        article.setCreateTime(new Date());
        articleDao.updateArticle(article);

    }

    @Override
    public void deleteArticleById(Integer articleId) {
        articleDao.deleteArticleById(articleId);
    }

    @Override
    public List<Tag> getHotTags(int size) {
        return articleDao.getHotTags(size);
    }


}
