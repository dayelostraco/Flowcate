package com.notiflowcate.model.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Dayel Ostraco
 * 9/28/16.
 */
public class GeofenceRecentUsersNotification extends GeofenceNotificationDto {

    @NotNull
    private int timeframeInHours;

    public int getTimeframeInHours() {
        return timeframeInHours;
    }

    public void setTimeframeInHours(int timeframeInHours) {
        this.timeframeInHours = timeframeInHours;
    }
}
