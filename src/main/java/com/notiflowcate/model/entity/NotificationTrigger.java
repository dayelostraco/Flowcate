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
import javax.persistence.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dayel Ostraco
 * 10/28/15.
 */
@Entity
@Table(name = "NotificationTrigger")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notificationTriggerType")
public abstract class NotificationTrigger implements Serializable {

    private static final long serialVersionUID = -4873183991902259831L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "notificationTriggerId", nullable = false)
    private Long notificationTriggerId;

    @Column(name = "applicationId", nullable = false)
    private Long applicationId;

    @Column(name = "notificationEventId", nullable = false)
    private Long notificationEventId;

    @Column(name = "userId", length = 64)
    private String userId;

    @Column(name = "deviceToken", nullable = false)
    private String deviceToken;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "enterTime")
    private LocalDateTime enterTime;

    @Column(name = "exitTime")
    private LocalDateTime exitTime;

    @Column(name = "serverTime")
    private LocalDateTime serverTime;

    @Transient
    private Double distanceInMiles;

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

    public Long getNotificationTriggerId() {
        return notificationTriggerId;
    }

    public void setNotificationTriggerId(Long notificationTriggerId) {
        this.notificationTriggerId = notificationTriggerId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(LocalDateTime enterTime) {
        this.enterTime = enterTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public LocalDateTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(LocalDateTime serverTime) {
        this.serverTime = serverTime;
    }

    public Double getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(Double distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
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
        return "NotificationTrigger{" +
                "notificationTriggerId=" + notificationTriggerId +
                ", applicationId=" + applicationId +
                ", notificationEventId=" + notificationEventId +
                ", userId='" + userId + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", enterTime=" + enterTime +
                ", exitTime=" + exitTime +
                ", serverTime=" + serverTime +
                ", distanceInMiles=" + distanceInMiles +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationTrigger)) return false;

        NotificationTrigger that = (NotificationTrigger) o;

        if (notificationTriggerId != null ? !notificationTriggerId.equals(that.notificationTriggerId) : that.notificationTriggerId != null)
            return false;
        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (notificationEventId != null ? !notificationEventId.equals(that.notificationEventId) : that.notificationEventId != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (deviceToken != null ? !deviceToken.equals(that.deviceToken) : that.deviceToken != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (enterTime != null ? !enterTime.equals(that.enterTime) : that.enterTime != null) return false;
        if (exitTime != null ? !exitTime.equals(that.exitTime) : that.exitTime != null) return false;
        if (serverTime != null ? !serverTime.equals(that.serverTime) : that.serverTime != null) return false;
        if (distanceInMiles != null ? !distanceInMiles.equals(that.distanceInMiles) : that.distanceInMiles != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return modified != null ? modified.equals(that.modified) : that.modified == null;

    }

    @Override
    public int hashCode() {
        int result = notificationTriggerId != null ? notificationTriggerId.hashCode() : 0;
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (notificationEventId != null ? notificationEventId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (deviceToken != null ? deviceToken.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (enterTime != null ? enterTime.hashCode() : 0);
        result = 31 * result + (exitTime != null ? exitTime.hashCode() : 0);
        result = 31 * result + (serverTime != null ? serverTime.hashCode() : 0);
        result = 31 * result + (distanceInMiles != null ? distanceInMiles.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
