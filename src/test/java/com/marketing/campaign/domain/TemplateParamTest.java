package com.marketing.campaign.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.marketing.campaign.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateParamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateParam.class);
        TemplateParam templateParam1 = new TemplateParam();
        templateParam1.setId(1L);
        TemplateParam templateParam2 = new TemplateParam();
        templateParam2.setId(templateParam1.getId());
        assertThat(templateParam1).isEqualTo(templateParam2);
        templateParam2.setId(2L);
        assertThat(templateParam1).isNotEqualTo(templateParam2);
        templateParam1.setId(null);
        assertThat(templateParam1).isNotEqualTo(templateParam2);
    }
}
