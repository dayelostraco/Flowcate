package com.notiflowcate.service.impl;

import com.notiflowcate.exception.BeaconException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconEventDto;
import com.notiflowcate.model.entity.Application;
import com.notiflowcate.model.entity.Beacon;
import com.notiflowcate.model.entity.BeaconEvent;
import com.notiflowcate.repository.ApplicationRepository;
import com.notiflowcate.repository.BeaconEventRepository;
import com.notiflowcate.repository.BeaconRepository;
import com.notiflowcate.service.BeaconEventService;
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
@Service("beaconEventService")
public class BeaconEventServiceImpl implements BeaconEventService {

    private final TransformService orikaTransformService;
    private final BeaconEventRepository beaconEventRepository;
    private final BeaconRepository beaconRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public BeaconEventServiceImpl(TransformService orikaTransformService,
                                  BeaconEventRepository beaconEventRepository,
                                  BeaconRepository beaconRepository,
                                  ApplicationRepository applicationRepository) {

        this.orikaTransformService = orikaTransformService;
        this.beaconEventRepository = beaconEventRepository;
        this.beaconRepository = beaconRepository;
        this.applicationRepository = applicationRepository;
    }

    /**
     * Validates the {@link BeaconEventDto}, persists the {@link BeaconEvent} and returns
     * the persisted {@link BeaconEventDto}.
     *
     * @param beaconEventDto {@link BeaconEventDto}
     * @return Persisted {@link BeaconEventDto}
     * @throws BeaconException
     */
    @Override
    public BeaconEventDto save(BeaconEventDto beaconEventDto) throws BeaconException {

        // Verify that the Beacon triggered exists and is active
        Beacon beaconVerification = beaconRepository.findOne(beaconEventDto.getBeaconId());
        if (beaconVerification == null || !beaconVerification.getActive()) {
            throw new BeaconException("Assigned Beacon is not active");
        }

        // Convert BeaconEventDto to BeaconEvent Entity
        BeaconEvent beaconEvent = orikaTransformService.map(beaconEventDto, BeaconEvent.class);

        // Set start and end dates to the exact start and end hours/minutes/seconds/nanoseconds
        LocalDateTime startDate = beaconEvent.getStartDate();
        startDate = startDate.withHour(0);
        startDate = startDate.withMinute(0);
        startDate = startDate.withSecond(0);
        startDate = startDate.withNano(0);
        beaconEvent.setStartDate(startDate);

        LocalDateTime endDate = beaconEvent.getEndDate();
        endDate = endDate.withHour(23);
        endDate = endDate.withMinute(59);
        endDate = endDate.withSecond(59);
        endDate = endDate.withNano(999);
        beaconEvent.setEndDate(endDate);

        // Persist new BeaconEvent
        beaconEvent = beaconEventRepository.save(beaconEvent);

        // Convert and Return BeaconEvent
        return orikaTransformService.map(beaconEvent, BeaconEventDto.class);
    }

    /**
     * Update an existing beacon event
     *
     * @param beaconEventDto Object representing the updated beacon event
     * @return The updated {@link BeaconEvent} as a {@link BeaconEventDto}
     * @throws BeaconException
     */
    @Override
    public BeaconEventDto update(BeaconEventDto beaconEventDto) throws BeaconException {

        // Verify that the Beacon exists
        BeaconEvent beaconVerification =
                beaconEventRepository.findBeaconEventByApplicationIdAndNotificationEventId(beaconEventDto.getApplicationId(), beaconEventDto.getNotificationEventId());
        if (beaconVerification == null) {
            throw new BeaconException("No beacon event found");
        }

        // Convert BeaconEventDto to BeaconEvent Entity
        BeaconEvent beaconEvent = orikaTransformService.map(beaconEventDto, BeaconEvent.class);
        beaconEvent.setCreated(beaconVerification.getCreated()); // Retain createdDate

        // Set start and end dates to the exact start and end hours/minutes/seconds/nanoseconds
        LocalDateTime startDate = beaconEvent.getStartDate();
        startDate = startDate.withHour(0);
        startDate = startDate.withMinute(0);
        startDate = startDate.withSecond(0);
        startDate = startDate.withNano(0);
        beaconEvent.setStartDate(startDate);

        LocalDateTime endDate = beaconEvent.getEndDate();
        endDate = endDate.withHour(23);
        endDate = endDate.withMinute(59);
        endDate = endDate.withSecond(59);
        endDate = endDate.withNano(999);
        beaconEvent.setEndDate(endDate);

        // Persist new BeaconEvent
        beaconEvent = beaconEventRepository.save(beaconEvent);

        // Convert and Return BeaconEvent
        return orikaTransformService.map(beaconEvent, BeaconEventDto.class);
    }

    /**
     * Deletes a BeaconEvent that has the passed in notificationEventId.
     *
     * @param application         {@link ApplicationDto}
     * @param notificationEventId {@link Long}
     * @throws BeaconException Is thrown when the provided notificationEventId is not valid
     */
    @Override
    public void delete(ApplicationDto application, Long notificationEventId) throws BeaconException {

        // Verify that the Beacon Event exists
        BeaconEvent beaconEventVerification =
                beaconEventRepository.findBeaconEventByApplicationIdAndNotificationEventId(application.getApplicationId(), notificationEventId);
        if (beaconEventVerification == null) {
            throw new BeaconException("No beacon event found or application does not have access.");
        }

        // Soft Delete
        beaconEventVerification.setDeleted(LocalDateTime.now(ZoneId.of("UTC")));

        // Persist soft deleted BeaconEvent
        beaconEventRepository.save(beaconEventVerification);
    }

    /**
     * Returns a collection of active {@link BeaconEvent} for the passed in beaconId and
     * returns a {@link BeaconEventDto}
     *
     * @param apiKey   String representation of the {@link Application} apiKey
     * @param beaconId Long ID of the requested {@link Beacon}
     * @return The most recent active {@link BeaconEvent} as a {@link BeaconEventDto}
     * @throws BeaconException
     */
    @Override
    public List<BeaconEventDto> findActiveBeaconEvents(String apiKey, Long beaconId) throws BeaconException {

        // Retrieve application record by apiKey
        Application application = applicationRepository.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            throw new BeaconException("You did not provide a valid ApiKey.");
        }

        // Verify that the Beacon triggered exists and is active
        Beacon beaconVerification = beaconRepository.findOne(beaconId);
        if (beaconVerification == null || !beaconVerification.getActive()) {
            throw new BeaconException("Assigned Beacon is not active");
        }

        List<BeaconEvent> activeBeaconEvents =
                beaconEventRepository.findActiveBeaconEventByBeaconId(application.getApplicationId(), beaconId,
                        LocalDateTime.now(ZoneOffset.UTC));

        // Convert BeaconEvent to BeaconEventDto
        return orikaTransformService.mapList(activeBeaconEvents, BeaconEventDto.class);
    }

    /**
     * Returns all available BeaconEvents across all applications.
     *
     * @return List of {@link BeaconEventDto}
     * @throws BeaconException
     */
    @Override
    public List<BeaconEventDto> getAllBeaconEvents() throws BeaconException {
        List<BeaconEvent> events = beaconEventRepository.findAllApplicationBeaconEvents();
        if (events.size() > 0) {
            return orikaTransformService.mapList(events, BeaconEventDto.class);
        }

        return new ArrayList<>();
    }

    /**
     * Retrieves a specific {@link BeaconEventDto} by it's Primary Key id.
     *
     * @param id Long
     * @return {@link BeaconEventDto}
     * @throws BeaconException
     */
    @Override
    public BeaconEventDto findById(Long id) throws BeaconException {
        BeaconEvent beaconEvent = beaconEventRepository.findOne(id);
        return orikaTransformService.map(beaconEvent, BeaconEventDto.class);
    }
}
