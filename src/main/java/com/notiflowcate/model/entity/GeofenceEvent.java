package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.io.Serializable;

/**
 * @author Dayel Ostraco
 * 10/28/15.
 */
@Entity
@DiscriminatorValue(value = "GEOFENCE")
public class GeofenceEvent extends NotificationEvent implements Serializable {

    private static final long serialVersionUID = 6902543010459436215L;

    @Column(name = "geofenceId", nullable = false)
    private Long geofenceId;

    @Column(name = "onDwell")
    private Boolean dwell;

    @Column(name = "dwellTimeInMilliseconds")
    private Integer dwellTimeInMilliseconds;

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

    @Override
    public String toString() {
        return "GeofenceEvent{" +
                "geofenceId=" + geofenceId +
                ", dwell=" + dwell +
                ", dwellTimeInMilliseconds=" + dwellTimeInMilliseconds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeofenceEvent)) return false;

        GeofenceEvent that = (GeofenceEvent) o;

        if (geofenceId != null ? !geofenceId.equals(that.geofenceId) : that.geofenceId != null) return false;
        if (dwell != null ? !dwell.equals(that.dwell) : that.dwell != null) return false;
        return dwellTimeInMilliseconds != null ? dwellTimeInMilliseconds.equals(that.dwellTimeInMilliseconds) : that.dwellTimeInMilliseconds == null;

    }

    @Override
    public int hashCode() {
        int result = geofenceId != null ? geofenceId.hashCode() : 0;
        result = 31 * result + (dwell != null ? dwell.hashCode() : 0);
        result = 31 * result + (dwellTimeInMilliseconds != null ? dwellTimeInMilliseconds.hashCode() : 0);
        return result;
    }
}
