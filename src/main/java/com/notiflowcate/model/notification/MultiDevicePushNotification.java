package com.notiflowcate.model.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will Goss
 * 12/22/15
 */
public class MultiDevicePushNotification {

    @JsonProperty("registration_ids")
    private List<String> registrationIds;

    @JsonProperty("priority")
    private String priority = "high"; // Default

    @JsonProperty("notification")
    private StandardNotificationBody notification;

    @JsonProperty("data")
    private NotificationData data;

    public MultiDevicePushNotification() {
    }

    public MultiDevicePushNotification(List<String> registrationIds, StandardNotificationBody notification) {
        this.registrationIds = registrationIds;
        this.notification = notification;
    }

    public MultiDevicePushNotification(List<String> registrationIds, NotificationData data) {
        this.registrationIds = registrationIds;
        this.data = data;
    }

    public MultiDevicePushNotification(List<String> registrationIds, StandardNotificationBody notification, NotificationData data) {
        this.registrationIds = registrationIds;
        this.notification = notification;
        this.data = data;
    }

    public List<String> getRegistrationIds() {

        if (registrationIds == null) {
            this.registrationIds = new ArrayList<>();
        }

        return this.registrationIds;
    }

    public void setRegistrationIds(List<String> registrationIds) {
        this.registrationIds = registrationIds;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public StandardNotificationBody getNotification() {
        return notification;
    }

    public void setNotification(StandardNotificationBody notification) {
        this.notification = notification;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }
}
