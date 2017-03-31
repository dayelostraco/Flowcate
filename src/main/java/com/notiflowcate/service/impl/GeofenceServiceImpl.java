package com.notiflowcate.service.impl;

import com.notiflowcate.exception.GeofenceException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.GeofenceDto;
import com.notiflowcate.model.entity.Geofence;
import com.notiflowcate.repository.GeofenceRepository;
import com.notiflowcate.service.GeofenceService;
import com.notiflowcate.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Service("geofenceService")
public class GeofenceServiceImpl implements GeofenceService {

    private static final Long IOS_PLATFORM_ID = 1L;
    private static final Long ANDROID_PLATFORM_ID = 2L;
    private static final Integer IOS_MAX_GEOFENCES = 15;
    private static final Integer ANDROID_MAX_GEOFENCES = 100;
    private static final Double MAX_DISTANCE_MILES = 5.0; // miles

    private final GeofenceRepository geofenceRepository;
    private final TransformService orikaTransformService;

    @Autowired
    public GeofenceServiceImpl(GeofenceRepository geofenceRepository,
                               TransformService orikaTransformService) {

        this.geofenceRepository = geofenceRepository;
        this.orikaTransformService = orikaTransformService;
    }

    /**
     * Persists a new {@link GeofenceDto}.
     *
     * @param applicationDto {@link ApplicationDto }
     * @param geofenceDto    {@link GeofenceDto} request
     * @return Persisted {@link GeofenceDto}
     * @throws GeofenceException
     */
    @Override
    public GeofenceDto save(ApplicationDto applicationDto, GeofenceDto geofenceDto) throws GeofenceException {

        // Transform GeofenceDto
        geofenceDto.setApplicationId(applicationDto.getApplicationId());
        Geofence geofence = orikaTransformService.map(geofenceDto, Geofence.class);

        // Persist Geofence
        geofence = geofenceRepository.save(geofence);

        // Transform persisted Geofence entity to a GeofenceDto
        return orikaTransformService.map(geofence, GeofenceDto.class);
    }

    /**
     * Updates a Geofence provided that it has an existing record.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param geofenceDto    {@link GeofenceDto}
     * @return Updated {@link GeofenceDto}
     * @throws GeofenceException
     */
    @Override
    public GeofenceDto update(ApplicationDto applicationDto, GeofenceDto geofenceDto) throws GeofenceException {

        // Verify that the Geofence exists
        Geofence geofenceVerification = geofenceRepository.findGeofenceByApplicationIdAndGeofenceId(applicationDto.getApplicationId(), geofenceDto.getGeofenceId());
        if (geofenceVerification == null) {
            throw new GeofenceException("Geofence does not exist");
        }

        // Transform GeofenceDto
        Geofence geofence = orikaTransformService.map(geofenceDto, Geofence.class);
        geofence.setCreated(geofenceVerification.getCreated()); // Retain created date
        geofence.setApplicationId(applicationDto.getApplicationId());

        // Persist Geofence
        geofence = geofenceRepository.save(geofence);

        // Transform persisted Geofence entity to a GeofenceDto
        return orikaTransformService.map(geofence, GeofenceDto.class);
    }

    /**
     * Soft deletes a Geofence.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param geofenceId     {@link Long}
     * @return Updated {@link GeofenceDto}
     * @throws GeofenceException
     */
    @Override
    public void delete(ApplicationDto applicationDto, Long geofenceId) throws GeofenceException {

        // Verify that the Geofence exists
        Geofence geofenceVerification =
                geofenceRepository.findGeofenceByApplicationIdAndGeofenceId(applicationDto.getApplicationId(), geofenceId);
        if (geofenceVerification == null) {
            throw new GeofenceException("Geofence does not exist or you do not have access.");
        }

        // Soft Delete
        geofenceVerification.setDeleted(LocalDateTime.now(ZoneId.of("UTC")));

        // Persist soft deleted Geofence
        geofenceRepository.save(geofenceVerification);
    }

    /**
     * Retrieves a {@link GeofenceDto} by its Primary Key id.
     *
     * @param application {@link ApplicationDto}
     * @param geofenceId  {@link Long}
     * @return Retrieved {@link GeofenceDto} or null if it does not exist.
     */
    @Override
    public GeofenceDto findById(ApplicationDto application, Long geofenceId) {
        return orikaTransformService.map(geofenceRepository.findGeofenceByApplicationIdAndGeofenceId(application.getApplicationId(), geofenceId), GeofenceDto.class);
    }

    /**
     * Retrieves a collection of all Geofences for an Application.
     *
     * @param application {@link ApplicationDto}
     * @return Collection of {@link GeofenceDto}
     */
    @Override
    public List<GeofenceDto> findGeofencesByApplication(ApplicationDto application) {

        // Retrieve Application Geofences
        List<Geofence> applicationGeofences = geofenceRepository.findGeofencesByApplication(application.getApplicationId());

        // Transform Geofence Entities to GeofenceDtos
        return orikaTransformService.mapList(applicationGeofences, GeofenceDto.class);
    }

    /**
     * Retrieves a collection of all Geofences for an Application.
     *
     * @param applicationId ID of application we're searching against.
     * @param latitude      Latitude to search against.
     * @param longitude     Longitude to search against.
     * @param platformId    Platform of the device we're searching for. This helps us decide how many results to return.
     * @return Collection of {@link GeofenceDto}
     */
    @Override
    public List<GeofenceDto> findGeofencesByLocation(Long applicationId, Double latitude, Double longitude, Long platformId) {

        //determine max results
        Integer maxResults;
        if (platformId == IOS_PLATFORM_ID) {
            maxResults = IOS_MAX_GEOFENCES;
        } else if (platformId == ANDROID_PLATFORM_ID) {
            maxResults = ANDROID_MAX_GEOFENCES;
        } else {
            maxResults = IOS_MAX_GEOFENCES;
        }

        // Make Procedure Call
        List<Geofence> nearbyGeofences = new ArrayList<>();
        try {
            nearbyGeofences = geofenceRepository.findGeofencesByLocation(applicationId, latitude, longitude, MAX_DISTANCE_MILES, maxResults);
        } catch (DataIntegrityViolationException e) {
            // DO NOTHING
        }

        // Transform Geofence Entities to GeofenceDtos
        return orikaTransformService.mapList(nearbyGeofences, GeofenceDto.class);
    }

    /**
     * Retrieves a collection of Geofences by the provided Listing id.
     *
     * @param application {@link ApplicationDto}
     * @param listingId   {@link String}
     * @return Collection of {@link GeofenceDto}
     */
    @Override
    public List<GeofenceDto> findGeofencesByListing(ApplicationDto application, String listingId) {

        // Retrieve Listing Geofences
        List<Geofence> listingGeofences = geofenceRepository.findGeofencesByListing(application.getApplicationId(), listingId);

        // Transform Geofence Entities to GeofenceDtos
        return orikaTransformService.mapList(listingGeofences, GeofenceDto.class);
    }
}
