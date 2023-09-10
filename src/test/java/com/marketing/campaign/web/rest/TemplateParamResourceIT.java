package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.TemplateParam;
import com.marketing.campaign.domain.enumeration.ParamType;
import com.marketing.campaign.repository.TemplateParamRepository;
import com.marketing.campaign.service.dto.TemplateParamDTO;
import com.marketing.campaign.service.mapper.TemplateParamMapper;
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
 * Integration tests for the {@link TemplateParamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TemplateParamResourceIT {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final ParamType DEFAULT_PARAM_TYPE = ParamType.Attribute;
    private static final ParamType UPDATED_PARAM_TYPE = ParamType.Coupon;

    private static final String DEFAULT_REPLACED_BY_ATRRIBUTE = "AAAAAAAAAA";
    private static final String UPDATED_REPLACED_BY_ATRRIBUTE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/template-params";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplateParamRepository templateParamRepository;

    @Autowired
    private TemplateParamMapper templateParamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateParamMockMvc;

    private TemplateParam templateParam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateParam createEntity(EntityManager em) {
        TemplateParam templateParam = new TemplateParam()
            .tag(DEFAULT_TAG)
            .paramType(DEFAULT_PARAM_TYPE)
            .replacedByAtrribute(DEFAULT_REPLACED_BY_ATRRIBUTE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return templateParam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateParam createUpdatedEntity(EntityManager em) {
        TemplateParam templateParam = new TemplateParam()
            .tag(UPDATED_TAG)
            .paramType(UPDATED_PARAM_TYPE)
            .replacedByAtrribute(UPDATED_REPLACED_BY_ATRRIBUTE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return templateParam;
    }

    @BeforeEach
    public void initTest() {
        templateParam = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplateParam() throws Exception {
        int databaseSizeBeforeCreate = templateParamRepository.findAll().size();
        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);
        restTemplateParamMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateParam testTemplateParam = templateParamList.get(templateParamList.size() - 1);
        assertThat(testTemplateParam.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testTemplateParam.getParamType()).isEqualTo(DEFAULT_PARAM_TYPE);
        assertThat(testTemplateParam.getReplacedByAtrribute()).isEqualTo(DEFAULT_REPLACED_BY_ATRRIBUTE);
        assertThat(testTemplateParam.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testTemplateParam.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTemplateParam.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTemplateParam.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTemplateParam.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createTemplateParamWithExistingId() throws Exception {
        // Create the TemplateParam with an existing ID
        templateParam.setId(1L);
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        int databaseSizeBeforeCreate = templateParamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateParamMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTemplateParams() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get all the templateParamList
        restTemplateParamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].paramType").value(hasItem(DEFAULT_PARAM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].replacedByAtrribute").value(hasItem(DEFAULT_REPLACED_BY_ATRRIBUTE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getTemplateParam() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        // Get the templateParam
        restTemplateParamMockMvc
            .perform(get(ENTITY_API_URL_ID, templateParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateParam.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.paramType").value(DEFAULT_PARAM_TYPE.toString()))
            .andExpect(jsonPath("$.replacedByAtrribute").value(DEFAULT_REPLACED_BY_ATRRIBUTE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTemplateParam() throws Exception {
        // Get the templateParam
        restTemplateParamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemplateParam() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();

        // Update the templateParam
        TemplateParam updatedTemplateParam = templateParamRepository.findById(templateParam.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTemplateParam are not directly saved in db
        em.detach(updatedTemplateParam);
        updatedTemplateParam
            .tag(UPDATED_TAG)
            .paramType(UPDATED_PARAM_TYPE)
            .replacedByAtrribute(UPDATED_REPLACED_BY_ATRRIBUTE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(updatedTemplateParam);

        restTemplateParamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateParamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isOk());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
        TemplateParam testTemplateParam = templateParamList.get(templateParamList.size() - 1);
        assertThat(testTemplateParam.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testTemplateParam.getParamType()).isEqualTo(UPDATED_PARAM_TYPE);
        assertThat(testTemplateParam.getReplacedByAtrribute()).isEqualTo(UPDATED_REPLACED_BY_ATRRIBUTE);
        assertThat(testTemplateParam.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testTemplateParam.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTemplateParam.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTemplateParam.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTemplateParam.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingTemplateParam() throws Exception {
        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();
        templateParam.setId(count.incrementAndGet());

        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateParamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateParamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplateParam() throws Exception {
        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();
        templateParam.setId(count.incrementAndGet());

        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateParamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplateParam() throws Exception {
        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();
        templateParam.setId(count.incrementAndGet());

        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateParamMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateParamWithPatch() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();

        // Update the templateParam using partial update
        TemplateParam partialUpdatedTemplateParam = new TemplateParam();
        partialUpdatedTemplateParam.setId(templateParam.getId());

        partialUpdatedTemplateParam
            .replacedByAtrribute(UPDATED_REPLACED_BY_ATRRIBUTE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restTemplateParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateParam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateParam))
            )
            .andExpect(status().isOk());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
        TemplateParam testTemplateParam = templateParamList.get(templateParamList.size() - 1);
        assertThat(testTemplateParam.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testTemplateParam.getParamType()).isEqualTo(DEFAULT_PARAM_TYPE);
        assertThat(testTemplateParam.getReplacedByAtrribute()).isEqualTo(UPDATED_REPLACED_BY_ATRRIBUTE);
        assertThat(testTemplateParam.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testTemplateParam.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTemplateParam.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTemplateParam.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTemplateParam.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateTemplateParamWithPatch() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();

        // Update the templateParam using partial update
        TemplateParam partialUpdatedTemplateParam = new TemplateParam();
        partialUpdatedTemplateParam.setId(templateParam.getId());

        partialUpdatedTemplateParam
            .tag(UPDATED_TAG)
            .paramType(UPDATED_PARAM_TYPE)
            .replacedByAtrribute(UPDATED_REPLACED_BY_ATRRIBUTE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restTemplateParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateParam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateParam))
            )
            .andExpect(status().isOk());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
        TemplateParam testTemplateParam = templateParamList.get(templateParamList.size() - 1);
        assertThat(testTemplateParam.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testTemplateParam.getParamType()).isEqualTo(UPDATED_PARAM_TYPE);
        assertThat(testTemplateParam.getReplacedByAtrribute()).isEqualTo(UPDATED_REPLACED_BY_ATRRIBUTE);
        assertThat(testTemplateParam.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testTemplateParam.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTemplateParam.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTemplateParam.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTemplateParam.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingTemplateParam() throws Exception {
        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();
        templateParam.setId(count.incrementAndGet());

        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateParamDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplateParam() throws Exception {
        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();
        templateParam.setId(count.incrementAndGet());

        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateParamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplateParam() throws Exception {
        int databaseSizeBeforeUpdate = templateParamRepository.findAll().size();
        templateParam.setId(count.incrementAndGet());

        // Create the TemplateParam
        TemplateParamDTO templateParamDTO = templateParamMapper.toDto(templateParam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateParamMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateParamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateParam in the database
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplateParam() throws Exception {
        // Initialize the database
        templateParamRepository.saveAndFlush(templateParam);

        int databaseSizeBeforeDelete = templateParamRepository.findAll().size();

        // Delete the templateParam
        restTemplateParamMockMvc
            .perform(delete(ENTITY_API_URL_ID, templateParam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateParam> templateParamList = templateParamRepository.findAll();
        assertThat(templateParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
