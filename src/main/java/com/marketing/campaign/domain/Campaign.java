package com.marketing.campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marketing.campaign.domain.enumeration.ChannelType;
import com.marketing.campaign.domain.enumeration.ScheduleType;
import com.marketing.campaign.domain.enumeration.VerticalType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "vertial")
    private VerticalType vertial;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private ChannelType channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule")
    private ScheduleType schedule;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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

    @JsonIgnoreProperties(value = { "approvedBy", "campaign" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private ApprovalStatus approvalStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaign")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "campaign" }, allowSetters = true)
    private Set<Events> events = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "segmentFilters" }, allowSetters = true)
    private Segment segment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "templateParams" }, allowSetters = true)
    private Template template;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Campaign id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Campaign name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VerticalType getVertial() {
        return this.vertial;
    }

    public Campaign vertial(VerticalType vertial) {
        this.setVertial(vertial);
        return this;
    }

    public void setVertial(VerticalType vertial) {
        this.vertial = vertial;
    }

    public ChannelType getChannel() {
        return this.channel;
    }

    public Campaign channel(ChannelType channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }

    public ScheduleType getSchedule() {
        return this.schedule;
    }

    public Campaign schedule(ScheduleType schedule) {
        this.setSchedule(schedule);
        return this;
    }

    public void setSchedule(ScheduleType schedule) {
        this.schedule = schedule;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Campaign startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Campaign endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Campaign isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Campaign createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public Campaign createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Campaign updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Campaign updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ApprovalStatus getApprovalStatus() {
        return this.approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Campaign approvalStatus(ApprovalStatus approvalStatus) {
        this.setApprovalStatus(approvalStatus);
        return this;
    }

    public Set<Events> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Events> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.setCampaign(null));
        }
        if (events != null) {
            events.forEach(i -> i.setCampaign(this));
        }
        this.events = events;
    }

    public Campaign events(Set<Events> events) {
        this.setEvents(events);
        return this;
    }

    public Campaign addEvents(Events events) {
        this.events.add(events);
        events.setCampaign(this);
        return this;
    }

    public Campaign removeEvents(Events events) {
        this.events.remove(events);
        events.setCampaign(null);
        return this;
    }

    public Segment getSegment() {
        return this.segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Campaign segment(Segment segment) {
        this.setSegment(segment);
        return this;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Campaign template(Template template) {
        this.setTemplate(template);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campaign)) {
            return false;
        }
        return id != null && id.equals(((Campaign) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campaign{" +
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
            "}";
    }
}
