package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalStatus.class);
        ApprovalStatus approvalStatus1 = new ApprovalStatus();
        approvalStatus1.setId(1L);
        ApprovalStatus approvalStatus2 = new ApprovalStatus();
        approvalStatus2.setId(approvalStatus1.getId());
        assertThat(approvalStatus1).isEqualTo(approvalStatus2);
        approvalStatus2.setId(2L);
        assertThat(approvalStatus1).isNotEqualTo(approvalStatus2);
        approvalStatus1.setId(null);
        assertThat(approvalStatus1).isNotEqualTo(approvalStatus2);
    }
}
