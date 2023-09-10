package com.marketing.campaign.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalAuthorityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalAuthorityDTO.class);
        ApprovalAuthorityDTO approvalAuthorityDTO1 = new ApprovalAuthorityDTO();
        approvalAuthorityDTO1.setId(1L);
        ApprovalAuthorityDTO approvalAuthorityDTO2 = new ApprovalAuthorityDTO();
        assertThat(approvalAuthorityDTO1).isNotEqualTo(approvalAuthorityDTO2);
        approvalAuthorityDTO2.setId(approvalAuthorityDTO1.getId());
        assertThat(approvalAuthorityDTO1).isEqualTo(approvalAuthorityDTO2);
        approvalAuthorityDTO2.setId(2L);
        assertThat(approvalAuthorityDTO1).isNotEqualTo(approvalAuthorityDTO2);
        approvalAuthorityDTO1.setId(null);
        assertThat(approvalAuthorityDTO1).isNotEqualTo(approvalAuthorityDTO2);
    }
}
