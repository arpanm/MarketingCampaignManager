package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.ApprovalAuthority;
import com.marketing.campaign.service.dto.ApprovalAuthorityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApprovalAuthority} and its DTO {@link ApprovalAuthorityDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalAuthorityMapper extends EntityMapper<ApprovalAuthorityDTO, ApprovalAuthority> {}
