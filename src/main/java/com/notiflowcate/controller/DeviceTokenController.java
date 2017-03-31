package com.notiflowcate.controller;

import com.notiflowcate.exception.DeviceTokenException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.DeviceTokenDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.DeviceTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Dayel Ostraco
 * 12/21/15
 */
@RestController
@RequestMapping(path = "/api/1/deviceToken")
@Api(value = "/deviceToken", description = "Operations on DeviceTokens")
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;
    private final ApplicationService applicationService;

    @Autowired
    public DeviceTokenController(DeviceTokenService deviceTokenService,
                                 ApplicationService applicationService) {

        this.deviceTokenService = deviceTokenService;
        this.applicationService = applicationService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Register a new Device Token",
            httpMethod = "POST",
            nickname = "createDeviceToken",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new BeaconEvent request with an active ApiKey."),
            @ApiResponse(code = 200, message = "The device token was created.",
                    response = DeviceTokenDto.class),
            @ApiResponse(code = 500, message = "The device token could not be created.",
                    response = Void.class)
    })
    public HttpEntity<DeviceTokenDto> createDeviceToken(@RequestHeader String apiKey,
                                                        @Valid @RequestBody DeviceTokenDto deviceTokenDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Set validated applicationId to DeviceToken Request
        deviceTokenDto.setApplicationId(application.getApplicationId());

        return new ResponseEntity<>(deviceTokenService.createToken(deviceTokenDto), HttpStatus.OK);
    }

    @RequestMapping(path = "/{deviceTokenId}", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Update an existing Device Token",
            httpMethod = "PUT",
            nickname = "updateDeviceToken",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new BeaconEvent request with an active ApiKey."),
            @ApiResponse(code = 200, message = "The Device Token was updated.",
                    response = DeviceTokenDto.class),
            @ApiResponse(code = 500, message = "The Device Token could not be updated.",
                    response = Void.class)
    })
    public HttpEntity<DeviceTokenDto> updateDeviceToken(@RequestHeader String apiKey,
                                                        @PathVariable("deviceTokenId") Long deviceTokenId,
                                                        @Valid @RequestBody DeviceTokenDto deviceTokenDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Verify that the passed in deviceTokenDto is valid
        try {
            deviceTokenService.findDeviceTokenById(deviceTokenId);
        } catch (DeviceTokenException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the Device Token
        try {
            deviceTokenDto = deviceTokenService.updateToken(application, deviceTokenId, deviceTokenDto);
        } catch (DeviceTokenException e) {
            deviceTokenDto.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(deviceTokenDto, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(deviceTokenDto, HttpStatus.OK);
    }
}
