package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.ApprovalAuthority;
import com.marketing.campaign.domain.ApprovalStatus;
import com.marketing.campaign.service.dto.ApprovalAuthorityDTO;
import com.marketing.campaign.service.dto.ApprovalStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApprovalStatus} and its DTO {@link ApprovalStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalStatusMapper extends EntityMapper<ApprovalStatusDTO, ApprovalStatus> {
    @Mapping(target = "approvedBy", source = "approvedBy", qualifiedByName = "approvalAuthorityId")
    ApprovalStatusDTO toDto(ApprovalStatus s);

    @Named("approvalAuthorityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApprovalAuthorityDTO toDtoApprovalAuthorityId(ApprovalAuthority approvalAuthority);
}
