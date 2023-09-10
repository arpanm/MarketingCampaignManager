package com.marketing.campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FilterPossibleValue.
 */
@Entity
@Table(name = "filter_possible_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FilterPossibleValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ui_name")
    private String uiName;

    @Column(name = "attribute_value")
    private String attributeValue;

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
    @JsonIgnoreProperties(value = { "mapping", "filterPossibleValues", "segmentFilter" }, allowSetters = true)
    private FilterMetadata filter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filter", "values", "segment" }, allowSetters = true)
    private SegmentFilter segmentFilter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FilterPossibleValue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUiName() {
        return this.uiName;
    }

    public FilterPossibleValue uiName(String uiName) {
        this.setUiName(uiName);
        return this;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public FilterPossibleValue attributeValue(String attributeValue) {
        this.setAttributeValue(attributeValue);
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FilterPossibleValue isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public FilterPossibleValue createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public FilterPossibleValue createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public FilterPossibleValue updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public FilterPossibleValue updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public FilterMetadata getFilter() {
        return this.filter;
    }

    public void setFilter(FilterMetadata filterMetadata) {
        this.filter = filterMetadata;
    }

    public FilterPossibleValue filter(FilterMetadata filterMetadata) {
        this.setFilter(filterMetadata);
        return this;
    }

    public SegmentFilter getSegmentFilter() {
        return this.segmentFilter;
    }

    public void setSegmentFilter(SegmentFilter segmentFilter) {
        this.segmentFilter = segmentFilter;
    }

    public FilterPossibleValue segmentFilter(SegmentFilter segmentFilter) {
        this.setSegmentFilter(segmentFilter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilterPossibleValue)) {
            return false;
        }
        return id != null && id.equals(((FilterPossibleValue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilterPossibleValue{" +
            "id=" + getId() +
            ", uiName='" + getUiName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
