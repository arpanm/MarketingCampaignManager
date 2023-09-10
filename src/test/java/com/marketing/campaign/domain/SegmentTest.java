package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Segment.class);
        Segment segment1 = new Segment();
        segment1.setId(1L);
        Segment segment2 = new Segment();
        segment2.setId(segment1.getId());
        assertThat(segment1).isEqualTo(segment2);
        segment2.setId(2L);
        assertThat(segment1).isNotEqualTo(segment2);
        segment1.setId(null);
        assertThat(segment1).isNotEqualTo(segment2);
    }
}
