package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.SegmentFilterRepository;
import com.marketing.campaign.service.SegmentFilterService;
import com.marketing.campaign.service.dto.SegmentFilterDTO;
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
 * REST controller for managing {@link com.marketing.campaign.domain.SegmentFilter}.
 */
@RestController
@RequestMapping("/api")
public class SegmentFilterResource {

    private final Logger log = LoggerFactory.getLogger(SegmentFilterResource.class);

    private static final String ENTITY_NAME = "segmentFilter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentFilterService segmentFilterService;

    private final SegmentFilterRepository segmentFilterRepository;

    public SegmentFilterResource(SegmentFilterService segmentFilterService, SegmentFilterRepository segmentFilterRepository) {
        this.segmentFilterService = segmentFilterService;
        this.segmentFilterRepository = segmentFilterRepository;
    }

    /**
     * {@code POST  /segment-filters} : Create a new segmentFilter.
     *
     * @param segmentFilterDTO the segmentFilterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmentFilterDTO, or with status {@code 400 (Bad Request)} if the segmentFilter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/segment-filters")
    public ResponseEntity<SegmentFilterDTO> createSegmentFilter(@RequestBody SegmentFilterDTO segmentFilterDTO) throws URISyntaxException {
        log.debug("REST request to save SegmentFilter : {}", segmentFilterDTO);
        if (segmentFilterDTO.getId() != null) {
            throw new BadRequestAlertException("A new segmentFilter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SegmentFilterDTO result = segmentFilterService.save(segmentFilterDTO);
        return ResponseEntity
            .created(new URI("/api/segment-filters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /segment-filters/:id} : Updates an existing segmentFilter.
     *
     * @param id the id of the segmentFilterDTO to save.
     * @param segmentFilterDTO the segmentFilterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentFilterDTO,
     * or with status {@code 400 (Bad Request)} if the segmentFilterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmentFilterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/segment-filters/{id}")
    public ResponseEntity<SegmentFilterDTO> updateSegmentFilter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SegmentFilterDTO segmentFilterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SegmentFilter : {}, {}", id, segmentFilterDTO);
        if (segmentFilterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentFilterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentFilterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SegmentFilterDTO result = segmentFilterService.update(segmentFilterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentFilterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /segment-filters/:id} : Partial updates given fields of an existing segmentFilter, field will ignore if it is null
     *
     * @param id the id of the segmentFilterDTO to save.
     * @param segmentFilterDTO the segmentFilterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentFilterDTO,
     * or with status {@code 400 (Bad Request)} if the segmentFilterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the segmentFilterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmentFilterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/segment-filters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SegmentFilterDTO> partialUpdateSegmentFilter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SegmentFilterDTO segmentFilterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SegmentFilter partially : {}, {}", id, segmentFilterDTO);
        if (segmentFilterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentFilterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentFilterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SegmentFilterDTO> result = segmentFilterService.partialUpdate(segmentFilterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentFilterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /segment-filters} : get all the segmentFilters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segmentFilters in body.
     */
    @GetMapping("/segment-filters")
    public ResponseEntity<List<SegmentFilterDTO>> getAllSegmentFilters(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SegmentFilters");
        Page<SegmentFilterDTO> page = segmentFilterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /segment-filters/:id} : get the "id" segmentFilter.
     *
     * @param id the id of the segmentFilterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmentFilterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/segment-filters/{id}")
    public ResponseEntity<SegmentFilterDTO> getSegmentFilter(@PathVariable Long id) {
        log.debug("REST request to get SegmentFilter : {}", id);
        Optional<SegmentFilterDTO> segmentFilterDTO = segmentFilterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(segmentFilterDTO);
    }

    /**
     * {@code DELETE  /segment-filters/:id} : delete the "id" segmentFilter.
     *
     * @param id the id of the segmentFilterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/segment-filters/{id}")
    public ResponseEntity<Void> deleteSegmentFilter(@PathVariable Long id) {
        log.debug("REST request to delete SegmentFilter : {}", id);
        segmentFilterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
