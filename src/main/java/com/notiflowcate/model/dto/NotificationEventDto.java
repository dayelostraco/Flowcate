package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Dayel Ostraco
 * 5/28/16
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "notificationEventType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BeaconEventDto.class, name = "beacon"),
        @JsonSubTypes.Type(value = GeofenceEventDto.class, name = "geofence")
})
public abstract class NotificationEventDto {

    private Long notificationEventId;

    @NotNull(message = "You must provide a valid Listing Id")
    private String listingId;

    @NotNull(message = "You must provide a valid Application Id")
    private Long applicationId;

    @NotNull(message = "You must provide a valid Beacon Event Name")
    @Size(min = 1, max = 64, message = "Beacon Event Name must contain at least 1 character and not exceed 64 characters")
    private String eventName;

    @NotNull(message = "You must provide a valid Beacon Event Description")
    @Size(min = 1, max = 1024, message = "Beacon Event Description must contain at least 1 character and not exceed 1024 characters")
    private String eventDescription;

    @NotNull(message = "You must provide a valid Beacon Event Notification Text")
    @Size(min = 1, max = 236, message = "Beacon Event Notification Text must contain at least 1 character and not exceed 236 characters")
    private String eventNotificationText;

    @NotNull(message = "You must specify if the Beacon Event is active")
    private Boolean active = true;

    @NotNull(message = "You must specify if the Beacon Event should be sent when the user enters a Beacon region")
    private Boolean enter = false;

    @NotNull(message = "You must specify if the Beacon Event should be sent when the user exits a Beacon region")
    private Boolean exit = false;

    @NotNull(message = "You must provide a valid Beacon Event Start Date")
    private Date startDate;

    @NotNull(message = "You must provide a valid Beacon Event End Date")
    private Date endDate;

    @JsonIgnore
    private LocalDateTime deleted;

    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime modified;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String errorMsg;

    public Long getNotificationEventId() {
        return notificationEventId;
    }

    public void setNotificationEventId(Long notificationEventId) {
        this.notificationEventId = notificationEventId;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
