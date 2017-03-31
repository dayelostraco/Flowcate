package com.notiflowcate.model.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Dayel
 * 12/21/15.
 */
public class SmsDto {

    @NotNull(message = "You must provide a valid phone number")
    private String phoneNumber;

    @NotNull(message = "You must provide a valid message")
    private String message;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
