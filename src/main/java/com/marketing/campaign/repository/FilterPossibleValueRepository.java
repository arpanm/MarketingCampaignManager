package com.marketing.campaign.repository;

import com.marketing.campaign.domain.FilterPossibleValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FilterPossibleValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterPossibleValueRepository extends JpaRepository<FilterPossibleValue, Long> {}
