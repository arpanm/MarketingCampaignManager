package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.FilterMetadata;
import com.marketing.campaign.domain.enumeration.FilterType;
import com.marketing.campaign.domain.enumeration.FilterUiType;
import com.marketing.campaign.repository.FilterMetadataRepository;
import com.marketing.campaign.service.dto.FilterMetadataDTO;
import com.marketing.campaign.service.mapper.FilterMetadataMapper;
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
 * Integration tests for the {@link FilterMetadataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FilterMetadataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final FilterType DEFAULT_FILTER_TYPE = FilterType.SingleSelect;
    private static final FilterType UPDATED_FILTER_TYPE = FilterType.MultiSelect;

    private static final FilterUiType DEFAULT_UI_TYPE = FilterUiType.Checkbox;
    private static final FilterUiType UPDATED_UI_TYPE = FilterUiType.RadioButton;

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

    private static final String ENTITY_API_URL = "/api/filter-metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FilterMetadataRepository filterMetadataRepository;

    @Autowired
    private FilterMetadataMapper filterMetadataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilterMetadataMockMvc;

    private FilterMetadata filterMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilterMetadata createEntity(EntityManager em) {
        FilterMetadata filterMetadata = new FilterMetadata()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .filterType(DEFAULT_FILTER_TYPE)
            .uiType(DEFAULT_UI_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return filterMetadata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilterMetadata createUpdatedEntity(EntityManager em) {
        FilterMetadata filterMetadata = new FilterMetadata()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .filterType(UPDATED_FILTER_TYPE)
            .uiType(UPDATED_UI_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return filterMetadata;
    }

    @BeforeEach
    public void initTest() {
        filterMetadata = createEntity(em);
    }

    @Test
    @Transactional
    void createFilterMetadata() throws Exception {
        int databaseSizeBeforeCreate = filterMetadataRepository.findAll().size();
        // Create the FilterMetadata
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);
        restFilterMetadataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeCreate + 1);
        FilterMetadata testFilterMetadata = filterMetadataList.get(filterMetadataList.size() - 1);
        assertThat(testFilterMetadata.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFilterMetadata.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testFilterMetadata.getFilterType()).isEqualTo(DEFAULT_FILTER_TYPE);
        assertThat(testFilterMetadata.getUiType()).isEqualTo(DEFAULT_UI_TYPE);
        assertThat(testFilterMetadata.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFilterMetadata.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFilterMetadata.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFilterMetadata.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFilterMetadata.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createFilterMetadataWithExistingId() throws Exception {
        // Create the FilterMetadata with an existing ID
        filterMetadata.setId(1L);
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);

        int databaseSizeBeforeCreate = filterMetadataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterMetadataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFilterMetadata() throws Exception {
        // Initialize the database
        filterMetadataRepository.saveAndFlush(filterMetadata);

        // Get all the filterMetadataList
        restFilterMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filterMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].filterType").value(hasItem(DEFAULT_FILTER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uiType").value(hasItem(DEFAULT_UI_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getFilterMetadata() throws Exception {
        // Initialize the database
        filterMetadataRepository.saveAndFlush(filterMetadata);

        // Get the filterMetadata
        restFilterMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, filterMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filterMetadata.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.filterType").value(DEFAULT_FILTER_TYPE.toString()))
            .andExpect(jsonPath("$.uiType").value(DEFAULT_UI_TYPE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFilterMetadata() throws Exception {
        // Get the filterMetadata
        restFilterMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFilterMetadata() throws Exception {
        // Initialize the database
        filterMetadataRepository.saveAndFlush(filterMetadata);

        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();

        // Update the filterMetadata
        FilterMetadata updatedFilterMetadata = filterMetadataRepository.findById(filterMetadata.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFilterMetadata are not directly saved in db
        em.detach(updatedFilterMetadata);
        updatedFilterMetadata
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .filterType(UPDATED_FILTER_TYPE)
            .uiType(UPDATED_UI_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(updatedFilterMetadata);

        restFilterMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filterMetadataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isOk());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
        FilterMetadata testFilterMetadata = filterMetadataList.get(filterMetadataList.size() - 1);
        assertThat(testFilterMetadata.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFilterMetadata.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testFilterMetadata.getFilterType()).isEqualTo(UPDATED_FILTER_TYPE);
        assertThat(testFilterMetadata.getUiType()).isEqualTo(UPDATED_UI_TYPE);
        assertThat(testFilterMetadata.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterMetadata.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterMetadata.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterMetadata.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFilterMetadata.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingFilterMetadata() throws Exception {
        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();
        filterMetadata.setId(count.incrementAndGet());

        // Create the FilterMetadata
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filterMetadataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFilterMetadata() throws Exception {
        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();
        filterMetadata.setId(count.incrementAndGet());

        // Create the FilterMetadata
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFilterMetadata() throws Exception {
        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();
        filterMetadata.setId(count.incrementAndGet());

        // Create the FilterMetadata
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterMetadataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFilterMetadataWithPatch() throws Exception {
        // Initialize the database
        filterMetadataRepository.saveAndFlush(filterMetadata);

        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();

        // Update the filterMetadata using partial update
        FilterMetadata partialUpdatedFilterMetadata = new FilterMetadata();
        partialUpdatedFilterMetadata.setId(filterMetadata.getId());

        partialUpdatedFilterMetadata
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .filterType(UPDATED_FILTER_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON);

        restFilterMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterMetadata.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilterMetadata))
            )
            .andExpect(status().isOk());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
        FilterMetadata testFilterMetadata = filterMetadataList.get(filterMetadataList.size() - 1);
        assertThat(testFilterMetadata.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFilterMetadata.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testFilterMetadata.getFilterType()).isEqualTo(UPDATED_FILTER_TYPE);
        assertThat(testFilterMetadata.getUiType()).isEqualTo(DEFAULT_UI_TYPE);
        assertThat(testFilterMetadata.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterMetadata.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFilterMetadata.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterMetadata.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFilterMetadata.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateFilterMetadataWithPatch() throws Exception {
        // Initialize the database
        filterMetadataRepository.saveAndFlush(filterMetadata);

        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();

        // Update the filterMetadata using partial update
        FilterMetadata partialUpdatedFilterMetadata = new FilterMetadata();
        partialUpdatedFilterMetadata.setId(filterMetadata.getId());

        partialUpdatedFilterMetadata
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .filterType(UPDATED_FILTER_TYPE)
            .uiType(UPDATED_UI_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restFilterMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterMetadata.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilterMetadata))
            )
            .andExpect(status().isOk());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
        FilterMetadata testFilterMetadata = filterMetadataList.get(filterMetadataList.size() - 1);
        assertThat(testFilterMetadata.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFilterMetadata.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testFilterMetadata.getFilterType()).isEqualTo(UPDATED_FILTER_TYPE);
        assertThat(testFilterMetadata.getUiType()).isEqualTo(UPDATED_UI_TYPE);
        assertThat(testFilterMetadata.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterMetadata.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterMetadata.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterMetadata.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFilterMetadata.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingFilterMetadata() throws Exception {
        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();
        filterMetadata.setId(count.incrementAndGet());

        // Create the FilterMetadata
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, filterMetadataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFilterMetadata() throws Exception {
        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();
        filterMetadata.setId(count.incrementAndGet());

        // Create the FilterMetadata
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFilterMetadata() throws Exception {
        int databaseSizeBeforeUpdate = filterMetadataRepository.findAll().size();
        filterMetadata.setId(count.incrementAndGet());

        // Create the FilterMetadata
        FilterMetadataDTO filterMetadataDTO = filterMetadataMapper.toDto(filterMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterMetadataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FilterMetadata in the database
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFilterMetadata() throws Exception {
        // Initialize the database
        filterMetadataRepository.saveAndFlush(filterMetadata);

        int databaseSizeBeforeDelete = filterMetadataRepository.findAll().size();

        // Delete the filterMetadata
        restFilterMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, filterMetadata.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FilterMetadata> filterMetadataList = filterMetadataRepository.findAll();
        assertThat(filterMetadataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
