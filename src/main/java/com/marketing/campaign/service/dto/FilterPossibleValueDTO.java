package com.marketing.campaign.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.marketing.campaign.domain.FilterPossibleValue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FilterPossibleValueDTO implements Serializable {

    private Long id;

    private String uiName;

    private String attributeValue;

    private Boolean isActive;

    private Long createdBy;

    private LocalDate createdOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private FilterMetadataDTO filter;

    private SegmentFilterDTO segmentFilter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
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

    public FilterMetadataDTO getFilter() {
        return filter;
    }

    public void setFilter(FilterMetadataDTO filter) {
        this.filter = filter;
    }

    public SegmentFilterDTO getSegmentFilter() {
        return segmentFilter;
    }

    public void setSegmentFilter(SegmentFilterDTO segmentFilter) {
        this.segmentFilter = segmentFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilterPossibleValueDTO)) {
            return false;
        }

        FilterPossibleValueDTO filterPossibleValueDTO = (FilterPossibleValueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, filterPossibleValueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilterPossibleValueDTO{" +
            "id=" + getId() +
            ", uiName='" + getUiName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", filter=" + getFilter() +
            ", segmentFilter=" + getSegmentFilter() +
            "}";
    }
}
