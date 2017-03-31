package com.notiflowcate.model.dto;

/**
 * @author Dayel Ostraco
 * 11/27/2015.
 */
public class BeaconEventDto extends NotificationEventDto {

    private Long beaconId;

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }
}
