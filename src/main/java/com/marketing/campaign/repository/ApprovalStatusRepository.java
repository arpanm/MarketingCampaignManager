package com.marketing.campaign.repository;

import com.marketing.campaign.domain.ApprovalStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApprovalStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Long> {}
