package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.SegmentFilterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.SegmentFilter}.
 */
public interface SegmentFilterService {
    /**
     * Save a segmentFilter.
     *
     * @param segmentFilterDTO the entity to save.
     * @return the persisted entity.
     */
    SegmentFilterDTO save(SegmentFilterDTO segmentFilterDTO);

    /**
     * Updates a segmentFilter.
     *
     * @param segmentFilterDTO the entity to update.
     * @return the persisted entity.
     */
    SegmentFilterDTO update(SegmentFilterDTO segmentFilterDTO);

    /**
     * Partially updates a segmentFilter.
     *
     * @param segmentFilterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SegmentFilterDTO> partialUpdate(SegmentFilterDTO segmentFilterDTO);

    /**
     * Get all the segmentFilters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SegmentFilterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" segmentFilter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SegmentFilterDTO> findOne(Long id);

    /**
     * Delete the "id" segmentFilter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
