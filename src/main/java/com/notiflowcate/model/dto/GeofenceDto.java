package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Dayel Ostraco
 * 11/27/2015.
 */
public class GeofenceDto {

    private Long geofenceId;

    @JsonIgnore
    private Long applicationId;

    @NotNull(message = "You must provide a valid Listing Id")
    @Size(min = 1, max = 64, message = "ListingId must contain at least 1 character and not exceed 64 characters")
    private String listingId;

    @NotNull(message = "You must provide a valid Geofence Name")
    @Size(min = 1, max = 64, message = "Geofence Name must contain at least 1 character and not exceed 64 characters")
    private String name;

    @NotNull(message = "You must provide a valid Geofence Description")
    private String description;

    @NotNull(message = "You must provide a valid Latitude between -90.0 and 90.0")
    @Min(value = -90, message = "Latitude can not be lower than -90.0")
    @Max(value = 90, message = "Latitude cannot be greater than 90.0")
    private BigDecimal latitude;

    @NotNull(message = "You must provide a valid Longitude between -180.0 and 180.0")
    @Min(value = -180, message = "Longitude can not be lower than -180.0")
    @Max(value = 180, message = "Longitude cannot be greater than 180.0")
    private BigDecimal longitude;

    @NotNull(message = "You must provide a valid RadiusInMeters between 100.00 and 99999.00 meters")
    @Min(value = 10, message = "RadiusInMeters can not be lower than 100.00")
    @Max(value = 99999, message = "RadiusInMeters cannot be greater than 99999.00")
    private Double radiusInMeters;

    @NotNull(message = "You must specify if the Geofence is active")
    private Boolean active;

    private Date expiration;

    @JsonIgnore
    private LocalDateTime deleted;

    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime modified;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String errorMsg;

    private Long expirationDurationInMilliseconds;

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

    public Double getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(Double radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Long getExpirationDurationInMilliseconds() {

        if (this.expiration != null) {
            return (this.expiration.getTime() - LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC).toEpochMilli());
        }

        return null;
    }

    public void setExpirationDurationInMilliseconds(Long expirationDuration) {
        this.expirationDurationInMilliseconds = expirationDuration;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
