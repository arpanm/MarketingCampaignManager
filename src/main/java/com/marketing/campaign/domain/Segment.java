package com.marketing.campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Segment.
 */
@Entity
@Table(name = "segment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Segment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "source_table")
    private String sourceTable;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "segment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "filter", "values", "segment" }, allowSetters = true)
    private Set<SegmentFilter> segmentFilters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Segment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Segment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceTable() {
        return this.sourceTable;
    }

    public Segment sourceTable(String sourceTable) {
        this.setSourceTable(sourceTable);
        return this;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Segment isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Segment createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public Segment createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Segment updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Segment updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<SegmentFilter> getSegmentFilters() {
        return this.segmentFilters;
    }

    public void setSegmentFilters(Set<SegmentFilter> segmentFilters) {
        if (this.segmentFilters != null) {
            this.segmentFilters.forEach(i -> i.setSegment(null));
        }
        if (segmentFilters != null) {
            segmentFilters.forEach(i -> i.setSegment(this));
        }
        this.segmentFilters = segmentFilters;
    }

    public Segment segmentFilters(Set<SegmentFilter> segmentFilters) {
        this.setSegmentFilters(segmentFilters);
        return this;
    }

    public Segment addSegmentFilter(SegmentFilter segmentFilter) {
        this.segmentFilters.add(segmentFilter);
        segmentFilter.setSegment(this);
        return this;
    }

    public Segment removeSegmentFilter(SegmentFilter segmentFilter) {
        this.segmentFilters.remove(segmentFilter);
        segmentFilter.setSegment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Segment)) {
            return false;
        }
        return id != null && id.equals(((Segment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Segment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sourceTable='" + getSourceTable() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
