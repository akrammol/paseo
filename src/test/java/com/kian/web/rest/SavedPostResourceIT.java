package com.kian.web.rest;

import com.kian.PaseoApp;
import com.kian.config.SecurityBeanOverrideConfiguration;
import com.kian.domain.SavedPost;
import com.kian.repository.SavedPostRepository;
import com.kian.service.SavedPostService;
import com.kian.service.dto.SavedPostDTO;
import com.kian.service.mapper.SavedPostMapper;
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
 * Integration tests for the {@Link SavedPostResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PaseoApp.class})
public class SavedPostResourceIT {

    private static final Instant DEFAULT_SAVE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SAVE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SavedPostRepository savedPostRepository;

    @Autowired
    private SavedPostMapper savedPostMapper;

    @Autowired
    private SavedPostService savedPostService;

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

    private MockMvc restSavedPostMockMvc;

    private SavedPost savedPost;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SavedPostResource savedPostResource = new SavedPostResource(savedPostService);
        this.restSavedPostMockMvc = MockMvcBuilders.standaloneSetup(savedPostResource)
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
    public static SavedPost createEntity(EntityManager em) {
        SavedPost savedPost = new SavedPost()
            .saveTime(DEFAULT_SAVE_TIME);
        return savedPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SavedPost createUpdatedEntity(EntityManager em) {
        SavedPost savedPost = new SavedPost()
            .saveTime(UPDATED_SAVE_TIME);
        return savedPost;
    }

    @BeforeEach
    public void initTest() {
        savedPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createSavedPost() throws Exception {
        int databaseSizeBeforeCreate = savedPostRepository.findAll().size();

        // Create the SavedPost
        SavedPostDTO savedPostDTO = savedPostMapper.toDto(savedPost);
        restSavedPostMockMvc.perform(post("/api/saved-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(savedPostDTO)))
            .andExpect(status().isCreated());

        // Validate the SavedPost in the database
        List<SavedPost> savedPostList = savedPostRepository.findAll();
        assertThat(savedPostList).hasSize(databaseSizeBeforeCreate + 1);
        SavedPost testSavedPost = savedPostList.get(savedPostList.size() - 1);
        assertThat(testSavedPost.getSaveTime()).isEqualTo(DEFAULT_SAVE_TIME);
    }

    @Test
    @Transactional
    public void createSavedPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = savedPostRepository.findAll().size();

        // Create the SavedPost with an existing ID
        savedPost.setId(1L);
        SavedPostDTO savedPostDTO = savedPostMapper.toDto(savedPost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSavedPostMockMvc.perform(post("/api/saved-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(savedPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SavedPost in the database
        List<SavedPost> savedPostList = savedPostRepository.findAll();
        assertThat(savedPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSavedPosts() throws Exception {
        // Initialize the database
        savedPostRepository.saveAndFlush(savedPost);

        // Get all the savedPostList
        restSavedPostMockMvc.perform(get("/api/saved-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(savedPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].saveTime").value(hasItem(DEFAULT_SAVE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSavedPost() throws Exception {
        // Initialize the database
        savedPostRepository.saveAndFlush(savedPost);

        // Get the savedPost
        restSavedPostMockMvc.perform(get("/api/saved-posts/{id}", savedPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(savedPost.getId().intValue()))
            .andExpect(jsonPath("$.saveTime").value(DEFAULT_SAVE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSavedPost() throws Exception {
        // Get the savedPost
        restSavedPostMockMvc.perform(get("/api/saved-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSavedPost() throws Exception {
        // Initialize the database
        savedPostRepository.saveAndFlush(savedPost);

        int databaseSizeBeforeUpdate = savedPostRepository.findAll().size();

        // Update the savedPost
        SavedPost updatedSavedPost = savedPostRepository.findById(savedPost.getId()).get();
        // Disconnect from session so that the updates on updatedSavedPost are not directly saved in db
        em.detach(updatedSavedPost);
        updatedSavedPost
            .saveTime(UPDATED_SAVE_TIME);
        SavedPostDTO savedPostDTO = savedPostMapper.toDto(updatedSavedPost);

        restSavedPostMockMvc.perform(put("/api/saved-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(savedPostDTO)))
            .andExpect(status().isOk());

        // Validate the SavedPost in the database
        List<SavedPost> savedPostList = savedPostRepository.findAll();
        assertThat(savedPostList).hasSize(databaseSizeBeforeUpdate);
        SavedPost testSavedPost = savedPostList.get(savedPostList.size() - 1);
        assertThat(testSavedPost.getSaveTime()).isEqualTo(UPDATED_SAVE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSavedPost() throws Exception {
        int databaseSizeBeforeUpdate = savedPostRepository.findAll().size();

        // Create the SavedPost
        SavedPostDTO savedPostDTO = savedPostMapper.toDto(savedPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSavedPostMockMvc.perform(put("/api/saved-posts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(savedPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SavedPost in the database
        List<SavedPost> savedPostList = savedPostRepository.findAll();
        assertThat(savedPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSavedPost() throws Exception {
        // Initialize the database
        savedPostRepository.saveAndFlush(savedPost);

        int databaseSizeBeforeDelete = savedPostRepository.findAll().size();

        // Delete the savedPost
        restSavedPostMockMvc.perform(delete("/api/saved-posts/{id}", savedPost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SavedPost> savedPostList = savedPostRepository.findAll();
        assertThat(savedPostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SavedPost.class);
        SavedPost savedPost1 = new SavedPost();
        savedPost1.setId(1L);
        SavedPost savedPost2 = new SavedPost();
        savedPost2.setId(savedPost1.getId());
        assertThat(savedPost1).isEqualTo(savedPost2);
        savedPost2.setId(2L);
        assertThat(savedPost1).isNotEqualTo(savedPost2);
        savedPost1.setId(null);
        assertThat(savedPost1).isNotEqualTo(savedPost2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SavedPostDTO.class);
        SavedPostDTO savedPostDTO1 = new SavedPostDTO();
        savedPostDTO1.setId(1L);
        SavedPostDTO savedPostDTO2 = new SavedPostDTO();
        assertThat(savedPostDTO1).isNotEqualTo(savedPostDTO2);
        savedPostDTO2.setId(savedPostDTO1.getId());
        assertThat(savedPostDTO1).isEqualTo(savedPostDTO2);
        savedPostDTO2.setId(2L);
        assertThat(savedPostDTO1).isNotEqualTo(savedPostDTO2);
        savedPostDTO1.setId(null);
        assertThat(savedPostDTO1).isNotEqualTo(savedPostDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(savedPostMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(savedPostMapper.fromId(null)).isNull();
    }
}
