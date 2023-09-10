package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.ApprovalAuthority;
import com.marketing.campaign.domain.enumeration.VerticalType;
import com.marketing.campaign.repository.ApprovalAuthorityRepository;
import com.marketing.campaign.service.dto.ApprovalAuthorityDTO;
import com.marketing.campaign.service.mapper.ApprovalAuthorityMapper;
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
 * Integration tests for the {@link ApprovalAuthorityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApprovalAuthorityResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final VerticalType DEFAULT_VERTIAL = VerticalType.JMD;
    private static final VerticalType UPDATED_VERTIAL = VerticalType.PBG;

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

    private static final String ENTITY_API_URL = "/api/approval-authorities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApprovalAuthorityRepository approvalAuthorityRepository;

    @Autowired
    private ApprovalAuthorityMapper approvalAuthorityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApprovalAuthorityMockMvc;

    private ApprovalAuthority approvalAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalAuthority createEntity(EntityManager em) {
        ApprovalAuthority approvalAuthority = new ApprovalAuthority()
            .userId(DEFAULT_USER_ID)
            .vertial(DEFAULT_VERTIAL)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return approvalAuthority;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalAuthority createUpdatedEntity(EntityManager em) {
        ApprovalAuthority approvalAuthority = new ApprovalAuthority()
            .userId(UPDATED_USER_ID)
            .vertial(UPDATED_VERTIAL)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return approvalAuthority;
    }

    @BeforeEach
    public void initTest() {
        approvalAuthority = createEntity(em);
    }

    @Test
    @Transactional
    void createApprovalAuthority() throws Exception {
        int databaseSizeBeforeCreate = approvalAuthorityRepository.findAll().size();
        // Create the ApprovalAuthority
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);
        restApprovalAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        ApprovalAuthority testApprovalAuthority = approvalAuthorityList.get(approvalAuthorityList.size() - 1);
        assertThat(testApprovalAuthority.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testApprovalAuthority.getVertial()).isEqualTo(DEFAULT_VERTIAL);
        assertThat(testApprovalAuthority.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testApprovalAuthority.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApprovalAuthority.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testApprovalAuthority.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testApprovalAuthority.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createApprovalAuthorityWithExistingId() throws Exception {
        // Create the ApprovalAuthority with an existing ID
        approvalAuthority.setId(1L);
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);

        int databaseSizeBeforeCreate = approvalAuthorityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprovalAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApprovalAuthorities() throws Exception {
        // Initialize the database
        approvalAuthorityRepository.saveAndFlush(approvalAuthority);

        // Get all the approvalAuthorityList
        restApprovalAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvalAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].vertial").value(hasItem(DEFAULT_VERTIAL.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getApprovalAuthority() throws Exception {
        // Initialize the database
        approvalAuthorityRepository.saveAndFlush(approvalAuthority);

        // Get the approvalAuthority
        restApprovalAuthorityMockMvc
            .perform(get(ENTITY_API_URL_ID, approvalAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approvalAuthority.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.vertial").value(DEFAULT_VERTIAL.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApprovalAuthority() throws Exception {
        // Get the approvalAuthority
        restApprovalAuthorityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApprovalAuthority() throws Exception {
        // Initialize the database
        approvalAuthorityRepository.saveAndFlush(approvalAuthority);

        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();

        // Update the approvalAuthority
        ApprovalAuthority updatedApprovalAuthority = approvalAuthorityRepository.findById(approvalAuthority.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApprovalAuthority are not directly saved in db
        em.detach(updatedApprovalAuthority);
        updatedApprovalAuthority
            .userId(UPDATED_USER_ID)
            .vertial(UPDATED_VERTIAL)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(updatedApprovalAuthority);

        restApprovalAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalAuthorityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ApprovalAuthority testApprovalAuthority = approvalAuthorityList.get(approvalAuthorityList.size() - 1);
        assertThat(testApprovalAuthority.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testApprovalAuthority.getVertial()).isEqualTo(UPDATED_VERTIAL);
        assertThat(testApprovalAuthority.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testApprovalAuthority.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApprovalAuthority.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApprovalAuthority.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApprovalAuthority.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingApprovalAuthority() throws Exception {
        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();
        approvalAuthority.setId(count.incrementAndGet());

        // Create the ApprovalAuthority
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalAuthorityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprovalAuthority() throws Exception {
        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();
        approvalAuthority.setId(count.incrementAndGet());

        // Create the ApprovalAuthority
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprovalAuthority() throws Exception {
        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();
        approvalAuthority.setId(count.incrementAndGet());

        // Create the ApprovalAuthority
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApprovalAuthorityWithPatch() throws Exception {
        // Initialize the database
        approvalAuthorityRepository.saveAndFlush(approvalAuthority);

        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();

        // Update the approvalAuthority using partial update
        ApprovalAuthority partialUpdatedApprovalAuthority = new ApprovalAuthority();
        partialUpdatedApprovalAuthority.setId(approvalAuthority.getId());

        partialUpdatedApprovalAuthority
            .vertial(UPDATED_VERTIAL)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApprovalAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalAuthority.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalAuthority))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ApprovalAuthority testApprovalAuthority = approvalAuthorityList.get(approvalAuthorityList.size() - 1);
        assertThat(testApprovalAuthority.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testApprovalAuthority.getVertial()).isEqualTo(UPDATED_VERTIAL);
        assertThat(testApprovalAuthority.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testApprovalAuthority.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApprovalAuthority.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApprovalAuthority.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApprovalAuthority.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateApprovalAuthorityWithPatch() throws Exception {
        // Initialize the database
        approvalAuthorityRepository.saveAndFlush(approvalAuthority);

        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();

        // Update the approvalAuthority using partial update
        ApprovalAuthority partialUpdatedApprovalAuthority = new ApprovalAuthority();
        partialUpdatedApprovalAuthority.setId(approvalAuthority.getId());

        partialUpdatedApprovalAuthority
            .userId(UPDATED_USER_ID)
            .vertial(UPDATED_VERTIAL)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restApprovalAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalAuthority.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalAuthority))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ApprovalAuthority testApprovalAuthority = approvalAuthorityList.get(approvalAuthorityList.size() - 1);
        assertThat(testApprovalAuthority.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testApprovalAuthority.getVertial()).isEqualTo(UPDATED_VERTIAL);
        assertThat(testApprovalAuthority.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testApprovalAuthority.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApprovalAuthority.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testApprovalAuthority.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testApprovalAuthority.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingApprovalAuthority() throws Exception {
        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();
        approvalAuthority.setId(count.incrementAndGet());

        // Create the ApprovalAuthority
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvalAuthorityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprovalAuthority() throws Exception {
        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();
        approvalAuthority.setId(count.incrementAndGet());

        // Create the ApprovalAuthority
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprovalAuthority() throws Exception {
        int databaseSizeBeforeUpdate = approvalAuthorityRepository.findAll().size();
        approvalAuthority.setId(count.incrementAndGet());

        // Create the ApprovalAuthority
        ApprovalAuthorityDTO approvalAuthorityDTO = approvalAuthorityMapper.toDto(approvalAuthority);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalAuthorityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalAuthority in the database
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprovalAuthority() throws Exception {
        // Initialize the database
        approvalAuthorityRepository.saveAndFlush(approvalAuthority);

        int databaseSizeBeforeDelete = approvalAuthorityRepository.findAll().size();

        // Delete the approvalAuthority
        restApprovalAuthorityMockMvc
            .perform(delete(ENTITY_API_URL_ID, approvalAuthority.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApprovalAuthority> approvalAuthorityList = approvalAuthorityRepository.findAll();
        assertThat(approvalAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
