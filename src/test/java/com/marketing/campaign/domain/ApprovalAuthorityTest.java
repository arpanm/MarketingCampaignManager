package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalAuthorityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalAuthority.class);
        ApprovalAuthority approvalAuthority1 = new ApprovalAuthority();
        approvalAuthority1.setId(1L);
        ApprovalAuthority approvalAuthority2 = new ApprovalAuthority();
        approvalAuthority2.setId(approvalAuthority1.getId());
        assertThat(approvalAuthority1).isEqualTo(approvalAuthority2);
        approvalAuthority2.setId(2L);
        assertThat(approvalAuthority1).isNotEqualTo(approvalAuthority2);
        approvalAuthority1.setId(null);
        assertThat(approvalAuthority1).isNotEqualTo(approvalAuthority2);
    }
}
