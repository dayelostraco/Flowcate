package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notiflowcate.model.notification.NotificationData;

/**
 * ryanmoore - 7/26/16.
 */
public class DataPushNotification {

    @JsonProperty("to")
    protected String deviceToken;

    @JsonProperty("priority")
    protected String priority = "high"; // Default

    @JsonProperty("data")
    protected NotificationData data;

    public DataPushNotification(){}

    public DataPushNotification(String deviceToken, NotificationData data) {
        this.deviceToken = deviceToken;
        this.data = data;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

}
