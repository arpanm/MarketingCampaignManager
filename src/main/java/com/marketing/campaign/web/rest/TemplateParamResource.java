package com.marketing.campaign.web.rest;

import com.marketing.campaign.repository.TemplateParamRepository;
import com.marketing.campaign.service.TemplateParamService;
import com.marketing.campaign.service.dto.TemplateParamDTO;
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
 * REST controller for managing {@link com.marketing.campaign.domain.TemplateParam}.
 */
@RestController
@RequestMapping("/api")
public class TemplateParamResource {

    private final Logger log = LoggerFactory.getLogger(TemplateParamResource.class);

    private static final String ENTITY_NAME = "templateParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateParamService templateParamService;

    private final TemplateParamRepository templateParamRepository;

    public TemplateParamResource(TemplateParamService templateParamService, TemplateParamRepository templateParamRepository) {
        this.templateParamService = templateParamService;
        this.templateParamRepository = templateParamRepository;
    }

    /**
     * {@code POST  /template-params} : Create a new templateParam.
     *
     * @param templateParamDTO the templateParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateParamDTO, or with status {@code 400 (Bad Request)} if the templateParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-params")
    public ResponseEntity<TemplateParamDTO> createTemplateParam(@RequestBody TemplateParamDTO templateParamDTO) throws URISyntaxException {
        log.debug("REST request to save TemplateParam : {}", templateParamDTO);
        if (templateParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateParamDTO result = templateParamService.save(templateParamDTO);
        return ResponseEntity
            .created(new URI("/api/template-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-params/:id} : Updates an existing templateParam.
     *
     * @param id the id of the templateParamDTO to save.
     * @param templateParamDTO the templateParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateParamDTO,
     * or with status {@code 400 (Bad Request)} if the templateParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-params/{id}")
    public ResponseEntity<TemplateParamDTO> updateTemplateParam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplateParamDTO templateParamDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TemplateParam : {}, {}", id, templateParamDTO);
        if (templateParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateParamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateParamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemplateParamDTO result = templateParamService.update(templateParamDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateParamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /template-params/:id} : Partial updates given fields of an existing templateParam, field will ignore if it is null
     *
     * @param id the id of the templateParamDTO to save.
     * @param templateParamDTO the templateParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateParamDTO,
     * or with status {@code 400 (Bad Request)} if the templateParamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateParamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/template-params/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemplateParamDTO> partialUpdateTemplateParam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplateParamDTO templateParamDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemplateParam partially : {}, {}", id, templateParamDTO);
        if (templateParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateParamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateParamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplateParamDTO> result = templateParamService.partialUpdate(templateParamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateParamDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /template-params} : get all the templateParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateParams in body.
     */
    @GetMapping("/template-params")
    public ResponseEntity<List<TemplateParamDTO>> getAllTemplateParams(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TemplateParams");
        Page<TemplateParamDTO> page = templateParamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-params/:id} : get the "id" templateParam.
     *
     * @param id the id of the templateParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-params/{id}")
    public ResponseEntity<TemplateParamDTO> getTemplateParam(@PathVariable Long id) {
        log.debug("REST request to get TemplateParam : {}", id);
        Optional<TemplateParamDTO> templateParamDTO = templateParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateParamDTO);
    }

    /**
     * {@code DELETE  /template-params/:id} : delete the "id" templateParam.
     *
     * @param id the id of the templateParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-params/{id}")
    public ResponseEntity<Void> deleteTemplateParam(@PathVariable Long id) {
        log.debug("REST request to delete TemplateParam : {}", id);
        templateParamService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
