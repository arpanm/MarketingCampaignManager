package com.marketing.campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marketing.campaign.domain.enumeration.StatusType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApprovalStatus.
 */
@Entity
@Table(name = "approval_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_type")
    private StatusType statusType;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApprovalAuthority approvedBy;

    @JsonIgnoreProperties(value = { "approvalStatus", "events", "segment", "template" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "approvalStatus")
    private Campaign campaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApprovalStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatusType() {
        return this.statusType;
    }

    public ApprovalStatus statusType(StatusType statusType) {
        this.setStatusType(statusType);
        return this;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ApprovalStatus isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public ApprovalStatus createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public ApprovalStatus createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public ApprovalStatus updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public ApprovalStatus updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ApprovalAuthority getApprovedBy() {
        return this.approvedBy;
    }

    public void setApprovedBy(ApprovalAuthority approvalAuthority) {
        this.approvedBy = approvalAuthority;
    }

    public ApprovalStatus approvedBy(ApprovalAuthority approvalAuthority) {
        this.setApprovedBy(approvalAuthority);
        return this;
    }

    public Campaign getCampaign() {
        return this.campaign;
    }

    public void setCampaign(Campaign campaign) {
        if (this.campaign != null) {
            this.campaign.setApprovalStatus(null);
        }
        if (campaign != null) {
            campaign.setApprovalStatus(this);
        }
        this.campaign = campaign;
    }

    public ApprovalStatus campaign(Campaign campaign) {
        this.setCampaign(campaign);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalStatus)) {
            return false;
        }
        return id != null && id.equals(((ApprovalStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalStatus{" +
            "id=" + getId() +
            ", statusType='" + getStatusType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
