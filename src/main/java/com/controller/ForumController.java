package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.entity.Forum;
import com.service.ForumService;
import com.util.ValidationUtil;

/** 
* @ClassName: ForumController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午2:57:00 
* @version V1.0 
*/
@RestController
@RequestMapping("/forum")
public class ForumController {
        @Autowired
        ForumService forumService;
        /** 
        * @Title: addForum 
        * @Description: 增加板块  
        *  
        */
        @RequestMapping(value="/addforum",method=RequestMethod.POST)
        public JSONObject addForum(@RequestBody Forum forum){
        	
        	JSONObject json = new JSONObject();
        	
        	if(!ValidationUtil.validateNull(forum.getName())){
        		json.put("result", "话题名称不能为空");
    			return json;
    		}
    		if (!ValidationUtil.validateLength(forum.getName(), 1000)) {
    			json.put("result", "话题名称长度不能超过1000");
    			return json;
    		}
    		if(!ValidationUtil.validateNull(forum.getDescription())){
    			json.put("result", "话题描述不能为空");
    			return json;
    		}
    		if (!ValidationUtil.validateLength(forum.getDescription(), 1000)) {
    			json.put("result", "话题描述长度不能超过1000");
    			return json;
    		}
    		
    		forumService.addForum(forum);
    		
    		json.put("result", "话题创建成功");
    		return json;
        }
        
        /** 
        * @Title: findForums 
        * @Description: 查询所有板块  
        *  
        */
        @RequestMapping(value="/queryAll",method=RequestMethod.GET)
        public List<Forum> findForums(){
            return forumService.findForums();
        }
        
        /** 
         * @Title: findHotForums 
         * @Description: 查询热门板块  
         *  
         */
        @RequestMapping(value="/showHot",method = RequestMethod.GET)
        public List<JSONObject> findHotForums(){

        	return forumService.showHotForums();
        }
        /** 
         * @Title: searchForums 
         * @Description: 搜索板块  
         *  
         */
        @RequestMapping(value="/search",method = RequestMethod.POST)
        public List<JSONObject> searchForums(@RequestBody Forum forum){

        	return forumService.searchForums(forum.getName());
        }
}
