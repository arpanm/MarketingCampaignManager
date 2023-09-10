package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.FilterMetadata;
import com.marketing.campaign.domain.FilterSourceMapping;
import com.marketing.campaign.service.dto.FilterMetadataDTO;
import com.marketing.campaign.service.dto.FilterSourceMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FilterMetadata} and its DTO {@link FilterMetadataDTO}.
 */
@Mapper(componentModel = "spring")
public interface FilterMetadataMapper extends EntityMapper<FilterMetadataDTO, FilterMetadata> {
    @Mapping(target = "mapping", source = "mapping", qualifiedByName = "filterSourceMappingId")
    FilterMetadataDTO toDto(FilterMetadata s);

    @Named("filterSourceMappingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FilterSourceMappingDTO toDtoFilterSourceMappingId(FilterSourceMapping filterSourceMapping);
}
