package com.kian.web.rest;

import com.kian.service.LikeService;
import com.kian.web.rest.errors.BadRequestAlertException;
import com.kian.service.dto.LikeDTO;

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
 * REST controller for managing {@link com.kian.domain.Like}.
 */
@RestController
@RequestMapping("/api")
public class LikeResource {

    private final Logger log = LoggerFactory.getLogger(LikeResource.class);

    private static final String ENTITY_NAME = "paseoLike";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeService likeService;

    public LikeResource(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * {@code POST  /likes} : Create a new like.
     *
     * @param likeDTO the likeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likeDTO, or with status {@code 400 (Bad Request)} if the like has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/likes")
    public ResponseEntity<LikeDTO> createLike(@RequestBody LikeDTO likeDTO) throws URISyntaxException {
        log.debug("REST request to save Like : {}", likeDTO);
        if (likeDTO.getId() != null) {
            throw new BadRequestAlertException("A new like cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikeDTO result = likeService.save(likeDTO);
        return ResponseEntity.created(new URI("/api/likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /likes} : Updates an existing like.
     *
     * @param likeDTO the likeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeDTO,
     * or with status {@code 400 (Bad Request)} if the likeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/likes")
    public ResponseEntity<LikeDTO> updateLike(@RequestBody LikeDTO likeDTO) throws URISyntaxException {
        log.debug("REST request to update Like : {}", likeDTO);
        if (likeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LikeDTO result = likeService.save(likeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, likeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /likes} : get all the likes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likes in body.
     */
    @GetMapping("/likes")
    public List<LikeDTO> getAllLikes() {
        log.debug("REST request to get all Likes");
        return likeService.findAll();
    }

    /**
     * {@code GET  /likes/:id} : get the "id" like.
     *
     * @param id the id of the likeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/likes/{id}")
    public ResponseEntity<LikeDTO> getLike(@PathVariable Long id) {
        log.debug("REST request to get Like : {}", id);
        Optional<LikeDTO> likeDTO = likeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeDTO);
    }

    /**
     * {@code DELETE  /likes/:id} : delete the "id" like.
     *
     * @param id the id of the likeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/likes/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        log.debug("REST request to delete Like : {}", id);
        likeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
