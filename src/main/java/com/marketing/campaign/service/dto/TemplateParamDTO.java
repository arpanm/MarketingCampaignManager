package com.marketing.campaign.service.dto;

import com.marketing.campaign.domain.enumeration.ParamType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.marketing.campaign.domain.TemplateParam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateParamDTO implements Serializable {

    private Long id;

    private String tag;

    private ParamType paramType;

    private String replacedByAtrribute;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ParamType getParamType() {
        return paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public String getReplacedByAtrribute() {
        return replacedByAtrribute;
    }

    public void setReplacedByAtrribute(String replacedByAtrribute) {
        this.replacedByAtrribute = replacedByAtrribute;
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
        if (!(o instanceof TemplateParamDTO)) {
            return false;
        }

        TemplateParamDTO templateParamDTO = (TemplateParamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateParamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateParamDTO{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            ", paramType='" + getParamType() + "'" +
            ", replacedByAtrribute='" + getReplacedByAtrribute() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
