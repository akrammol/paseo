package com.kian.web.rest;

import com.kian.PaseoApp;
import com.kian.config.SecurityBeanOverrideConfiguration;
import com.kian.domain.BlockedPerson;
import com.kian.repository.BlockedPersonRepository;
import com.kian.service.BlockedPersonService;
import com.kian.service.dto.BlockedPersonDTO;
import com.kian.service.mapper.BlockedPersonMapper;
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
 * Integration tests for the {@Link BlockedPersonResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PaseoApp.class})
public class BlockedPersonResourceIT {

    @Autowired
    private BlockedPersonRepository blockedPersonRepository;

    @Autowired
    private BlockedPersonMapper blockedPersonMapper;

    @Autowired
    private BlockedPersonService blockedPersonService;

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

    private MockMvc restBlockedPersonMockMvc;

    private BlockedPerson blockedPerson;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlockedPersonResource blockedPersonResource = new BlockedPersonResource(blockedPersonService);
        this.restBlockedPersonMockMvc = MockMvcBuilders.standaloneSetup(blockedPersonResource)
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
    public static BlockedPerson createEntity(EntityManager em) {
        BlockedPerson blockedPerson = new BlockedPerson();
        return blockedPerson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlockedPerson createUpdatedEntity(EntityManager em) {
        BlockedPerson blockedPerson = new BlockedPerson();
        return blockedPerson;
    }

    @BeforeEach
    public void initTest() {
        blockedPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlockedPerson() throws Exception {
        int databaseSizeBeforeCreate = blockedPersonRepository.findAll().size();

        // Create the BlockedPerson
        BlockedPersonDTO blockedPersonDTO = blockedPersonMapper.toDto(blockedPerson);
        restBlockedPersonMockMvc.perform(post("/api/blocked-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the BlockedPerson in the database
        List<BlockedPerson> blockedPersonList = blockedPersonRepository.findAll();
        assertThat(blockedPersonList).hasSize(databaseSizeBeforeCreate + 1);
        BlockedPerson testBlockedPerson = blockedPersonList.get(blockedPersonList.size() - 1);
    }

    @Test
    @Transactional
    public void createBlockedPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blockedPersonRepository.findAll().size();

        // Create the BlockedPerson with an existing ID
        blockedPerson.setId(1L);
        BlockedPersonDTO blockedPersonDTO = blockedPersonMapper.toDto(blockedPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockedPersonMockMvc.perform(post("/api/blocked-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlockedPerson in the database
        List<BlockedPerson> blockedPersonList = blockedPersonRepository.findAll();
        assertThat(blockedPersonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBlockedPeople() throws Exception {
        // Initialize the database
        blockedPersonRepository.saveAndFlush(blockedPerson);

        // Get all the blockedPersonList
        restBlockedPersonMockMvc.perform(get("/api/blocked-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blockedPerson.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBlockedPerson() throws Exception {
        // Initialize the database
        blockedPersonRepository.saveAndFlush(blockedPerson);

        // Get the blockedPerson
        restBlockedPersonMockMvc.perform(get("/api/blocked-people/{id}", blockedPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blockedPerson.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBlockedPerson() throws Exception {
        // Get the blockedPerson
        restBlockedPersonMockMvc.perform(get("/api/blocked-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlockedPerson() throws Exception {
        // Initialize the database
        blockedPersonRepository.saveAndFlush(blockedPerson);

        int databaseSizeBeforeUpdate = blockedPersonRepository.findAll().size();

        // Update the blockedPerson
        BlockedPerson updatedBlockedPerson = blockedPersonRepository.findById(blockedPerson.getId()).get();
        // Disconnect from session so that the updates on updatedBlockedPerson are not directly saved in db
        em.detach(updatedBlockedPerson);
        BlockedPersonDTO blockedPersonDTO = blockedPersonMapper.toDto(updatedBlockedPerson);

        restBlockedPersonMockMvc.perform(put("/api/blocked-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPersonDTO)))
            .andExpect(status().isOk());

        // Validate the BlockedPerson in the database
        List<BlockedPerson> blockedPersonList = blockedPersonRepository.findAll();
        assertThat(blockedPersonList).hasSize(databaseSizeBeforeUpdate);
        BlockedPerson testBlockedPerson = blockedPersonList.get(blockedPersonList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBlockedPerson() throws Exception {
        int databaseSizeBeforeUpdate = blockedPersonRepository.findAll().size();

        // Create the BlockedPerson
        BlockedPersonDTO blockedPersonDTO = blockedPersonMapper.toDto(blockedPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockedPersonMockMvc.perform(put("/api/blocked-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blockedPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BlockedPerson in the database
        List<BlockedPerson> blockedPersonList = blockedPersonRepository.findAll();
        assertThat(blockedPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlockedPerson() throws Exception {
        // Initialize the database
        blockedPersonRepository.saveAndFlush(blockedPerson);

        int databaseSizeBeforeDelete = blockedPersonRepository.findAll().size();

        // Delete the blockedPerson
        restBlockedPersonMockMvc.perform(delete("/api/blocked-people/{id}", blockedPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BlockedPerson> blockedPersonList = blockedPersonRepository.findAll();
        assertThat(blockedPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockedPerson.class);
        BlockedPerson blockedPerson1 = new BlockedPerson();
        blockedPerson1.setId(1L);
        BlockedPerson blockedPerson2 = new BlockedPerson();
        blockedPerson2.setId(blockedPerson1.getId());
        assertThat(blockedPerson1).isEqualTo(blockedPerson2);
        blockedPerson2.setId(2L);
        assertThat(blockedPerson1).isNotEqualTo(blockedPerson2);
        blockedPerson1.setId(null);
        assertThat(blockedPerson1).isNotEqualTo(blockedPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockedPersonDTO.class);
        BlockedPersonDTO blockedPersonDTO1 = new BlockedPersonDTO();
        blockedPersonDTO1.setId(1L);
        BlockedPersonDTO blockedPersonDTO2 = new BlockedPersonDTO();
        assertThat(blockedPersonDTO1).isNotEqualTo(blockedPersonDTO2);
        blockedPersonDTO2.setId(blockedPersonDTO1.getId());
        assertThat(blockedPersonDTO1).isEqualTo(blockedPersonDTO2);
        blockedPersonDTO2.setId(2L);
        assertThat(blockedPersonDTO1).isNotEqualTo(blockedPersonDTO2);
        blockedPersonDTO1.setId(null);
        assertThat(blockedPersonDTO1).isNotEqualTo(blockedPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(blockedPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(blockedPersonMapper.fromId(null)).isNull();
    }
}
