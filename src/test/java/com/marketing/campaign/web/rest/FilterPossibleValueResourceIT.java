package com.marketing.campaign.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marketing.campaign.IntegrationTest;
import com.marketing.campaign.domain.FilterPossibleValue;
import com.marketing.campaign.repository.FilterPossibleValueRepository;
import com.marketing.campaign.service.dto.FilterPossibleValueDTO;
import com.marketing.campaign.service.mapper.FilterPossibleValueMapper;
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
 * Integration tests for the {@link FilterPossibleValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FilterPossibleValueResourceIT {

    private static final String DEFAULT_UI_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UI_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/filter-possible-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FilterPossibleValueRepository filterPossibleValueRepository;

    @Autowired
    private FilterPossibleValueMapper filterPossibleValueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilterPossibleValueMockMvc;

    private FilterPossibleValue filterPossibleValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilterPossibleValue createEntity(EntityManager em) {
        FilterPossibleValue filterPossibleValue = new FilterPossibleValue()
            .uiName(DEFAULT_UI_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return filterPossibleValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilterPossibleValue createUpdatedEntity(EntityManager em) {
        FilterPossibleValue filterPossibleValue = new FilterPossibleValue()
            .uiName(UPDATED_UI_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return filterPossibleValue;
    }

    @BeforeEach
    public void initTest() {
        filterPossibleValue = createEntity(em);
    }

    @Test
    @Transactional
    void createFilterPossibleValue() throws Exception {
        int databaseSizeBeforeCreate = filterPossibleValueRepository.findAll().size();
        // Create the FilterPossibleValue
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);
        restFilterPossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeCreate + 1);
        FilterPossibleValue testFilterPossibleValue = filterPossibleValueList.get(filterPossibleValueList.size() - 1);
        assertThat(testFilterPossibleValue.getUiName()).isEqualTo(DEFAULT_UI_NAME);
        assertThat(testFilterPossibleValue.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
        assertThat(testFilterPossibleValue.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFilterPossibleValue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFilterPossibleValue.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFilterPossibleValue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFilterPossibleValue.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createFilterPossibleValueWithExistingId() throws Exception {
        // Create the FilterPossibleValue with an existing ID
        filterPossibleValue.setId(1L);
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);

        int databaseSizeBeforeCreate = filterPossibleValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterPossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFilterPossibleValues() throws Exception {
        // Initialize the database
        filterPossibleValueRepository.saveAndFlush(filterPossibleValue);

        // Get all the filterPossibleValueList
        restFilterPossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filterPossibleValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].uiName").value(hasItem(DEFAULT_UI_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getFilterPossibleValue() throws Exception {
        // Initialize the database
        filterPossibleValueRepository.saveAndFlush(filterPossibleValue);

        // Get the filterPossibleValue
        restFilterPossibleValueMockMvc
            .perform(get(ENTITY_API_URL_ID, filterPossibleValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filterPossibleValue.getId().intValue()))
            .andExpect(jsonPath("$.uiName").value(DEFAULT_UI_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFilterPossibleValue() throws Exception {
        // Get the filterPossibleValue
        restFilterPossibleValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFilterPossibleValue() throws Exception {
        // Initialize the database
        filterPossibleValueRepository.saveAndFlush(filterPossibleValue);

        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();

        // Update the filterPossibleValue
        FilterPossibleValue updatedFilterPossibleValue = filterPossibleValueRepository.findById(filterPossibleValue.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFilterPossibleValue are not directly saved in db
        em.detach(updatedFilterPossibleValue);
        updatedFilterPossibleValue
            .uiName(UPDATED_UI_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(updatedFilterPossibleValue);

        restFilterPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filterPossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isOk());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
        FilterPossibleValue testFilterPossibleValue = filterPossibleValueList.get(filterPossibleValueList.size() - 1);
        assertThat(testFilterPossibleValue.getUiName()).isEqualTo(UPDATED_UI_NAME);
        assertThat(testFilterPossibleValue.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testFilterPossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterPossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterPossibleValue.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterPossibleValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFilterPossibleValue.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingFilterPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();
        filterPossibleValue.setId(count.incrementAndGet());

        // Create the FilterPossibleValue
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filterPossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFilterPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();
        filterPossibleValue.setId(count.incrementAndGet());

        // Create the FilterPossibleValue
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFilterPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();
        filterPossibleValue.setId(count.incrementAndGet());

        // Create the FilterPossibleValue
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFilterPossibleValueWithPatch() throws Exception {
        // Initialize the database
        filterPossibleValueRepository.saveAndFlush(filterPossibleValue);

        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();

        // Update the filterPossibleValue using partial update
        FilterPossibleValue partialUpdatedFilterPossibleValue = new FilterPossibleValue();
        partialUpdatedFilterPossibleValue.setId(filterPossibleValue.getId());

        partialUpdatedFilterPossibleValue.isActive(UPDATED_IS_ACTIVE).createdBy(UPDATED_CREATED_BY).updatedOn(UPDATED_UPDATED_ON);

        restFilterPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterPossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilterPossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
        FilterPossibleValue testFilterPossibleValue = filterPossibleValueList.get(filterPossibleValueList.size() - 1);
        assertThat(testFilterPossibleValue.getUiName()).isEqualTo(DEFAULT_UI_NAME);
        assertThat(testFilterPossibleValue.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
        assertThat(testFilterPossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterPossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterPossibleValue.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFilterPossibleValue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFilterPossibleValue.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateFilterPossibleValueWithPatch() throws Exception {
        // Initialize the database
        filterPossibleValueRepository.saveAndFlush(filterPossibleValue);

        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();

        // Update the filterPossibleValue using partial update
        FilterPossibleValue partialUpdatedFilterPossibleValue = new FilterPossibleValue();
        partialUpdatedFilterPossibleValue.setId(filterPossibleValue.getId());

        partialUpdatedFilterPossibleValue
            .uiName(UPDATED_UI_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restFilterPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilterPossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilterPossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
        FilterPossibleValue testFilterPossibleValue = filterPossibleValueList.get(filterPossibleValueList.size() - 1);
        assertThat(testFilterPossibleValue.getUiName()).isEqualTo(UPDATED_UI_NAME);
        assertThat(testFilterPossibleValue.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
        assertThat(testFilterPossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFilterPossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilterPossibleValue.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFilterPossibleValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFilterPossibleValue.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingFilterPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();
        filterPossibleValue.setId(count.incrementAndGet());

        // Create the FilterPossibleValue
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilterPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, filterPossibleValueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFilterPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();
        filterPossibleValue.setId(count.incrementAndGet());

        // Create the FilterPossibleValue
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFilterPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = filterPossibleValueRepository.findAll().size();
        filterPossibleValue.setId(count.incrementAndGet());

        // Create the FilterPossibleValue
        FilterPossibleValueDTO filterPossibleValueDTO = filterPossibleValueMapper.toDto(filterPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilterPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filterPossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FilterPossibleValue in the database
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFilterPossibleValue() throws Exception {
        // Initialize the database
        filterPossibleValueRepository.saveAndFlush(filterPossibleValue);

        int databaseSizeBeforeDelete = filterPossibleValueRepository.findAll().size();

        // Delete the filterPossibleValue
        restFilterPossibleValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, filterPossibleValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FilterPossibleValue> filterPossibleValueList = filterPossibleValueRepository.findAll();
        assertThat(filterPossibleValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
