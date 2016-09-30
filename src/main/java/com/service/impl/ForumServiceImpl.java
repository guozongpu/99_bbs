package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.entity.Forum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.respositry.ForumRespositry;
import com.service.ForumService;
import com.util.JacksonCustomObjectMapper;
import com.util.SortUtil;

/**
 * @ClassName: ForumServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月7日 下午3:03:12
 * @version V1.0
 */
@Service
public class ForumServiceImpl implements ForumService {

	@Autowired
	ForumRespositry forumRepository;

	@Override
	public void addForum(Forum forum) {
		forumRepository.save(forum);

	}

	@Override
	public List<Forum> findForums() {

		return forumRepository.findForums();
	}

	@Override
	public List<JSONObject> showHotForums() {
		List<Forum> list = new ArrayList<Forum>();
		list = forumRepository.findAll();
		SortUtil.sortForums(list);
		List<JSONObject> result = new ArrayList<JSONObject>();
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
	public void newArticle(String forumId) {
		int floor = forumRepository.findForumById(forumId).getfloor();
		floor++;
		forumRepository.addFloorById(forumId, floor);
	}

	@Override
	public List<JSONObject> searchForums(String keywords) {
		List<Forum> list = forumRepository.findAll();
		List<JSONObject> result = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().contains(keywords)) {
				try {
					result.add(JSONObject
							.parseObject(new JacksonCustomObjectMapper()
									.writeValueAsString(list.get(i))));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
