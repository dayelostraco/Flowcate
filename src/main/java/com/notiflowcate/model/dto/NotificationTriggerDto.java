package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Dayel Ostraco
 * 11/27/2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "triggerType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BeaconTriggerDto.class, name = "beacon"),
        @JsonSubTypes.Type(value = GeofenceTriggerDto.class, name = "geofence")
})
public abstract class NotificationTriggerDto {

    private Long notificationTriggerId;

    private Long applicationId;

    private Long notificationEventId;

    private String userId;

    @NotNull(message = "You must provide a valid deviceToken")
    private String deviceToken;

    @Min(value = -90, message = "Latitude can not be lower than -90.0")
    @Max(value = 90, message = "Latitude cannot be greater than 90.0")
    private BigDecimal latitude;

    @Min(value = -180, message = "Longitude can not be lower than -180.0")
    @Max(value = 180, message = "Longitude cannot be greater than 180.0")
    private BigDecimal longitude;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean onEnter = false;

    @JsonIgnore
    private LocalDateTime enterTime;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean onExit = false;

    @JsonIgnore
    private LocalDateTime exitTime;

    private Long serverTime;

    private Double distanceInMiles;

    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime modified;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String errorMsg;

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

    public Boolean getOnEnter() {
        return onEnter;
    }

    public void setOnEnter(Boolean onEnter) {
        this.onEnter = onEnter;
    }

    public LocalDateTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(LocalDateTime enterTime) {
        this.enterTime = enterTime;
    }

    public Boolean getOnExit() {
        return onExit;
    }

    public void setOnExit(Boolean onExit) {
        this.onExit = onExit;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
