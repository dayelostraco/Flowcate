package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Dayel Ostraco
 * 10/28/15.
 */
@Entity
@DiscriminatorValue(value = "GEOFENCE")
public class GeofenceTrigger extends NotificationTrigger implements Serializable {

    private static final long serialVersionUID = -2392979585454648L;

    @Column(name = "geofenceId", nullable = false)
    private Long geofenceId;

    @Column(name = "dwellTime")
    private LocalDateTime dwellTime;

    public Long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(Long geofenceId) {
        this.geofenceId = geofenceId;
    }

    public LocalDateTime getDwellTime() {
        return dwellTime;
    }

    public void setDwellTime(LocalDateTime dwellTime) {
        this.dwellTime = dwellTime;
    }

    @Override
    public String toString() {
        return "GeofenceTrigger{" +
                "geofenceId=" + geofenceId +
                ", dwellTime=" + dwellTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeofenceTrigger)) return false;

        GeofenceTrigger that = (GeofenceTrigger) o;

        if (geofenceId != null ? !geofenceId.equals(that.geofenceId) : that.geofenceId != null) return false;
        return dwellTime != null ? dwellTime.equals(that.dwellTime) : that.dwellTime == null;

    }

    @Override
    public int hashCode() {
        int result = geofenceId != null ? geofenceId.hashCode() : 0;
        result = 31 * result + (dwellTime != null ? dwellTime.hashCode() : 0);
        return result;
    }
}
