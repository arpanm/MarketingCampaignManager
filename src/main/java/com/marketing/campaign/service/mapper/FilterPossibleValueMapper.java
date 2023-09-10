package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.FilterMetadata;
import com.marketing.campaign.domain.FilterPossibleValue;
import com.marketing.campaign.domain.SegmentFilter;
import com.marketing.campaign.service.dto.FilterMetadataDTO;
import com.marketing.campaign.service.dto.FilterPossibleValueDTO;
import com.marketing.campaign.service.dto.SegmentFilterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FilterPossibleValue} and its DTO {@link FilterPossibleValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface FilterPossibleValueMapper extends EntityMapper<FilterPossibleValueDTO, FilterPossibleValue> {
    @Mapping(target = "filter", source = "filter", qualifiedByName = "filterMetadataId")
    @Mapping(target = "segmentFilter", source = "segmentFilter", qualifiedByName = "segmentFilterId")
    FilterPossibleValueDTO toDto(FilterPossibleValue s);

    @Named("filterMetadataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FilterMetadataDTO toDtoFilterMetadataId(FilterMetadata filterMetadata);

    @Named("segmentFilterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SegmentFilterDTO toDtoSegmentFilterId(SegmentFilter segmentFilter);
}
