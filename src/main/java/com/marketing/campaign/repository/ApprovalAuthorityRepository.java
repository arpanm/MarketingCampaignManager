package com.marketing.campaign.repository;

import com.marketing.campaign.domain.ApprovalAuthority;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApprovalAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalAuthorityRepository extends JpaRepository<ApprovalAuthority, Long> {}
