package com.kian.web.rest;

import com.kian.service.BlockedPhraseService;
import com.kian.web.rest.errors.BadRequestAlertException;
import com.kian.service.dto.BlockedPhraseDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kian.domain.BlockedPhrase}.
 */
@RestController
@RequestMapping("/api")
public class BlockedPhraseResource {

    private final Logger log = LoggerFactory.getLogger(BlockedPhraseResource.class);

    private static final String ENTITY_NAME = "paseoBlockedPhrase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlockedPhraseService blockedPhraseService;

    public BlockedPhraseResource(BlockedPhraseService blockedPhraseService) {
        this.blockedPhraseService = blockedPhraseService;
    }

    /**
     * {@code POST  /blocked-phrases} : Create a new blockedPhrase.
     *
     * @param blockedPhraseDTO the blockedPhraseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blockedPhraseDTO, or with status {@code 400 (Bad Request)} if the blockedPhrase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blocked-phrases")
    public ResponseEntity<BlockedPhraseDTO> createBlockedPhrase(@RequestBody BlockedPhraseDTO blockedPhraseDTO) throws URISyntaxException {
        log.debug("REST request to save BlockedPhrase : {}", blockedPhraseDTO);
        if (blockedPhraseDTO.getId() != null) {
            throw new BadRequestAlertException("A new blockedPhrase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlockedPhraseDTO result = blockedPhraseService.save(blockedPhraseDTO);
        return ResponseEntity.created(new URI("/api/blocked-phrases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blocked-phrases} : Updates an existing blockedPhrase.
     *
     * @param blockedPhraseDTO the blockedPhraseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockedPhraseDTO,
     * or with status {@code 400 (Bad Request)} if the blockedPhraseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blockedPhraseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blocked-phrases")
    public ResponseEntity<BlockedPhraseDTO> updateBlockedPhrase(@RequestBody BlockedPhraseDTO blockedPhraseDTO) throws URISyntaxException {
        log.debug("REST request to update BlockedPhrase : {}", blockedPhraseDTO);
        if (blockedPhraseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlockedPhraseDTO result = blockedPhraseService.save(blockedPhraseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blockedPhraseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blocked-phrases} : get all the blockedPhrases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blockedPhrases in body.
     */
    @GetMapping("/blocked-phrases")
    public List<BlockedPhraseDTO> getAllBlockedPhrases() {
        log.debug("REST request to get all BlockedPhrases");
        return blockedPhraseService.findAll();
    }

    /**
     * {@code GET  /blocked-phrases/:id} : get the "id" blockedPhrase.
     *
     * @param id the id of the blockedPhraseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blockedPhraseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blocked-phrases/{id}")
    public ResponseEntity<BlockedPhraseDTO> getBlockedPhrase(@PathVariable Long id) {
        log.debug("REST request to get BlockedPhrase : {}", id);
        Optional<BlockedPhraseDTO> blockedPhraseDTO = blockedPhraseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blockedPhraseDTO);
    }

    /**
     * {@code DELETE  /blocked-phrases/:id} : delete the "id" blockedPhrase.
     *
     * @param id the id of the blockedPhraseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blocked-phrases/{id}")
    public ResponseEntity<Void> deleteBlockedPhrase(@PathVariable Long id) {
        log.debug("REST request to delete BlockedPhrase : {}", id);
        blockedPhraseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
