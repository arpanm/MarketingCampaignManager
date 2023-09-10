package com.marketing.campaign.repository;

import com.marketing.campaign.domain.SegmentFilter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SegmentFilter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentFilterRepository extends JpaRepository<SegmentFilter, Long> {}
