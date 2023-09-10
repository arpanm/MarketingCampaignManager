package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.ApprovalStatus;
import com.marketing.campaign.repository.ApprovalStatusRepository;
import com.marketing.campaign.service.ApprovalStatusService;
import com.marketing.campaign.service.dto.ApprovalStatusDTO;
import com.marketing.campaign.service.mapper.ApprovalStatusMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marketing.campaign.domain.ApprovalStatus}.
 */
@Service
@Transactional
public class ApprovalStatusServiceImpl implements ApprovalStatusService {

    private final Logger log = LoggerFactory.getLogger(ApprovalStatusServiceImpl.class);

    private final ApprovalStatusRepository approvalStatusRepository;

    private final ApprovalStatusMapper approvalStatusMapper;

    public ApprovalStatusServiceImpl(ApprovalStatusRepository approvalStatusRepository, ApprovalStatusMapper approvalStatusMapper) {
        this.approvalStatusRepository = approvalStatusRepository;
        this.approvalStatusMapper = approvalStatusMapper;
    }

    @Override
    public ApprovalStatusDTO save(ApprovalStatusDTO approvalStatusDTO) {
        log.debug("Request to save ApprovalStatus : {}", approvalStatusDTO);
        ApprovalStatus approvalStatus = approvalStatusMapper.toEntity(approvalStatusDTO);
        approvalStatus = approvalStatusRepository.save(approvalStatus);
        return approvalStatusMapper.toDto(approvalStatus);
    }

    @Override
    public ApprovalStatusDTO update(ApprovalStatusDTO approvalStatusDTO) {
        log.debug("Request to update ApprovalStatus : {}", approvalStatusDTO);
        ApprovalStatus approvalStatus = approvalStatusMapper.toEntity(approvalStatusDTO);
        approvalStatus = approvalStatusRepository.save(approvalStatus);
        return approvalStatusMapper.toDto(approvalStatus);
    }

    @Override
    public Optional<ApprovalStatusDTO> partialUpdate(ApprovalStatusDTO approvalStatusDTO) {
        log.debug("Request to partially update ApprovalStatus : {}", approvalStatusDTO);

        return approvalStatusRepository
            .findById(approvalStatusDTO.getId())
            .map(existingApprovalStatus -> {
                approvalStatusMapper.partialUpdate(existingApprovalStatus, approvalStatusDTO);

                return existingApprovalStatus;
            })
            .map(approvalStatusRepository::save)
            .map(approvalStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApprovalStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApprovalStatuses");
        return approvalStatusRepository.findAll(pageable).map(approvalStatusMapper::toDto);
    }

    /**
     *  Get all the approvalStatuses where Campaign is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApprovalStatusDTO> findAllWhereCampaignIsNull() {
        log.debug("Request to get all approvalStatuses where Campaign is null");
        return StreamSupport
            .stream(approvalStatusRepository.findAll().spliterator(), false)
            .filter(approvalStatus -> approvalStatus.getCampaign() == null)
            .map(approvalStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApprovalStatusDTO> findOne(Long id) {
        log.debug("Request to get ApprovalStatus : {}", id);
        return approvalStatusRepository.findById(id).map(approvalStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApprovalStatus : {}", id);
        approvalStatusRepository.deleteById(id);
    }
}
