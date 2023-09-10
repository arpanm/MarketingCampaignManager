package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.FilterSourceMapping;
import com.marketing.campaign.repository.FilterSourceMappingRepository;
import com.marketing.campaign.service.dto.FilterSourceMappingDTO;
import com.marketing.campaign.service.mapper.FilterSourceMappingMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FilterSourceMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FilterSourceMappingResourceIT {

    private static final String DEFAULT_SOURCE_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_ATRRIBUTE_MAPPING = "AAAAAAAAAA";
    private static final String UPDATED_ATRRIBUTE_MAPPING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/filter-source-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FilterSourceMappingRepository filterSourceMappingRepository;

    @Autowired
    private FilterSourceMappingMapper filterSourceMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilterSourceMappingMockMvc;

    private FilterSourceMapping filterSourceMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilterSourceMapping createEntity(EntityManager em) {
        FilterSourceMapping filterSourceMapping = new FilterSourceMapping()
            .sourceTable(DEFAULT_SOURCE_TABLE)
            .atrributeMapping(DEFAULT_ATRRIBUTE_MAPPING)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return filterSourceMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilterSourceMapping createUpdatedEntity(EntityManager em) {
        FilterSourceMapping filterSourceMapping = new FilterSourceMapping()
            .sourceTable(UPDATED_SOURCE_TABLE)
            .atrributeMapping(UPDATED_ATRRIBUTE_MAPPING)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return filterSourceMapping;
    }

    @BeforeEach
    public void initTest() {
        filterSourceMapping = createEntity(em);
    }

    @Test
    @Transactional
    void createFilterSourceMapping() throws Exception {
        int databaseSizeBeforeCreate = filterSourceMappingRepository.findAll().size();
        // Create the FilterSourceMapping
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);
        restFilterSourceMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeCreate + 1);
        FilterSourceMapping testFilterSourceMapping = filterSourceMappingList.get(filterSourceMappingList.size() - 1);
        assertThat(testFilterSourceMapping.getSourceTable()).isEqualTo(DEFAULT_SOURCE_TABLE);
        assertThat(testFilterSourceMapping.getAtrributeMapping()).isEqualTo(DEFAULT_ATRRIBUTE_MAPPING);
        assertThat(testFilterSourceMapping.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFilterSourceMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFilterSourceMapping.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFilterSourceMapping.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFilterSourceMapping.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createFilterSourceMappingWithExistingId() throws Exception {
        // Create the FilterSourceMapping with an existing ID
        filterSourceMapping.setId(1L);
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);

        int databaseSizeBeforeCreate = filterSourceMappingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterSourceMappingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFilterSourceMappings() throws Exception {
        // Initialize the database
        filterSourceMappingRepository.saveAndFlush(filterSourceMapping);

        // Get all the filterSourceMappingList
        restFilterSourceMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filterSourceMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceTable").value(hasItem(DEFAULT_SOURCE_TABLE)))
            .andExpect(jsonPath("$.[*].atrributeMapping").value(hasItem(DEFAULT_ATRRIBUTE_MAPPING)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getFilterSourceMapping() throws Exception {
        // Initialize the database
        filterSourceMappingRepository.saveAndFlush(filterSourceMapping);

        // Get the filterSourceMapping
        restFilterSourceMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, filterSourceMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filterSourceMapping.getId().intValue()))
            .andExpect(jsonPath("$.sourceTable").value(DEFAULT_SOURCE_TABLE))
            .andExpect(jsonPath("$.atrributeMapping").value(DEFAULT_ATRRIBUTE_MAPPING))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFilterSourceMapping() throws Exception {
        // Get the filterSourceMapping
        restFilterSourceMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFilterSourceMapping() throws Exception {
        // Initialize the database
        filterSourceMappingRepository.saveAndFlush(filterSourceMapping);

        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();

        // Update the filterSourceMapping
        FilterSourceMapping updatedFilterSourceMapping = filterSourceMappingRepository.findById(filterSourceMapping.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFilterSourceMapping are not directly saved in db
        em.detach(updatedFilterSourceMapping);
        updatedFilterSourceMapping
            .sourceTable(UPDATED_SOURCE_TABLE)
            .atrributeMapping(UPDATED_ATRRIBUTE_MAPPING)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(updatedFilterSourceMapping);

        restFilterSourceMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filterSourceMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
        FilterSourceMapping testFilterSourceMapping = filterSourceMappingList.get(filterSourceMappingList.size() - 1);
        assertThat(testFilterSourceMapping.getSourceTable()).isEqualTo(UPDATED_SOURCE_TABLE);
        assertThat(testFilterSourceMapping.getAtrributeMapping()).isEqualTo(UPDATED_ATRRIBUTE_MAPPING);
        assertThat(testFilterSourceMapping.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterSourceMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterSourceMapping.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterSourceMapping.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFilterSourceMapping.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingFilterSourceMapping() throws Exception {
        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();
        filterSourceMapping.setId(count.incrementAndGet());

        // Create the FilterSourceMapping
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterSourceMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filterSourceMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFilterSourceMapping() throws Exception {
        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();
        filterSourceMapping.setId(count.incrementAndGet());

        // Create the FilterSourceMapping
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterSourceMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFilterSourceMapping() throws Exception {
        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();
        filterSourceMapping.setId(count.incrementAndGet());

        // Create the FilterSourceMapping
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterSourceMappingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFilterSourceMappingWithPatch() throws Exception {
        // Initialize the database
        filterSourceMappingRepository.saveAndFlush(filterSourceMapping);

        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();

        // Update the filterSourceMapping using partial update
        FilterSourceMapping partialUpdatedFilterSourceMapping = new FilterSourceMapping();
        partialUpdatedFilterSourceMapping.setId(filterSourceMapping.getId());

        partialUpdatedFilterSourceMapping
            .atrributeMapping(UPDATED_ATRRIBUTE_MAPPING)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restFilterSourceMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterSourceMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilterSourceMapping))
            )
            .andExpect(status().isOk());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
        FilterSourceMapping testFilterSourceMapping = filterSourceMappingList.get(filterSourceMappingList.size() - 1);
        assertThat(testFilterSourceMapping.getSourceTable()).isEqualTo(DEFAULT_SOURCE_TABLE);
        assertThat(testFilterSourceMapping.getAtrributeMapping()).isEqualTo(UPDATED_ATRRIBUTE_MAPPING);
        assertThat(testFilterSourceMapping.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFilterSourceMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterSourceMapping.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterSourceMapping.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFilterSourceMapping.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateFilterSourceMappingWithPatch() throws Exception {
        // Initialize the database
        filterSourceMappingRepository.saveAndFlush(filterSourceMapping);

        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();

        // Update the filterSourceMapping using partial update
        FilterSourceMapping partialUpdatedFilterSourceMapping = new FilterSourceMapping();
        partialUpdatedFilterSourceMapping.setId(filterSourceMapping.getId());

        partialUpdatedFilterSourceMapping
            .sourceTable(UPDATED_SOURCE_TABLE)
            .atrributeMapping(UPDATED_ATRRIBUTE_MAPPING)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restFilterSourceMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterSourceMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilterSourceMapping))
            )
            .andExpect(status().isOk());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
        FilterSourceMapping testFilterSourceMapping = filterSourceMappingList.get(filterSourceMappingList.size() - 1);
        assertThat(testFilterSourceMapping.getSourceTable()).isEqualTo(UPDATED_SOURCE_TABLE);
        assertThat(testFilterSourceMapping.getAtrributeMapping()).isEqualTo(UPDATED_ATRRIBUTE_MAPPING);
        assertThat(testFilterSourceMapping.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterSourceMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterSourceMapping.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterSourceMapping.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFilterSourceMapping.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingFilterSourceMapping() throws Exception {
        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();
        filterSourceMapping.setId(count.incrementAndGet());

        // Create the FilterSourceMapping
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterSourceMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, filterSourceMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFilterSourceMapping() throws Exception {
        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();
        filterSourceMapping.setId(count.incrementAndGet());

        // Create the FilterSourceMapping
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterSourceMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFilterSourceMapping() throws Exception {
        int databaseSizeBeforeUpdate = filterSourceMappingRepository.findAll().size();
        filterSourceMapping.setId(count.incrementAndGet());

        // Create the FilterSourceMapping
        FilterSourceMappingDTO filterSourceMappingDTO = filterSourceMappingMapper.toDto(filterSourceMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterSourceMappingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterSourceMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FilterSourceMapping in the database
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFilterSourceMapping() throws Exception {
        // Initialize the database
        filterSourceMappingRepository.saveAndFlush(filterSourceMapping);

        int databaseSizeBeforeDelete = filterSourceMappingRepository.findAll().size();

        // Delete the filterSourceMapping
        restFilterSourceMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, filterSourceMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FilterSourceMapping> filterSourceMappingList = filterSourceMappingRepository.findAll();
        assertThat(filterSourceMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
