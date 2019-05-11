package com.kian.service;

import com.kian.service.dto.SavedPostDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kian.domain.SavedPost}.
 */
public interface SavedPostService {

    /**
     * Save a savedPost.
     *
     * @param savedPostDTO the entity to save.
     * @return the persisted entity.
     */
    SavedPostDTO save(SavedPostDTO savedPostDTO);

    /**
     * Get all the savedPosts.
     *
     * @return the list of entities.
     */
    List<SavedPostDTO> findAll();


    /**
     * Get the "id" savedPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SavedPostDTO> findOne(Long id);

    /**
     * Delete the "id" savedPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
