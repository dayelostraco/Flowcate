package com.notiflowcate.model.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Dayel Ostraco
 * 1/4/16
 */
public class BeaconNotificationDto extends NotificationDto {

    @NotNull
    private Long beaconId;

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }
}
