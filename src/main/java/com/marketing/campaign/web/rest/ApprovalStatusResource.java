package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.ApprovalStatusRepository;
import com.marketing.campaign.service.ApprovalStatusService;
import com.marketing.campaign.service.dto.ApprovalStatusDTO;
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
 * REST controller for managing {@link com.marketing.campaign.domain.ApprovalStatus}.
 */
@RestController
@RequestMapping("/api")
public class ApprovalStatusResource {

    private final Logger log = LoggerFactory.getLogger(ApprovalStatusResource.class);

    private static final String ENTITY_NAME = "approvalStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApprovalStatusService approvalStatusService;

    private final ApprovalStatusRepository approvalStatusRepository;

    public ApprovalStatusResource(ApprovalStatusService approvalStatusService, ApprovalStatusRepository approvalStatusRepository) {
        this.approvalStatusService = approvalStatusService;
        this.approvalStatusRepository = approvalStatusRepository;
    }

    /**
     * {@code POST  /approval-statuses} : Create a new approvalStatus.
     *
     * @param approvalStatusDTO the approvalStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approvalStatusDTO, or with status {@code 400 (Bad Request)} if the approvalStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approval-statuses")
    public ResponseEntity<ApprovalStatusDTO> createApprovalStatus(@RequestBody ApprovalStatusDTO approvalStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save ApprovalStatus : {}", approvalStatusDTO);
        if (approvalStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new approvalStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApprovalStatusDTO result = approvalStatusService.save(approvalStatusDTO);
        return ResponseEntity
            .created(new URI("/api/approval-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approval-statuses/:id} : Updates an existing approvalStatus.
     *
     * @param id the id of the approvalStatusDTO to save.
     * @param approvalStatusDTO the approvalStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalStatusDTO,
     * or with status {@code 400 (Bad Request)} if the approvalStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approvalStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approval-statuses/{id}")
    public ResponseEntity<ApprovalStatusDTO> updateApprovalStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApprovalStatusDTO approvalStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApprovalStatus : {}, {}", id, approvalStatusDTO);
        if (approvalStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApprovalStatusDTO result = approvalStatusService.update(approvalStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approval-statuses/:id} : Partial updates given fields of an existing approvalStatus, field will ignore if it is null
     *
     * @param id the id of the approvalStatusDTO to save.
     * @param approvalStatusDTO the approvalStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalStatusDTO,
     * or with status {@code 400 (Bad Request)} if the approvalStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the approvalStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the approvalStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approval-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApprovalStatusDTO> partialUpdateApprovalStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApprovalStatusDTO approvalStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApprovalStatus partially : {}, {}", id, approvalStatusDTO);
        if (approvalStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApprovalStatusDTO> result = approvalStatusService.partialUpdate(approvalStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /approval-statuses} : get all the approvalStatuses.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approvalStatuses in body.
     */
    @GetMapping("/approval-statuses")
    public ResponseEntity<List<ApprovalStatusDTO>> getAllApprovalStatuses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("campaign-is-null".equals(filter)) {
            log.debug("REST request to get all ApprovalStatuss where campaign is null");
            return new ResponseEntity<>(approvalStatusService.findAllWhereCampaignIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of ApprovalStatuses");
        Page<ApprovalStatusDTO> page = approvalStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approval-statuses/:id} : get the "id" approvalStatus.
     *
     * @param id the id of the approvalStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approvalStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approval-statuses/{id}")
    public ResponseEntity<ApprovalStatusDTO> getApprovalStatus(@PathVariable Long id) {
        log.debug("REST request to get ApprovalStatus : {}", id);
        Optional<ApprovalStatusDTO> approvalStatusDTO = approvalStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approvalStatusDTO);
    }

    /**
     * {@code DELETE  /approval-statuses/:id} : delete the "id" approvalStatus.
     *
     * @param id the id of the approvalStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approval-statuses/{id}")
    public ResponseEntity<Void> deleteApprovalStatus(@PathVariable Long id) {
        log.debug("REST request to delete ApprovalStatus : {}", id);
        approvalStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
