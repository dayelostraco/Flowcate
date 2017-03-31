package com.notiflowcate.service;

import com.notiflowcate.exception.DeviceTokenException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.DeviceTokenDto;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 12/21/15.
 */
public interface DeviceTokenService {

    List<DeviceTokenDto> findDeviceTokensByApplicationIdAndAppUserId(ApplicationDto applicationDto, String appUserId);

    DeviceTokenDto findDeviceTokenById(Long deviceTokenId) throws DeviceTokenException;

    DeviceTokenDto createToken(DeviceTokenDto deviceTokenDto);

    DeviceTokenDto updateToken(ApplicationDto applicationDto, Long deviceTokenId,
                               DeviceTokenDto deviceTokenDto) throws DeviceTokenException;
}
