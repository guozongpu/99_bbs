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

import com.alibaba.fastjson.JSONObject;
import com.entity.Reply;
import com.service.ArticleService;
import com.service.ReplyService;
import com.service.UserService;
import com.util.ValidationUtil;

/**
 * @ClassName: ReplyController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月8日 下午5:40:39
 * @version V1.0
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	ReplyService replyService;
	@Autowired
	ArticleService articleService;
	@Autowired
	UserService userService;

	/**
	 * @Title: addReply
	 * @Description: 回复
	 * 
	 */
	@RequestMapping(value = "/add/{user_id}/{article_id}", method = RequestMethod.POST)
	public JSONObject addReply(@RequestBody Reply reply,
			@PathVariable(value = "user_id") String user_id,
			@PathVariable(value = "article_id") String article_id) {
		reply.setPostTime(new Date());
		reply.setUserId(user_id);
		reply.setArticleId(article_id);
		reply.setPostTime(new Date());
		
		JSONObject json = new JSONObject();
		if(!ValidationUtil.validateNull(reply.getContent())){
			json.put("result", "回复不能为空");
			return json;
		}
		if (!ValidationUtil.validateLength(reply.getContent(), 1000)) {
			json.put("result", "回复长度不能超过1000");
			return json;
		}
		replyService.addReply(reply);
		articleService.newReply(article_id);
		userService.newReply(articleService.queryArticle(article_id).getUserId());
		
		json.put("result", "回复成功");
		return json;
	}

	/**
	 * @Title: delete
	 * @Description: 删除回复
	 * 
	 */
	@RequestMapping(value = "/delete/{reply_id}", method = RequestMethod.GET)
	public void delete(@PathVariable(value = "reply_id") String replyId) {
		replyService.delete(replyId);
	}

	/**
	 * @Title: findReplies
	 * @Description: 查找帖子下面的所有回复
	 * 
	 */
	@RequestMapping(value = "/queryByArticleId/{article_id}", method = RequestMethod.GET)
	public List<JSONObject> findReplies(
			@PathVariable(value = "article_id") String articleId) {

		return replyService.findReplyByArticleId(articleId);
	}

	/**
	 * @Title: findSbodyReplies
	 * @Description: 查找某个用户的所有回复
	 * 
	 */
	@RequestMapping(value = "/queryByUserId/{user_id}")
	public List<JSONObject> findSbodyReplies(
			@PathVariable(value = "user_id") String user_id) {
	
		return replyService.findSbodyReplies(user_id);
	}
}
