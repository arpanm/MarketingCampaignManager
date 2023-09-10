package com.marketing.campaign.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentFilterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentFilterDTO.class);
        SegmentFilterDTO segmentFilterDTO1 = new SegmentFilterDTO();
        segmentFilterDTO1.setId(1L);
        SegmentFilterDTO segmentFilterDTO2 = new SegmentFilterDTO();
        assertThat(segmentFilterDTO1).isNotEqualTo(segmentFilterDTO2);
        segmentFilterDTO2.setId(segmentFilterDTO1.getId());
        assertThat(segmentFilterDTO1).isEqualTo(segmentFilterDTO2);
        segmentFilterDTO2.setId(2L);
        assertThat(segmentFilterDTO1).isNotEqualTo(segmentFilterDTO2);
        segmentFilterDTO1.setId(null);
        assertThat(segmentFilterDTO1).isNotEqualTo(segmentFilterDTO2);
    }
}
