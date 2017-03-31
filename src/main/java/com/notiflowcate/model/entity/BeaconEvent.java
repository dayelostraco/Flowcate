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
@DiscriminatorValue(value = "BEACON")
public class BeaconEvent extends NotificationEvent implements Serializable {

    private static final long serialVersionUID = -4912637781475255206L;

    @Column(name = "beaconId", nullable = false)
    private Long beaconId;

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }

    @Override
    public String toString() {
        return "BeaconEvent{" +
                "beaconId=" + beaconId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeaconEvent)) return false;

        BeaconEvent that = (BeaconEvent) o;

        return beaconId != null ? beaconId.equals(that.beaconId) : that.beaconId == null;

    }

    @Override
    public int hashCode() {
        return beaconId != null ? beaconId.hashCode() : 0;
    }
}
