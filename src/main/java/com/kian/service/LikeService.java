package com.kian.service;

import com.kian.service.dto.LikeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kian.domain.Like}.
 */
public interface LikeService {

    /**
     * Save a like.
     *
     * @param likeDTO the entity to save.
     * @return the persisted entity.
     */
    LikeDTO save(LikeDTO likeDTO);

    /**
     * Get all the likes.
     *
     * @return the list of entities.
     */
    List<LikeDTO> findAll();


    /**
     * Get the "id" like.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeDTO> findOne(Long id);

    /**
     * Delete the "id" like.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
