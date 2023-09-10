package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.ApprovalAuthorityRepository;
import com.marketing.campaign.service.ApprovalAuthorityService;
import com.marketing.campaign.service.dto.ApprovalAuthorityDTO;
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
 * REST controller for managing {@link com.marketing.campaign.domain.ApprovalAuthority}.
 */
@RestController
@RequestMapping("/api")
public class ApprovalAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(ApprovalAuthorityResource.class);

    private static final String ENTITY_NAME = "approvalAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApprovalAuthorityService approvalAuthorityService;

    private final ApprovalAuthorityRepository approvalAuthorityRepository;

    public ApprovalAuthorityResource(
        ApprovalAuthorityService approvalAuthorityService,
        ApprovalAuthorityRepository approvalAuthorityRepository
    ) {
        this.approvalAuthorityService = approvalAuthorityService;
        this.approvalAuthorityRepository = approvalAuthorityRepository;
    }

    /**
     * {@code POST  /approval-authorities} : Create a new approvalAuthority.
     *
     * @param approvalAuthorityDTO the approvalAuthorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approvalAuthorityDTO, or with status {@code 400 (Bad Request)} if the approvalAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approval-authorities")
    public ResponseEntity<ApprovalAuthorityDTO> createApprovalAuthority(@RequestBody ApprovalAuthorityDTO approvalAuthorityDTO)
        throws URISyntaxException {
        log.debug("REST request to save ApprovalAuthority : {}", approvalAuthorityDTO);
        if (approvalAuthorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new approvalAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApprovalAuthorityDTO result = approvalAuthorityService.save(approvalAuthorityDTO);
        return ResponseEntity
            .created(new URI("/api/approval-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approval-authorities/:id} : Updates an existing approvalAuthority.
     *
     * @param id the id of the approvalAuthorityDTO to save.
     * @param approvalAuthorityDTO the approvalAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the approvalAuthorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approvalAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approval-authorities/{id}")
    public ResponseEntity<ApprovalAuthorityDTO> updateApprovalAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApprovalAuthorityDTO approvalAuthorityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApprovalAuthority : {}, {}", id, approvalAuthorityDTO);
        if (approvalAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalAuthorityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalAuthorityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApprovalAuthorityDTO result = approvalAuthorityService.update(approvalAuthorityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approval-authorities/:id} : Partial updates given fields of an existing approvalAuthority, field will ignore if it is null
     *
     * @param id the id of the approvalAuthorityDTO to save.
     * @param approvalAuthorityDTO the approvalAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the approvalAuthorityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the approvalAuthorityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the approvalAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approval-authorities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApprovalAuthorityDTO> partialUpdateApprovalAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApprovalAuthorityDTO approvalAuthorityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApprovalAuthority partially : {}, {}", id, approvalAuthorityDTO);
        if (approvalAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalAuthorityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalAuthorityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApprovalAuthorityDTO> result = approvalAuthorityService.partialUpdate(approvalAuthorityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalAuthorityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /approval-authorities} : get all the approvalAuthorities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approvalAuthorities in body.
     */
    @GetMapping("/approval-authorities")
    public ResponseEntity<List<ApprovalAuthorityDTO>> getAllApprovalAuthorities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ApprovalAuthorities");
        Page<ApprovalAuthorityDTO> page = approvalAuthorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approval-authorities/:id} : get the "id" approvalAuthority.
     *
     * @param id the id of the approvalAuthorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approvalAuthorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approval-authorities/{id}")
    public ResponseEntity<ApprovalAuthorityDTO> getApprovalAuthority(@PathVariable Long id) {
        log.debug("REST request to get ApprovalAuthority : {}", id);
        Optional<ApprovalAuthorityDTO> approvalAuthorityDTO = approvalAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approvalAuthorityDTO);
    }

    /**
     * {@code DELETE  /approval-authorities/:id} : delete the "id" approvalAuthority.
     *
     * @param id the id of the approvalAuthorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approval-authorities/{id}")
    public ResponseEntity<Void> deleteApprovalAuthority(@PathVariable Long id) {
        log.debug("REST request to delete ApprovalAuthority : {}", id);
        approvalAuthorityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
