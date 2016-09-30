package com.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.entity.Reply;

public interface ReplyService {
	 void addReply(Reply reply);
	 void delete(String replyId);
	 List<JSONObject> findReplyByArticleId(String articleId);
	 List<JSONObject> findSbodyReplies(String userId);
}
