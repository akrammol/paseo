package com.kian.web.rest;

import com.kian.PaseoApp;
import com.kian.config.SecurityBeanOverrideConfiguration;
import com.kian.domain.BlockedPhrase;
import com.kian.repository.BlockedPhraseRepository;
import com.kian.service.BlockedPhraseService;
import com.kian.service.dto.BlockedPhraseDTO;
import com.kian.service.mapper.BlockedPhraseMapper;
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
import java.util.List;

import static com.kian.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link BlockedPhraseResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PaseoApp.class})
public class BlockedPhraseResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private BlockedPhraseRepository blockedPhraseRepository;

    @Autowired
    private BlockedPhraseMapper blockedPhraseMapper;

    @Autowired
    private BlockedPhraseService blockedPhraseService;

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

    private MockMvc restBlockedPhraseMockMvc;

    private BlockedPhrase blockedPhrase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlockedPhraseResource blockedPhraseResource = new BlockedPhraseResource(blockedPhraseService);
        this.restBlockedPhraseMockMvc = MockMvcBuilders.standaloneSetup(blockedPhraseResource)
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
    public static BlockedPhrase createEntity(EntityManager em) {
        BlockedPhrase blockedPhrase = new BlockedPhrase()
            .value(DEFAULT_VALUE);
        return blockedPhrase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlockedPhrase createUpdatedEntity(EntityManager em) {
        BlockedPhrase blockedPhrase = new BlockedPhrase()
            .value(UPDATED_VALUE);
        return blockedPhrase;
    }

    @BeforeEach
    public void initTest() {
        blockedPhrase = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlockedPhrase() throws Exception {
        int databaseSizeBeforeCreate = blockedPhraseRepository.findAll().size();

        // Create the BlockedPhrase
        BlockedPhraseDTO blockedPhraseDTO = blockedPhraseMapper.toDto(blockedPhrase);
        restBlockedPhraseMockMvc.perform(post("/api/blocked-phrases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPhraseDTO)))
            .andExpect(status().isCreated());

        // Validate the BlockedPhrase in the database
        List<BlockedPhrase> blockedPhraseList = blockedPhraseRepository.findAll();
        assertThat(blockedPhraseList).hasSize(databaseSizeBeforeCreate + 1);
        BlockedPhrase testBlockedPhrase = blockedPhraseList.get(blockedPhraseList.size() - 1);
        assertThat(testBlockedPhrase.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createBlockedPhraseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blockedPhraseRepository.findAll().size();

        // Create the BlockedPhrase with an existing ID
        blockedPhrase.setId(1L);
        BlockedPhraseDTO blockedPhraseDTO = blockedPhraseMapper.toDto(blockedPhrase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockedPhraseMockMvc.perform(post("/api/blocked-phrases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPhraseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlockedPhrase in the database
        List<BlockedPhrase> blockedPhraseList = blockedPhraseRepository.findAll();
        assertThat(blockedPhraseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBlockedPhrases() throws Exception {
        // Initialize the database
        blockedPhraseRepository.saveAndFlush(blockedPhrase);

        // Get all the blockedPhraseList
        restBlockedPhraseMockMvc.perform(get("/api/blocked-phrases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blockedPhrase.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getBlockedPhrase() throws Exception {
        // Initialize the database
        blockedPhraseRepository.saveAndFlush(blockedPhrase);

        // Get the blockedPhrase
        restBlockedPhraseMockMvc.perform(get("/api/blocked-phrases/{id}", blockedPhrase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blockedPhrase.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBlockedPhrase() throws Exception {
        // Get the blockedPhrase
        restBlockedPhraseMockMvc.perform(get("/api/blocked-phrases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlockedPhrase() throws Exception {
        // Initialize the database
        blockedPhraseRepository.saveAndFlush(blockedPhrase);

        int databaseSizeBeforeUpdate = blockedPhraseRepository.findAll().size();

        // Update the blockedPhrase
        BlockedPhrase updatedBlockedPhrase = blockedPhraseRepository.findById(blockedPhrase.getId()).get();
        // Disconnect from session so that the updates on updatedBlockedPhrase are not directly saved in db
        em.detach(updatedBlockedPhrase);
        updatedBlockedPhrase
            .value(UPDATED_VALUE);
        BlockedPhraseDTO blockedPhraseDTO = blockedPhraseMapper.toDto(updatedBlockedPhrase);

        restBlockedPhraseMockMvc.perform(put("/api/blocked-phrases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPhraseDTO)))
            .andExpect(status().isOk());

        // Validate the BlockedPhrase in the database
        List<BlockedPhrase> blockedPhraseList = blockedPhraseRepository.findAll();
        assertThat(blockedPhraseList).hasSize(databaseSizeBeforeUpdate);
        BlockedPhrase testBlockedPhrase = blockedPhraseList.get(blockedPhraseList.size() - 1);
        assertThat(testBlockedPhrase.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingBlockedPhrase() throws Exception {
        int databaseSizeBeforeUpdate = blockedPhraseRepository.findAll().size();

        // Create the BlockedPhrase
        BlockedPhraseDTO blockedPhraseDTO = blockedPhraseMapper.toDto(blockedPhrase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockedPhraseMockMvc.perform(put("/api/blocked-phrases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPhraseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlockedPhrase in the database
        List<BlockedPhrase> blockedPhraseList = blockedPhraseRepository.findAll();
        assertThat(blockedPhraseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlockedPhrase() throws Exception {
        // Initialize the database
        blockedPhraseRepository.saveAndFlush(blockedPhrase);

        int databaseSizeBeforeDelete = blockedPhraseRepository.findAll().size();

        // Delete the blockedPhrase
        restBlockedPhraseMockMvc.perform(delete("/api/blocked-phrases/{id}", blockedPhrase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BlockedPhrase> blockedPhraseList = blockedPhraseRepository.findAll();
        assertThat(blockedPhraseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockedPhrase.class);
        BlockedPhrase blockedPhrase1 = new BlockedPhrase();
        blockedPhrase1.setId(1L);
        BlockedPhrase blockedPhrase2 = new BlockedPhrase();
        blockedPhrase2.setId(blockedPhrase1.getId());
        assertThat(blockedPhrase1).isEqualTo(blockedPhrase2);
        blockedPhrase2.setId(2L);
        assertThat(blockedPhrase1).isNotEqualTo(blockedPhrase2);
        blockedPhrase1.setId(null);
        assertThat(blockedPhrase1).isNotEqualTo(blockedPhrase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockedPhraseDTO.class);
        BlockedPhraseDTO blockedPhraseDTO1 = new BlockedPhraseDTO();
        blockedPhraseDTO1.setId(1L);
        BlockedPhraseDTO blockedPhraseDTO2 = new BlockedPhraseDTO();
        assertThat(blockedPhraseDTO1).isNotEqualTo(blockedPhraseDTO2);
        blockedPhraseDTO2.setId(blockedPhraseDTO1.getId());
        assertThat(blockedPhraseDTO1).isEqualTo(blockedPhraseDTO2);
        blockedPhraseDTO2.setId(2L);
        assertThat(blockedPhraseDTO1).isNotEqualTo(blockedPhraseDTO2);
        blockedPhraseDTO1.setId(null);
        assertThat(blockedPhraseDTO1).isNotEqualTo(blockedPhraseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(blockedPhraseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(blockedPhraseMapper.fromId(null)).isNull();
    }
}
