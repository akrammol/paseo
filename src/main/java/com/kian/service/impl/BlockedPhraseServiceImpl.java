package com.kian.service.impl;

import com.kian.service.BlockedPhraseService;
import com.kian.domain.BlockedPhrase;
import com.kian.repository.BlockedPhraseRepository;
import com.kian.service.dto.BlockedPhraseDTO;
import com.kian.service.mapper.BlockedPhraseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BlockedPhrase}.
 */
@Service
@Transactional
public class BlockedPhraseServiceImpl implements BlockedPhraseService {

    private final Logger log = LoggerFactory.getLogger(BlockedPhraseServiceImpl.class);

    private final BlockedPhraseRepository blockedPhraseRepository;

    private final BlockedPhraseMapper blockedPhraseMapper;

    public BlockedPhraseServiceImpl(BlockedPhraseRepository blockedPhraseRepository, BlockedPhraseMapper blockedPhraseMapper) {
        this.blockedPhraseRepository = blockedPhraseRepository;
        this.blockedPhraseMapper = blockedPhraseMapper;
    }

    /**
     * Save a blockedPhrase.
     *
     * @param blockedPhraseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlockedPhraseDTO save(BlockedPhraseDTO blockedPhraseDTO) {
        log.debug("Request to save BlockedPhrase : {}", blockedPhraseDTO);
        BlockedPhrase blockedPhrase = blockedPhraseMapper.toEntity(blockedPhraseDTO);
        blockedPhrase = blockedPhraseRepository.save(blockedPhrase);
        return blockedPhraseMapper.toDto(blockedPhrase);
    }

    /**
     * Get all the blockedPhrases.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BlockedPhraseDTO> findAll() {
        log.debug("Request to get all BlockedPhrases");
        return blockedPhraseRepository.findAll().stream()
            .map(blockedPhraseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one blockedPhrase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BlockedPhraseDTO> findOne(Long id) {
        log.debug("Request to get BlockedPhrase : {}", id);
        return blockedPhraseRepository.findById(id)
            .map(blockedPhraseMapper::toDto);
    }

    /**
     * Delete the blockedPhrase by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlockedPhrase : {}", id);
        blockedPhraseRepository.deleteById(id);
    }
}
