package com.kian.repository;

import com.kian.domain.BlockedPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BlockedPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockedPersonRepository extends JpaRepository<BlockedPerson, Long> {

}
