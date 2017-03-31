package com.notiflowcate.service.impl;

import com.notiflowcate.exception.TriggerException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.GeofenceTriggerDto;
import com.notiflowcate.model.entity.Geofence;
import com.notiflowcate.model.entity.GeofenceEvent;
import com.notiflowcate.model.entity.GeofenceTrigger;
import com.notiflowcate.repository.ApplicationRepository;
import com.notiflowcate.repository.GeofenceEventRepository;
import com.notiflowcate.repository.GeofenceRepository;
import com.notiflowcate.repository.GeofenceTriggerRepository;
import com.notiflowcate.service.GeofenceTriggerService;
import com.notiflowcate.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
@Service(value = "geofenceTriggerService")
public class GeofenceTriggerServiceImpl implements GeofenceTriggerService {

    private final TransformService orikaTransformService;
    private final ApplicationRepository applicationRepository;
    private final GeofenceTriggerRepository geofenceTriggerRepository;
    private final GeofenceRepository geofenceRepository;
    private final GeofenceEventRepository geofenceEventRepository;

    @Autowired
    public GeofenceTriggerServiceImpl(TransformService orikaTransformService,
                                      ApplicationRepository applicationRepository,
                                      GeofenceTriggerRepository geofenceTriggerRepository,
                                      GeofenceRepository geofenceRepository,
                                      GeofenceEventRepository geofenceEventRepository) {

        this.orikaTransformService = orikaTransformService;
        this.applicationRepository = applicationRepository;
        this.geofenceTriggerRepository = geofenceTriggerRepository;
        this.geofenceRepository = geofenceRepository;
        this.geofenceEventRepository = geofenceEventRepository;
    }

    /**
     * Service interface for saving a new {@link GeofenceTrigger} from a {@link GeofenceTriggerDto}.
     *
     * @param geofenceTriggerDto {@link GeofenceTriggerDto} to persist
     * @return Persisted {@link GeofenceTriggerDto} record in DTO form
     */
    @Override
    public GeofenceTriggerDto save(ApplicationDto applicationDto, GeofenceTriggerDto geofenceTriggerDto)
            throws TriggerException {

        // Verify that the Geofence triggered exists and is active
        Geofence geofenceVerification =
                geofenceRepository.findGeofenceByApplicationIdAndGeofenceId(
                        applicationDto.getApplicationId(), geofenceTriggerDto.getGeofenceId());
        if (geofenceVerification == null || !geofenceVerification.getActive()) {
            throw new TriggerException("Triggered Geofence is not active");
        }

        // Set Enter/Exit/Dwell time
        if (geofenceTriggerDto.getOnEnter()) {
            geofenceTriggerDto.setEnterTime(LocalDateTime.now(ZoneId.of("UTC")));
        } else if (geofenceTriggerDto.getOnExit()) {
            geofenceTriggerDto.setExitTime(LocalDateTime.now(ZoneId.of("UTC")));
        } else if(geofenceTriggerDto.getOnDwell()) {
            geofenceTriggerDto.setDwellTime(LocalDateTime.now(ZoneId.of("UTC")));
        } else {
            throw new TriggerException("Invalid Trigger Event Type.");
        }

        // Transform GeofenceTriggerDto to a GeofenceTrigger entity and set the attached Geofence entity to it
        GeofenceTrigger geofenceTrigger = orikaTransformService.map(geofenceTriggerDto, GeofenceTrigger.class);

        // Add attached entity relationships to GeofenceTrigger
        GeofenceEvent geofenceEvent;
        if (geofenceTrigger.getNotificationEventId() != null) {
            geofenceEvent = geofenceEventRepository.findOne(geofenceTrigger.getNotificationEventId());
            geofenceTrigger.setNotificationEventId(geofenceEvent.getNotificationEventId());
        }
        geofenceTrigger.setGeofenceId(geofenceVerification.getGeofenceId());
        geofenceTrigger.setApplicationId(applicationDto.getApplicationId());

        // Save the GeofenceTrigger
        geofenceTrigger = geofenceTriggerRepository.save(geofenceTrigger);

        // Convert the saved GeofenceTrigger entity back to a GeofenceTriggerDTO
        return orikaTransformService.map(geofenceTrigger, GeofenceTriggerDto.class);
    }

    /**
     * Updates an existing {@link GeofenceTriggerDto} provided that it exists in the system.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param geofenceTriggerDto {@link GeofenceTriggerDto}
     * @return GeofenceTriggerDto
     * @throws TriggerException
     */
    @Override
    public GeofenceTriggerDto update(ApplicationDto applicationDto, GeofenceTriggerDto geofenceTriggerDto)
            throws TriggerException {

        GeofenceTrigger geofenceTriggerVerification =
                geofenceTriggerRepository.findGeofenceTriggerByApplicationIdAndNotificationTriggerId(applicationDto.getApplicationId(), geofenceTriggerDto.getNotificationTriggerId());
        if(geofenceTriggerVerification == null) {
            throw new TriggerException("GeofenceTrigger verification failed.");
        }

        // Transform GeofenceTriggerDto to a GeofenceTrigger entity and set the attached Geofence entity to it
        GeofenceTrigger geofenceTrigger = orikaTransformService.map(geofenceTriggerDto, GeofenceTrigger.class);
        geofenceTrigger.setCreated(geofenceTriggerVerification.getCreated()); //retain created

        // Save the GeofenceTrigger
        geofenceTrigger = geofenceTriggerRepository.save(geofenceTrigger);

        // Convert the saved GeofenceTrigger entity back to a GeofenceTriggerDTO
        return orikaTransformService.map(geofenceTrigger, GeofenceTriggerDto.class);
    }

    /**
     * Retrieves the latest {@link GeofenceTriggerDto} using paginationStart and paginationEnd and the Limit
     * parameters
     *
     * @param application {@link ApplicationDto}
     * @param paginationStart {@link Integer}
     * @param paginationEnd {@link Integer}
     * @return Collection of the latest {@link GeofenceTriggerDto}
     */
    @Override
    public List<GeofenceTriggerDto> findLatestGeofenceTriggers(ApplicationDto application, Integer paginationStart, Integer paginationEnd) {
        Pageable pageRequest = new PageRequest(paginationStart, paginationEnd);
        return orikaTransformService.mapList(geofenceTriggerRepository.findLatestGeofenceTriggers(application.getApplicationId(), pageRequest), GeofenceTriggerDto.class);
    }

    /**
     * Retrieves a collection of {@link List<GeofenceTriggerDto>} for a specific beacon between a start and end time
     * parameters
     *
     * @param application {@link ApplicationDto}
     * @param geofenceId {@link Long}
     * @param geofenceWindowStart {@link LocalDateTime}
     * @param geofenceWindowEnd {@link LocalDateTime}
     * @return Collection of {@link List<GeofenceTriggerDto>}
     */
    @Override
    public List<GeofenceTriggerDto> findUsersWithinGeofence(ApplicationDto application, Long geofenceId, LocalDateTime geofenceWindowStart, LocalDateTime geofenceWindowEnd) {
        return orikaTransformService.mapList(geofenceTriggerRepository.findUsersWithinGeofence(application.getApplicationId(), geofenceId, geofenceWindowStart, geofenceWindowEnd), GeofenceTriggerDto.class);
    }
}
