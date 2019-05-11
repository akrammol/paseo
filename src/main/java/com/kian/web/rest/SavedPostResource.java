package com.kian.web.rest;

import com.kian.service.SavedPostService;
import com.kian.web.rest.errors.BadRequestAlertException;
import com.kian.service.dto.SavedPostDTO;

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
 * REST controller for managing {@link com.kian.domain.SavedPost}.
 */
@RestController
@RequestMapping("/api")
public class SavedPostResource {

    private final Logger log = LoggerFactory.getLogger(SavedPostResource.class);

    private static final String ENTITY_NAME = "paseoSavedPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SavedPostService savedPostService;

    public SavedPostResource(SavedPostService savedPostService) {
        this.savedPostService = savedPostService;
    }

    /**
     * {@code POST  /saved-posts} : Create a new savedPost.
     *
     * @param savedPostDTO the savedPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new savedPostDTO, or with status {@code 400 (Bad Request)} if the savedPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saved-posts")
    public ResponseEntity<SavedPostDTO> createSavedPost(@RequestBody SavedPostDTO savedPostDTO) throws URISyntaxException {
        log.debug("REST request to save SavedPost : {}", savedPostDTO);
        if (savedPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new savedPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SavedPostDTO result = savedPostService.save(savedPostDTO);
        return ResponseEntity.created(new URI("/api/saved-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saved-posts} : Updates an existing savedPost.
     *
     * @param savedPostDTO the savedPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated savedPostDTO,
     * or with status {@code 400 (Bad Request)} if the savedPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the savedPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saved-posts")
    public ResponseEntity<SavedPostDTO> updateSavedPost(@RequestBody SavedPostDTO savedPostDTO) throws URISyntaxException {
        log.debug("REST request to update SavedPost : {}", savedPostDTO);
        if (savedPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SavedPostDTO result = savedPostService.save(savedPostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, savedPostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /saved-posts} : get all the savedPosts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of savedPosts in body.
     */
    @GetMapping("/saved-posts")
    public List<SavedPostDTO> getAllSavedPosts() {
        log.debug("REST request to get all SavedPosts");
        return savedPostService.findAll();
    }

    /**
     * {@code GET  /saved-posts/:id} : get the "id" savedPost.
     *
     * @param id the id of the savedPostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the savedPostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saved-posts/{id}")
    public ResponseEntity<SavedPostDTO> getSavedPost(@PathVariable Long id) {
        log.debug("REST request to get SavedPost : {}", id);
        Optional<SavedPostDTO> savedPostDTO = savedPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(savedPostDTO);
    }

    /**
     * {@code DELETE  /saved-posts/:id} : delete the "id" savedPost.
     *
     * @param id the id of the savedPostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saved-posts/{id}")
    public ResponseEntity<Void> deleteSavedPost(@PathVariable Long id) {
        log.debug("REST request to delete SavedPost : {}", id);
        savedPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
