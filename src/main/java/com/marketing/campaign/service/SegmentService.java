package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.SegmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.Segment}.
 */
public interface SegmentService {
    /**
     * Save a segment.
     *
     * @param segmentDTO the entity to save.
     * @return the persisted entity.
     */
    SegmentDTO save(SegmentDTO segmentDTO);

    /**
     * Updates a segment.
     *
     * @param segmentDTO the entity to update.
     * @return the persisted entity.
     */
    SegmentDTO update(SegmentDTO segmentDTO);

    /**
     * Partially updates a segment.
     *
     * @param segmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SegmentDTO> partialUpdate(SegmentDTO segmentDTO);

    /**
     * Get all the segments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SegmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" segment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SegmentDTO> findOne(Long id);

    /**
     * Delete the "id" segment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
