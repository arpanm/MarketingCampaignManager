package com.marketing.campaign.service.dto;

import com.marketing.campaign.domain.enumeration.StatusType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.marketing.campaign.domain.ApprovalStatus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalStatusDTO implements Serializable {

    private Long id;

    private StatusType statusType;

    private Boolean isActive;

    private Long createdBy;

    private LocalDate createdOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private ApprovalAuthorityDTO approvedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ApprovalAuthorityDTO getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(ApprovalAuthorityDTO approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalStatusDTO)) {
            return false;
        }

        ApprovalStatusDTO approvalStatusDTO = (ApprovalStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalStatusDTO{" +
            "id=" + getId() +
            ", statusType='" + getStatusType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvedBy=" + getApprovedBy() +
            "}";
    }
}
