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
 * Created by Will Goss
 * 12/21/15
 */
@Entity
@Table(name = "Platform")
public class Platform implements Serializable {

    private static final long serialVersionUID = -7922255966921359829L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "platformId", nullable = false)
    private Long platformId;

    @Column(name = "platformName", nullable = false, length = 32)
    private String platformName;

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

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
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
        return "Platform{" +
                "platformId=" + platformId +
                ", platformName='" + platformName + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Platform)) return false;

        Platform platform = (Platform) o;

        if (platformId != null ? !platformId.equals(platform.platformId) : platform.platformId != null) return false;
        if (platformName != null ? !platformName.equals(platform.platformName) : platform.platformName != null)
            return false;
        if (created != null ? !created.equals(platform.created) : platform.created != null) return false;
        return modified != null ? modified.equals(platform.modified) : platform.modified == null;

    }

    @Override
    public int hashCode() {
        int result = platformId != null ? platformId.hashCode() : 0;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
