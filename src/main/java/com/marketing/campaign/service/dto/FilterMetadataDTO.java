package com.marketing.campaign.service.dto;

import com.marketing.campaign.domain.enumeration.FilterType;
import com.marketing.campaign.domain.enumeration.FilterUiType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.marketing.campaign.domain.FilterMetadata} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FilterMetadataDTO implements Serializable {

    private Long id;

    private String name;

    private FilterType filterType;

    private FilterUiType uiType;

    private Boolean isActive;

    private Long createdBy;

    private LocalDate createdOn;

    private Long updatedBy;

    private LocalDate updatedOn;

    private FilterSourceMappingDTO mapping;

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

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public FilterUiType getUiType() {
        return uiType;
    }

    public void setUiType(FilterUiType uiType) {
        this.uiType = uiType;
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

    public FilterSourceMappingDTO getMapping() {
        return mapping;
    }

    public void setMapping(FilterSourceMappingDTO mapping) {
        this.mapping = mapping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilterMetadataDTO)) {
            return false;
        }

        FilterMetadataDTO filterMetadataDTO = (FilterMetadataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, filterMetadataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilterMetadataDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", filterType='" + getFilterType() + "'" +
            ", uiType='" + getUiType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", mapping=" + getMapping() +
            "}";
    }
}
