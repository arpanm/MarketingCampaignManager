package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentFilterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentFilter.class);
        SegmentFilter segmentFilter1 = new SegmentFilter();
        segmentFilter1.setId(1L);
        SegmentFilter segmentFilter2 = new SegmentFilter();
        segmentFilter2.setId(segmentFilter1.getId());
        assertThat(segmentFilter1).isEqualTo(segmentFilter2);
        segmentFilter2.setId(2L);
        assertThat(segmentFilter1).isNotEqualTo(segmentFilter2);
        segmentFilter1.setId(null);
        assertThat(segmentFilter1).isNotEqualTo(segmentFilter2);
    }
}
