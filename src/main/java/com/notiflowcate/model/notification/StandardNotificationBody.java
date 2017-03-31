package com.notiflowcate.model.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dayel Ostraco
 * 06/28/16
 */
public class StandardNotificationBody {

    @JsonProperty("body")
    private String body;

    @JsonProperty("title")
    private String title;

    @JsonProperty("icon")
    private String icon = "new"; // Default

    @JsonProperty("sound")
    private String sound = "default"; // Default

    @JsonProperty("badge")
    private String badge = "0"; // Default

    public StandardNotificationBody() {
    }

    public StandardNotificationBody(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public StandardNotificationBody(String body, String title, String icon) {
        this.body = body;
        this.title = title;
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
