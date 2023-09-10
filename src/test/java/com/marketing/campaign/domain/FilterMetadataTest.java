package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FilterMetadataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterMetadata.class);
        FilterMetadata filterMetadata1 = new FilterMetadata();
        filterMetadata1.setId(1L);
        FilterMetadata filterMetadata2 = new FilterMetadata();
        filterMetadata2.setId(filterMetadata1.getId());
        assertThat(filterMetadata1).isEqualTo(filterMetadata2);
        filterMetadata2.setId(2L);
        assertThat(filterMetadata1).isNotEqualTo(filterMetadata2);
        filterMetadata1.setId(null);
        assertThat(filterMetadata1).isNotEqualTo(filterMetadata2);
    }
}
