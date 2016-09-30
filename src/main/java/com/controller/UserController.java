package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.entity.User;
import com.service.UserService;

/**
 * @ClassName: UserController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月7日 下午2:57:13
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * @Title: registerUser
	 * @Description: 注册用户
	 * 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void registerUser(@RequestBody User user) {
		userService.addUser(user);
	}

	/**
	 * @Title: login
	 * @Description: 用户登录
	 * 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONObject login(@RequestBody User user) {
		String loginName = user.getLoginName();
		String password = user.getPassword();
		JSONObject json = userService.findUser(loginName, password);
		if (json != null) {
			if (userService.checkNotified(json.get("uuid").toString())) {
				json.put("result", "new reply");
			} else {
				json.put("result", "nothing");
			}
		}
		return json;
	}

	/**
	 * @Title: logout
	 * @Description: 用户登出
	 * 
	 */
	@RequestMapping(value = "/logout/{user_id}", method = RequestMethod.GET)
	public JSONObject logout(@PathVariable(value = "user_id") String userId) {

		userService.updateNotified(userId);
		JSONObject json = new JSONObject();
		json.put("result", "登出成功！");
		return json;
	}

	/**
	 * @Title: show
	 * @Description: 查看所有用户
	 * 
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public List<JSONObject> showAllUsers() {

		return userService.showAllUsers();
	}
}
