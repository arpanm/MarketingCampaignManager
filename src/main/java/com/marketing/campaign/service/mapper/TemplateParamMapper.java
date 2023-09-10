package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.TemplateParam;
import com.marketing.campaign.service.dto.TemplateParamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TemplateParam} and its DTO {@link TemplateParamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateParamMapper extends EntityMapper<TemplateParamDTO, TemplateParam> {}
