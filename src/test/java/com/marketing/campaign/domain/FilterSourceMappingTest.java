package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FilterSourceMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterSourceMapping.class);
        FilterSourceMapping filterSourceMapping1 = new FilterSourceMapping();
        filterSourceMapping1.setId(1L);
        FilterSourceMapping filterSourceMapping2 = new FilterSourceMapping();
        filterSourceMapping2.setId(filterSourceMapping1.getId());
        assertThat(filterSourceMapping1).isEqualTo(filterSourceMapping2);
        filterSourceMapping2.setId(2L);
        assertThat(filterSourceMapping1).isNotEqualTo(filterSourceMapping2);
        filterSourceMapping1.setId(null);
        assertThat(filterSourceMapping1).isNotEqualTo(filterSourceMapping2);
    }
}
