package com.notiflowcate.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Entity
@DiscriminatorValue(value = "BEACON")
public class BeaconNotification extends Notification {

    private static final long serialVersionUID = -861102262498684383L;

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
        return "BeaconNotification{" +
                "beaconId=" + beaconId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeaconNotification)) return false;

        BeaconNotification that = (BeaconNotification) o;

        return beaconId != null ? beaconId.equals(that.beaconId) : that.beaconId == null;

    }

    @Override
    public int hashCode() {
        return beaconId != null ? beaconId.hashCode() : 0;
    }
}
