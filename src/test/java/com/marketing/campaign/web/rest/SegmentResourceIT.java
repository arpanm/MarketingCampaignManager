package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.Segment;
import com.marketing.campaign.repository.SegmentRepository;
import com.marketing.campaign.service.dto.SegmentDTO;
import com.marketing.campaign.service.mapper.SegmentMapper;
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
 * Integration tests for the {@link SegmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SegmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_TABLE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/segments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private SegmentMapper segmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentMockMvc;

    private Segment segment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segment createEntity(EntityManager em) {
        Segment segment = new Segment()
            .name(DEFAULT_NAME)
            .sourceTable(DEFAULT_SOURCE_TABLE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return segment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segment createUpdatedEntity(EntityManager em) {
        Segment segment = new Segment()
            .name(UPDATED_NAME)
            .sourceTable(UPDATED_SOURCE_TABLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return segment;
    }

    @BeforeEach
    public void initTest() {
        segment = createEntity(em);
    }

    @Test
    @Transactional
    void createSegment() throws Exception {
        int databaseSizeBeforeCreate = segmentRepository.findAll().size();
        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);
        restSegmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate + 1);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSegment.getSourceTable()).isEqualTo(DEFAULT_SOURCE_TABLE);
        assertThat(testSegment.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSegment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSegment.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSegment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSegment.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createSegmentWithExistingId() throws Exception {
        // Create the Segment with an existing ID
        segment.setId(1L);
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        int databaseSizeBeforeCreate = segmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSegments() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get all the segmentList
        restSegmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sourceTable").value(hasItem(DEFAULT_SOURCE_TABLE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get the segment
        restSegmentMockMvc
            .perform(get(ENTITY_API_URL_ID, segment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sourceTable").value(DEFAULT_SOURCE_TABLE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSegment() throws Exception {
        // Get the segment
        restSegmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // Update the segment
        Segment updatedSegment = segmentRepository.findById(segment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSegment are not directly saved in db
        em.detach(updatedSegment);
        updatedSegment
            .name(UPDATED_NAME)
            .sourceTable(UPDATED_SOURCE_TABLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SegmentDTO segmentDTO = segmentMapper.toDto(updatedSegment);

        restSegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSegment.getSourceTable()).isEqualTo(UPDATED_SOURCE_TABLE);
        assertThat(testSegment.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSegment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSegment.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSegment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSegment.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();
        segment.setId(count.incrementAndGet());

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();
        segment.setId(count.incrementAndGet());

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(segmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();
        segment.setId(count.incrementAndGet());

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(segmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSegmentWithPatch() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // Update the segment using partial update
        Segment partialUpdatedSegment = new Segment();
        partialUpdatedSegment.setId(segment.getId());

        partialUpdatedSegment.sourceTable(UPDATED_SOURCE_TABLE).isActive(UPDATED_IS_ACTIVE);

        restSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegment))
            )
            .andExpect(status().isOk());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSegment.getSourceTable()).isEqualTo(UPDATED_SOURCE_TABLE);
        assertThat(testSegment.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSegment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSegment.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSegment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSegment.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateSegmentWithPatch() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // Update the segment using partial update
        Segment partialUpdatedSegment = new Segment();
        partialUpdatedSegment.setId(segment.getId());

        partialUpdatedSegment
            .name(UPDATED_NAME)
            .sourceTable(UPDATED_SOURCE_TABLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSegment))
            )
            .andExpect(status().isOk());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSegment.getSourceTable()).isEqualTo(UPDATED_SOURCE_TABLE);
        assertThat(testSegment.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSegment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSegment.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSegment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSegment.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();
        segment.setId(count.incrementAndGet());

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, segmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();
        segment.setId(count.incrementAndGet());

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(segmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();
        segment.setId(count.incrementAndGet());

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(segmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeDelete = segmentRepository.findAll().size();

        // Delete the segment
        restSegmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, segment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
