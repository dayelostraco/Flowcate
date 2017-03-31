package com.notiflowcate.model.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dayel Ostraco
 * 6/28/16
 */
public class TopicPushNotification {

    @JsonProperty("to")
    private String topicName;

    @JsonProperty("priority")
    private String priority = "high"; // Default

    @JsonProperty("notification")
    private StandardNotificationBody notification;

    @JsonProperty("data")
    private NotificationData data;

    public TopicPushNotification() {
    }

    public TopicPushNotification(String topicName) {
        this.topicName = "/topics/" + topicName;
    }

    public TopicPushNotification(String topicName, StandardNotificationBody notification) {
        this.topicName = topicName;
        this.notification = notification;
    }

    public TopicPushNotification(String topicName, NotificationData data) {
        this.topicName = "/topics/" + topicName;
        this.data = data;
    }

    public TopicPushNotification(String topicName, StandardNotificationBody notification, NotificationData data) {
        this.topicName = "/topics/" + topicName;
        this.notification = notification;
        this.data = data;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = "/topics/" + topicName;
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
