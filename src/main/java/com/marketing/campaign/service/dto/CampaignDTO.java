package com.marketing.campaign.service.dto;

import com.marketing.campaign.domain.enumeration.ChannelType;
import com.marketing.campaign.domain.enumeration.ScheduleType;
import com.marketing.campaign.domain.enumeration.VerticalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.marketing.campaign.domain.Campaign} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CampaignDTO implements Serializable {

    private Long id;

    private String name;

    private VerticalType vertial;

    private ChannelType channel;

    private ScheduleType schedule;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive;

    private Long createdBy;

    private LocalDate createdOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private ApprovalStatusDTO approvalStatus;

    private SegmentDTO segment;

    private TemplateDTO template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VerticalType getVertial() {
        return vertial;
    }

    public void setVertial(VerticalType vertial) {
        this.vertial = vertial;
    }

    public ChannelType getChannel() {
        return channel;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }

    public ScheduleType getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleType schedule) {
        this.schedule = schedule;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public ApprovalStatusDTO getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatusDTO approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public SegmentDTO getSegment() {
        return segment;
    }

    public void setSegment(SegmentDTO segment) {
        this.segment = segment;
    }

    public TemplateDTO getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDTO template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignDTO)) {
            return false;
        }

        CampaignDTO campaignDTO = (CampaignDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, campaignDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vertial='" + getVertial() + "'" +
            ", channel='" + getChannel() + "'" +
            ", schedule='" + getSchedule() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", approvalStatus=" + getApprovalStatus() +
            ", segment=" + getSegment() +
            ", template=" + getTemplate() +
            "}";
    }
}
