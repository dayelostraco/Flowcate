package com.notiflowcate.service.impl;

import com.notiflowcate.exception.BeaconException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconDto;
import com.notiflowcate.model.entity.Beacon;
import com.notiflowcate.repository.BeaconRepository;
import com.notiflowcate.service.BeaconService;
import com.notiflowcate.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Service("beaconService")
public class BeaconServiceImpl implements BeaconService {

    private final BeaconRepository beaconRepository;
    private final TransformService orikaTransformService;

    @Autowired
    public BeaconServiceImpl(BeaconRepository beaconRepository,
                             TransformService orikaTransformService) {

        this.beaconRepository = beaconRepository;
        this.orikaTransformService = orikaTransformService;
    }

    /**
     * Verifies that a Beacon with the provided UUID, Major and Minor doesn't already exist in the system and
     * persists the request {@link BeaconDto}
     *
     * @param applicationDto {@link ApplicationDto}
     * @param beaconDto      {@link com.notiflowcate.model.dto.BeaconDto} to be persisted
     * @return Persisted {@link com.notiflowcate.model.dto.BeaconDto}
     * @throws BeaconException
     */
    @Override
    public BeaconDto save(ApplicationDto applicationDto, BeaconDto beaconDto) throws BeaconException {

        // Verify that the Beacon does not exist
        Beacon beaconVerification = beaconRepository.findBeacon(applicationDto.getApplicationId(),
                beaconDto.getBeaconUUID(), beaconDto.getBeaconMajor(), beaconDto.getBeaconMinor());
        if (beaconVerification != null) {
            throw new BeaconException("Beacon already exists");
        }

        // Transform BeaconDto
        beaconDto.setApplicationId(applicationDto.getApplicationId());
        Beacon beacon = orikaTransformService.map(beaconDto, Beacon.class);

        // Persist Beacon
        beacon = beaconRepository.save(beacon);

        // Transform persisted Beacon entity to a BeaconDto
        return orikaTransformService.map(beacon, BeaconDto.class);
    }

    /**
     * Updates an existing Beacon provided that it already exists in the system. If so, updates the requested
     * record.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param beaconDto      {@link BeaconDto}
     * @return {@link BeaconDto}
     * @throws BeaconException
     */
    @Override
    public BeaconDto update(ApplicationDto applicationDto, BeaconDto beaconDto) throws BeaconException {

        // Verify that the Beacon exists
        Beacon beaconVerification = beaconRepository.findOne(beaconDto.getBeaconId());
        if (beaconVerification == null) {
            throw new BeaconException("Beacon does not exist");
        }

        // Transform BeaconDto
        Beacon beacon = orikaTransformService.map(beaconDto, Beacon.class);
        beacon.setCreated(beaconVerification.getCreated()); // Retain created date
        beacon.setApplicationId(applicationDto.getApplicationId());

        // Persist Beacon
        beacon = beaconRepository.save(beacon);

        // Transform persisted Beacon entity to a BeaconDto
        return orikaTransformService.map(beacon, BeaconDto.class);
    }

    /**
     * Soft deletes a Beacon.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param beaconId       {@link Long}
     * @throws BeaconException
     */
    @Override
    public void delete(ApplicationDto applicationDto, Long beaconId) throws BeaconException {

        // Verify that the Geofence exists
        Beacon beaconVerification =
                beaconRepository.findBeaconByBeaconIdAndApplicationId(applicationDto.getApplicationId(), beaconId);
        if (beaconVerification == null) {
            throw new BeaconException("Beacon does not exist or you do not have access.");
        }

        // Soft Delete
        beaconVerification.setDeleted(LocalDateTime.now(ZoneId.of("UTC")));

        // Persist soft deleted Beacon
        beaconRepository.save(beaconVerification);
    }

    /**
     * Retrieves a Beacon by it's Primary Key id.
     *
     * @param application {@link ApplicationDto}
     * @param beaconId    Long
     * @return Retrieved {@link BeaconDto}. Returns null if one is not found.
     */
    @Override
    public BeaconDto findById(ApplicationDto application, Long beaconId) {
        return orikaTransformService.map(beaconRepository.findOne(beaconId), BeaconDto.class);
    }

    /**
     * Retrieves a {@link BeaconDto} based on the provided beaconUUID, beaconMajor and beaconMinor only if it
     * is listed as active in the repository.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param beaconUUID     {@link String}
     * @param beaconMajor    {@link Integer}
     * @param beaconMinor    {@link Integer}
     * @return BeaconDto
     */
    @Override
    public BeaconDto findActiveBeacon(ApplicationDto applicationDto, String beaconUUID, Integer beaconMajor, Integer beaconMinor) {

        // Find Beacon
        Beacon beacon = beaconRepository.findActiveBeacon(applicationDto.getApplicationId(), beaconUUID, beaconMajor, beaconMinor);

        // Transform Beacon Entity to BeaconDto
        return orikaTransformService.map(beacon, BeaconDto.class);
    }

    /**
     * Retrieves all Beacons registered for an Application regardless of its active flag.
     *
     * @param application {@link ApplicationDto}
     * @return Collection of {@link BeaconDto}
     */
    @Override
    public List<BeaconDto> findBeaconsByApplication(ApplicationDto application) {

        // Retrieve Application Beacons
        List<Beacon> applicationBeacons = beaconRepository.findBeaconsByApplication(application.getApplicationId());

        // Transform Beacon Entities to BeaconDtos
        return orikaTransformService.mapList(applicationBeacons, BeaconDto.class);
    }

    /**
     * Retrieves all registered Beacons for a Listing regardless of its active flag.
     *
     * @param application {@link ApplicationDto}
     * @param listingId   {@link String}
     * @return Collection of Listing {@link BeaconDto}
     */
    @Override
    public List<BeaconDto> findBeaconsByListing(ApplicationDto application, String listingId) {

        // Retrieve Listing Beacons
        List<Beacon> listingBeacons = beaconRepository.findBeaconsByListing(application.getApplicationId(), listingId);

        // Transform Beacon Entities to BeaconDtos
        return orikaTransformService.mapList(listingBeacons, BeaconDto.class);
    }
}
