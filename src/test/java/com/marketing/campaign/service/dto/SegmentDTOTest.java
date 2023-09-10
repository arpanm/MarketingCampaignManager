package com.marketing.campaign.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentDTO.class);
        SegmentDTO segmentDTO1 = new SegmentDTO();
        segmentDTO1.setId(1L);
        SegmentDTO segmentDTO2 = new SegmentDTO();
        assertThat(segmentDTO1).isNotEqualTo(segmentDTO2);
        segmentDTO2.setId(segmentDTO1.getId());
        assertThat(segmentDTO1).isEqualTo(segmentDTO2);
        segmentDTO2.setId(2L);
        assertThat(segmentDTO1).isNotEqualTo(segmentDTO2);
        segmentDTO1.setId(null);
        assertThat(segmentDTO1).isNotEqualTo(segmentDTO2);
    }
}
