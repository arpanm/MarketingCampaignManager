package com.marketing.campaign.service.mapper;

import com.marketing.campaign.domain.Campaign;
import com.marketing.campaign.domain.Events;
import com.marketing.campaign.service.dto.CampaignDTO;
import com.marketing.campaign.service.dto.EventsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Events} and its DTO {@link EventsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventsMapper extends EntityMapper<EventsDTO, Events> {
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "campaignId")
    EventsDTO toDto(Events s);

    @Named("campaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoCampaignId(Campaign campaign);
}
