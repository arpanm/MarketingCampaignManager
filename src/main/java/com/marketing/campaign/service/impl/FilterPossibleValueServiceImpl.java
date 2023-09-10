package com.marketing.campaign.service.impl;

import com.marketing.campaign.domain.FilterPossibleValue;
import com.marketing.campaign.repository.FilterPossibleValueRepository;
import com.marketing.campaign.service.FilterPossibleValueService;
import com.marketing.campaign.service.dto.FilterPossibleValueDTO;
import com.marketing.campaign.service.mapper.FilterPossibleValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.marketing.campaign.domain.FilterPossibleValue}.
 */
@Service
@Transactional
public class FilterPossibleValueServiceImpl implements FilterPossibleValueService {

    private final Logger log = LoggerFactory.getLogger(FilterPossibleValueServiceImpl.class);

    private final FilterPossibleValueRepository filterPossibleValueRepository;

    private final FilterPossibleValueMapper filterPossibleValueMapper;

    public FilterPossibleValueServiceImpl(
        FilterPossibleValueRepository filterPossibleValueRepository,
        FilterPossibleValueMapper filterPossibleValueMapper
    ) {
        this.filterPossibleValueRepository = filterPossibleValueRepository;
        this.filterPossibleValueMapper = filterPossibleValueMapper;
    }

    @Override
    public FilterPossibleValueDTO save(FilterPossibleValueDTO filterPossibleValueDTO) {
        log.debug("Request to save FilterPossibleValue : {}", filterPossibleValueDTO);
        FilterPossibleValue filterPossibleValue = filterPossibleValueMapper.toEntity(filterPossibleValueDTO);
        filterPossibleValue = filterPossibleValueRepository.save(filterPossibleValue);
        return filterPossibleValueMapper.toDto(filterPossibleValue);
    }

    @Override
    public FilterPossibleValueDTO update(FilterPossibleValueDTO filterPossibleValueDTO) {
        log.debug("Request to update FilterPossibleValue : {}", filterPossibleValueDTO);
        FilterPossibleValue filterPossibleValue = filterPossibleValueMapper.toEntity(filterPossibleValueDTO);
        filterPossibleValue = filterPossibleValueRepository.save(filterPossibleValue);
        return filterPossibleValueMapper.toDto(filterPossibleValue);
    }

    @Override
    public Optional<FilterPossibleValueDTO> partialUpdate(FilterPossibleValueDTO filterPossibleValueDTO) {
        log.debug("Request to partially update FilterPossibleValue : {}", filterPossibleValueDTO);

        return filterPossibleValueRepository
            .findById(filterPossibleValueDTO.getId())
            .map(existingFilterPossibleValue -> {
                filterPossibleValueMapper.partialUpdate(existingFilterPossibleValue, filterPossibleValueDTO);

                return existingFilterPossibleValue;
            })
            .map(filterPossibleValueRepository::save)
            .map(filterPossibleValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilterPossibleValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FilterPossibleValues");
        return filterPossibleValueRepository.findAll(pageable).map(filterPossibleValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilterPossibleValueDTO> findOne(Long id) {
        log.debug("Request to get FilterPossibleValue : {}", id);
        return filterPossibleValueRepository.findById(id).map(filterPossibleValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FilterPossibleValue : {}", id);
        filterPossibleValueRepository.deleteById(id);
    }
}
