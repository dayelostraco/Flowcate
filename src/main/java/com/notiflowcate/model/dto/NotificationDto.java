package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

/**
 * @author Dayel Ostraco
 * 5/24/16
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "notificationType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BeaconNotificationDto.class, name = "beacon"),
        @JsonSubTypes.Type(value = TopicNotificationDto.class, name = "topic"),
        @JsonSubTypes.Type(value = GeofenceNotificationDto.class, name = "geofence"),
        @JsonSubTypes.Type(value = GeofenceRecentUsersNotification.class, name = "geofenceRecentUsers"),
        @JsonSubTypes.Type(value = ApplicationNotificationDto.class, name = "application")
})
public abstract class NotificationDto {

    @JsonIgnore
    private Long notificationId;

    private Long applicationId;

    private String listingId;

    private Long notificationTriggerId;

    private Long notificationEventId;

    @JsonIgnore
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String deviceToken;

    private String message;

    @JsonIgnore
    private LocalDateTime sentTime;

    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime modified;

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

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public Long getNotificationTriggerId() {
        return notificationTriggerId;
    }

    public void setNotificationTriggerId(Long notificationTriggerId) {
        this.notificationTriggerId = notificationTriggerId;
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
}