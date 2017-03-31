package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * @author Dayel Ostraco
 * 12/21/15
 */
public class DeviceTokenDto {

    private Long deviceTokenId;
    @NotNull(message = "You must provide a valid DeviceToken")
    @Length(min = 1, max = 255, message = "You must provide a valid DeviceToken between 1 and 255 characters")
    private String deviceToken;
    private String userId;
    private Long platformId;
    @JsonIgnore
    private Long applicationId;
    @JsonIgnore
    private LocalDateTime created;
    @JsonIgnore
    private LocalDateTime modified;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String errorMsg;

    public Long getDeviceTokenId() {
        return deviceTokenId;
    }

    public void setDeviceTokenId(Long deviceTokenId) {
        this.deviceTokenId = deviceTokenId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
