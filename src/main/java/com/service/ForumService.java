package com.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.entity.Forum;

/** 
* @ClassName: ForumService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午3:02:09 
* @version V1.0 
*/
public interface ForumService {
    void addForum(Forum forum);
    List<Forum> findForums();
    List<JSONObject> showHotForums();
    void newArticle(String forumId);
    List<JSONObject> searchForums(String keywords);
}
