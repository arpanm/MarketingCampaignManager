package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.Campaign;
import com.marketing.campaign.repository.CampaignRepository;
import com.marketing.campaign.service.CampaignService;
import com.marketing.campaign.service.dto.CampaignDTO;
import com.marketing.campaign.service.mapper.CampaignMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marketing.campaign.domain.Campaign}.
 */
@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    private final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    private final CampaignRepository campaignRepository;

    private final CampaignMapper campaignMapper;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignMapper campaignMapper) {
        this.campaignRepository = campaignRepository;
        this.campaignMapper = campaignMapper;
    }

    @Override
    public CampaignDTO save(CampaignDTO campaignDTO) {
        log.debug("Request to save Campaign : {}", campaignDTO);
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        campaign = campaignRepository.save(campaign);
        return campaignMapper.toDto(campaign);
    }

    @Override
    public CampaignDTO update(CampaignDTO campaignDTO) {
        log.debug("Request to update Campaign : {}", campaignDTO);
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        campaign = campaignRepository.save(campaign);
        return campaignMapper.toDto(campaign);
    }

    @Override
    public Optional<CampaignDTO> partialUpdate(CampaignDTO campaignDTO) {
        log.debug("Request to partially update Campaign : {}", campaignDTO);

        return campaignRepository
            .findById(campaignDTO.getId())
            .map(existingCampaign -> {
                campaignMapper.partialUpdate(existingCampaign, campaignDTO);

                return existingCampaign;
            })
            .map(campaignRepository::save)
            .map(campaignMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampaignDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campaigns");
        return campaignRepository.findAll(pageable).map(campaignMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignDTO> findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        return campaignRepository.findById(id).map(campaignMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campaign : {}", id);
        campaignRepository.deleteById(id);
    }
}
