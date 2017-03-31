package com.notiflowcate.service.impl;

import com.notiflowcate.exception.GeofenceException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.GeofenceEventDto;
import com.notiflowcate.model.entity.Application;
import com.notiflowcate.model.entity.Geofence;
import com.notiflowcate.model.entity.GeofenceEvent;
import com.notiflowcate.repository.ApplicationRepository;
import com.notiflowcate.repository.GeofenceEventRepository;
import com.notiflowcate.repository.GeofenceRepository;
import com.notiflowcate.service.GeofenceEventService;
import com.notiflowcate.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/23/16
 */
@Service("geofenceEventService")
public class GeofenceEventServiceImpl implements GeofenceEventService {

    private final TransformService orikaTransformService;
    private final GeofenceEventRepository geofenceEventRepository;
    private final GeofenceRepository geofenceRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public GeofenceEventServiceImpl(TransformService orikaTransformService,
                                    GeofenceEventRepository geofenceEventRepository,
                                    GeofenceRepository geofenceRepository,
                                    ApplicationRepository applicationRepository) {

        this.orikaTransformService = orikaTransformService;
        this.geofenceEventRepository = geofenceEventRepository;
        this.geofenceRepository = geofenceRepository;
        this.applicationRepository = applicationRepository;
    }

    /**
     * Validates the {@link GeofenceEventDto}, persists the {@link GeofenceEvent} and returns
     * the persisted {@link GeofenceEventDto}.
     *
     * @param geofenceEventDto {@link GeofenceEventDto}
     * @return Persisted {@link GeofenceEventDto}
     * @throws GeofenceException
     */
    @Override
    public GeofenceEventDto save(GeofenceEventDto geofenceEventDto)
            throws GeofenceException {

        // Verify that the Geofence triggered exists and is active
        Geofence geofenceVerification = geofenceRepository.findOne(geofenceEventDto.getGeofenceId());
        if (geofenceVerification == null || !geofenceVerification.getActive()) {
            throw new GeofenceException("Assigned Geofence is not active");
        }

        // Convert GeofenceEventDto to GeofenceEvent Entity
        GeofenceEvent geofenceEvent = orikaTransformService.map(geofenceEventDto, GeofenceEvent.class);

        // Set start and end dates to the exact start and end hours/minutes/seconds/nanoseconds
        LocalDateTime startDate = geofenceEvent.getStartDate();
        startDate = startDate.withHour(0);
        startDate = startDate.withMinute(0);
        startDate = startDate.withSecond(0);
        startDate = startDate.withNano(0);
        geofenceEvent.setStartDate(startDate);

        LocalDateTime endDate = geofenceEvent.getEndDate();
        endDate = endDate.withHour(23);
        endDate = endDate.withMinute(59);
        endDate = endDate.withSecond(59);
        endDate = endDate.withNano(999);
        geofenceEvent.setEndDate(endDate);

        // Persist new GeofenceEvent
        geofenceEvent = geofenceEventRepository.save(geofenceEvent);

        // Convert and Return GeofenceEvent
        return orikaTransformService.map(geofenceEvent, GeofenceEventDto.class);
    }


    /**
     * Updates an existing {@link GeofenceEventDto} provided that it exists.
     *
     * @param geofenceEventDto {@link GeofenceEventDto}
     * @return GeofenceEventDto
     * @throws GeofenceException
     */
    @Override
    public GeofenceEventDto update(GeofenceEventDto geofenceEventDto) throws GeofenceException {

        // Verify that the Geofence Event exists
        GeofenceEvent geofenceEventVerification =
                geofenceEventRepository.findGeofenceEventByApplicationIdAndNotificationEventId(geofenceEventDto.getApplicationId(), geofenceEventDto.getNotificationEventId());
        if (geofenceEventVerification == null) {
            throw new GeofenceException("No geofence event found");
        }

        // Convert GeofenceEventDto to GeofenceEvent Entity
        GeofenceEvent geofenceEvent = orikaTransformService.map(geofenceEventDto, GeofenceEvent.class);
        geofenceEvent.setCreated(geofenceEventVerification.getCreated()); // retain createdDate

        // Set start and end dates to the exact start and end hours/minutes/seconds/nanoseconds
        LocalDateTime startDate = geofenceEvent.getStartDate();
        startDate = startDate.withHour(0);
        startDate = startDate.withMinute(0);
        startDate = startDate.withSecond(0);
        startDate = startDate.withNano(0);
        geofenceEvent.setStartDate(startDate);

        LocalDateTime endDate = geofenceEvent.getEndDate();
        endDate = endDate.withHour(23);
        endDate = endDate.withMinute(59);
        endDate = endDate.withSecond(59);
        endDate = endDate.withNano(999);
        geofenceEvent.setEndDate(endDate);

        // Persist new GeofenceEvent
        geofenceEvent = geofenceEventRepository.save(geofenceEvent);

        // Convert and Return GeofenceEvent
        return orikaTransformService.map(geofenceEvent, GeofenceEventDto.class);
    }

    /**
     * Soft deletes a BeaconEvent that has the passed in notificationEventId.
     *
     * @param application         {@link ApplicationDto}
     * @param notificationEventId {@link Long}
     * @throws GeofenceException Is thrown when the provided notificationEventId is not valid
     */
    @Override
    public void delete(ApplicationDto application, Long notificationEventId) throws GeofenceException {

        // Verify that the Geofence Event exists
        GeofenceEvent geofenceEventVerification =
                geofenceEventRepository.findGeofenceEventByApplicationIdAndNotificationEventId(application.getApplicationId(), notificationEventId);
        if (geofenceEventVerification == null) {
            throw new GeofenceException("No geofence event found or application does not have access.");
        }

        // Soft Delete
        geofenceEventVerification.setDeleted(LocalDateTime.now(ZoneId.of("UTC")));

        // Persist soft deleted GeofenceEvent
        geofenceEventRepository.save(geofenceEventVerification);
    }

    /**
     * Returns all active {@link GeofenceEvent} for the passed in geofenceId and returns
     * a collection of {@link GeofenceEventDto}.
     *
     * @param apiKey     String representation of the {@link Application} apiKey
     * @param geofenceId Long ID of the requested {@link Geofence}
     * @return All active {@link GeofenceEvent} as a {@link GeofenceEventDto}
     * @throws GeofenceException
     */
    @Override
    public List<GeofenceEventDto> findActiveGeofenceEvents(String apiKey, Long geofenceId) throws GeofenceException {

        // Retrieve application record by apiKey
        Application application = applicationRepository.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            throw new GeofenceException("You did not provide a valid ApiKey.");
        }

        // Verify that the Geofence triggered exists and is active
        Geofence geofenceVerification = geofenceRepository.findOne(geofenceId);
        if (geofenceVerification == null || !geofenceVerification.getActive()) {
            throw new GeofenceException("Assigned Geofence is not active");
        }

        List<GeofenceEvent> activeGeofenceEvents =
                geofenceEventRepository.findActiveGeofenceEventByGeofenceId(application.getApplicationId(), geofenceId,
                        LocalDateTime.now(ZoneOffset.UTC));

        // Convert GeofenceEvent to GeofenceEventDto
        return orikaTransformService.mapList(activeGeofenceEvents, GeofenceEventDto.class);
    }

    /**
     * Returns all available GeofenceEvents across all applications.
     *
     * @return List of {@link GeofenceEventDto}
     * @throws GeofenceException
     */
    @Override
    public List<GeofenceEventDto> getAllGeofenceEvents() throws GeofenceException {

        List<GeofenceEvent> events =
                geofenceEventRepository.findAllApplicationGeofenceEvents();

        if (events.size() > 0) {
            return orikaTransformService.mapList(events, GeofenceEventDto.class);
        }

        return new ArrayList<>();
    }

    /**
     * Retrieves a {@link GeofenceEventDto} by its Primary Key id.
     *
     * @param id {@link Long}
     * @return Retrieved {@link GeofenceEventDto} or null if it does not exist.
     * @throws GeofenceException
     */
    @Override
    public GeofenceEventDto findById(Long id) throws GeofenceException {
        GeofenceEvent geofenceEvent = geofenceEventRepository.findOne(id);
        return orikaTransformService.map(geofenceEvent, GeofenceEventDto.class);
    }
}
