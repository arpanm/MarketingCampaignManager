package com.marketing.campaign.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalStatusDTO.class);
        ApprovalStatusDTO approvalStatusDTO1 = new ApprovalStatusDTO();
        approvalStatusDTO1.setId(1L);
        ApprovalStatusDTO approvalStatusDTO2 = new ApprovalStatusDTO();
        assertThat(approvalStatusDTO1).isNotEqualTo(approvalStatusDTO2);
        approvalStatusDTO2.setId(approvalStatusDTO1.getId());
        assertThat(approvalStatusDTO1).isEqualTo(approvalStatusDTO2);
        approvalStatusDTO2.setId(2L);
        assertThat(approvalStatusDTO1).isNotEqualTo(approvalStatusDTO2);
        approvalStatusDTO1.setId(null);
        assertThat(approvalStatusDTO1).isNotEqualTo(approvalStatusDTO2);
    }
}
