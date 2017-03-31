package com.notiflowcate.service.impl;

import com.notiflowcate.exception.DeviceTokenException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.DeviceTokenDto;
import com.notiflowcate.model.entity.DeviceToken;
import com.notiflowcate.repository.DeviceTokenRepository;
import com.notiflowcate.service.DeviceTokenService;
import com.notiflowcate.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 12/21/15.
 */
@Service("deviceTokenService")
public class DeviceTokenServiceImpl implements DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;
    private final TransformService orikaTransformService;

    @Autowired
    public DeviceTokenServiceImpl(DeviceTokenRepository deviceTokenRepository,
                                  TransformService orikaTransformService) {

        this.deviceTokenRepository = deviceTokenRepository;
        this.orikaTransformService = orikaTransformService;
    }

    /**
     * Retrieves the available {@link DeviceTokenDto} for an appUser by {@link ApplicationDto}.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param appUserId String
     * @return DeviceTokenDto {@link List}
     */
    public List<DeviceTokenDto> findDeviceTokensByApplicationIdAndAppUserId(ApplicationDto applicationDto, String appUserId) {

        // Find DeviceTokens
        List<DeviceToken> deviceTokens = deviceTokenRepository.findDeviceTokensByApplicationAndAppUser(applicationDto.getApplicationId(), appUserId);


        // Convert and return available DeviceTokens
        return orikaTransformService.mapList(deviceTokens, DeviceTokenDto.class);
    }

    /**
     * Locates a {@link DeviceTokenDto} by deviceTokenId.
     *
     * @param deviceTokenId Long
     * @return Retrieved deviceTokenDto {@link DeviceTokenDto}
     * @throws DeviceTokenException
     */
    @Override
    public DeviceTokenDto findDeviceTokenById(Long deviceTokenId) throws DeviceTokenException {

        // Get existing DeviceToken
        DeviceToken deviceToken = deviceTokenRepository.findOne(deviceTokenId);
        if(deviceToken==null) {
            throw new DeviceTokenException("Could not locate requested DeviceToken");
        }

        return orikaTransformService.map(deviceToken, DeviceTokenDto.class);
    }

    /**
     * Creates a {@link DeviceToken} from the passed in {@link DeviceTokenDto}.
     *
     * @param deviceTokenDto {@link DeviceTokenDto}
     * @return Persisted {@link DeviceTokenDto}
     */
    @Override
    public DeviceTokenDto createToken(DeviceTokenDto deviceTokenDto) {

        // Convert DeviceTokenDto to DeviceToken Entity
        DeviceToken deviceToken = orikaTransformService.map(deviceTokenDto, DeviceToken.class);

        // Persist DeviceToken Entity
        deviceToken = deviceTokenRepository.save(deviceToken);

        return orikaTransformService.map(deviceToken, DeviceTokenDto.class);
    }

    /**
     * Updates a {@link DeviceToken} from the passed in {@link DeviceTokenDto}.
     *
     * @param deviceTokenId Long
     * @param deviceTokenDto {@link DeviceTokenDto}
     * @return Updated {@link DeviceTokenDto}
     */
    @Override
    public DeviceTokenDto updateToken(ApplicationDto applicationDto, Long deviceTokenId,
                                      DeviceTokenDto deviceTokenDto) throws DeviceTokenException {

        // Reattach DeviceToken entity and update it with the DeviceTokenDto change request
        DeviceToken originalDeviceToken =
                deviceTokenRepository.findByDeviceTokenId(applicationDto.getApplicationId(), deviceTokenId);

        // Verify that if the token is being updated, that it doesn't already exist in the system
        if (!originalDeviceToken.getDeviceToken().equals(deviceTokenDto.getDeviceToken())) {
            DeviceToken deviceTokenVerification =
                    deviceTokenRepository.findByDeviceToken(applicationDto.getApplicationId(),
                            deviceTokenDto.getDeviceToken());
            if(deviceTokenVerification!=null) {
                throw new DeviceTokenException("The requested device token update conflicts with an existing DeviceToken");
            }
        }

        // Update DeviceToken
        originalDeviceToken.setDeviceToken(deviceTokenDto.getDeviceToken());
        originalDeviceToken.setUserId(deviceTokenDto.getUserId());

        // Persist DeviceToken Entity
        DeviceToken updatedDeviceToken = deviceTokenRepository.save(originalDeviceToken);

        return orikaTransformService.map(updatedDeviceToken, DeviceTokenDto.class);
    }
}