package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.ApprovalStatus;
import com.marketing.campaign.domain.Campaign;
import com.marketing.campaign.domain.Segment;
import com.marketing.campaign.domain.Template;
import com.marketing.campaign.service.dto.ApprovalStatusDTO;
import com.marketing.campaign.service.dto.CampaignDTO;
import com.marketing.campaign.service.dto.SegmentDTO;
import com.marketing.campaign.service.dto.TemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campaign} and its DTO {@link CampaignDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {
    @Mapping(target = "approvalStatus", source = "approvalStatus", qualifiedByName = "approvalStatusId")
    @Mapping(target = "segment", source = "segment", qualifiedByName = "segmentId")
    @Mapping(target = "template", source = "template", qualifiedByName = "templateId")
    CampaignDTO toDto(Campaign s);

    @Named("approvalStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApprovalStatusDTO toDtoApprovalStatusId(ApprovalStatus approvalStatus);

    @Named("segmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SegmentDTO toDtoSegmentId(Segment segment);

    @Named("templateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TemplateDTO toDtoTemplateId(Template template);
}
