package com.marketing.campaign.repository;

import com.marketing.campaign.domain.TemplateParam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TemplateParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateParamRepository extends JpaRepository<TemplateParam, Long> {}
