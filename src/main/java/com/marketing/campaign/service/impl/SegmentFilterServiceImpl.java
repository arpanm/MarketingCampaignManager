package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.SegmentFilter;
import com.marketing.campaign.repository.SegmentFilterRepository;
import com.marketing.campaign.service.SegmentFilterService;
import com.marketing.campaign.service.dto.SegmentFilterDTO;
import com.marketing.campaign.service.mapper.SegmentFilterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marketing.campaign.domain.SegmentFilter}.
 */
@Service
@Transactional
public class SegmentFilterServiceImpl implements SegmentFilterService {

    private final Logger log = LoggerFactory.getLogger(SegmentFilterServiceImpl.class);

    private final SegmentFilterRepository segmentFilterRepository;

    private final SegmentFilterMapper segmentFilterMapper;

    public SegmentFilterServiceImpl(SegmentFilterRepository segmentFilterRepository, SegmentFilterMapper segmentFilterMapper) {
        this.segmentFilterRepository = segmentFilterRepository;
        this.segmentFilterMapper = segmentFilterMapper;
    }

    @Override
    public SegmentFilterDTO save(SegmentFilterDTO segmentFilterDTO) {
        log.debug("Request to save SegmentFilter : {}", segmentFilterDTO);
        SegmentFilter segmentFilter = segmentFilterMapper.toEntity(segmentFilterDTO);
        segmentFilter = segmentFilterRepository.save(segmentFilter);
        return segmentFilterMapper.toDto(segmentFilter);
    }

    @Override
    public SegmentFilterDTO update(SegmentFilterDTO segmentFilterDTO) {
        log.debug("Request to update SegmentFilter : {}", segmentFilterDTO);
        SegmentFilter segmentFilter = segmentFilterMapper.toEntity(segmentFilterDTO);
        segmentFilter = segmentFilterRepository.save(segmentFilter);
        return segmentFilterMapper.toDto(segmentFilter);
    }

    @Override
    public Optional<SegmentFilterDTO> partialUpdate(SegmentFilterDTO segmentFilterDTO) {
        log.debug("Request to partially update SegmentFilter : {}", segmentFilterDTO);

        return segmentFilterRepository
            .findById(segmentFilterDTO.getId())
            .map(existingSegmentFilter -> {
                segmentFilterMapper.partialUpdate(existingSegmentFilter, segmentFilterDTO);

                return existingSegmentFilter;
            })
            .map(segmentFilterRepository::save)
            .map(segmentFilterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SegmentFilterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SegmentFilters");
        return segmentFilterRepository.findAll(pageable).map(segmentFilterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SegmentFilterDTO> findOne(Long id) {
        log.debug("Request to get SegmentFilter : {}", id);
        return segmentFilterRepository.findById(id).map(segmentFilterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SegmentFilter : {}", id);
        segmentFilterRepository.deleteById(id);
    }
}
