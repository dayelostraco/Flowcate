package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Will Goss
 * 12/21/15.
 */
@Entity
@Table(name = "DeviceToken")
public class DeviceToken implements Serializable {

    private static final long serialVersionUID = 422742072058227340L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceTokenId", nullable = false)
    private Long deviceTokenId;

    @Column(name = "deviceToken", length = 255)
    private String deviceToken;

    @ManyToOne(targetEntity = Application.class)
    @JoinColumn(name = "applicationId")
    private Application application;

    @ManyToOne(targetEntity = Platform.class)
    @JoinColumn(name = "platformId")
    private Platform platform;

    @Column(name = "userId", length = 64, nullable = true)
    private String userId;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @PrePersist
    protected void onCreate() {
        created = modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
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

    @Override
    public String toString() {
        return "DeviceToken{" +
                "deviceTokenId=" + deviceTokenId +
                ", deviceToken='" + deviceToken + '\'' +
                ", application=" + application +
                ", platform=" + platform +
                ", userId='" + userId + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceToken)) return false;

        DeviceToken that = (DeviceToken) o;

        if (deviceTokenId != null ? !deviceTokenId.equals(that.deviceTokenId) : that.deviceTokenId != null)
            return false;
        if (deviceToken != null ? !deviceToken.equals(that.deviceToken) : that.deviceToken != null) return false;
        if (application != null ? !application.equals(that.application) : that.application != null) return false;
        if (platform != null ? !platform.equals(that.platform) : that.platform != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return modified != null ? modified.equals(that.modified) : that.modified == null;

    }

    @Override
    public int hashCode() {
        int result = deviceTokenId != null ? deviceTokenId.hashCode() : 0;
        result = 31 * result + (deviceToken != null ? deviceToken.hashCode() : 0);
        result = 31 * result + (application != null ? application.hashCode() : 0);
        result = 31 * result + (platform != null ? platform.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
