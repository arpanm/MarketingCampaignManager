package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.FilterMetadataRepository;
import com.marketing.campaign.service.FilterMetadataService;
import com.marketing.campaign.service.dto.FilterMetadataDTO;
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
 * REST controller for managing {@link com.marketing.campaign.domain.FilterMetadata}.
 */
@RestController
@RequestMapping("/api")
public class FilterMetadataResource {

    private final Logger log = LoggerFactory.getLogger(FilterMetadataResource.class);

    private static final String ENTITY_NAME = "filterMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilterMetadataService filterMetadataService;

    private final FilterMetadataRepository filterMetadataRepository;

    public FilterMetadataResource(FilterMetadataService filterMetadataService, FilterMetadataRepository filterMetadataRepository) {
        this.filterMetadataService = filterMetadataService;
        this.filterMetadataRepository = filterMetadataRepository;
    }

    /**
     * {@code POST  /filter-metadata} : Create a new filterMetadata.
     *
     * @param filterMetadataDTO the filterMetadataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filterMetadataDTO, or with status {@code 400 (Bad Request)} if the filterMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/filter-metadata")
    public ResponseEntity<FilterMetadataDTO> createFilterMetadata(@RequestBody FilterMetadataDTO filterMetadataDTO)
        throws URISyntaxException {
        log.debug("REST request to save FilterMetadata : {}", filterMetadataDTO);
        if (filterMetadataDTO.getId() != null) {
            throw new BadRequestAlertException("A new filterMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilterMetadataDTO result = filterMetadataService.save(filterMetadataDTO);
        return ResponseEntity
            .created(new URI("/api/filter-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /filter-metadata/:id} : Updates an existing filterMetadata.
     *
     * @param id the id of the filterMetadataDTO to save.
     * @param filterMetadataDTO the filterMetadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterMetadataDTO,
     * or with status {@code 400 (Bad Request)} if the filterMetadataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filterMetadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/filter-metadata/{id}")
    public ResponseEntity<FilterMetadataDTO> updateFilterMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FilterMetadataDTO filterMetadataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FilterMetadata : {}, {}", id, filterMetadataDTO);
        if (filterMetadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterMetadataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FilterMetadataDTO result = filterMetadataService.update(filterMetadataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filterMetadataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /filter-metadata/:id} : Partial updates given fields of an existing filterMetadata, field will ignore if it is null
     *
     * @param id the id of the filterMetadataDTO to save.
     * @param filterMetadataDTO the filterMetadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterMetadataDTO,
     * or with status {@code 400 (Bad Request)} if the filterMetadataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the filterMetadataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the filterMetadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/filter-metadata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FilterMetadataDTO> partialUpdateFilterMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FilterMetadataDTO filterMetadataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FilterMetadata partially : {}, {}", id, filterMetadataDTO);
        if (filterMetadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterMetadataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FilterMetadataDTO> result = filterMetadataService.partialUpdate(filterMetadataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filterMetadataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /filter-metadata} : get all the filterMetadata.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filterMetadata in body.
     */
    @GetMapping("/filter-metadata")
    public ResponseEntity<List<FilterMetadataDTO>> getAllFilterMetadata(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("segmentfilter-is-null".equals(filter)) {
            log.debug("REST request to get all FilterMetadatas where segmentFilter is null");
            return new ResponseEntity<>(filterMetadataService.findAllWhereSegmentFilterIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of FilterMetadata");
        Page<FilterMetadataDTO> page = filterMetadataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /filter-metadata/:id} : get the "id" filterMetadata.
     *
     * @param id the id of the filterMetadataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filterMetadataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/filter-metadata/{id}")
    public ResponseEntity<FilterMetadataDTO> getFilterMetadata(@PathVariable Long id) {
        log.debug("REST request to get FilterMetadata : {}", id);
        Optional<FilterMetadataDTO> filterMetadataDTO = filterMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filterMetadataDTO);
    }

    /**
     * {@code DELETE  /filter-metadata/:id} : delete the "id" filterMetadata.
     *
     * @param id the id of the filterMetadataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/filter-metadata/{id}")
    public ResponseEntity<Void> deleteFilterMetadata(@PathVariable Long id) {
        log.debug("REST request to delete FilterMetadata : {}", id);
        filterMetadataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
