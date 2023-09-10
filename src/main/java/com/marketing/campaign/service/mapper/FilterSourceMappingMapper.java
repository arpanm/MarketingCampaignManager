package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.FilterSourceMapping;
import com.marketing.campaign.service.dto.FilterSourceMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FilterSourceMapping} and its DTO {@link FilterSourceMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface FilterSourceMappingMapper extends EntityMapper<FilterSourceMappingDTO, FilterSourceMapping> {}
