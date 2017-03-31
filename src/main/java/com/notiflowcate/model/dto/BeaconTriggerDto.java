package com.notiflowcate.model.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Dayel Ostraco
 * 11/27/2015.
 */
public class BeaconTriggerDto extends NotificationTriggerDto {

    private Long beaconId;

    @NotNull(message = "You must provide a valid Beacon UUID")
    private String beaconUUID;

    @NotNull(message = "You must provide a valid Beacon Major")
    private Integer beaconMajor;

    @NotNull(message = "You must provide a valid Beacon Minor")
    private Integer beaconMinor;

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
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
}
