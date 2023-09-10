package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.FilterSourceMappingDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.FilterSourceMapping}.
 */
public interface FilterSourceMappingService {
    /**
     * Save a filterSourceMapping.
     *
     * @param filterSourceMappingDTO the entity to save.
     * @return the persisted entity.
     */
    FilterSourceMappingDTO save(FilterSourceMappingDTO filterSourceMappingDTO);

    /**
     * Updates a filterSourceMapping.
     *
     * @param filterSourceMappingDTO the entity to update.
     * @return the persisted entity.
     */
    FilterSourceMappingDTO update(FilterSourceMappingDTO filterSourceMappingDTO);

    /**
     * Partially updates a filterSourceMapping.
     *
     * @param filterSourceMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FilterSourceMappingDTO> partialUpdate(FilterSourceMappingDTO filterSourceMappingDTO);

    /**
     * Get all the filterSourceMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FilterSourceMappingDTO> findAll(Pageable pageable);

    /**
     * Get all the FilterSourceMappingDTO where FilterMetadata is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<FilterSourceMappingDTO> findAllWhereFilterMetadataIsNull();

    /**
     * Get the "id" filterSourceMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FilterSourceMappingDTO> findOne(Long id);

    /**
     * Delete the "id" filterSourceMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
