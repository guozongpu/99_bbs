package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.Article;
import com.service.ArticleService;
import com.service.ForumService;
import com.service.UserService;
import com.util.ValidationUtil;

/**
 * @ClassName: ArticleController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月7日 下午2:56:19
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/article")
public class ArticleController {
	@Autowired
	ArticleService articleService;
	@Autowired
	ForumService forumService;
	@Autowired
	UserService userService;

	/**
	 * @Title: addArticle
	 * @Description: 发帖
	 * @author （zqh）
	 * 
	 */
	@RequestMapping(value = "/add/{user_id}/{forum_id}", method = RequestMethod.POST)
	public JSONObject addArticle(@RequestBody Article article,
			@PathVariable(value = "user_id") String userId,
			@PathVariable(value = "forum_id") String forumId) {
		article.setUserId(userId);
		article.setForumId(forumId);
		article.setPostTime(new Date());

		JSONObject json = new JSONObject();
		if (!ValidationUtil.validateNull(article.getContent())) {
			json.put("result", "帖子不能为空");
			return json;
		}
		if (!ValidationUtil.validateLength(article.getContent(), 1000)) {
			json.put("result", "帖子长度不能超过1000");
			return json;
		}
		articleService.addAticle(article);
		forumService.newArticle(forumId);

		json.put("result", "发帖成功");
		return json;
	}

	/**
	 * @Title: queryArticles
	 * @Description: 查看用户的帖子
	 * @author （zqh）
	 * 
	 */
	@RequestMapping(value = "/queryByUserId/{user_id}", method = RequestMethod.GET)
	public List<JSONObject> queryArticles(
			@PathVariable(value = "user_id") String userId) {

		return articleService.queryArticlesByUserId(userId);
	}

	/**
	 * @Title: queryArticlesByForum_id
	 * @Description: 查看板块下的贴子
	 * @author （zqh）
	 * 
	 */
	@RequestMapping(value = "/queryByForumId/{forum_id}", method = RequestMethod.GET)
	public List<JSONObject> queryArticlesByForum_id(
			@PathVariable(value = "forum_id") String forumId) {

		return articleService.queArticlesByForumId(forumId);
	}

	/**
	 * @Title: delteArticle
	 * @Description: 删除帖子
	 * @author （zqh）
	 * 
	 */
	@RequestMapping(value = "/delete/{user_id}/{article_id}", method = RequestMethod.GET)
	public void delteArticle(@PathVariable(value = "user_id") String userId,
			@PathVariable(value = "article_id") String articleId) {

		articleService.deleteByUserIDandArticleId(userId, articleId);
	}

	/**
	 * @Title: shwoHotArticles
	 * @Description: 显示热门帖子
	 * @author （gzp）
	 * 
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public List<JSONObject> showHotArticles() {

		return articleService.showHotArticles();
	}

	/**
	 * @Title: viewArticle
	 * @Description: 查看某个帖子
	 * @author （gzp）
	 * 
	 */
	@RequestMapping(value = "/{article_id}", method = RequestMethod.GET)
	public JSONObject viewArticle(
			@PathVariable(value = "article_id") String articleId) {

		return articleService.viewArticles(articleId);
	}

	/**
	 * @Title: searchArticles
	 * @Description: 搜索帖子
	 * @author （gzp）
	 * 
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public List<JSONObject> searchArticles(@RequestBody Article article) {

		return articleService.searchArticles(article.getTitle());
	}

	/**
	 * @Title: CollectArticle
	 * @Description: 收藏帖子
	 * @author （gzp）
	 * 
	 */
	@RequestMapping(value = "/collectArticle/{user_id}/{article_id}")
	public void collectArticle(@PathVariable(value = "user_id") String userId,
			@PathVariable(value = "article_id") String articleId) {

		articleService.colletArticle(userId, articleId);
	}

	/**
	 * @Title: showCollection
	 * @Description: 查看用户收藏帖子
	 * @author （gzp）
	 * 
	 */
	@RequestMapping(value = "/showCollection/{user_id}", method = RequestMethod.GET)
	public List<JSONObject> showCollection(
			@PathVariable(value = "user_id") String userId) {

		return articleService.showCollection(userId);

	}

	/**
	 * @Title: notify
	 * @Description: 通知用户帖子被评论
	 * @author （gzp）
	 * 
	 */
	@RequestMapping(value = "/notify/{user_id}")
	public JSONObject notify(@PathVariable(value = "user_id") String userId) {
		JSONObject json = new JSONObject();
		if (userService.checkNotified(userId)) {
			json.put("result", "new reply");
		} else {
			json.put("result", "nothing");
		}
		return json;
	}

	/**
	 * @Title: dealWithNotify
	 * @Description: 处理通知
	 * @author （gzp）
	 * 
	 */
	@RequestMapping(value = "/dealWithNotify/{user_id}")
	public void dealWithNotify(@PathVariable(value = "user_id") String userId) {
		userService.updateNotified(userId);
	}
}
