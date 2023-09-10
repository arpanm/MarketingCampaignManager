package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.FilterMetadataDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.FilterMetadata}.
 */
public interface FilterMetadataService {
    /**
     * Save a filterMetadata.
     *
     * @param filterMetadataDTO the entity to save.
     * @return the persisted entity.
     */
    FilterMetadataDTO save(FilterMetadataDTO filterMetadataDTO);

    /**
     * Updates a filterMetadata.
     *
     * @param filterMetadataDTO the entity to update.
     * @return the persisted entity.
     */
    FilterMetadataDTO update(FilterMetadataDTO filterMetadataDTO);

    /**
     * Partially updates a filterMetadata.
     *
     * @param filterMetadataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FilterMetadataDTO> partialUpdate(FilterMetadataDTO filterMetadataDTO);

    /**
     * Get all the filterMetadata.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FilterMetadataDTO> findAll(Pageable pageable);

    /**
     * Get all the FilterMetadataDTO where SegmentFilter is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<FilterMetadataDTO> findAllWhereSegmentFilterIsNull();

    /**
     * Get the "id" filterMetadata.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FilterMetadataDTO> findOne(Long id);

    /**
     * Delete the "id" filterMetadata.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
