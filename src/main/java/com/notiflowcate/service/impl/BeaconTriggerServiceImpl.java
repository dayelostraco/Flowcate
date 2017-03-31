package com.notiflowcate.service.impl;

import com.notiflowcate.exception.TriggerException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconTriggerDto;
import com.notiflowcate.model.entity.Beacon;
import com.notiflowcate.model.entity.BeaconEvent;
import com.notiflowcate.model.entity.BeaconTrigger;
import com.notiflowcate.repository.ApplicationRepository;
import com.notiflowcate.repository.BeaconEventRepository;
import com.notiflowcate.repository.BeaconRepository;
import com.notiflowcate.repository.BeaconTriggerRepository;
import com.notiflowcate.service.BeaconTriggerService;
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
@Service(value = "beaconTriggerService")
public class BeaconTriggerServiceImpl implements BeaconTriggerService {

    private final TransformService orikaTransformService;
    private final ApplicationRepository applicationRepository;
    private final BeaconTriggerRepository beaconTriggerRepository;
    private final BeaconRepository beaconRepository;
    private final BeaconEventRepository beaconEventRepository;

    @Autowired
    public BeaconTriggerServiceImpl(TransformService orikaTransformService,
                                    ApplicationRepository applicationRepository,
                                    BeaconTriggerRepository beaconTriggerRepository,
                                    BeaconRepository beaconRepository,
                                    BeaconEventRepository beaconEventRepository) {

        this.orikaTransformService = orikaTransformService;
        this.applicationRepository = applicationRepository;
        this.beaconTriggerRepository = beaconTriggerRepository;
        this.beaconRepository = beaconRepository;
        this.beaconEventRepository = beaconEventRepository;
    }

    /**
     * Service interface for saving a new {@link BeaconTrigger} from a {@link BeaconTriggerDto}. Confirms that
     * the Beacon triggered is registered and active. If so, the trigger event is timestamped based on whether the
     * user entered or exited the beacon region.
     *
     * @param applicationDto   {@link ApplicationDto}
     * @param beaconTriggerDto {@link BeaconTriggerDto} to persist
     * @return Persisted {@link BeaconTriggerDto} record in DTO form
     */
    @Override
    public BeaconTriggerDto registerBeaconTrigger(ApplicationDto applicationDto, BeaconTriggerDto beaconTriggerDto)
            throws TriggerException {

        // Verify that the Beacon triggered exists and is active
        Beacon beaconVerification = beaconRepository.findBeaconByBeaconIdAndApplicationId(applicationDto.getApplicationId(), beaconTriggerDto.getBeaconId());
        if (beaconVerification == null || !beaconVerification.getActive()) {
            throw new TriggerException("Triggered Beacon is not active");
        }

        // Set Enter/Exit time
        if (beaconTriggerDto.getOnEnter()) {
            beaconTriggerDto.setEnterTime(LocalDateTime.now(ZoneId.of("UTC")));
        } else if (beaconTriggerDto.getOnExit()) {
            beaconTriggerDto.setExitTime(LocalDateTime.now(ZoneId.of("UTC")));
        } else {
            throw new TriggerException("Invalid Trigger Event Type.");
        }

        // Transform BeaconTriggerDto to a BeaconTrigger entity and set the attached Beacon entity to it
        BeaconTrigger beaconTrigger = orikaTransformService.map(beaconTriggerDto, BeaconTrigger.class);

        // Add attached entity relationships to BeaconTrigger
        BeaconEvent beaconEvent;
        if (beaconTriggerDto.getNotificationEventId() != null) {
            beaconEvent = beaconEventRepository.findOne(beaconTriggerDto.getNotificationEventId());
            beaconTrigger.setNotificationEventId(beaconEvent.getNotificationEventId());
        }
        beaconTrigger.setBeaconId(beaconVerification.getBeaconId());
        beaconTrigger.setApplicationId(applicationDto.getApplicationId());

        // Save the BeaconTrigger
        beaconTrigger = beaconTriggerRepository.save(beaconTrigger);

        // Convert the saved BeaconTrigger entity back to a BeaconTriggerDTO
        return orikaTransformService.map(beaconTrigger, BeaconTriggerDto.class);
    }

    /**
     * Updates an existing {@link BeaconTriggerDto} record provided that it exists and the Beacon associated
     * with it exists.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param beaconTriggerDto {@link BeaconTriggerDto}
     * @return BeaconTriggerDto
     * @throws TriggerException
     */
    @Override
    public BeaconTriggerDto update(ApplicationDto applicationDto, BeaconTriggerDto beaconTriggerDto)
            throws TriggerException {

        BeaconTrigger beaconTriggerVerification =
                beaconTriggerRepository.findBeaconTriggerByApplicationIdAndNotificationTriggerId(applicationDto.getApplicationId(), beaconTriggerDto.getNotificationTriggerId());
        if (beaconTriggerVerification == null) {
            throw new TriggerException("BeaconTrigger verification failed.");
        }

        // Transform BeaconTriggerDto to a BeaconTrigger entity and set the attached Beacon entity to it
        BeaconTrigger beaconTrigger = orikaTransformService.map(beaconTriggerDto, BeaconTrigger.class);
        beaconTrigger.setCreated(beaconTriggerVerification.getCreated()); // Retain createdDate

        // Save the BeaconTrigger
        beaconTrigger = beaconTriggerRepository.save(beaconTrigger);

        // Convert the saved BeaconTrigger entity back to a BeaconTriggerDTO
        return orikaTransformService.map(beaconTrigger, BeaconTriggerDto.class);
    }

    /**
     * Retrieves the latest {@link BeaconTriggerDto} using paginationStart and paginationEnd and the Limit
     * parameters
     *
     * @param application {@link ApplicationDto}
     * @param paginationStart {@link Integer}
     * @param paginationEnd {@link Integer}
     * @return Collection of the latest {@link BeaconTriggerDto}
     */
    @Override
    public List<BeaconTriggerDto> findLatestBeaconTriggers(ApplicationDto application, Integer paginationStart,
                                                           Integer paginationEnd) {

        Pageable pageRequest = new PageRequest(paginationStart, paginationEnd);
        return orikaTransformService.mapList(beaconTriggerRepository.findLatestBeaconTriggers(application.getApplicationId(), pageRequest), BeaconTriggerDto.class);
    }
}
