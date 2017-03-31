package com.notiflowcate.service;

import com.notiflowcate.exception.TriggerException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.GeofenceTriggerDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
public interface GeofenceTriggerService {

    GeofenceTriggerDto save(ApplicationDto application, GeofenceTriggerDto geofenceTrigger) throws TriggerException;

    GeofenceTriggerDto update(ApplicationDto application, GeofenceTriggerDto geofenceTrigger)
            throws TriggerException;

    List<GeofenceTriggerDto> findLatestGeofenceTriggers(ApplicationDto application,
                                                        Integer paginationStart, Integer paginationEnd);

    List<GeofenceTriggerDto> findUsersWithinGeofence(ApplicationDto application,
                                                     Long geofenceId,
                                                     LocalDateTime geofenceWindowStart,
                                                     LocalDateTime geofenceWindowEnd);

}
