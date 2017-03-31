package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dayel Ostraco
 * 12/21/15
 */
@Entity
@Table(name = "Application")
public class Application implements Serializable {

    private static final long serialVersionUID = 881371299168053692L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationId", nullable = false)
    private Long applicationId;

    @Column(name = "applicationName", nullable = false, length = 64)
    private String applicationName;

    @Column(name = "apiKey", nullable = false, length = 36)
    private String apiKey;

    @Column(name = "fcmApiKey", nullable = false, length = 36)
    private String fcmApiKey;

    @Column(name = "fcmSenderId", nullable = false)
    private Long fcmSenderId;

    @Column(name = "sesAccessKey")
    private String sesAccessKey;

    @Column(name = "sesSecretKey")
    private String sesSecretKey;

    @Column(name = "twilioSmsPhone")
    private String twilioSmsPhone;

    @Column(name = "twilioMmsPhone")
    private String twilioMmsPhone;

    @Column(name = "twilioVoicePhone")
    private String twilioVoicePhone;

    @Column(name = "twilioAccountSid")
    private String twilioAccountSid;

    @Column(name = "twilioPrimaryToken")
    private String twilioPrimaryToken;

    @Column(name = "twilioSecondaryToken")
    private String twilioSecondaryToken;

    @Column(name = "twilioTestAccountSid")
    private String twilioTestAccountSid;

    @Column(name = "twilioTestToken")
    private String twilioTestToken;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "deleted")
    private LocalDateTime deleted;

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

    public String getFcmApiKey() {
        return fcmApiKey;
    }

    public void setFcmApiKey(String fcmApiKey) {
        this.fcmApiKey = fcmApiKey;
    }

    public Long getFcmSenderId() {
        return fcmSenderId;
    }

    public void setFcmSenderId(Long fcmSenderId) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", applicationName='" + applicationName + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", fcmApiKey='" + fcmApiKey + '\'' +
                ", fcmSenderId=" + fcmSenderId +
                ", sesAccessKey='" + sesAccessKey + '\'' +
                ", sesSecretKey='" + sesSecretKey + '\'' +
                ", twilioSmsPhone='" + twilioSmsPhone + '\'' +
                ", twilioMmsPhone='" + twilioMmsPhone + '\'' +
                ", twilioVoicePhone='" + twilioVoicePhone + '\'' +
                ", twilioAccountSid='" + twilioAccountSid + '\'' +
                ", twilioPrimaryToken='" + twilioPrimaryToken + '\'' +
                ", twilioSecondaryToken='" + twilioSecondaryToken + '\'' +
                ", twilioTestAccountSid='" + twilioTestAccountSid + '\'' +
                ", twilioTestToken='" + twilioTestToken + '\'' +
                ", active=" + active +
                ", deleted=" + deleted +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Application)) return false;

        Application that = (Application) o;

        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (applicationName != null ? !applicationName.equals(that.applicationName) : that.applicationName != null)
            return false;
        if (apiKey != null ? !apiKey.equals(that.apiKey) : that.apiKey != null) return false;
        if (fcmApiKey != null ? !fcmApiKey.equals(that.fcmApiKey) : that.fcmApiKey != null) return false;
        if (fcmSenderId != null ? !fcmSenderId.equals(that.fcmSenderId) : that.fcmSenderId != null) return false;
        if (sesAccessKey != null ? !sesAccessKey.equals(that.sesAccessKey) : that.sesAccessKey != null) return false;
        if (sesSecretKey != null ? !sesSecretKey.equals(that.sesSecretKey) : that.sesSecretKey != null) return false;
        if (twilioSmsPhone != null ? !twilioSmsPhone.equals(that.twilioSmsPhone) : that.twilioSmsPhone != null)
            return false;
        if (twilioMmsPhone != null ? !twilioMmsPhone.equals(that.twilioMmsPhone) : that.twilioMmsPhone != null)
            return false;
        if (twilioVoicePhone != null ? !twilioVoicePhone.equals(that.twilioVoicePhone) : that.twilioVoicePhone != null)
            return false;
        if (twilioAccountSid != null ? !twilioAccountSid.equals(that.twilioAccountSid) : that.twilioAccountSid != null)
            return false;
        if (twilioPrimaryToken != null ? !twilioPrimaryToken.equals(that.twilioPrimaryToken) : that.twilioPrimaryToken != null)
            return false;
        if (twilioSecondaryToken != null ? !twilioSecondaryToken.equals(that.twilioSecondaryToken) : that.twilioSecondaryToken != null)
            return false;
        if (twilioTestAccountSid != null ? !twilioTestAccountSid.equals(that.twilioTestAccountSid) : that.twilioTestAccountSid != null)
            return false;
        if (twilioTestToken != null ? !twilioTestToken.equals(that.twilioTestToken) : that.twilioTestToken != null)
            return false;
        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return modified != null ? modified.equals(that.modified) : that.modified == null;

    }

    @Override
    public int hashCode() {
        int result = applicationId != null ? applicationId.hashCode() : 0;
        result = 31 * result + (applicationName != null ? applicationName.hashCode() : 0);
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        result = 31 * result + (fcmApiKey != null ? fcmApiKey.hashCode() : 0);
        result = 31 * result + (fcmSenderId != null ? fcmSenderId.hashCode() : 0);
        result = 31 * result + (sesAccessKey != null ? sesAccessKey.hashCode() : 0);
        result = 31 * result + (sesSecretKey != null ? sesSecretKey.hashCode() : 0);
        result = 31 * result + (twilioSmsPhone != null ? twilioSmsPhone.hashCode() : 0);
        result = 31 * result + (twilioMmsPhone != null ? twilioMmsPhone.hashCode() : 0);
        result = 31 * result + (twilioVoicePhone != null ? twilioVoicePhone.hashCode() : 0);
        result = 31 * result + (twilioAccountSid != null ? twilioAccountSid.hashCode() : 0);
        result = 31 * result + (twilioPrimaryToken != null ? twilioPrimaryToken.hashCode() : 0);
        result = 31 * result + (twilioSecondaryToken != null ? twilioSecondaryToken.hashCode() : 0);
        result = 31 * result + (twilioTestAccountSid != null ? twilioTestAccountSid.hashCode() : 0);
        result = 31 * result + (twilioTestToken != null ? twilioTestToken.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
