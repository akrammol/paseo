package com.kian.service;

import com.kian.service.dto.BlockedPersonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kian.domain.BlockedPerson}.
 */
public interface BlockedPersonService {

    /**
     * Save a blockedPerson.
     *
     * @param blockedPersonDTO the entity to save.
     * @return the persisted entity.
     */
    BlockedPersonDTO save(BlockedPersonDTO blockedPersonDTO);

    /**
     * Get all the blockedPeople.
     *
     * @return the list of entities.
     */
    List<BlockedPersonDTO> findAll();


    /**
     * Get the "id" blockedPerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlockedPersonDTO> findOne(Long id);

    /**
     * Delete the "id" blockedPerson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
