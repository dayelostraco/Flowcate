package com.notiflowcate.model.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Dayel
 * 12/21/15.
 */
public class MmsDto {

    @NotNull(message = "You must provide a valid phone number")
    private String phoneNumber;

    @NotNull(message = "You must provide a valid message")
    private String message;

    @NotNull(message = "You must provide valid media URLs")
    private String[] mediaUrls;

    private String error;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(String[] mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
