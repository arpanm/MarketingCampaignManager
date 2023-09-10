package com.marketing.campaign.repository;

import com.marketing.campaign.domain.FilterSourceMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FilterSourceMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterSourceMappingRepository extends JpaRepository<FilterSourceMapping, Long> {}
