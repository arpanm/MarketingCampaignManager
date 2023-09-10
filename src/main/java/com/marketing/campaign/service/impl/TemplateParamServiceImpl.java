package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.TemplateParam;
import com.marketing.campaign.repository.TemplateParamRepository;
import com.marketing.campaign.service.TemplateParamService;
import com.marketing.campaign.service.dto.TemplateParamDTO;
import com.marketing.campaign.service.mapper.TemplateParamMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marketing.campaign.domain.TemplateParam}.
 */
@Service
@Transactional
public class TemplateParamServiceImpl implements TemplateParamService {

    private final Logger log = LoggerFactory.getLogger(TemplateParamServiceImpl.class);

    private final TemplateParamRepository templateParamRepository;

    private final TemplateParamMapper templateParamMapper;

    public TemplateParamServiceImpl(TemplateParamRepository templateParamRepository, TemplateParamMapper templateParamMapper) {
        this.templateParamRepository = templateParamRepository;
        this.templateParamMapper = templateParamMapper;
    }

    @Override
    public TemplateParamDTO save(TemplateParamDTO templateParamDTO) {
        log.debug("Request to save TemplateParam : {}", templateParamDTO);
        TemplateParam templateParam = templateParamMapper.toEntity(templateParamDTO);
        templateParam = templateParamRepository.save(templateParam);
        return templateParamMapper.toDto(templateParam);
    }

    @Override
    public TemplateParamDTO update(TemplateParamDTO templateParamDTO) {
        log.debug("Request to update TemplateParam : {}", templateParamDTO);
        TemplateParam templateParam = templateParamMapper.toEntity(templateParamDTO);
        templateParam = templateParamRepository.save(templateParam);
        return templateParamMapper.toDto(templateParam);
    }

    @Override
    public Optional<TemplateParamDTO> partialUpdate(TemplateParamDTO templateParamDTO) {
        log.debug("Request to partially update TemplateParam : {}", templateParamDTO);

        return templateParamRepository
            .findById(templateParamDTO.getId())
            .map(existingTemplateParam -> {
                templateParamMapper.partialUpdate(existingTemplateParam, templateParamDTO);

                return existingTemplateParam;
            })
            .map(templateParamRepository::save)
            .map(templateParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateParams");
        return templateParamRepository.findAll(pageable).map(templateParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TemplateParamDTO> findOne(Long id) {
        log.debug("Request to get TemplateParam : {}", id);
        return templateParamRepository.findById(id).map(templateParamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TemplateParam : {}", id);
        templateParamRepository.deleteById(id);
    }
}
