package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FilterPossibleValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterPossibleValue.class);
        FilterPossibleValue filterPossibleValue1 = new FilterPossibleValue();
        filterPossibleValue1.setId(1L);
        FilterPossibleValue filterPossibleValue2 = new FilterPossibleValue();
        filterPossibleValue2.setId(filterPossibleValue1.getId());
        assertThat(filterPossibleValue1).isEqualTo(filterPossibleValue2);
        filterPossibleValue2.setId(2L);
        assertThat(filterPossibleValue1).isNotEqualTo(filterPossibleValue2);
        filterPossibleValue1.setId(null);
        assertThat(filterPossibleValue1).isNotEqualTo(filterPossibleValue2);
    }
}
