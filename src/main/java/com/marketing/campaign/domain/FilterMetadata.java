package com.marketing.campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marketing.campaign.domain.enumeration.FilterType;
import com.marketing.campaign.domain.enumeration.FilterUiType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FilterMetadata.
 */
@Entity
@Table(name = "filter_metadata")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FilterMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "filter_type")
    private FilterType filterType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ui_type")
    private FilterUiType uiType;

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

    @JsonIgnoreProperties(value = { "filterMetadata" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private FilterSourceMapping mapping;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "filter", "segmentFilter" }, allowSetters = true)
    private Set<FilterPossibleValue> filterPossibleValues = new HashSet<>();

    @JsonIgnoreProperties(value = { "filter", "values", "segment" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "filter")
    private SegmentFilter segmentFilter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FilterMetadata id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FilterMetadata name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FilterType getFilterType() {
        return this.filterType;
    }

    public FilterMetadata filterType(FilterType filterType) {
        this.setFilterType(filterType);
        return this;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public FilterUiType getUiType() {
        return this.uiType;
    }

    public FilterMetadata uiType(FilterUiType uiType) {
        this.setUiType(uiType);
        return this;
    }

    public void setUiType(FilterUiType uiType) {
        this.uiType = uiType;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FilterMetadata isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public FilterMetadata createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public FilterMetadata createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public FilterMetadata updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public FilterMetadata updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public FilterSourceMapping getMapping() {
        return this.mapping;
    }

    public void setMapping(FilterSourceMapping filterSourceMapping) {
        this.mapping = filterSourceMapping;
    }

    public FilterMetadata mapping(FilterSourceMapping filterSourceMapping) {
        this.setMapping(filterSourceMapping);
        return this;
    }

    public Set<FilterPossibleValue> getFilterPossibleValues() {
        return this.filterPossibleValues;
    }

    public void setFilterPossibleValues(Set<FilterPossibleValue> filterPossibleValues) {
        if (this.filterPossibleValues != null) {
            this.filterPossibleValues.forEach(i -> i.setFilter(null));
        }
        if (filterPossibleValues != null) {
            filterPossibleValues.forEach(i -> i.setFilter(this));
        }
        this.filterPossibleValues = filterPossibleValues;
    }

    public FilterMetadata filterPossibleValues(Set<FilterPossibleValue> filterPossibleValues) {
        this.setFilterPossibleValues(filterPossibleValues);
        return this;
    }

    public FilterMetadata addFilterPossibleValue(FilterPossibleValue filterPossibleValue) {
        this.filterPossibleValues.add(filterPossibleValue);
        filterPossibleValue.setFilter(this);
        return this;
    }

    public FilterMetadata removeFilterPossibleValue(FilterPossibleValue filterPossibleValue) {
        this.filterPossibleValues.remove(filterPossibleValue);
        filterPossibleValue.setFilter(null);
        return this;
    }

    public SegmentFilter getSegmentFilter() {
        return this.segmentFilter;
    }

    public void setSegmentFilter(SegmentFilter segmentFilter) {
        if (this.segmentFilter != null) {
            this.segmentFilter.setFilter(null);
        }
        if (segmentFilter != null) {
            segmentFilter.setFilter(this);
        }
        this.segmentFilter = segmentFilter;
    }

    public FilterMetadata segmentFilter(SegmentFilter segmentFilter) {
        this.setSegmentFilter(segmentFilter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilterMetadata)) {
            return false;
        }
        return id != null && id.equals(((FilterMetadata) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilterMetadata{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", filterType='" + getFilterType() + "'" +
            ", uiType='" + getUiType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
