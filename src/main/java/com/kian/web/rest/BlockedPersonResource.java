package com.kian.web.rest;

import com.kian.service.BlockedPersonService;
import com.kian.web.rest.errors.BadRequestAlertException;
import com.kian.service.dto.BlockedPersonDTO;

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
 * REST controller for managing {@link com.kian.domain.BlockedPerson}.
 */
@RestController
@RequestMapping("/api")
public class BlockedPersonResource {

    private final Logger log = LoggerFactory.getLogger(BlockedPersonResource.class);

    private static final String ENTITY_NAME = "paseoBlockedPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlockedPersonService blockedPersonService;

    public BlockedPersonResource(BlockedPersonService blockedPersonService) {
        this.blockedPersonService = blockedPersonService;
    }

    /**
     * {@code POST  /blocked-people} : Create a new blockedPerson.
     *
     * @param blockedPersonDTO the blockedPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blockedPersonDTO, or with status {@code 400 (Bad Request)} if the blockedPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blocked-people")
    public ResponseEntity<BlockedPersonDTO> createBlockedPerson(@RequestBody BlockedPersonDTO blockedPersonDTO) throws URISyntaxException {
        log.debug("REST request to save BlockedPerson : {}", blockedPersonDTO);
        if (blockedPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new blockedPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlockedPersonDTO result = blockedPersonService.save(blockedPersonDTO);
        return ResponseEntity.created(new URI("/api/blocked-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blocked-people} : Updates an existing blockedPerson.
     *
     * @param blockedPersonDTO the blockedPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockedPersonDTO,
     * or with status {@code 400 (Bad Request)} if the blockedPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blockedPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blocked-people")
    public ResponseEntity<BlockedPersonDTO> updateBlockedPerson(@RequestBody BlockedPersonDTO blockedPersonDTO) throws URISyntaxException {
        log.debug("REST request to update BlockedPerson : {}", blockedPersonDTO);
        if (blockedPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlockedPersonDTO result = blockedPersonService.save(blockedPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blockedPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blocked-people} : get all the blockedPeople.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blockedPeople in body.
     */
    @GetMapping("/blocked-people")
    public List<BlockedPersonDTO> getAllBlockedPeople() {
        log.debug("REST request to get all BlockedPeople");
        return blockedPersonService.findAll();
    }

    /**
     * {@code GET  /blocked-people/:id} : get the "id" blockedPerson.
     *
     * @param id the id of the blockedPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blockedPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blocked-people/{id}")
    public ResponseEntity<BlockedPersonDTO> getBlockedPerson(@PathVariable Long id) {
        log.debug("REST request to get BlockedPerson : {}", id);
        Optional<BlockedPersonDTO> blockedPersonDTO = blockedPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blockedPersonDTO);
    }

    /**
     * {@code DELETE  /blocked-people/:id} : delete the "id" blockedPerson.
     *
     * @param id the id of the blockedPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blocked-people/{id}")
    public ResponseEntity<Void> deleteBlockedPerson(@PathVariable Long id) {
        log.debug("REST request to delete BlockedPerson : {}", id);
        blockedPersonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
