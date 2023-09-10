package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.ApprovalAuthorityDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.ApprovalAuthority}.
 */
public interface ApprovalAuthorityService {
    /**
     * Save a approvalAuthority.
     *
     * @param approvalAuthorityDTO the entity to save.
     * @return the persisted entity.
     */
    ApprovalAuthorityDTO save(ApprovalAuthorityDTO approvalAuthorityDTO);

    /**
     * Updates a approvalAuthority.
     *
     * @param approvalAuthorityDTO the entity to update.
     * @return the persisted entity.
     */
    ApprovalAuthorityDTO update(ApprovalAuthorityDTO approvalAuthorityDTO);

    /**
     * Partially updates a approvalAuthority.
     *
     * @param approvalAuthorityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApprovalAuthorityDTO> partialUpdate(ApprovalAuthorityDTO approvalAuthorityDTO);

    /**
     * Get all the approvalAuthorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApprovalAuthorityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" approvalAuthority.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApprovalAuthorityDTO> findOne(Long id);

    /**
     * Delete the "id" approvalAuthority.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
