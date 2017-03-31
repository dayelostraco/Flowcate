package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dayel Ostraco
 * 10/28/15.
 */
@Entity
@Table(name = "NotificationEvent")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notificationEventType")
public abstract class NotificationEvent implements Serializable {

    private static final long serialVersionUID = -6724055550394818260L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationEventId", nullable = false)
    private Long notificationEventId;

    @Column(name = "applicationId", nullable = false)
    private Long applicationId;

    @Column(name = "listingId", nullable = false, length = 64)
    private String listingId;

    @Column(name = "eventName", nullable = false, length = 64)
    private String eventName;

    @Column(name = "eventDescription", nullable = false, length = 1024)
    private String eventDescription;

    @Column(name = "eventNotificationText", nullable = false, length = 236)
    private String eventNotificationText;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "onEnter", nullable = false)
    private Boolean enter;

    @Column(name = "onExit", nullable = false)
    private Boolean exit;

    @Column(name = "startDate")
    private LocalDateTime startDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;

    @Column(name = "deleted")
    private LocalDateTime deleted;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @PrePersist
    protected void onCreate() {
        created = modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public Long getNotificationEventId() {
        return notificationEventId;
    }

    public void setNotificationEventId(Long notificationEventId) {
        this.notificationEventId = notificationEventId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventNotificationText() {
        return eventNotificationText;
    }

    public void setEventNotificationText(String eventNotificationText) {
        this.eventNotificationText = eventNotificationText;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getEnter() {
        return enter;
    }

    public void setEnter(Boolean enter) {
        this.enter = enter;
    }

    public Boolean getExit() {
        return exit;
    }

    public void setExit(Boolean exit) {
        this.exit = exit;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "notificationEventId=" + notificationEventId +
                ", applicationId=" + applicationId +
                ", listingId='" + listingId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventNotificationText='" + eventNotificationText + '\'' +
                ", active=" + active +
                ", enter=" + enter +
                ", exit=" + exit +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", deleted=" + deleted +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationEvent)) return false;

        NotificationEvent that = (NotificationEvent) o;

        if (notificationEventId != null ? !notificationEventId.equals(that.notificationEventId) : that.notificationEventId != null)
            return false;
        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (listingId != null ? !listingId.equals(that.listingId) : that.listingId != null) return false;
        if (eventName != null ? !eventName.equals(that.eventName) : that.eventName != null) return false;
        if (eventDescription != null ? !eventDescription.equals(that.eventDescription) : that.eventDescription != null)
            return false;
        if (eventNotificationText != null ? !eventNotificationText.equals(that.eventNotificationText) : that.eventNotificationText != null)
            return false;
        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (enter != null ? !enter.equals(that.enter) : that.enter != null) return false;
        if (exit != null ? !exit.equals(that.exit) : that.exit != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return modified != null ? modified.equals(that.modified) : that.modified == null;

    }

    @Override
    public int hashCode() {
        int result = notificationEventId != null ? notificationEventId.hashCode() : 0;
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (listingId != null ? listingId.hashCode() : 0);
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (eventDescription != null ? eventDescription.hashCode() : 0);
        result = 31 * result + (eventNotificationText != null ? eventNotificationText.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (enter != null ? enter.hashCode() : 0);
        result = 31 * result + (exit != null ? exit.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
