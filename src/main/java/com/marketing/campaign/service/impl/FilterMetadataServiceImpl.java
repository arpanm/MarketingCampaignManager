package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.FilterMetadata;
import com.marketing.campaign.repository.FilterMetadataRepository;
import com.marketing.campaign.service.FilterMetadataService;
import com.marketing.campaign.service.dto.FilterMetadataDTO;
import com.marketing.campaign.service.mapper.FilterMetadataMapper;
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
 * Service Implementation for managing {@link com.marketing.campaign.domain.FilterMetadata}.
 */
@Service
@Transactional
public class FilterMetadataServiceImpl implements FilterMetadataService {

    private final Logger log = LoggerFactory.getLogger(FilterMetadataServiceImpl.class);

    private final FilterMetadataRepository filterMetadataRepository;

    private final FilterMetadataMapper filterMetadataMapper;

    public FilterMetadataServiceImpl(FilterMetadataRepository filterMetadataRepository, FilterMetadataMapper filterMetadataMapper) {
        this.filterMetadataRepository = filterMetadataRepository;
        this.filterMetadataMapper = filterMetadataMapper;
    }

    @Override
    public FilterMetadataDTO save(FilterMetadataDTO filterMetadataDTO) {
        log.debug("Request to save FilterMetadata : {}", filterMetadataDTO);
        FilterMetadata filterMetadata = filterMetadataMapper.toEntity(filterMetadataDTO);
        filterMetadata = filterMetadataRepository.save(filterMetadata);
        return filterMetadataMapper.toDto(filterMetadata);
    }

    @Override
    public FilterMetadataDTO update(FilterMetadataDTO filterMetadataDTO) {
        log.debug("Request to update FilterMetadata : {}", filterMetadataDTO);
        FilterMetadata filterMetadata = filterMetadataMapper.toEntity(filterMetadataDTO);
        filterMetadata = filterMetadataRepository.save(filterMetadata);
        return filterMetadataMapper.toDto(filterMetadata);
    }

    @Override
    public Optional<FilterMetadataDTO> partialUpdate(FilterMetadataDTO filterMetadataDTO) {
        log.debug("Request to partially update FilterMetadata : {}", filterMetadataDTO);

        return filterMetadataRepository
            .findById(filterMetadataDTO.getId())
            .map(existingFilterMetadata -> {
                filterMetadataMapper.partialUpdate(existingFilterMetadata, filterMetadataDTO);

                return existingFilterMetadata;
            })
            .map(filterMetadataRepository::save)
            .map(filterMetadataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilterMetadataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FilterMetadata");
        return filterMetadataRepository.findAll(pageable).map(filterMetadataMapper::toDto);
    }

    /**
     *  Get all the filterMetadata where SegmentFilter is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FilterMetadataDTO> findAllWhereSegmentFilterIsNull() {
        log.debug("Request to get all filterMetadata where SegmentFilter is null");
        return StreamSupport
            .stream(filterMetadataRepository.findAll().spliterator(), false)
            .filter(filterMetadata -> filterMetadata.getSegmentFilter() == null)
            .map(filterMetadataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilterMetadataDTO> findOne(Long id) {
        log.debug("Request to get FilterMetadata : {}", id);
        return filterMetadataRepository.findById(id).map(filterMetadataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FilterMetadata : {}", id);
        filterMetadataRepository.deleteById(id);
    }
}
