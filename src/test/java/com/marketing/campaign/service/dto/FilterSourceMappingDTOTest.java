package com.marketing.campaign.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FilterSourceMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterSourceMappingDTO.class);
        FilterSourceMappingDTO filterSourceMappingDTO1 = new FilterSourceMappingDTO();
        filterSourceMappingDTO1.setId(1L);
        FilterSourceMappingDTO filterSourceMappingDTO2 = new FilterSourceMappingDTO();
        assertThat(filterSourceMappingDTO1).isNotEqualTo(filterSourceMappingDTO2);
        filterSourceMappingDTO2.setId(filterSourceMappingDTO1.getId());
        assertThat(filterSourceMappingDTO1).isEqualTo(filterSourceMappingDTO2);
        filterSourceMappingDTO2.setId(2L);
        assertThat(filterSourceMappingDTO1).isNotEqualTo(filterSourceMappingDTO2);
        filterSourceMappingDTO1.setId(null);
        assertThat(filterSourceMappingDTO1).isNotEqualTo(filterSourceMappingDTO2);
    }
}
