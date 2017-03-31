package com.notiflowcate.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * @author Dayel Ostraco
 * 11/27/2015.
 */
public class GeofenceTriggerDto extends NotificationTriggerDto {

    @NotNull
    private Long geofenceId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean onDwell = false;

    @JsonIgnore
    private LocalDateTime dwellTime;

    public Long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(Long geofenceId) {
        this.geofenceId = geofenceId;
    }

    public Boolean getOnDwell() {
        return onDwell;
    }

    public void setOnDwell(Boolean onDwell) {
        this.onDwell = onDwell;
    }

    public LocalDateTime getDwellTime() {
        return dwellTime;
    }

    public void setDwellTime(LocalDateTime dwellTime) {
        this.dwellTime = dwellTime;
    }
}
