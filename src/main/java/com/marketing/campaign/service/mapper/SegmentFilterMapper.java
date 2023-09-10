package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.FilterMetadata;
import com.marketing.campaign.domain.Segment;
import com.marketing.campaign.domain.SegmentFilter;
import com.marketing.campaign.service.dto.FilterMetadataDTO;
import com.marketing.campaign.service.dto.SegmentDTO;
import com.marketing.campaign.service.dto.SegmentFilterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SegmentFilter} and its DTO {@link SegmentFilterDTO}.
 */
@Mapper(componentModel = "spring")
public interface SegmentFilterMapper extends EntityMapper<SegmentFilterDTO, SegmentFilter> {
    @Mapping(target = "filter", source = "filter", qualifiedByName = "filterMetadataId")
    @Mapping(target = "segment", source = "segment", qualifiedByName = "segmentId")
    SegmentFilterDTO toDto(SegmentFilter s);

    @Named("filterMetadataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FilterMetadataDTO toDtoFilterMetadataId(FilterMetadata filterMetadata);

    @Named("segmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SegmentDTO toDtoSegmentId(Segment segment);
}
