package com.kian.web.rest;

import com.kian.PaseoApp;
import com.kian.config.SecurityBeanOverrideConfiguration;
import com.kian.domain.Like;
import com.kian.repository.LikeRepository;
import com.kian.service.LikeService;
import com.kian.service.dto.LikeDTO;
import com.kian.service.mapper.LikeMapper;
import com.kian.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.kian.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link LikeResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PaseoApp.class})
public class LikeResourceIT {

    private static final Instant DEFAULT_SUBMIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private LikeService likeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLikeMockMvc;

    private Like like;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LikeResource likeResource = new LikeResource(likeService);
        this.restLikeMockMvc = MockMvcBuilders.standaloneSetup(likeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createEntity(EntityManager em) {
        Like like = new Like()
            .submitTime(DEFAULT_SUBMIT_TIME);
        return like;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createUpdatedEntity(EntityManager em) {
        Like like = new Like()
            .submitTime(UPDATED_SUBMIT_TIME);
        return like;
    }

    @BeforeEach
    public void initTest() {
        like = createEntity(em);
    }

    @Test
    @Transactional
    public void createLike() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);
        restLikeMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isCreated());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate + 1);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getSubmitTime()).isEqualTo(DEFAULT_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void createLikeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();

        // Create the Like with an existing ID
        like.setId(1L);
        LikeDTO likeDTO = likeMapper.toDto(like);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLikes() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList
        restLikeMockMvc.perform(get("/api/likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId().intValue())))
            .andExpect(jsonPath("$.[*].submitTime").value(hasItem(DEFAULT_SUBMIT_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get the like
        restLikeMockMvc.perform(get("/api/likes/{id}", like.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(like.getId().intValue()))
            .andExpect(jsonPath("$.submitTime").value(DEFAULT_SUBMIT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLike() throws Exception {
        // Get the like
        restLikeMockMvc.perform(get("/api/likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like
        Like updatedLike = likeRepository.findById(like.getId()).get();
        // Disconnect from session so that the updates on updatedLike are not directly saved in db
        em.detach(updatedLike);
        updatedLike
            .submitTime(UPDATED_SUBMIT_TIME);
        LikeDTO likeDTO = likeMapper.toDto(updatedLike);

        restLikeMockMvc.perform(put("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getSubmitTime()).isEqualTo(UPDATED_SUBMIT_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc.perform(put("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        int databaseSizeBeforeDelete = likeRepository.findAll().size();

        // Delete the like
        restLikeMockMvc.perform(delete("/api/likes/{id}", like.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Like.class);
        Like like1 = new Like();
        like1.setId(1L);
        Like like2 = new Like();
        like2.setId(like1.getId());
        assertThat(like1).isEqualTo(like2);
        like2.setId(2L);
        assertThat(like1).isNotEqualTo(like2);
        like1.setId(null);
        assertThat(like1).isNotEqualTo(like2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeDTO.class);
        LikeDTO likeDTO1 = new LikeDTO();
        likeDTO1.setId(1L);
        LikeDTO likeDTO2 = new LikeDTO();
        assertThat(likeDTO1).isNotEqualTo(likeDTO2);
        likeDTO2.setId(likeDTO1.getId());
        assertThat(likeDTO1).isEqualTo(likeDTO2);
        likeDTO2.setId(2L);
        assertThat(likeDTO1).isNotEqualTo(likeDTO2);
        likeDTO1.setId(null);
        assertThat(likeDTO1).isNotEqualTo(likeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(likeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(likeMapper.fromId(null)).isNull();
    }
}
