package com.respositry;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.Forum;

/** 
* @ClassName: ForumRespositry 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午3:01:07 
* @version V1.0 
*/
@Repository
public interface ForumRespositry extends JpaRepository<Forum, String>{
    @Query("select f from Forum f order by f.floor asc")
    public List<Forum> findForums();
    @Query("select f from Forum f where f.id = ?1")
    public Forum findForumById(String forumId);
    @Transactional
	@Modifying
	@Query("update Forum f set f.floor=?2 where f.id=?1")
	void addFloorById(String forumId, int replyNumber);
}
