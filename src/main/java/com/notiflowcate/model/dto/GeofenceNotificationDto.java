package com.notiflowcate.model.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Dayel Ostraco
 * 1/4/16
 */
public class GeofenceNotificationDto extends NotificationDto {

    @NotNull
    private Long geofenceId;

    public Long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(Long geofenceId) {
        this.geofenceId = geofenceId;
    }
}
