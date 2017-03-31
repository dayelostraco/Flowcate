package com.notiflowcate.service;

import com.notiflowcate.exception.BeaconException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Service("beaconService")
public interface BeaconService {

    BeaconDto save(ApplicationDto application, BeaconDto beaconDto) throws BeaconException;

    BeaconDto update(ApplicationDto application, BeaconDto beaconDto) throws BeaconException;

    void delete(ApplicationDto applicationDto, Long beaconId) throws BeaconException;
    
    BeaconDto findById(ApplicationDto application, Long beaconId);

    BeaconDto findActiveBeacon(ApplicationDto application, String beaconUUID, Integer beaconMajor, Integer beaconMinor);

    List<BeaconDto> findBeaconsByApplication(ApplicationDto application);

    List<BeaconDto> findBeaconsByListing(ApplicationDto application, String listingId);
}
