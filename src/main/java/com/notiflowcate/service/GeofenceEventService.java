package com.notiflowcate.service;

import com.notiflowcate.exception.GeofenceException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.GeofenceEventDto;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/23/16
 */
public interface GeofenceEventService {

    GeofenceEventDto save(GeofenceEventDto geofenceEventDto) throws GeofenceException;

    GeofenceEventDto update(GeofenceEventDto geofenceEventDto) throws GeofenceException;

    void delete(ApplicationDto application, Long notificationEventId) throws GeofenceException;

    List<GeofenceEventDto> findActiveGeofenceEvents(String apiKey, Long geofenceId) throws GeofenceException;

    List<GeofenceEventDto> getAllGeofenceEvents() throws GeofenceException;

    GeofenceEventDto findById(Long id) throws GeofenceException;
}
