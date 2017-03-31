package com.notiflowcate.service;

import com.notiflowcate.exception.BeaconException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconEventDto;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/23/16
 */
public interface BeaconEventService {

    BeaconEventDto save(BeaconEventDto beaconEventDto) throws BeaconException;

    BeaconEventDto update(BeaconEventDto beaconEventDto) throws BeaconException;

    void delete(ApplicationDto application, Long notificationEventId) throws BeaconException;

    List<BeaconEventDto> findActiveBeaconEvents(String apiKey, Long beaconId) throws BeaconException;

    List<BeaconEventDto> getAllBeaconEvents() throws BeaconException;

    BeaconEventDto findById(Long id) throws BeaconException;
}
