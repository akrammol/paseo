package com.kian.service;

import com.kian.service.dto.BlockedPhraseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kian.domain.BlockedPhrase}.
 */
public interface BlockedPhraseService {

    /**
     * Save a blockedPhrase.
     *
     * @param blockedPhraseDTO the entity to save.
     * @return the persisted entity.
     */
    BlockedPhraseDTO save(BlockedPhraseDTO blockedPhraseDTO);

    /**
     * Get all the blockedPhrases.
     *
     * @return the list of entities.
     */
    List<BlockedPhraseDTO> findAll();


    /**
     * Get the "id" blockedPhrase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlockedPhraseDTO> findOne(Long id);

    /**
     * Delete the "id" blockedPhrase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
