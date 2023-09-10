package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.Template;
import com.marketing.campaign.domain.TemplateParam;
import com.marketing.campaign.service.dto.TemplateDTO;
import com.marketing.campaign.service.dto.TemplateParamDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Template} and its DTO {@link TemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {
    @Mapping(target = "templateParams", source = "templateParams", qualifiedByName = "templateParamIdSet")
    TemplateDTO toDto(Template s);

    @Mapping(target = "removeTemplateParam", ignore = true)
    Template toEntity(TemplateDTO templateDTO);

    @Named("templateParamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TemplateParamDTO toDtoTemplateParamId(TemplateParam templateParam);

    @Named("templateParamIdSet")
    default Set<TemplateParamDTO> toDtoTemplateParamIdSet(Set<TemplateParam> templateParam) {
        return templateParam.stream().map(this::toDtoTemplateParamId).collect(Collectors.toSet());
    }
}
