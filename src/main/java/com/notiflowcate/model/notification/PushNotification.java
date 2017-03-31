package com.notiflowcate.model.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notiflowcate.model.dto.DataPushNotification;

/**
 * @author Dayel Ostraco
 * 6/28/16
 */
public class PushNotification extends DataPushNotification {

    @JsonProperty("notification")
    private StandardNotificationBody notification;

    public PushNotification() {
    }

    public PushNotification(String deviceToken, StandardNotificationBody notification) {
        this.deviceToken = deviceToken;
        this.notification = notification;
    }

    public PushNotification(String deviceToken, StandardNotificationBody notification, NotificationData data) {
        this.deviceToken = deviceToken;
        this.notification = notification;
        this.data = data;
    }

    public StandardNotificationBody getNotification() {
        return notification;
    }

    public void setNotification(StandardNotificationBody notification) {
        this.notification = notification;
    }

}
