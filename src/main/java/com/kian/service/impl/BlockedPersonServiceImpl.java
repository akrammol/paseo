package com.kian.service.impl;

import com.kian.service.BlockedPersonService;
import com.kian.domain.BlockedPerson;
import com.kian.repository.BlockedPersonRepository;
import com.kian.service.dto.BlockedPersonDTO;
import com.kian.service.mapper.BlockedPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BlockedPerson}.
 */
@Service
@Transactional
public class BlockedPersonServiceImpl implements BlockedPersonService {

    private final Logger log = LoggerFactory.getLogger(BlockedPersonServiceImpl.class);

    private final BlockedPersonRepository blockedPersonRepository;

    private final BlockedPersonMapper blockedPersonMapper;

    public BlockedPersonServiceImpl(BlockedPersonRepository blockedPersonRepository, BlockedPersonMapper blockedPersonMapper) {
        this.blockedPersonRepository = blockedPersonRepository;
        this.blockedPersonMapper = blockedPersonMapper;
    }

    /**
     * Save a blockedPerson.
     *
     * @param blockedPersonDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BlockedPersonDTO save(BlockedPersonDTO blockedPersonDTO) {
        log.debug("Request to save BlockedPerson : {}", blockedPersonDTO);
        BlockedPerson blockedPerson = blockedPersonMapper.toEntity(blockedPersonDTO);
        blockedPerson = blockedPersonRepository.save(blockedPerson);
        return blockedPersonMapper.toDto(blockedPerson);
    }

    /**
     * Get all the blockedPeople.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BlockedPersonDTO> findAll() {
        log.debug("Request to get all BlockedPeople");
        return blockedPersonRepository.findAll().stream()
            .map(blockedPersonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one blockedPerson by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BlockedPersonDTO> findOne(Long id) {
        log.debug("Request to get BlockedPerson : {}", id);
        return blockedPersonRepository.findById(id)
            .map(blockedPersonMapper::toDto);
    }

    /**
     * Delete the blockedPerson by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlockedPerson : {}", id);
        blockedPersonRepository.deleteById(id);
    }
}
