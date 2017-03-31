package com.notiflowcate.controller;

import com.notiflowcate.exception.BeaconException;
import com.notiflowcate.exception.GeofenceException;
import com.notiflowcate.exception.NotificationException;
import com.notiflowcate.exception.TriggerException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconDto;
import com.notiflowcate.model.dto.BeaconEventDto;
import com.notiflowcate.model.dto.BeaconNotificationDto;
import com.notiflowcate.model.dto.BeaconTriggerDto;
import com.notiflowcate.model.dto.GeofenceDto;
import com.notiflowcate.model.dto.GeofenceEventDto;
import com.notiflowcate.model.dto.GeofenceNotificationDto;
import com.notiflowcate.model.dto.GeofenceTriggerDto;
import com.notiflowcate.model.dto.NotificationTriggerDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.BeaconEventService;
import com.notiflowcate.service.BeaconService;
import com.notiflowcate.service.BeaconTriggerService;
import com.notiflowcate.service.GeofenceEventService;
import com.notiflowcate.service.GeofenceService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
@RestController(value = "notificationTriggerController")
@RequestMapping(path = "/api/1")
@Api(value = "notificationTrigger", description = "Operations on NotificationTrigger")
public class NotificationTriggerController {

    private final ApplicationService applicationService;
    private final BeaconTriggerService beaconTriggerService;
    private final BeaconService beaconService;
    private final BeaconEventService beaconEventService;
    private final GeofenceService geofenceService;
    private final GeofenceTriggerService geofenceTriggerService;
    private final GeofenceEventService geofenceEventService;
    private final PushNotificationService fcmPushPushNotificationService;

    @Autowired
    public NotificationTriggerController(ApplicationService applicationService,
                                         BeaconTriggerService beaconTriggerService,
                                         BeaconService beaconService,
                                         BeaconEventService beaconEventService,
                                         GeofenceService geofenceService,
                                         GeofenceTriggerService geofenceTriggerService,
                                         GeofenceEventService geofenceEventService,
                                         PushNotificationService fcmPushPushNotificationService) {

        this.applicationService = applicationService;
        this.beaconTriggerService = beaconTriggerService;
        this.beaconService = beaconService;
        this.beaconEventService = beaconEventService;
        this.geofenceService = geofenceService;
        this.geofenceTriggerService = geofenceTriggerService;
        this.geofenceEventService = geofenceEventService;
        this.fcmPushPushNotificationService = fcmPushPushNotificationService;
    }

    @RequestMapping(path = "/trigger", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Create a NotificationTrigger record",
            httpMethod = "POST",
            nickname = "createNotificationTrigger",
            position = 0)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new BeaconTrigger request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The NotificationTrigger was reported.",
                    response = NotificationTriggerDto.class),
            @ApiResponse(code = 209, message = "The NotificationTrigger provided appears to already have been saved. " +
                    "Please make a new BeaconTrigger request.",
                    response = Void.class),
            @ApiResponse(code = 400, message = "Invalid Trigger request.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Error sending Notification.",
                    response = Void.class)
    })
    public HttpEntity<NotificationTriggerDto> createTrigger(@RequestHeader String apiKey,
                                                            @Valid @RequestBody NotificationTriggerDto notificationTriggerDto) {

        // Set Server received request time to provided UserLocation
        notificationTriggerDto.setServerTime(System.currentTimeMillis());

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            notificationTriggerDto.setErrorMsg("The provided API Key is not valid.");
            return new ResponseEntity<>(notificationTriggerDto, HttpStatus.FORBIDDEN);
        }

        // Check to make sure that this is a new BeaconTrigger POST request
        if (notificationTriggerDto.getNotificationTriggerId() != null) {
            notificationTriggerDto.setErrorMsg("You are trying to create a new NotificationTrigger that already has an assigned id.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Validate triggerEventType
        if (!isValidNotificationTrigger(notificationTriggerDto)) {
            notificationTriggerDto.setErrorMsg("You have provided no trigger event type or more than one trigger event type.");
            return new ResponseEntity<>(notificationTriggerDto, HttpStatus.BAD_REQUEST);
        }

        // Process BeaconTrigger and Notification
        if (notificationTriggerDto instanceof BeaconTriggerDto) {
            return createBeaconTrigger(application, (BeaconTriggerDto) notificationTriggerDto);
        }

        // Process GeofenceTrigger and Notification
        if (notificationTriggerDto instanceof GeofenceTriggerDto) {
            return createGeofenceTrigger(application, (GeofenceTriggerDto) notificationTriggerDto);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "/triggers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Retrieves the latest user TriggerNotifications",
            httpMethod = "GET",
            nickname = "retrieveLatestNotificationTriggers",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "NotificationTriggers were successfully found",
                    response = NotificationTriggerDto.class, responseContainer = "List"),
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new NotificationTrigger query request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 400, message = "Invalid NotificationTrigger request.",
                    response = Void.class),
            @ApiResponse(code = 500, message = "Error retrieving NotificationTriggers.",
                    response = Void.class)
    })
    public HttpEntity<List<NotificationTriggerDto>> findLatestNotificationTriggers(@RequestHeader String apiKey,
                                                                                   @RequestParam(required = false) Boolean beacon,
                                                                                   @RequestParam(required = false) Boolean geofence,
                                                                                   @RequestParam(required = false) Integer paginationStart,
                                                                                   @RequestParam(required = false) Integer paginationEnd) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!beacon && !geofence) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (paginationStart == null || paginationEnd == null) {
            paginationStart = 0;
            paginationEnd = 100;
        }

        List<NotificationTriggerDto> latestTriggers = new ArrayList<>();
        if (beacon) {
            beaconTriggerService.findLatestBeaconTriggers(application, paginationStart, paginationEnd);
        }
        if (geofence) {
            geofenceTriggerService.findLatestGeofenceTriggers(application, paginationStart, paginationEnd);
        }

        // Sort the latestTriggers List by created
        Collections.sort(latestTriggers, (NotificationTriggerDto t1, NotificationTriggerDto t2) ->
                t1.getCreated().compareTo(t2.getCreated()));

        // Truncate the list using paginationStart and paginationEnd
        if(latestTriggers.size()>=(paginationEnd-paginationStart)) {
            return new ResponseEntity<>(latestTriggers.subList(paginationStart, paginationEnd), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(latestTriggers, HttpStatus.OK);
        }
    }

    /**
     * Verifies that the Beacon is active, persists the {@link BeaconTriggerDto}, determines if the
     * {@link BeaconTriggerDto} has an active {@link BeaconEventDto} and sends a device notification based on
     * the active {@link BeaconEventDto}.
     *
     * @param application   {@link ApplicationDto}
     * @param beaconTrigger {@link BeaconTriggerDto}
     * @return HttpEntity
     */
    private HttpEntity<NotificationTriggerDto> createBeaconTrigger(ApplicationDto application,
                                                                   BeaconTriggerDto beaconTrigger) {

        // Verify that the Beacon is active in the system
        BeaconDto beacon = beaconService.findActiveBeacon(application, beaconTrigger.getBeaconUUID(),
                beaconTrigger.getBeaconMajor(), beaconTrigger.getBeaconMinor());
        if (beacon == null) {
            beaconTrigger.setErrorMsg("The provided beacon UUID, Major and Minor is not registered or is no longer active.");
            return new ResponseEntity<>(beaconTrigger, HttpStatus.NOT_FOUND);
        }

        // Save the BeaconTriggerDto
        try {
            beaconTrigger.setBeaconId(beacon.getBeaconId());
            beaconTrigger = beaconTriggerService.registerBeaconTrigger(application, beaconTrigger);
        } catch (TriggerException e) {
            beaconTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(beaconTrigger, HttpStatus.BAD_REQUEST);
        }

        // Find active BeaconEvent for Beacon and send notification to the user's devices
        List<BeaconEventDto> beaconEvents;
        try {
            beaconEvents = beaconEventService.findActiveBeaconEvents(application.getApiKey(), beacon.getBeaconId());

            if (beaconEvents.size()>0) {

                for(BeaconEventDto beaconEvent : beaconEvents) {

                    BeaconNotificationDto beaconNotification = new BeaconNotificationDto();
                    beaconNotification.setApplicationId(application.getApplicationId());
                    beaconNotification.setUserId(beaconTrigger.getUserId());
                    beaconNotification.setListingId(beaconEvent.getListingId());
                    beaconNotification.setDeviceToken(beaconTrigger.getDeviceToken());
                    beaconNotification.setMessage(beaconEvent.getEventNotificationText());
                    beaconNotification.setBeaconId(beacon.getBeaconId());
                    beaconNotification.setNotificationTriggerId(beaconTrigger.getNotificationTriggerId());
                    beaconNotification.setNotificationEventId(beaconEvent.getNotificationEventId());

                    // Add BeaconEvent to persisted BeaconTrigger
                    beaconTrigger.setNotificationEventId(beaconEvent.getNotificationEventId());
                    beaconTriggerService.update(application, beaconTrigger);

                    // Send Push Notification to User
                    fcmPushPushNotificationService.sendNotification(application, beaconNotification);
                }
            }

        } catch (BeaconException e) {
            beaconTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(beaconTrigger, HttpStatus.NOT_FOUND);
        } catch (TriggerException e) {
            beaconTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(beaconTrigger, HttpStatus.BAD_REQUEST);
        } catch (NotificationException e) {
            beaconTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(beaconTrigger, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        beaconTrigger.setBeaconUUID(beacon.getBeaconUUID());
        beaconTrigger.setBeaconMajor(beacon.getBeaconMajor());
        beaconTrigger.setBeaconMinor(beacon.getBeaconMinor());

        return new ResponseEntity<>(beaconTrigger, HttpStatus.CREATED);
    }

    /**
     * Verifies that the Geofence is active, persists the {@link GeofenceTriggerDto}, determines if the
     * {@link GeofenceTriggerDto} has an active {@link GeofenceEventDto} and sends a device notification based on
     * the active {@link GeofenceEventDto}.
     *
     * @param application     {@link ApplicationDto}
     * @param geofenceTrigger {@link GeofenceTriggerDto}
     * @return HttpEntity
     */
    private HttpEntity<NotificationTriggerDto> createGeofenceTrigger(ApplicationDto application,
                                                                     GeofenceTriggerDto geofenceTrigger) {

        // Verify that the Geofence is active in the system
        GeofenceDto geofenceDto =
                geofenceService.findById(application, geofenceTrigger.getGeofenceId());
        if (geofenceDto == null || !geofenceDto.getActive()) {
            geofenceTrigger.setErrorMsg("The provided geofenceId is not registered or is not active.");
            return new ResponseEntity<>(geofenceTrigger, HttpStatus.NOT_FOUND);
        }

        // Save the GeofenceTriggerDto
        try {
            geofenceTrigger.setGeofenceId(geofenceDto.getGeofenceId());
            geofenceTrigger = geofenceTriggerService.save(application, geofenceTrigger);
        } catch (TriggerException e) {
            geofenceTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Find active GeofenceEvent for Geofence and send notification to the user's devices
        List<GeofenceEventDto> geofenceEventDtos;
        try {
            geofenceEventDtos = geofenceEventService.findActiveGeofenceEvents(
                    application.getApiKey(), geofenceDto.getGeofenceId());

            if (geofenceEventDtos.size()>0) {

                for(GeofenceEventDto geofenceEventDto : geofenceEventDtos) {

                    GeofenceNotificationDto geofenceNotificationDto = new GeofenceNotificationDto();
                    geofenceNotificationDto.setApplicationId(application.getApplicationId());
                    geofenceNotificationDto.setUserId(geofenceTrigger.getUserId());
                    geofenceNotificationDto.setDeviceToken(geofenceTrigger.getDeviceToken());
                    geofenceNotificationDto.setMessage(geofenceEventDto.getEventNotificationText());
                    geofenceNotificationDto.setListingId(geofenceEventDto.getListingId());
                    geofenceNotificationDto.setGeofenceId(geofenceDto.getGeofenceId());
                    geofenceNotificationDto.setNotificationTriggerId(geofenceTrigger.getNotificationTriggerId());
                    geofenceNotificationDto.setNotificationEventId(geofenceEventDto.getNotificationEventId());

                    // Add GeofenceEvent to persisted GeofenceTrigger
                    geofenceTrigger.setNotificationEventId(geofenceEventDto.getNotificationEventId());
                    geofenceTriggerService.update(application, geofenceTrigger);

                    // Send Push Notification to User
                    fcmPushPushNotificationService.sendNotification(application, geofenceNotificationDto);
                }
            }

        } catch (GeofenceException e) {
            geofenceTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(geofenceTrigger, HttpStatus.NOT_FOUND);
        } catch (TriggerException e) {
            geofenceTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(geofenceTrigger, HttpStatus.BAD_REQUEST);
        } catch (NotificationException e) {
            geofenceTrigger.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(geofenceTrigger, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(geofenceTrigger, HttpStatus.CREATED);
    }

    /**
     * Validates that a submitted NotificationTrigger has only one trigger notification type.
     *
     * @param notificationTriggerDto {@link NotificationTriggerDto}
     * @return Boolean
     */
    private Boolean isValidNotificationTrigger(NotificationTriggerDto notificationTriggerDto) {

        int triggerTypeCount = 0;

        if (notificationTriggerDto.getOnEnter()) {
            triggerTypeCount++;
        }

        if (notificationTriggerDto.getOnExit()) {
            triggerTypeCount++;
        }

        if (notificationTriggerDto instanceof GeofenceTriggerDto
                && ((GeofenceTriggerDto) notificationTriggerDto).getOnDwell()) {
            triggerTypeCount++;
        }

        return triggerTypeCount == 1;
    }
}
