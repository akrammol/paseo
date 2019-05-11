package com.kian.repository;

import com.kian.domain.SavedPost;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SavedPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {

}
