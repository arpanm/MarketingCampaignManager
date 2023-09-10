package com.marketing.campaign.service.dto;

import com.marketing.campaign.domain.enumeration.VerticalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.marketing.campaign.domain.ApprovalAuthority} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalAuthorityDTO implements Serializable {

    private Long id;

    private Long userId;

    private VerticalType vertial;

    private Boolean isActive;

    private Long createdBy;

    private LocalDate createdOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public VerticalType getVertial() {
        return vertial;
    }

    public void setVertial(VerticalType vertial) {
        this.vertial = vertial;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalAuthorityDTO)) {
            return false;
        }

        ApprovalAuthorityDTO approvalAuthorityDTO = (ApprovalAuthorityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalAuthorityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalAuthorityDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", vertial='" + getVertial() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
