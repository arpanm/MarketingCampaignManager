package com.marketing.campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marketing.campaign.domain.enumeration.ParamType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TemplateParam.
 */
@Entity
@Table(name = "template_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag")
    private String tag;

    @Enumerated(EnumType.STRING)
    @Column(name = "param_type")
    private ParamType paramType;

    @Column(name = "replaced_by_atrribute")
    private String replacedByAtrribute;

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "templateParams")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "templateParams" }, allowSetters = true)
    private Set<Template> templates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TemplateParam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return this.tag;
    }

    public TemplateParam tag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ParamType getParamType() {
        return this.paramType;
    }

    public TemplateParam paramType(ParamType paramType) {
        this.setParamType(paramType);
        return this;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public String getReplacedByAtrribute() {
        return this.replacedByAtrribute;
    }

    public TemplateParam replacedByAtrribute(String replacedByAtrribute) {
        this.setReplacedByAtrribute(replacedByAtrribute);
        return this;
    }

    public void setReplacedByAtrribute(String replacedByAtrribute) {
        this.replacedByAtrribute = replacedByAtrribute;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public TemplateParam isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public TemplateParam createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public TemplateParam createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public TemplateParam updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public TemplateParam updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Template> getTemplates() {
        return this.templates;
    }

    public void setTemplates(Set<Template> templates) {
        if (this.templates != null) {
            this.templates.forEach(i -> i.removeTemplateParam(this));
        }
        if (templates != null) {
            templates.forEach(i -> i.addTemplateParam(this));
        }
        this.templates = templates;
    }

    public TemplateParam templates(Set<Template> templates) {
        this.setTemplates(templates);
        return this;
    }

    public TemplateParam addTemplate(Template template) {
        this.templates.add(template);
        template.getTemplateParams().add(this);
        return this;
    }

    public TemplateParam removeTemplate(Template template) {
        this.templates.remove(template);
        template.getTemplateParams().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateParam)) {
            return false;
        }
        return id != null && id.equals(((TemplateParam) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateParam{" +
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
