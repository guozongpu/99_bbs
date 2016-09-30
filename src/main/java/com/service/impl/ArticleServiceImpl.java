package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.entity.Article;
import com.entity.Collection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.respositry.ArticleRespositry;
import com.respositry.CollectionRepository;
import com.respositry.ForumRespositry;
import com.respositry.UserRespositry;
import com.service.ArticleService;
import com.util.DateUtil;
import com.util.JacksonCustomObjectMapper;
import com.util.SortUtil;

/**
 * @ClassName: ArticleServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月7日 下午3:02:56
 * @version V1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRespositry articleRepository;
	@Autowired
	UserRespositry userRepository;
	@Autowired
	ForumRespositry forumRepository;
	@Autowired
	CollectionRepository collectionRepository;

	@Override
	public void addAticle(Article article) {
		articleRepository.save(article);
	}

	@Override
	public Article queryArticle(String id) {
		return articleRepository.findOne(id);

	}

	@Override
	public void delete(String Article_id) {
		articleRepository.delete(Article_id);

	}

	@Override
	public List<JSONObject> queArticlesByForumId(String forumId) {
		List<JSONObject> result = new ArrayList<JSONObject>();
		List<Article> list = articleRepository.queArticlesByForumId(forumId);
		for (int i = 0; i < list.size(); i++) {
			try {
				String formatDate = DateUtil.getInstance()
						.transformDateToFormatString(list.get(i).getPostTime());
				result.add(JSONObject
						.parseObject(new JacksonCustomObjectMapper()
								.writeValueAsString(list.get(i))));
				result.get(i).put(
						"user_name",
						userRepository
								.findUserByUserId(list.get(i).getUserId())
								.getLoginName());
				result.get(i).put(
						"forum_name",
						forumRepository.findForumById(list.get(i).getForumId())
								.getName());
				result.get(i).put("post_time", formatDate);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<JSONObject> queryArticlesByUserId(String userId) {
		List<JSONObject> result = new ArrayList<JSONObject>();
		List<Article> list = articleRepository.queryArticlesByUserId(userId);
		for (int i = 0; i < list.size(); i++) {
			try {
				String formatDate = DateUtil.getInstance()
						.transformDateToFormatString(list.get(i).getPostTime());
				result.add(JSONObject
						.parseObject(new JacksonCustomObjectMapper()
								.writeValueAsString(list.get(i))));
				result.get(i).put(
						"user_name",
						userRepository
								.findUserByUserId(list.get(i).getUserId())
								.getLoginName());
				result.get(i).put(
						"forum_name",
						forumRepository.findForumById(list.get(i).getForumId())
								.getName());
				result.get(i).put("post_time", formatDate);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void deleteByUserIDandArticleId(String userId, String ArticleId) {
		articleRepository.deleteByUserIDandArticleId(userId, ArticleId);
	}

	@Override
	public List<JSONObject> showHotArticles() {
		List<Article> list = new ArrayList<Article>();
		list = articleRepository.findAll();
		SortUtil.sortArticles(list);
		List<JSONObject> result = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			try {
				String formatDate = DateUtil.getInstance()
						.transformDateToFormatString(list.get(i).getPostTime());
				result.add(JSONObject
						.parseObject(new JacksonCustomObjectMapper()
								.writeValueAsString(list.get(i))));
				result.get(i).put(
						"user_name",
						userRepository
								.findUserByUserId(list.get(i).getUserId())
								.getLoginName());
				result.get(i).put(
						"forum_name",
						forumRepository.findForumById(list.get(i).getForumId())
								.getName());
				result.get(i).put("post_time", formatDate);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public JSONObject viewArticles(String articleId) {
		JSONObject json = new JSONObject();
		Article article = articleRepository.findArticleById(articleId);
		try {
			String formatDate = DateUtil.getInstance()
					.transformDateToFormatString(article.getPostTime());
			json = JSONObject.parseObject(new JacksonCustomObjectMapper()
					.writeValueAsString(article));
			json.put("user_name",
					userRepository.findUserByUserId(article.getUserId())
							.getLoginName());
			json.put("forum_name",
					forumRepository.findForumById(article.getForumId())
							.getName());
			json.put("post_time", formatDate);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public void newReply(String articleId) {
		int replyNumber = articleRepository.findArticleById(articleId)
				.getReplyNumber();
		replyNumber++;
		articleRepository.addReplyNumberById(articleId, replyNumber);
	}

	@Override
	public List<JSONObject> searchArticles(String keywords) {
		List<Article> list = articleRepository.findAll();
		List<JSONObject> result = new ArrayList<JSONObject>();
		int num = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTitle().contains(keywords)) {
				try {
					String formatDate = DateUtil.getInstance()
							.transformDateToFormatString(
									list.get(i).getPostTime());
					result.add(JSONObject
							.parseObject(new JacksonCustomObjectMapper()
									.writeValueAsString(list.get(i))));
					result.get(num).put(
							"user_name",
							userRepository.findUserByUserId(
									list.get(i).getUserId()).getLoginName());
					result.get(num).put(
							"forum_name",
							forumRepository.findForumById(
									list.get(i).getForumId()).getName());
					result.get(num++).put("post_time", formatDate);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public void colletArticle(String userId, String articleId) {
		Collection collection = new Collection();
		collection.setUserId(userId);
		collection.setArticleId(articleId);
		collectionRepository.save(collection);
	}

	@Override
	public List<JSONObject> showCollection(String userId) {
		List<Collection> list = collectionRepository
				.findCollectionByUserId(userId);
		List<String> articleIdList = new ArrayList<String>();
		List<Article> articleList = new ArrayList<Article>();
		for (int i = 0; i < list.size(); i++) {
			articleIdList.add(list.get(i).getArticleId());
		}
		for (String articleId : articleIdList) {
			articleList.add(articleRepository.findArticleById(articleId));
		}
		List<JSONObject> result = new ArrayList<JSONObject>();
		for (int i = 0; i < articleList.size(); i++) {
			try {
				result.add(JSONObject
						.parseObject(new JacksonCustomObjectMapper()
								.writeValueAsString(articleList.get(i))));
				result.get(i).put(
						"user_name",
						userRepository
								.findUserByUserId(articleList.get(i).getUserId())
								.getLoginName());
				result.get(i).put(
						"forum_name",
						forumRepository.findForumById(articleList.get(i).getForumId())
								.getName());
				String formatDate = DateUtil.getInstance()
						.transformDateToFormatString(articleList.get(i).getPostTime());
				result.get(i).put("post_time", formatDate);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public JSONObject notify(String userId) {
		List<Article> list = articleRepository.queryArticlesByUserId(userId);
		return null;
	}
}
