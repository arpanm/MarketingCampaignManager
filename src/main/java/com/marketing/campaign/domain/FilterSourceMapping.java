package com.marketing.campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FilterSourceMapping.
 */
@Entity
@Table(name = "filter_source_mapping")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FilterSourceMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "source_table")
    private String sourceTable;

    @Column(name = "atrribute_mapping")
    private String atrributeMapping;

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

    @JsonIgnoreProperties(value = { "mapping", "filterPossibleValues", "segmentFilter" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mapping")
    private FilterMetadata filterMetadata;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FilterSourceMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceTable() {
        return this.sourceTable;
    }

    public FilterSourceMapping sourceTable(String sourceTable) {
        this.setSourceTable(sourceTable);
        return this;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public String getAtrributeMapping() {
        return this.atrributeMapping;
    }

    public FilterSourceMapping atrributeMapping(String atrributeMapping) {
        this.setAtrributeMapping(atrributeMapping);
        return this;
    }

    public void setAtrributeMapping(String atrributeMapping) {
        this.atrributeMapping = atrributeMapping;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FilterSourceMapping isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public FilterSourceMapping createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public FilterSourceMapping createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public FilterSourceMapping updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public FilterSourceMapping updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public FilterMetadata getFilterMetadata() {
        return this.filterMetadata;
    }

    public void setFilterMetadata(FilterMetadata filterMetadata) {
        if (this.filterMetadata != null) {
            this.filterMetadata.setMapping(null);
        }
        if (filterMetadata != null) {
            filterMetadata.setMapping(this);
        }
        this.filterMetadata = filterMetadata;
    }

    public FilterSourceMapping filterMetadata(FilterMetadata filterMetadata) {
        this.setFilterMetadata(filterMetadata);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilterSourceMapping)) {
            return false;
        }
        return id != null && id.equals(((FilterSourceMapping) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilterSourceMapping{" +
            "id=" + getId() +
            ", sourceTable='" + getSourceTable() + "'" +
            ", atrributeMapping='" + getAtrributeMapping() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
