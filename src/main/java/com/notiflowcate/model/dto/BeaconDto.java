package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Dayel Ostraco
 * 11/27/2015.
 */
public class BeaconDto {

    private Long beaconId;

    @JsonIgnore
    private Long applicationId;

    @NotNull(message = "You must provide a valid Listing Id")
    @Size(min = 1, max = 64, message = "ListingId must contain at least 1 character and not exceed 64 characters")
    private String listingId;

    @NotNull(message = "You must provide a valid Beacon Name")
    @Size(min=1, max=64, message = "Beacon Name must contain at least 1 character and not exceed 64 characters")
    private String name;

    @NotNull(message = "You must provide a valid Beacon Description")
    private String description;

    @NotNull(message = "You must provide a valid Beacon UUID")
    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")
    private String beaconUUID;

    @NotNull(message = "You must provide a valid Beacon Major")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 1, message = "Beacon Major must be greater than or equal to 1.")
    @Max(value = 65535, message = "Beacon Major must be less or equal to 65535")
    private Integer beaconMajor;

    @NotNull(message = "You must provide a valid Beacon Minor")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 1, message = "Beacon Minor must be greater than or equal to 1.")
    @Max(value = 65535, message = "Beacon Minor must be less or equal to 65535")
    private Integer beaconMinor;

    @NotNull(message = "You must provide a valid Latitude between -90.0 and 90.0")
    @Min(value = -90, message = "Latitude can not be lower than -90.0")
    @Max(value = 90, message = "Latitude cannot be greater than 90.0")
    private BigDecimal latitude;

    @NotNull(message = "You must provide a valid Longitude between -180.0 and 180.0")
    @Min(value = -180, message = "Longitude can not be lower than -180.0")
    @Max(value = 180, message = "Longitude cannot be greater than 180.0")
    private BigDecimal longitude;

    @NotNull(message = "You must specify if the Beacon is active")
    private Boolean active;

    @JsonIgnore
    private LocalDateTime deleted;

    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime modified;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String errorMsg;

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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
