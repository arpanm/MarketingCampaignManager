package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.SegmentFilter;
import com.marketing.campaign.repository.SegmentFilterRepository;
import com.marketing.campaign.service.dto.SegmentFilterDTO;
import com.marketing.campaign.service.mapper.SegmentFilterMapper;
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
 * Integration tests for the {@link SegmentFilterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SegmentFilterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/segment-filters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SegmentFilterRepository segmentFilterRepository;

    @Autowired
    private SegmentFilterMapper segmentFilterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentFilterMockMvc;

    private SegmentFilter segmentFilter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentFilter createEntity(EntityManager em) {
        SegmentFilter segmentFilter = new SegmentFilter()
            .name(DEFAULT_NAME)
            .title(DEFAULT_TITLE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return segmentFilter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentFilter createUpdatedEntity(EntityManager em) {
        SegmentFilter segmentFilter = new SegmentFilter()
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return segmentFilter;
    }

    @BeforeEach
    public void initTest() {
        segmentFilter = createEntity(em);
    }

    @Test
    @Transactional
    void createSegmentFilter() throws Exception {
        int databaseSizeBeforeCreate = segmentFilterRepository.findAll().size();
        // Create the SegmentFilter
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);
        restSegmentFilterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeCreate + 1);
        SegmentFilter testSegmentFilter = segmentFilterList.get(segmentFilterList.size() - 1);
        assertThat(testSegmentFilter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSegmentFilter.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSegmentFilter.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSegmentFilter.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSegmentFilter.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSegmentFilter.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSegmentFilter.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSegmentFilterWithExistingId() throws Exception {
        // Create the SegmentFilter with an existing ID
        segmentFilter.setId(1L);
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);

        int databaseSizeBeforeCreate = segmentFilterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentFilterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSegmentFilters() throws Exception {
        // Initialize the database
        segmentFilterRepository.saveAndFlush(segmentFilter);

        // Get all the segmentFilterList
        restSegmentFilterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segmentFilter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getSegmentFilter() throws Exception {
        // Initialize the database
        segmentFilterRepository.saveAndFlush(segmentFilter);

        // Get the segmentFilter
        restSegmentFilterMockMvc
            .perform(get(ENTITY_API_URL_ID, segmentFilter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segmentFilter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSegmentFilter() throws Exception {
        // Get the segmentFilter
        restSegmentFilterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSegmentFilter() throws Exception {
        // Initialize the database
        segmentFilterRepository.saveAndFlush(segmentFilter);

        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();

        // Update the segmentFilter
        SegmentFilter updatedSegmentFilter = segmentFilterRepository.findById(segmentFilter.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSegmentFilter are not directly saved in db
        em.detach(updatedSegmentFilter);
        updatedSegmentFilter
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(updatedSegmentFilter);

        restSegmentFilterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentFilterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isOk());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
        SegmentFilter testSegmentFilter = segmentFilterList.get(segmentFilterList.size() - 1);
        assertThat(testSegmentFilter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSegmentFilter.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSegmentFilter.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSegmentFilter.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSegmentFilter.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSegmentFilter.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSegmentFilter.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSegmentFilter() throws Exception {
        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();
        segmentFilter.setId(count.incrementAndGet());

        // Create the SegmentFilter
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentFilterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentFilterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSegmentFilter() throws Exception {
        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();
        segmentFilter.setId(count.incrementAndGet());

        // Create the SegmentFilter
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentFilterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSegmentFilter() throws Exception {
        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();
        segmentFilter.setId(count.incrementAndGet());

        // Create the SegmentFilter
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentFilterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSegmentFilterWithPatch() throws Exception {
        // Initialize the database
        segmentFilterRepository.saveAndFlush(segmentFilter);

        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();

        // Update the segmentFilter using partial update
        SegmentFilter partialUpdatedSegmentFilter = new SegmentFilter();
        partialUpdatedSegmentFilter.setId(segmentFilter.getId());

        partialUpdatedSegmentFilter
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSegmentFilterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentFilter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmentFilter))
            )
            .andExpect(status().isOk());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
        SegmentFilter testSegmentFilter = segmentFilterList.get(segmentFilterList.size() - 1);
        assertThat(testSegmentFilter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSegmentFilter.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSegmentFilter.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSegmentFilter.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSegmentFilter.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSegmentFilter.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSegmentFilter.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSegmentFilterWithPatch() throws Exception {
        // Initialize the database
        segmentFilterRepository.saveAndFlush(segmentFilter);

        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();

        // Update the segmentFilter using partial update
        SegmentFilter partialUpdatedSegmentFilter = new SegmentFilter();
        partialUpdatedSegmentFilter.setId(segmentFilter.getId());

        partialUpdatedSegmentFilter
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSegmentFilterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentFilter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegmentFilter))
            )
            .andExpect(status().isOk());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
        SegmentFilter testSegmentFilter = segmentFilterList.get(segmentFilterList.size() - 1);
        assertThat(testSegmentFilter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSegmentFilter.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSegmentFilter.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSegmentFilter.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSegmentFilter.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSegmentFilter.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSegmentFilter.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSegmentFilter() throws Exception {
        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();
        segmentFilter.setId(count.incrementAndGet());

        // Create the SegmentFilter
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentFilterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, segmentFilterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSegmentFilter() throws Exception {
        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();
        segmentFilter.setId(count.incrementAndGet());

        // Create the SegmentFilter
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentFilterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSegmentFilter() throws Exception {
        int databaseSizeBeforeUpdate = segmentFilterRepository.findAll().size();
        segmentFilter.setId(count.incrementAndGet());

        // Create the SegmentFilter
        SegmentFilterDTO segmentFilterDTO = segmentFilterMapper.toDto(segmentFilter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentFilterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentFilterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentFilter in the database
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSegmentFilter() throws Exception {
        // Initialize the database
        segmentFilterRepository.saveAndFlush(segmentFilter);

        int databaseSizeBeforeDelete = segmentFilterRepository.findAll().size();

        // Delete the segmentFilter
        restSegmentFilterMockMvc
            .perform(delete(ENTITY_API_URL_ID, segmentFilter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SegmentFilter> segmentFilterList = segmentFilterRepository.findAll();
        assertThat(segmentFilterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
