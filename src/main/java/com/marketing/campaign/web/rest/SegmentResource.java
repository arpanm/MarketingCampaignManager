package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.SegmentRepository;
import com.marketing.campaign.service.SegmentService;
import com.marketing.campaign.service.dto.SegmentDTO;
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
 * REST controller for managing {@link com.marketing.campaign.domain.Segment}.
 */
@RestController
@RequestMapping("/api")
public class SegmentResource {

    private final Logger log = LoggerFactory.getLogger(SegmentResource.class);

    private static final String ENTITY_NAME = "segment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentService segmentService;

    private final SegmentRepository segmentRepository;

    public SegmentResource(SegmentService segmentService, SegmentRepository segmentRepository) {
        this.segmentService = segmentService;
        this.segmentRepository = segmentRepository;
    }

    /**
     * {@code POST  /segments} : Create a new segment.
     *
     * @param segmentDTO the segmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmentDTO, or with status {@code 400 (Bad Request)} if the segment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/segments")
    public ResponseEntity<SegmentDTO> createSegment(@RequestBody SegmentDTO segmentDTO) throws URISyntaxException {
        log.debug("REST request to save Segment : {}", segmentDTO);
        if (segmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new segment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SegmentDTO result = segmentService.save(segmentDTO);
        return ResponseEntity
            .created(new URI("/api/segments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /segments/:id} : Updates an existing segment.
     *
     * @param id the id of the segmentDTO to save.
     * @param segmentDTO the segmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentDTO,
     * or with status {@code 400 (Bad Request)} if the segmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/segments/{id}")
    public ResponseEntity<SegmentDTO> updateSegment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SegmentDTO segmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Segment : {}, {}", id, segmentDTO);
        if (segmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SegmentDTO result = segmentService.update(segmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /segments/:id} : Partial updates given fields of an existing segment, field will ignore if it is null
     *
     * @param id the id of the segmentDTO to save.
     * @param segmentDTO the segmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentDTO,
     * or with status {@code 400 (Bad Request)} if the segmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the segmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/segments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SegmentDTO> partialUpdateSegment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SegmentDTO segmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Segment partially : {}, {}", id, segmentDTO);
        if (segmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SegmentDTO> result = segmentService.partialUpdate(segmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /segments} : get all the segments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segments in body.
     */
    @GetMapping("/segments")
    public ResponseEntity<List<SegmentDTO>> getAllSegments(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Segments");
        Page<SegmentDTO> page = segmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /segments/:id} : get the "id" segment.
     *
     * @param id the id of the segmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/segments/{id}")
    public ResponseEntity<SegmentDTO> getSegment(@PathVariable Long id) {
        log.debug("REST request to get Segment : {}", id);
        Optional<SegmentDTO> segmentDTO = segmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(segmentDTO);
    }

    /**
     * {@code DELETE  /segments/:id} : delete the "id" segment.
     *
     * @param id the id of the segmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/segments/{id}")
    public ResponseEntity<Void> deleteSegment(@PathVariable Long id) {
        log.debug("REST request to delete Segment : {}", id);
        segmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
