package com.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.entity.User;

/** 
* @ClassName: UserService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午3:02:36 
* @version V1.0 
*/
public interface UserService {
     void addUser(User user);
     JSONObject findUser(String loginName,String password);
     List<JSONObject> showAllUsers();
     void updateNotified(String userId);
     boolean checkNotified(String userId);
     void newReply(String userId);
}
