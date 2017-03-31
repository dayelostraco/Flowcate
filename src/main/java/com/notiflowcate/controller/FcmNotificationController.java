package com.notiflowcate.controller;

import com.notiflowcate.exception.NotificationException;
import com.notiflowcate.model.dto.*;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.GeofenceTriggerService;
import com.notiflowcate.service.PushNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Dayel Ostraco
 * 12/22/15
 */
@RestController
@RequestMapping(path = "/api/1/notification")
@Api(value = "/notification", description = "FCM Notification API")
public class FcmNotificationController {

    private final PushNotificationService fcmPushNotificationService;
    private final ApplicationService applicationService;
    private final GeofenceTriggerService geofenceTriggerService;

    @Autowired
    public FcmNotificationController(PushNotificationService fcmPushNotificationService,
                                     ApplicationService applicationService,
                                     GeofenceTriggerService geofenceTriggerService) {

        this.fcmPushNotificationService = fcmPushNotificationService;
        this.applicationService = applicationService;
        this.geofenceTriggerService = geofenceTriggerService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Notify users of an application via Firebase Cloud Messaging",
            httpMethod = "POST",
            nickname = "notification",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new BeaconEvent request with an active ApiKey."),
            @ApiResponse(code = 200, message = "The notifications succeeded.",
                    response = NotificationDto.class),
            @ApiResponse(code = 500, message = "The notifications failed.",
                    response = Void.class)
    })
    public HttpEntity<NotificationDto> notify(@Valid @RequestBody NotificationDto notificationDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findByApplicationId(notificationDto.getApplicationId());
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Send Notification
        try {
            fcmPushNotificationService.sendNotification(application, notificationDto);
        } catch (NotificationException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
