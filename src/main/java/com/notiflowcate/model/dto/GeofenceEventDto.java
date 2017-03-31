package com.notiflowcate.model.dto;

/**
 * @author Dayel Ostraco
 * 11/27/2015.
 */
public class GeofenceEventDto extends NotificationEventDto {

    private Long geofenceId;

    private Boolean dwell = false;

    private Integer dwellTimeInMilliseconds = 300000; // Default to 5 minutes

    public Long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(Long geofenceId) {
        this.geofenceId = geofenceId;
    }

    public Boolean getDwell() {
        return dwell;
    }

    public void setDwell(Boolean dwell) {
        this.dwell = dwell;
    }

    public Integer getDwellTimeInMilliseconds() {
        return dwellTimeInMilliseconds;
    }

    public void setDwellTimeInMilliseconds(Integer dwellTimeInMilliseconds) {
        this.dwellTimeInMilliseconds = dwellTimeInMilliseconds;
    }
}
