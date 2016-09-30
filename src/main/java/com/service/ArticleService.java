package com.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.entity.Article;

/** 
* @ClassName: ArticleService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午3:01:56 
* @version V1.0 
*/
public interface ArticleService{
    
    void addAticle(Article article);
    Article queryArticle(String id);
    List<JSONObject> queArticlesByForumId(String forumId);
    List<JSONObject> queryArticlesByUserId(String userId);
    void delete(String Article_id);
    void deleteByUserIDandArticleId(String userId,String ArticleId);
    List<JSONObject> showHotArticles();
    JSONObject viewArticles(String articleId);
    void newReply(String articleId);
    List<JSONObject> searchArticles(String keywords);
    void colletArticle(String userId,String articleId);
    List<JSONObject> showCollection(String userId);
    JSONObject notify(String userId);
}
