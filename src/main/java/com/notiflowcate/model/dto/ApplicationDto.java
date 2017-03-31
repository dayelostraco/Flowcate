package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Created by Will Goss
 * 12/21/15
 */
public class ApplicationDto {

    private Long applicationId;

    @NotNull(message = "You must provide a valid Application Name")
    @Size(min=1, max=64, message = "Application Name must contain at least 1 character and not exceed 64 characters")
    private String applicationName;

    private String apiKey;

    @NotNull
    private Boolean active;

    @NotNull(message = "You must provide a valid FCM API Key")
    @Size(min=1, max=64, message = "FCM API Key must contain at least 1 character and not exceed 64 characters")
    private String fcmApiKey;

    @NotNull(message = "You must provide a valid FCM Sender ID")
    private BigInteger fcmSenderId;

    private String sesAccessKey;

    private String sesSecretKey;

    private String twilioSmsPhone;

    private String twilioMmsPhone;

    private String twilioVoicePhone;

    private String twilioAccountSid;

    private String twilioPrimaryToken;

    private String twilioSecondaryToken;

    private String twilioTestAccountSid;

    private String twilioTestToken;

    @JsonIgnore
    private LocalDateTime deleted;

    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime modified;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFcmApiKey() {
        return fcmApiKey;
    }

    public void setFcmApiKey(String fcmApiKey) {
        this.fcmApiKey = fcmApiKey;
    }

    public BigInteger getFcmSenderId() {
        return fcmSenderId;
    }

    public void setFcmSenderId(BigInteger fcmSenderId) {
        this.fcmSenderId = fcmSenderId;
    }

    public String getSesAccessKey() {
        return sesAccessKey;
    }

    public void setSesAccessKey(String sesAccessKey) {
        this.sesAccessKey = sesAccessKey;
    }

    public String getSesSecretKey() {
        return sesSecretKey;
    }

    public void setSesSecretKey(String sesSecretKey) {
        this.sesSecretKey = sesSecretKey;
    }

    public String getTwilioSmsPhone() {
        return twilioSmsPhone;
    }

    public void setTwilioSmsPhone(String twilioSmsPhone) {
        this.twilioSmsPhone = twilioSmsPhone;
    }

    public String getTwilioMmsPhone() {
        return twilioMmsPhone;
    }

    public void setTwilioMmsPhone(String twilioMmsPhone) {
        this.twilioMmsPhone = twilioMmsPhone;
    }

    public String getTwilioVoicePhone() {
        return twilioVoicePhone;
    }

    public void setTwilioVoicePhone(String twilioVoicePhone) {
        this.twilioVoicePhone = twilioVoicePhone;
    }

    public String getTwilioAccountSid() {
        return twilioAccountSid;
    }

    public void setTwilioAccountSid(String twilioAccountSid) {
        this.twilioAccountSid = twilioAccountSid;
    }

    public String getTwilioPrimaryToken() {
        return twilioPrimaryToken;
    }

    public void setTwilioPrimaryToken(String twilioPrimaryToken) {
        this.twilioPrimaryToken = twilioPrimaryToken;
    }

    public String getTwilioSecondaryToken() {
        return twilioSecondaryToken;
    }

    public void setTwilioSecondaryToken(String twilioSecondaryToken) {
        this.twilioSecondaryToken = twilioSecondaryToken;
    }

    public String getTwilioTestAccountSid() {
        return twilioTestAccountSid;
    }

    public void setTwilioTestAccountSid(String twilioTestAccountSid) {
        this.twilioTestAccountSid = twilioTestAccountSid;
    }

    public String getTwilioTestToken() {
        return twilioTestToken;
    }

    public void setTwilioTestToken(String twilioTestToken) {
        this.twilioTestToken = twilioTestToken;
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
}
