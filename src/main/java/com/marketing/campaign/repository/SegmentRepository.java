package com.marketing.campaign.repository;

import com.marketing.campaign.domain.Segment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Segment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {}
