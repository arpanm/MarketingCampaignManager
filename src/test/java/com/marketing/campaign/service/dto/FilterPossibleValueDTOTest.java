package com.marketing.campaign.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FilterPossibleValueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterPossibleValueDTO.class);
        FilterPossibleValueDTO filterPossibleValueDTO1 = new FilterPossibleValueDTO();
        filterPossibleValueDTO1.setId(1L);
        FilterPossibleValueDTO filterPossibleValueDTO2 = new FilterPossibleValueDTO();
        assertThat(filterPossibleValueDTO1).isNotEqualTo(filterPossibleValueDTO2);
        filterPossibleValueDTO2.setId(filterPossibleValueDTO1.getId());
        assertThat(filterPossibleValueDTO1).isEqualTo(filterPossibleValueDTO2);
        filterPossibleValueDTO2.setId(2L);
        assertThat(filterPossibleValueDTO1).isNotEqualTo(filterPossibleValueDTO2);
        filterPossibleValueDTO1.setId(null);
        assertThat(filterPossibleValueDTO1).isNotEqualTo(filterPossibleValueDTO2);
    }
}
