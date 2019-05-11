package com.kian.repository;

import com.kian.domain.BlockedPhrase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BlockedPhrase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockedPhraseRepository extends JpaRepository<BlockedPhrase, Long> {

}
