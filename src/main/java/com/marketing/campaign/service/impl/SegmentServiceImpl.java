package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.Segment;
import com.marketing.campaign.repository.SegmentRepository;
import com.marketing.campaign.service.SegmentService;
import com.marketing.campaign.service.dto.SegmentDTO;
import com.marketing.campaign.service.mapper.SegmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marketing.campaign.domain.Segment}.
 */
@Service
@Transactional
public class SegmentServiceImpl implements SegmentService {

    private final Logger log = LoggerFactory.getLogger(SegmentServiceImpl.class);

    private final SegmentRepository segmentRepository;

    private final SegmentMapper segmentMapper;

    public SegmentServiceImpl(SegmentRepository segmentRepository, SegmentMapper segmentMapper) {
        this.segmentRepository = segmentRepository;
        this.segmentMapper = segmentMapper;
    }

    @Override
    public SegmentDTO save(SegmentDTO segmentDTO) {
        log.debug("Request to save Segment : {}", segmentDTO);
        Segment segment = segmentMapper.toEntity(segmentDTO);
        segment = segmentRepository.save(segment);
        return segmentMapper.toDto(segment);
    }

    @Override
    public SegmentDTO update(SegmentDTO segmentDTO) {
        log.debug("Request to update Segment : {}", segmentDTO);
        Segment segment = segmentMapper.toEntity(segmentDTO);
        segment = segmentRepository.save(segment);
        return segmentMapper.toDto(segment);
    }

    @Override
    public Optional<SegmentDTO> partialUpdate(SegmentDTO segmentDTO) {
        log.debug("Request to partially update Segment : {}", segmentDTO);

        return segmentRepository
            .findById(segmentDTO.getId())
            .map(existingSegment -> {
                segmentMapper.partialUpdate(existingSegment, segmentDTO);

                return existingSegment;
            })
            .map(segmentRepository::save)
            .map(segmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SegmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Segments");
        return segmentRepository.findAll(pageable).map(segmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SegmentDTO> findOne(Long id) {
        log.debug("Request to get Segment : {}", id);
        return segmentRepository.findById(id).map(segmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Segment : {}", id);
        segmentRepository.deleteById(id);
    }
}
