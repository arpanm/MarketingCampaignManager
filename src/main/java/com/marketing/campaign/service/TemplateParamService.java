package com.marketing.campaign.service;

import com.marketing.campaign.service.dto.TemplateParamDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.marketing.campaign.domain.TemplateParam}.
 */
public interface TemplateParamService {
    /**
     * Save a templateParam.
     *
     * @param templateParamDTO the entity to save.
     * @return the persisted entity.
     */
    TemplateParamDTO save(TemplateParamDTO templateParamDTO);

    /**
     * Updates a templateParam.
     *
     * @param templateParamDTO the entity to update.
     * @return the persisted entity.
     */
    TemplateParamDTO update(TemplateParamDTO templateParamDTO);

    /**
     * Partially updates a templateParam.
     *
     * @param templateParamDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TemplateParamDTO> partialUpdate(TemplateParamDTO templateParamDTO);

    /**
     * Get all the templateParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TemplateParamDTO> findAll(Pageable pageable);

    /**
     * Get the "id" templateParam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TemplateParamDTO> findOne(Long id);

    /**
     * Delete the "id" templateParam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
