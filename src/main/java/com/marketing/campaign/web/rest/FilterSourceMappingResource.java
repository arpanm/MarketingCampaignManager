package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.FilterSourceMappingRepository;
import com.marketing.campaign.service.FilterSourceMappingService;
import com.marketing.campaign.service.dto.FilterSourceMappingDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.marketing.campaign.domain.FilterSourceMapping}.
 */
@RestController
@RequestMapping("/api")
public class FilterSourceMappingResource {

    private final Logger log = LoggerFactory.getLogger(FilterSourceMappingResource.class);

    private static final String ENTITY_NAME = "filterSourceMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilterSourceMappingService filterSourceMappingService;

    private final FilterSourceMappingRepository filterSourceMappingRepository;

    public FilterSourceMappingResource(
        FilterSourceMappingService filterSourceMappingService,
        FilterSourceMappingRepository filterSourceMappingRepository
    ) {
        this.filterSourceMappingService = filterSourceMappingService;
        this.filterSourceMappingRepository = filterSourceMappingRepository;
    }

    /**
     * {@code POST  /filter-source-mappings} : Create a new filterSourceMapping.
     *
     * @param filterSourceMappingDTO the filterSourceMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filterSourceMappingDTO, or with status {@code 400 (Bad Request)} if the filterSourceMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/filter-source-mappings")
    public ResponseEntity<FilterSourceMappingDTO> createFilterSourceMapping(@RequestBody FilterSourceMappingDTO filterSourceMappingDTO)
        throws URISyntaxException {
        log.debug("REST request to save FilterSourceMapping : {}", filterSourceMappingDTO);
        if (filterSourceMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new filterSourceMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilterSourceMappingDTO result = filterSourceMappingService.save(filterSourceMappingDTO);
        return ResponseEntity
            .created(new URI("/api/filter-source-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /filter-source-mappings/:id} : Updates an existing filterSourceMapping.
     *
     * @param id the id of the filterSourceMappingDTO to save.
     * @param filterSourceMappingDTO the filterSourceMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterSourceMappingDTO,
     * or with status {@code 400 (Bad Request)} if the filterSourceMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filterSourceMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/filter-source-mappings/{id}")
    public ResponseEntity<FilterSourceMappingDTO> updateFilterSourceMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FilterSourceMappingDTO filterSourceMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FilterSourceMapping : {}, {}", id, filterSourceMappingDTO);
        if (filterSourceMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterSourceMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterSourceMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FilterSourceMappingDTO result = filterSourceMappingService.update(filterSourceMappingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filterSourceMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /filter-source-mappings/:id} : Partial updates given fields of an existing filterSourceMapping, field will ignore if it is null
     *
     * @param id the id of the filterSourceMappingDTO to save.
     * @param filterSourceMappingDTO the filterSourceMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterSourceMappingDTO,
     * or with status {@code 400 (Bad Request)} if the filterSourceMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the filterSourceMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the filterSourceMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/filter-source-mappings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FilterSourceMappingDTO> partialUpdateFilterSourceMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FilterSourceMappingDTO filterSourceMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FilterSourceMapping partially : {}, {}", id, filterSourceMappingDTO);
        if (filterSourceMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterSourceMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterSourceMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FilterSourceMappingDTO> result = filterSourceMappingService.partialUpdate(filterSourceMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filterSourceMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /filter-source-mappings} : get all the filterSourceMappings.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filterSourceMappings in body.
     */
    @GetMapping("/filter-source-mappings")
    public ResponseEntity<List<FilterSourceMappingDTO>> getAllFilterSourceMappings(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("filtermetadata-is-null".equals(filter)) {
            log.debug("REST request to get all FilterSourceMappings where filterMetadata is null");
            return new ResponseEntity<>(filterSourceMappingService.findAllWhereFilterMetadataIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of FilterSourceMappings");
        Page<FilterSourceMappingDTO> page = filterSourceMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /filter-source-mappings/:id} : get the "id" filterSourceMapping.
     *
     * @param id the id of the filterSourceMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filterSourceMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/filter-source-mappings/{id}")
    public ResponseEntity<FilterSourceMappingDTO> getFilterSourceMapping(@PathVariable Long id) {
        log.debug("REST request to get FilterSourceMapping : {}", id);
        Optional<FilterSourceMappingDTO> filterSourceMappingDTO = filterSourceMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filterSourceMappingDTO);
    }

    /**
     * {@code DELETE  /filter-source-mappings/:id} : delete the "id" filterSourceMapping.
     *
     * @param id the id of the filterSourceMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/filter-source-mappings/{id}")
    public ResponseEntity<Void> deleteFilterSourceMapping(@PathVariable Long id) {
        log.debug("REST request to delete FilterSourceMapping : {}", id);
        filterSourceMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
