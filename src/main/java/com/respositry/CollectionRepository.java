package com.respositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, String> {
	@Query("select c from Collection c where c.userId = ?1")
	List<Collection> findCollectionByUserId(String userId);
}
