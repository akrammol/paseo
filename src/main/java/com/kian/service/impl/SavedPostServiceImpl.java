package com.kian.service.impl;

import com.kian.service.SavedPostService;
import com.kian.domain.SavedPost;
import com.kian.repository.SavedPostRepository;
import com.kian.service.dto.SavedPostDTO;
import com.kian.service.mapper.SavedPostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SavedPost}.
 */
@Service
@Transactional
public class SavedPostServiceImpl implements SavedPostService {

    private final Logger log = LoggerFactory.getLogger(SavedPostServiceImpl.class);

    private final SavedPostRepository savedPostRepository;

    private final SavedPostMapper savedPostMapper;

    public SavedPostServiceImpl(SavedPostRepository savedPostRepository, SavedPostMapper savedPostMapper) {
        this.savedPostRepository = savedPostRepository;
        this.savedPostMapper = savedPostMapper;
    }

    /**
     * Save a savedPost.
     *
     * @param savedPostDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SavedPostDTO save(SavedPostDTO savedPostDTO) {
        log.debug("Request to save SavedPost : {}", savedPostDTO);
        SavedPost savedPost = savedPostMapper.toEntity(savedPostDTO);
        savedPost = savedPostRepository.save(savedPost);
        return savedPostMapper.toDto(savedPost);
    }

    /**
     * Get all the savedPosts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SavedPostDTO> findAll() {
        log.debug("Request to get all SavedPosts");
        return savedPostRepository.findAll().stream()
            .map(savedPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one savedPost by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SavedPostDTO> findOne(Long id) {
        log.debug("Request to get SavedPost : {}", id);
        return savedPostRepository.findById(id)
            .map(savedPostMapper::toDto);
    }

    /**
     * Delete the savedPost by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SavedPost : {}", id);
        savedPostRepository.deleteById(id);
    }
}
