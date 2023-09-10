package com.marketing.campaign.repository;

import com.marketing.campaign.domain.FilterMetadata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FilterMetadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterMetadataRepository extends JpaRepository<FilterMetadata, Long> {}
