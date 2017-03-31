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
@Table(name = "Geofence")
public class Geofence implements Serializable {

    private static final long serialVersionUID = -4893343482924999887L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "geofenceId", nullable = false)
    private Long geofenceId;

    @Column(name = "applicationId", nullable = false)
    private Long applicationId;

    @Column(name = "listingId", nullable = false, length = 64)
    private String listingId;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude = null;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude = null;

    @Column(name = "radiusInMeters", nullable = false)
    private BigDecimal radiusInMeters = new BigDecimal(150.00);

    @Column(name = "active")
    private Boolean active;

    @Column(name = "expiration", nullable = false)
    private LocalDateTime expiration;

    @Column(name = "deleted")
    private LocalDateTime deleted;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "modified", nullable = false)
    private LocalDateTime modified;

    @PrePersist
    protected void onCreate() {
        created = modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public Long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(Long geofenceId) {
        this.geofenceId = geofenceId;
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

    public BigDecimal getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(BigDecimal radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
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
        return "Geofence{" +
                "geofenceId=" + geofenceId +
                ", applicationId=" + applicationId +
                ", listingId='" + listingId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", radiusInMeters=" + radiusInMeters +
                ", active=" + active +
                ", expiration=" + expiration +
                ", deleted=" + deleted +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Geofence)) return false;

        Geofence geofence = (Geofence) o;

        if (geofenceId != null ? !geofenceId.equals(geofence.geofenceId) : geofence.geofenceId != null) return false;
        if (applicationId != null ? !applicationId.equals(geofence.applicationId) : geofence.applicationId != null)
            return false;
        if (listingId != null ? !listingId.equals(geofence.listingId) : geofence.listingId != null) return false;
        if (name != null ? !name.equals(geofence.name) : geofence.name != null) return false;
        if (description != null ? !description.equals(geofence.description) : geofence.description != null)
            return false;
        if (latitude != null ? !latitude.equals(geofence.latitude) : geofence.latitude != null) return false;
        if (longitude != null ? !longitude.equals(geofence.longitude) : geofence.longitude != null) return false;
        if (radiusInMeters != null ? !radiusInMeters.equals(geofence.radiusInMeters) : geofence.radiusInMeters != null)
            return false;
        if (active != null ? !active.equals(geofence.active) : geofence.active != null) return false;
        if (expiration != null ? !expiration.equals(geofence.expiration) : geofence.expiration != null) return false;
        if (deleted != null ? !deleted.equals(geofence.deleted) : geofence.deleted != null) return false;
        if (created != null ? !created.equals(geofence.created) : geofence.created != null) return false;
        return modified != null ? modified.equals(geofence.modified) : geofence.modified == null;

    }

    @Override
    public int hashCode() {
        int result = geofenceId != null ? geofenceId.hashCode() : 0;
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (listingId != null ? listingId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (radiusInMeters != null ? radiusInMeters.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
