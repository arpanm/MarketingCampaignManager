package com.marketing.campaign.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FilterMetadataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterMetadataDTO.class);
        FilterMetadataDTO filterMetadataDTO1 = new FilterMetadataDTO();
        filterMetadataDTO1.setId(1L);
        FilterMetadataDTO filterMetadataDTO2 = new FilterMetadataDTO();
        assertThat(filterMetadataDTO1).isNotEqualTo(filterMetadataDTO2);
        filterMetadataDTO2.setId(filterMetadataDTO1.getId());
        assertThat(filterMetadataDTO1).isEqualTo(filterMetadataDTO2);
        filterMetadataDTO2.setId(2L);
        assertThat(filterMetadataDTO1).isNotEqualTo(filterMetadataDTO2);
        filterMetadataDTO1.setId(null);
        assertThat(filterMetadataDTO1).isNotEqualTo(filterMetadataDTO2);
    }
}
