package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.FilterSourceMapping;
import com.marketing.campaign.repository.FilterSourceMappingRepository;
import com.marketing.campaign.service.FilterSourceMappingService;
import com.marketing.campaign.service.dto.FilterSourceMappingDTO;
import com.marketing.campaign.service.mapper.FilterSourceMappingMapper;
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
 * Service Implementation for managing {@link com.marketing.campaign.domain.FilterSourceMapping}.
 */
@Service
@Transactional
public class FilterSourceMappingServiceImpl implements FilterSourceMappingService {

    private final Logger log = LoggerFactory.getLogger(FilterSourceMappingServiceImpl.class);

    private final FilterSourceMappingRepository filterSourceMappingRepository;

    private final FilterSourceMappingMapper filterSourceMappingMapper;

    public FilterSourceMappingServiceImpl(
        FilterSourceMappingRepository filterSourceMappingRepository,
        FilterSourceMappingMapper filterSourceMappingMapper
    ) {
        this.filterSourceMappingRepository = filterSourceMappingRepository;
        this.filterSourceMappingMapper = filterSourceMappingMapper;
    }

    @Override
    public FilterSourceMappingDTO save(FilterSourceMappingDTO filterSourceMappingDTO) {
        log.debug("Request to save FilterSourceMapping : {}", filterSourceMappingDTO);
        FilterSourceMapping filterSourceMapping = filterSourceMappingMapper.toEntity(filterSourceMappingDTO);
        filterSourceMapping = filterSourceMappingRepository.save(filterSourceMapping);
        return filterSourceMappingMapper.toDto(filterSourceMapping);
    }

    @Override
    public FilterSourceMappingDTO update(FilterSourceMappingDTO filterSourceMappingDTO) {
        log.debug("Request to update FilterSourceMapping : {}", filterSourceMappingDTO);
        FilterSourceMapping filterSourceMapping = filterSourceMappingMapper.toEntity(filterSourceMappingDTO);
        filterSourceMapping = filterSourceMappingRepository.save(filterSourceMapping);
        return filterSourceMappingMapper.toDto(filterSourceMapping);
    }

    @Override
    public Optional<FilterSourceMappingDTO> partialUpdate(FilterSourceMappingDTO filterSourceMappingDTO) {
        log.debug("Request to partially update FilterSourceMapping : {}", filterSourceMappingDTO);

        return filterSourceMappingRepository
            .findById(filterSourceMappingDTO.getId())
            .map(existingFilterSourceMapping -> {
                filterSourceMappingMapper.partialUpdate(existingFilterSourceMapping, filterSourceMappingDTO);

                return existingFilterSourceMapping;
            })
            .map(filterSourceMappingRepository::save)
            .map(filterSourceMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilterSourceMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FilterSourceMappings");
        return filterSourceMappingRepository.findAll(pageable).map(filterSourceMappingMapper::toDto);
    }

    /**
     *  Get all the filterSourceMappings where FilterMetadata is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FilterSourceMappingDTO> findAllWhereFilterMetadataIsNull() {
        log.debug("Request to get all filterSourceMappings where FilterMetadata is null");
        return StreamSupport
            .stream(filterSourceMappingRepository.findAll().spliterator(), false)
            .filter(filterSourceMapping -> filterSourceMapping.getFilterMetadata() == null)
            .map(filterSourceMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilterSourceMappingDTO> findOne(Long id) {
        log.debug("Request to get FilterSourceMapping : {}", id);
        return filterSourceMappingRepository.findById(id).map(filterSourceMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FilterSourceMapping : {}", id);
        filterSourceMappingRepository.deleteById(id);
    }
}
