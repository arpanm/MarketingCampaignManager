package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.ApprovalAuthority;
import com.marketing.campaign.repository.ApprovalAuthorityRepository;
import com.marketing.campaign.service.ApprovalAuthorityService;
import com.marketing.campaign.service.dto.ApprovalAuthorityDTO;
import com.marketing.campaign.service.mapper.ApprovalAuthorityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marketing.campaign.domain.ApprovalAuthority}.
 */
@Service
@Transactional
public class ApprovalAuthorityServiceImpl implements ApprovalAuthorityService {

    private final Logger log = LoggerFactory.getLogger(ApprovalAuthorityServiceImpl.class);

    private final ApprovalAuthorityRepository approvalAuthorityRepository;

    private final ApprovalAuthorityMapper approvalAuthorityMapper;

    public ApprovalAuthorityServiceImpl(
        ApprovalAuthorityRepository approvalAuthorityRepository,
        ApprovalAuthorityMapper approvalAuthorityMapper
    ) {
        this.approvalAuthorityRepository = approvalAuthorityRepository;
        this.approvalAuthorityMapper = approvalAuthorityMapper;
    }

    @Override
    public ApprovalAuthorityDTO save(ApprovalAuthorityDTO approvalAuthorityDTO) {
        log.debug("Request to save ApprovalAuthority : {}", approvalAuthorityDTO);
        ApprovalAuthority approvalAuthority = approvalAuthorityMapper.toEntity(approvalAuthorityDTO);
        approvalAuthority = approvalAuthorityRepository.save(approvalAuthority);
        return approvalAuthorityMapper.toDto(approvalAuthority);
    }

    @Override
    public ApprovalAuthorityDTO update(ApprovalAuthorityDTO approvalAuthorityDTO) {
        log.debug("Request to update ApprovalAuthority : {}", approvalAuthorityDTO);
        ApprovalAuthority approvalAuthority = approvalAuthorityMapper.toEntity(approvalAuthorityDTO);
        approvalAuthority = approvalAuthorityRepository.save(approvalAuthority);
        return approvalAuthorityMapper.toDto(approvalAuthority);
    }

    @Override
    public Optional<ApprovalAuthorityDTO> partialUpdate(ApprovalAuthorityDTO approvalAuthorityDTO) {
        log.debug("Request to partially update ApprovalAuthority : {}", approvalAuthorityDTO);

        return approvalAuthorityRepository
            .findById(approvalAuthorityDTO.getId())
            .map(existingApprovalAuthority -> {
                approvalAuthorityMapper.partialUpdate(existingApprovalAuthority, approvalAuthorityDTO);

                return existingApprovalAuthority;
            })
            .map(approvalAuthorityRepository::save)
            .map(approvalAuthorityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApprovalAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApprovalAuthorities");
        return approvalAuthorityRepository.findAll(pageable).map(approvalAuthorityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApprovalAuthorityDTO> findOne(Long id) {
        log.debug("Request to get ApprovalAuthority : {}", id);
        return approvalAuthorityRepository.findById(id).map(approvalAuthorityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApprovalAuthority : {}", id);
        approvalAuthorityRepository.deleteById(id);
    }
}
