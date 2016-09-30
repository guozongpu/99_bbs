package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.entity.Article;
import com.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.respositry.ArticleRespositry;
import com.respositry.ReplyRespository;
import com.respositry.UserRespositry;
import com.service.UserService;
import com.util.JacksonCustomObjectMapper;

/**
 * @ClassName: UserServiceimpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月7日 下午3:03:40
 * @version V1.0
 */
@Service
public class UserServiceimpl implements UserService {
	@Autowired
	UserRespositry userRepository;
	@Autowired
	ArticleRespositry articleRespository;
	@Autowired
	ReplyRespository replyrepository;

	@Override
	public void addUser(User user) {

		userRepository.save(user);
	}

	@Override
	public JSONObject findUser(String loginName, String password) {
		User user = userRepository.findUserByNameAndPawd(loginName, password);
		JSONObject json = new JSONObject();
		try {
			json = JSONObject.parseObject(new JacksonCustomObjectMapper()
					.writeValueAsString(user));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		int myArticle = articleRespository
				.queryArticlesByUserId(user.getUuid()).size();
		int myReply = replyrepository.findReplyByUserId(user.getUuid()).size();
		json.put("myArticle", myArticle);
		json.put("myReply", myReply);
		return json;
	}

	@Override
	public List<JSONObject> showAllUsers() {
		List<JSONObject> result = new ArrayList<JSONObject>();
		List<User> list = userRepository.findAll();
		for (int i = 0; i < list.size(); i++) {
			try {
				result.add(JSONObject
						.parseObject(new JacksonCustomObjectMapper()
								.writeValueAsString(list.get(i))));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void updateNotified(String userId) {
		int news = 0;
		List<Article> list = articleRespository.queryArticlesByUserId(userId);
		for(int i=0;i<list.size();i++){
			news += replyrepository.findReplyByArticleId(list.get(i).getId()).size();
		}
		userRepository.updateLogoutNumber(userId, news);
		userRepository.updateNowNumber(userId, news);
	}

	@Override
	public boolean checkNotified(String userId) {
		int olds = userRepository.findUserByUserId(userId).getLogoutNumber();
		int news = 0;
		List<Article> list = articleRespository.queryArticlesByUserId(userId);
		for(int i=0;i<list.size();i++){
			news += replyrepository.findReplyByArticleId(list.get(i).getId()).size();
		}
		if (news > olds) {
			return true;
		}
		if (news < olds) {
			updateNotified(userId);
		}
		return false;
	}

	@Override
	public void newReply(String userId) {
		int news = userRepository.findUserByUserId(userId).getNowNumber();
		news ++;
		userRepository.updateNowNumber(userId, news);
	}

}
