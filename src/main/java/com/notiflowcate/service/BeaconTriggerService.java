package com.notiflowcate.service;

import com.notiflowcate.exception.TriggerException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconTriggerDto;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
public interface BeaconTriggerService {

    BeaconTriggerDto registerBeaconTrigger(ApplicationDto applicationDto, BeaconTriggerDto beaconTriggerDto)
            throws TriggerException;

    BeaconTriggerDto update(ApplicationDto applicationDto, BeaconTriggerDto beaconTriggerDto)
            throws TriggerException;

    List<BeaconTriggerDto> findLatestBeaconTriggers(ApplicationDto application,
                                                    Integer paginationStart, Integer paginationEnd);
}
