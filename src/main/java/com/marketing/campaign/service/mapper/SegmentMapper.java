package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.Segment;
import com.marketing.campaign.service.dto.SegmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Segment} and its DTO {@link SegmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface SegmentMapper extends EntityMapper<SegmentDTO, Segment> {}
