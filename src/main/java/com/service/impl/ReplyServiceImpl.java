package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.entity.Reply;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.respositry.ArticleRespositry;
import com.respositry.ReplyRespository;
import com.respositry.UserRespositry;
import com.service.ReplyService;
import com.util.DateUtil;
import com.util.JacksonCustomObjectMapper;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyRespository replyRepository;
	@Autowired
	UserRespositry userRepository;
	@Autowired
	ArticleRespositry articleRepository;

	@Override
	public void addReply(Reply reply) {
		replyRepository.save(reply);
	}

	@Override
	public void delete(String replyId) {
		replyRepository.delete(replyId);

	}

	@Override
	public List<JSONObject> findReplyByArticleId(String articleId) {

		List<JSONObject> result = new ArrayList<JSONObject>();
		List<Reply> list = replyRepository.findReplyByArticleId(articleId);
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
				result.get(i).put("post_time", formatDate);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<JSONObject> findSbodyReplies(String userId) {
		List<JSONObject> result = new ArrayList<JSONObject>();
		List<Reply> list = replyRepository.findReplyByUserId(userId);
		for (int i = 0; i < list.size(); i++) {
			try {
				String formatDate = DateUtil.getInstance()
						.transformDateToFormatString(list.get(i).getPostTime());
				result.add(JSONObject
						.parseObject(new JacksonCustomObjectMapper()
								.writeValueAsString(list.get(i))));
				result.get(i).put(
						"article_title",
						articleRepository.findArticleById(
								list.get(i).getArticleId()).getTitle());
				result.get(i).put("post_time", formatDate);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
