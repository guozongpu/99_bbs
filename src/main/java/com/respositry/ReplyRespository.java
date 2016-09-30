package com.respositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.Reply;
@Repository
public interface ReplyRespository extends JpaRepository<Reply, String>{
    @Query("select r from Reply r where r.articleId=?1 order by r.postTime asc")
    List<Reply> findReplyByArticleId(String articleId);
    @Query("select r from Reply r where r.userId=?1 order by r.postTime asc")
    List<Reply> findReplyByUserId(String userId);
}
