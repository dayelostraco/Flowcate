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
public class BeaconTrigger extends NotificationTrigger implements Serializable {

    private static final long serialVersionUID = 3231002785506738810L;

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
        return "BeaconTrigger{" +
                "beaconId=" + beaconId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeaconTrigger)) return false;

        BeaconTrigger that = (BeaconTrigger) o;

        return beaconId != null ? beaconId.equals(that.beaconId) : that.beaconId == null;

    }

    @Override
    public int hashCode() {
        return beaconId != null ? beaconId.hashCode() : 0;
    }
}
