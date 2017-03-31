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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dayel Ostraco
 * 5/23/16
 */
@Entity
@Table(name = "Beacon")
public class Beacon implements Serializable {

    private static final long serialVersionUID = 436965352384872326L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "beaconId", nullable = false)
    private Long beaconId;

    @Column(name = "applicationId", nullable = false)
    private Long applicationId;

    @Column(name = "listingId", nullable = false, length = 64)
    private String listingId;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "beaconUUID", nullable = false)
    private String beaconUUID;

    @Column(name = "beaconMajor", nullable = false)
    private Integer beaconMajor;

    @Column(name = "beaconMinor", nullable = false)
    private Integer beaconMinor;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude = null;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude = null;

    @Column(name = "active")
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

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeaconUUID() {
        return beaconUUID;
    }

    public void setBeaconUUID(String beaconUUID) {
        this.beaconUUID = beaconUUID;
    }

    public Integer getBeaconMajor() {
        return beaconMajor;
    }

    public void setBeaconMajor(Integer beaconMajor) {
        this.beaconMajor = beaconMajor;
    }

    public Integer getBeaconMinor() {
        return beaconMinor;
    }

    public void setBeaconMinor(Integer beaconMinor) {
        this.beaconMinor = beaconMinor;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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
        return "Beacon{" +
                "beaconId=" + beaconId +
                ", applicationId=" + applicationId +
                ", listingId='" + listingId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", beaconUUID='" + beaconUUID + '\'' +
                ", beaconMajor=" + beaconMajor +
                ", beaconMinor=" + beaconMinor +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", active=" + active +
                ", deleted=" + deleted +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beacon)) return false;

        Beacon beacon = (Beacon) o;

        if (beaconId != null ? !beaconId.equals(beacon.beaconId) : beacon.beaconId != null) return false;
        if (applicationId != null ? !applicationId.equals(beacon.applicationId) : beacon.applicationId != null)
            return false;
        if (listingId != null ? !listingId.equals(beacon.listingId) : beacon.listingId != null) return false;
        if (name != null ? !name.equals(beacon.name) : beacon.name != null) return false;
        if (description != null ? !description.equals(beacon.description) : beacon.description != null) return false;
        if (beaconUUID != null ? !beaconUUID.equals(beacon.beaconUUID) : beacon.beaconUUID != null) return false;
        if (beaconMajor != null ? !beaconMajor.equals(beacon.beaconMajor) : beacon.beaconMajor != null) return false;
        if (beaconMinor != null ? !beaconMinor.equals(beacon.beaconMinor) : beacon.beaconMinor != null) return false;
        if (latitude != null ? !latitude.equals(beacon.latitude) : beacon.latitude != null) return false;
        if (longitude != null ? !longitude.equals(beacon.longitude) : beacon.longitude != null) return false;
        if (active != null ? !active.equals(beacon.active) : beacon.active != null) return false;
        if (deleted != null ? !deleted.equals(beacon.deleted) : beacon.deleted != null) return false;
        if (created != null ? !created.equals(beacon.created) : beacon.created != null) return false;
        return modified != null ? modified.equals(beacon.modified) : beacon.modified == null;

    }

    @Override
    public int hashCode() {
        int result = beaconId != null ? beaconId.hashCode() : 0;
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (listingId != null ? listingId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (beaconUUID != null ? beaconUUID.hashCode() : 0);
        result = 31 * result + (beaconMajor != null ? beaconMajor.hashCode() : 0);
        result = 31 * result + (beaconMinor != null ? beaconMinor.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
