package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.FilterPossibleValueRepository;
import com.marketing.campaign.service.FilterPossibleValueService;
import com.marketing.campaign.service.dto.FilterPossibleValueDTO;
import com.marketing.campaign.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.marketing.campaign.domain.FilterPossibleValue}.
 */
@RestController
@RequestMapping("/api")
public class FilterPossibleValueResource {

    private final Logger log = LoggerFactory.getLogger(FilterPossibleValueResource.class);

    private static final String ENTITY_NAME = "filterPossibleValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilterPossibleValueService filterPossibleValueService;

    private final FilterPossibleValueRepository filterPossibleValueRepository;

    public FilterPossibleValueResource(
        FilterPossibleValueService filterPossibleValueService,
        FilterPossibleValueRepository filterPossibleValueRepository
    ) {
        this.filterPossibleValueService = filterPossibleValueService;
        this.filterPossibleValueRepository = filterPossibleValueRepository;
    }

    /**
     * {@code POST  /filter-possible-values} : Create a new filterPossibleValue.
     *
     * @param filterPossibleValueDTO the filterPossibleValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filterPossibleValueDTO, or with status {@code 400 (Bad Request)} if the filterPossibleValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/filter-possible-values")
    public ResponseEntity<FilterPossibleValueDTO> createFilterPossibleValue(@RequestBody FilterPossibleValueDTO filterPossibleValueDTO)
        throws URISyntaxException {
        log.debug("REST request to save FilterPossibleValue : {}", filterPossibleValueDTO);
        if (filterPossibleValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new filterPossibleValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilterPossibleValueDTO result = filterPossibleValueService.save(filterPossibleValueDTO);
        return ResponseEntity
            .created(new URI("/api/filter-possible-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /filter-possible-values/:id} : Updates an existing filterPossibleValue.
     *
     * @param id the id of the filterPossibleValueDTO to save.
     * @param filterPossibleValueDTO the filterPossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterPossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the filterPossibleValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filterPossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/filter-possible-values/{id}")
    public ResponseEntity<FilterPossibleValueDTO> updateFilterPossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FilterPossibleValueDTO filterPossibleValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FilterPossibleValue : {}, {}", id, filterPossibleValueDTO);
        if (filterPossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterPossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterPossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FilterPossibleValueDTO result = filterPossibleValueService.update(filterPossibleValueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filterPossibleValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /filter-possible-values/:id} : Partial updates given fields of an existing filterPossibleValue, field will ignore if it is null
     *
     * @param id the id of the filterPossibleValueDTO to save.
     * @param filterPossibleValueDTO the filterPossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterPossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the filterPossibleValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the filterPossibleValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the filterPossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/filter-possible-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FilterPossibleValueDTO> partialUpdateFilterPossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FilterPossibleValueDTO filterPossibleValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FilterPossibleValue partially : {}, {}", id, filterPossibleValueDTO);
        if (filterPossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterPossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterPossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FilterPossibleValueDTO> result = filterPossibleValueService.partialUpdate(filterPossibleValueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filterPossibleValueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /filter-possible-values} : get all the filterPossibleValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filterPossibleValues in body.
     */
    @GetMapping("/filter-possible-values")
    public ResponseEntity<List<FilterPossibleValueDTO>> getAllFilterPossibleValues(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FilterPossibleValues");
        Page<FilterPossibleValueDTO> page = filterPossibleValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /filter-possible-values/:id} : get the "id" filterPossibleValue.
     *
     * @param id the id of the filterPossibleValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filterPossibleValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/filter-possible-values/{id}")
    public ResponseEntity<FilterPossibleValueDTO> getFilterPossibleValue(@PathVariable Long id) {
        log.debug("REST request to get FilterPossibleValue : {}", id);
        Optional<FilterPossibleValueDTO> filterPossibleValueDTO = filterPossibleValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filterPossibleValueDTO);
    }

    /**
     * {@code DELETE  /filter-possible-values/:id} : delete the "id" filterPossibleValue.
     *
     * @param id the id of the filterPossibleValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/filter-possible-values/{id}")
    public ResponseEntity<Void> deleteFilterPossibleValue(@PathVariable Long id) {
        log.debug("REST request to delete FilterPossibleValue : {}", id);
        filterPossibleValueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
