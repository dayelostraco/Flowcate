package com.notiflowcate.model.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dayel Ostraco
 * 06/28/16
 */
public class NotificationData {

    @JsonProperty("message")
    private String message;

    @JsonProperty("listingId")
    private String listingId;

    public NotificationData() {
    }

    public NotificationData(String message) {
        this.message = message;
    }

    public NotificationData(String message, String listingId) {
        this.message = message;
        this.listingId = listingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }
}
