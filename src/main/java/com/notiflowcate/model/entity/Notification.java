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
 * 12/21/15
 */
@Entity
@Table(name = "Notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "triggerType")
public abstract class Notification implements Serializable {

    private static final long serialVersionUID = 9197024062402326090L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "notificationId", nullable = false)
    private Long notificationId;

    @Column(name = "applicationId", nullable = false)
    private Long applicationId;

    @Column(name = "notificationEventId", nullable = false)
    private Long notificationEventId;

    @Column(name = "notificationTriggerId", nullable = false)
    private Long notificationTriggerId;

    @Column(name = "userId", length = 64)
    private String userId;

    @Column(name = "listingId", length = 64)
    private String listingId;

    @Column(name = "deviceToken", nullable = false)
    private String deviceToken;

    @Column(name = "message", nullable = false, length = 2048)
    private String message;

    @Column(name = "sentTime", nullable = false)
    private LocalDateTime sentTime;

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

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getNotificationEventId() {
        return notificationEventId;
    }

    public void setNotificationEventId(Long notificationEventId) {
        this.notificationEventId = notificationEventId;
    }

    public Long getNotificationTriggerId() {
        return notificationTriggerId;
    }

    public void setNotificationTriggerId(Long notificationTriggerId) {
        this.notificationTriggerId = notificationTriggerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
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
        return "Notification{" +
                "notificationId=" + notificationId +
                ", applicationId=" + applicationId +
                ", notificationEventId=" + notificationEventId +
                ", notificationTriggerId=" + notificationTriggerId +
                ", userId='" + userId + '\'' +
                ", listingId='" + listingId + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", message='" + message + '\'' +
                ", sentTime=" + sentTime +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        if (notificationId != null ? !notificationId.equals(that.notificationId) : that.notificationId != null)
            return false;
        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (notificationEventId != null ? !notificationEventId.equals(that.notificationEventId) : that.notificationEventId != null)
            return false;
        if (notificationTriggerId != null ? !notificationTriggerId.equals(that.notificationTriggerId) : that.notificationTriggerId != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (listingId != null ? !listingId.equals(that.listingId) : that.listingId != null) return false;
        if (deviceToken != null ? !deviceToken.equals(that.deviceToken) : that.deviceToken != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (sentTime != null ? !sentTime.equals(that.sentTime) : that.sentTime != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return modified != null ? modified.equals(that.modified) : that.modified == null;

    }

    @Override
    public int hashCode() {
        int result = notificationId != null ? notificationId.hashCode() : 0;
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (notificationEventId != null ? notificationEventId.hashCode() : 0);
        result = 31 * result + (notificationTriggerId != null ? notificationTriggerId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (listingId != null ? listingId.hashCode() : 0);
        result = 31 * result + (deviceToken != null ? deviceToken.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (sentTime != null ? sentTime.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
