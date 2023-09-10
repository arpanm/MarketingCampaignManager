package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.ApprovalStatus;
import com.marketing.campaign.domain.enumeration.StatusType;
import com.marketing.campaign.repository.ApprovalStatusRepository;
import com.marketing.campaign.service.dto.ApprovalStatusDTO;
import com.marketing.campaign.service.mapper.ApprovalStatusMapper;
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
 * Integration tests for the {@link ApprovalStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApprovalStatusResourceIT {

    private static final StatusType DEFAULT_STATUS_TYPE = StatusType.Draft;
    private static final StatusType UPDATED_STATUS_TYPE = StatusType.Pending;

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

    private static final String ENTITY_API_URL = "/api/approval-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApprovalStatusRepository approvalStatusRepository;

    @Autowired
    private ApprovalStatusMapper approvalStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApprovalStatusMockMvc;

    private ApprovalStatus approvalStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalStatus createEntity(EntityManager em) {
        ApprovalStatus approvalStatus = new ApprovalStatus()
            .statusType(DEFAULT_STATUS_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return approvalStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalStatus createUpdatedEntity(EntityManager em) {
        ApprovalStatus approvalStatus = new ApprovalStatus()
            .statusType(UPDATED_STATUS_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return approvalStatus;
    }

    @BeforeEach
    public void initTest() {
        approvalStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createApprovalStatus() throws Exception {
        int databaseSizeBeforeCreate = approvalStatusRepository.findAll().size();
        // Create the ApprovalStatus
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);
        restApprovalStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ApprovalStatus testApprovalStatus = approvalStatusList.get(approvalStatusList.size() - 1);
        assertThat(testApprovalStatus.getStatusType()).isEqualTo(DEFAULT_STATUS_TYPE);
        assertThat(testApprovalStatus.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testApprovalStatus.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApprovalStatus.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApprovalStatus.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testApprovalStatus.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createApprovalStatusWithExistingId() throws Exception {
        // Create the ApprovalStatus with an existing ID
        approvalStatus.setId(1L);
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);

        int databaseSizeBeforeCreate = approvalStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprovalStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApprovalStatuses() throws Exception {
        // Initialize the database
        approvalStatusRepository.saveAndFlush(approvalStatus);

        // Get all the approvalStatusList
        restApprovalStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvalStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusType").value(hasItem(DEFAULT_STATUS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getApprovalStatus() throws Exception {
        // Initialize the database
        approvalStatusRepository.saveAndFlush(approvalStatus);

        // Get the approvalStatus
        restApprovalStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, approvalStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approvalStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusType").value(DEFAULT_STATUS_TYPE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApprovalStatus() throws Exception {
        // Get the approvalStatus
        restApprovalStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApprovalStatus() throws Exception {
        // Initialize the database
        approvalStatusRepository.saveAndFlush(approvalStatus);

        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();

        // Update the approvalStatus
        ApprovalStatus updatedApprovalStatus = approvalStatusRepository.findById(approvalStatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApprovalStatus are not directly saved in db
        em.detach(updatedApprovalStatus);
        updatedApprovalStatus
            .statusType(UPDATED_STATUS_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(updatedApprovalStatus);

        restApprovalStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
        ApprovalStatus testApprovalStatus = approvalStatusList.get(approvalStatusList.size() - 1);
        assertThat(testApprovalStatus.getStatusType()).isEqualTo(UPDATED_STATUS_TYPE);
        assertThat(testApprovalStatus.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testApprovalStatus.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApprovalStatus.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApprovalStatus.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApprovalStatus.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingApprovalStatus() throws Exception {
        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();
        approvalStatus.setId(count.incrementAndGet());

        // Create the ApprovalStatus
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprovalStatus() throws Exception {
        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();
        approvalStatus.setId(count.incrementAndGet());

        // Create the ApprovalStatus
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprovalStatus() throws Exception {
        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();
        approvalStatus.setId(count.incrementAndGet());

        // Create the ApprovalStatus
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApprovalStatusWithPatch() throws Exception {
        // Initialize the database
        approvalStatusRepository.saveAndFlush(approvalStatus);

        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();

        // Update the approvalStatus using partial update
        ApprovalStatus partialUpdatedApprovalStatus = new ApprovalStatus();
        partialUpdatedApprovalStatus.setId(approvalStatus.getId());

        partialUpdatedApprovalStatus.statusType(UPDATED_STATUS_TYPE).createdBy(UPDATED_CREATED_BY);

        restApprovalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalStatus))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
        ApprovalStatus testApprovalStatus = approvalStatusList.get(approvalStatusList.size() - 1);
        assertThat(testApprovalStatus.getStatusType()).isEqualTo(UPDATED_STATUS_TYPE);
        assertThat(testApprovalStatus.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testApprovalStatus.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApprovalStatus.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApprovalStatus.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testApprovalStatus.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateApprovalStatusWithPatch() throws Exception {
        // Initialize the database
        approvalStatusRepository.saveAndFlush(approvalStatus);

        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();

        // Update the approvalStatus using partial update
        ApprovalStatus partialUpdatedApprovalStatus = new ApprovalStatus();
        partialUpdatedApprovalStatus.setId(approvalStatus.getId());

        partialUpdatedApprovalStatus
            .statusType(UPDATED_STATUS_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApprovalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalStatus))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
        ApprovalStatus testApprovalStatus = approvalStatusList.get(approvalStatusList.size() - 1);
        assertThat(testApprovalStatus.getStatusType()).isEqualTo(UPDATED_STATUS_TYPE);
        assertThat(testApprovalStatus.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testApprovalStatus.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApprovalStatus.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApprovalStatus.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApprovalStatus.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingApprovalStatus() throws Exception {
        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();
        approvalStatus.setId(count.incrementAndGet());

        // Create the ApprovalStatus
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvalStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprovalStatus() throws Exception {
        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();
        approvalStatus.setId(count.incrementAndGet());

        // Create the ApprovalStatus
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprovalStatus() throws Exception {
        int databaseSizeBeforeUpdate = approvalStatusRepository.findAll().size();
        approvalStatus.setId(count.incrementAndGet());

        // Create the ApprovalStatus
        ApprovalStatusDTO approvalStatusDTO = approvalStatusMapper.toDto(approvalStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalStatus in the database
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprovalStatus() throws Exception {
        // Initialize the database
        approvalStatusRepository.saveAndFlush(approvalStatus);

        int databaseSizeBeforeDelete = approvalStatusRepository.findAll().size();

        // Delete the approvalStatus
        restApprovalStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, approvalStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApprovalStatus> approvalStatusList = approvalStatusRepository.findAll();
        assertThat(approvalStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
