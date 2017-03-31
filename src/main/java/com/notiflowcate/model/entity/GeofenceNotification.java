package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Entity
@DiscriminatorValue(value = "GEOFENCE")
public class GeofenceNotification extends Notification {

    private static final long serialVersionUID = -1472639905102582033L;

    @Column(name = "geofenceId", nullable = false)
    private Long geofenceId;

    public Long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(Long geofenceId) {
        this.geofenceId = geofenceId;
    }

    @Override
    public String toString() {
        return "GeofenceNotification{" +
                "geofenceId=" + geofenceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeofenceNotification)) return false;

        GeofenceNotification that = (GeofenceNotification) o;

        return geofenceId != null ? geofenceId.equals(that.geofenceId) : that.geofenceId == null;

    }

    @Override
    public int hashCode() {
        return geofenceId != null ? geofenceId.hashCode() : 0;
    }
}
