package com.notiflowcate.service;

import com.notiflowcate.exception.GeofenceException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.GeofenceDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Service("geofenceService")
public interface GeofenceService {

    GeofenceDto save(ApplicationDto application, GeofenceDto geofenceDto) throws GeofenceException;

    GeofenceDto update(ApplicationDto application, GeofenceDto geofenceDto) throws GeofenceException;

    void delete(ApplicationDto applicationDto, Long geofenceId) throws GeofenceException;

    GeofenceDto findById(ApplicationDto application, Long geofenceId);

    List<GeofenceDto> findGeofencesByApplication(ApplicationDto application);

    List<GeofenceDto> findGeofencesByLocation(Long applicationId, Double latitude, Double longitude, Long platformId);

    List<GeofenceDto> findGeofencesByListing(ApplicationDto application, String listingId);
}
