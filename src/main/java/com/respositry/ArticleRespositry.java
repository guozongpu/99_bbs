package com.respositry;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.Article;

/**
 * @ClassName: ArticleRespositry
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author (黄志强)
 * @date 2016年9月7日 下午3:00:50
 * @version V1.0
 */
@Repository
public interface ArticleRespositry extends JpaRepository<Article, String> {
	@Query("select a from Article a where a.forumId=?1 order by a.postTime desc ")
	List<Article> queArticlesByForumId(String forumId);

	@Query("select a from Article a where a.userId=?1 order by a.postTime desc")
	List<Article> queryArticlesByUserId(String userId);

	@Modifying
	@Transactional
	@Query("delete from Article where userId=?1 and id=?2")
	void deleteByUserIDandArticleId(String userId, String ArticleId);

	@Query("select a from Article a where a.id=?1")
	Article findArticleById(String ArticleId);

	@Transactional
	@Modifying
	@Query("update Article a set a.replyNumber=?2 where a.id=?1")
	void addReplyNumberById(String ArticleId, int replyNumber);
}
