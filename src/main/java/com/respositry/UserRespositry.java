package com.respositry;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.User;

/**
 * @ClassName: UserRespositry
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月7日 下午3:01:41
 * @version V1.0
 */
@Repository
public interface UserRespositry extends JpaRepository<User, String> {
	@Query("select u from User u where u.loginName=?1 and u.password=?2")
	User findUserByNameAndPawd(String loginName, String password);

	@Query("select u from User u where u.uuid =?1")
	User findUserByUserId(String userId);
	@Transactional
	@Modifying
	@Query("update User u set u.logoutNumber = ?2 where u.uuid=?1")
	void updateLogoutNumber(String userId,int news);
	@Transactional
	@Modifying
	@Query("update User u set u.nowNumber = ?2 where u.uuid=?1")
	void updateNowNumber(String userId,int news);
}
