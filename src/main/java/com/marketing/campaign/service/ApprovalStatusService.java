package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.ApprovalStatusDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.ApprovalStatus}.
 */
public interface ApprovalStatusService {
    /**
     * Save a approvalStatus.
     *
     * @param approvalStatusDTO the entity to save.
     * @return the persisted entity.
     */
    ApprovalStatusDTO save(ApprovalStatusDTO approvalStatusDTO);

    /**
     * Updates a approvalStatus.
     *
     * @param approvalStatusDTO the entity to update.
     * @return the persisted entity.
     */
    ApprovalStatusDTO update(ApprovalStatusDTO approvalStatusDTO);

    /**
     * Partially updates a approvalStatus.
     *
     * @param approvalStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApprovalStatusDTO> partialUpdate(ApprovalStatusDTO approvalStatusDTO);

    /**
     * Get all the approvalStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApprovalStatusDTO> findAll(Pageable pageable);

    /**
     * Get all the ApprovalStatusDTO where Campaign is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ApprovalStatusDTO> findAllWhereCampaignIsNull();

    /**
     * Get the "id" approvalStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApprovalStatusDTO> findOne(Long id);

    /**
     * Delete the "id" approvalStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
