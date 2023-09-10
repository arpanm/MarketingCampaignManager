package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.FilterPossibleValueDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.FilterPossibleValue}.
 */
public interface FilterPossibleValueService {
    /**
     * Save a filterPossibleValue.
     *
     * @param filterPossibleValueDTO the entity to save.
     * @return the persisted entity.
     */
    FilterPossibleValueDTO save(FilterPossibleValueDTO filterPossibleValueDTO);

    /**
     * Updates a filterPossibleValue.
     *
     * @param filterPossibleValueDTO the entity to update.
     * @return the persisted entity.
     */
    FilterPossibleValueDTO update(FilterPossibleValueDTO filterPossibleValueDTO);

    /**
     * Partially updates a filterPossibleValue.
     *
     * @param filterPossibleValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FilterPossibleValueDTO> partialUpdate(FilterPossibleValueDTO filterPossibleValueDTO);

    /**
     * Get all the filterPossibleValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FilterPossibleValueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" filterPossibleValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FilterPossibleValueDTO> findOne(Long id);

    /**
     * Delete the "id" filterPossibleValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
