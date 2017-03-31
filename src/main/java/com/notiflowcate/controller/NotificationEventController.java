package com.notiflowcate.controller;

import com.notiflowcate.exception.BeaconException;
import com.notiflowcate.exception.GeofenceException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconEventDto;
import com.notiflowcate.model.dto.GeofenceEventDto;
import com.notiflowcate.model.dto.NotificationEventDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.BeaconEventService;
import com.notiflowcate.service.GeofenceEventService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
@RestController(value = "notificationEventController")
@RequestMapping(path = "/api/1/notificationEvent")
@Api(value = "notificationEvent", description = "Operations on NotificationEvent")
public class NotificationEventController {

    private final ApplicationService applicationService;
    private final BeaconEventService beaconEventService;
    private final GeofenceEventService geofenceEventService;

    @Autowired
    public NotificationEventController(ApplicationService applicationService,
                                       BeaconEventService beaconEventService,
                                       GeofenceEventService geofenceEventService) {

        this.applicationService = applicationService;
        this.beaconEventService = beaconEventService;
        this.geofenceEventService = geofenceEventService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Register a NotificationEvent",
            httpMethod = "POST",
            nickname = "createNotificationEvent",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new BeaconEvent request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The NotificationEvent was created.",
                    response = BeaconEventDto.class),
            @ApiResponse(code = 409, message = "The NotificationEvent provided appears to already have been saved. " +
                    "Please make a new NotificationEvent request.",
                    response = Void.class)
    })
    public HttpEntity<NotificationEventDto> createNotificationEvent(@Valid @RequestBody NotificationEventDto notificationEvent) {


        // Verify that the NotificationEvent has not been persisted
        if (notificationEvent.getNotificationEventId() != null) {
            notificationEvent.setErrorMsg("You are trying to create a new NotificationEvent that already has an assigned id.");
            return new ResponseEntity<>(notificationEvent, HttpStatus.CONFLICT);
        }

        // Verify that the NotificationEvent has a valid entered/exited/dwell value
        if (!isValidNotificationEvent(notificationEvent)) {
            notificationEvent.setErrorMsg("You have selected no event trigger types or more than one event trigger type.");
            return new ResponseEntity<>(notificationEvent, HttpStatus.BAD_REQUEST);
        }

        // Persist the BeaconEvent
        if (notificationEvent instanceof BeaconEventDto) {

            BeaconEventDto beaconEvent = (BeaconEventDto) notificationEvent;

            // Save the BeaconEventDto
            try {
                beaconEvent = beaconEventService.save(beaconEvent);
            } catch (BeaconException e) {
                notificationEvent.setErrorMsg("An active NotificationEvent already exists for the provided Beacon.");
                return new ResponseEntity<>(notificationEvent, HttpStatus.CONFLICT);
            }

            return new ResponseEntity<>(beaconEvent, HttpStatus.CREATED);
        }

        // Persist the GeofenceEvent
        if (notificationEvent instanceof GeofenceEventDto) {

            GeofenceEventDto geofenceEvent = (GeofenceEventDto) notificationEvent;

            // Save the BeaconEventDto
            try {
                geofenceEvent = geofenceEventService.save(geofenceEvent);
            } catch (GeofenceException e) {
                notificationEvent.setErrorMsg("An active NotificationEvent already exists for the provided Geofence.");
                return new ResponseEntity<>(notificationEvent, HttpStatus.CONFLICT);
            }

            return new ResponseEntity<>(geofenceEvent, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Update a NotificationEvent",
            httpMethod = "PUT",
            nickname = "updateNotificationEvent",
            position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The NotificationEvent was updated.",
                    response = BeaconEventDto.class)
    })
    public HttpEntity<NotificationEventDto> updateNotificationEvent(@Valid @RequestBody NotificationEventDto notificationEvent) {


        // Update the BeaconEvent
        if (notificationEvent instanceof BeaconEventDto) {

            BeaconEventDto beaconEvent = (BeaconEventDto) notificationEvent;

            // Save the BeaconEventDto
            try {
                beaconEvent = beaconEventService.update(beaconEvent);
            } catch (BeaconException e) {
                beaconEvent.setErrorMsg(e.getMessage());
                return new ResponseEntity<>(beaconEvent, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(beaconEvent, HttpStatus.OK);
        }

        // Update the GeofenceEvent
        if (notificationEvent instanceof GeofenceEventDto) {

            GeofenceEventDto geofenceEvent = (GeofenceEventDto) notificationEvent;

            // Save the BeaconEventDto
            try {
                geofenceEvent = geofenceEventService.update(geofenceEvent);
            } catch (GeofenceException e) {
                geofenceEvent.setErrorMsg(e.getMessage());
                return new ResponseEntity<>(geofenceEvent, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(geofenceEvent, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "/{notificationEventId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Delete a NotificationEvent record",
            httpMethod = "DELETE",
            nickname = "delete",
            position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Delete request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "The NotificationEvent has been deleted.",
                    response = NotificationEventDto.class),
            @ApiResponse(code = 404, message = "The notificationEventId provided does not exist. Please create it first.",
                    response = Void.class),
    })
    public HttpEntity<NotificationEventDto> delete(@RequestHeader String apiKey,
                                                   @PathVariable("notificationEventId") Long notificationEventId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Delete GeofenceEvent
        GeofenceEventDto geofenceExceptionCheck = null;
        try {
            geofenceEventService.delete(application, notificationEventId);
        } catch (GeofenceException e) {
            geofenceExceptionCheck = new GeofenceEventDto();
            geofenceExceptionCheck.setErrorMsg(e.getMessage());
        }

        // Delete BeaconEvent
        BeaconEventDto beaconExceptionCheck = null;
        try {
            beaconEventService.delete(application, notificationEventId);
        } catch (BeaconException e) {
            beaconExceptionCheck = new BeaconEventDto();
            beaconExceptionCheck.setErrorMsg(e.getMessage());
        }

        if (geofenceExceptionCheck != null && beaconExceptionCheck != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/beacon/{beaconId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find the active BeaconEvent for Beacon",
            httpMethod = "GET",
            nickname = "findBeaconEventForBeacon",
            position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find BeaconEvent request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "An active BeaconEvent was found for the provided beaconId",
                    response = BeaconEventDto.class),
            @ApiResponse(code = 404, message = "An active BeaconEvent was not found for the provided beaconId",
                    response = BeaconEventDto.class)
    })
    public HttpEntity<List<BeaconEventDto>> findActiveBeaconEventsForBeacon(@RequestHeader String apiKey,
                                                                            @PathVariable Long beaconId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Find the latest active BeaconEvent for the provided beaconId
        try {
            return new ResponseEntity<>(beaconEventService.findActiveBeaconEvents(application.getApiKey(), beaconId),
                    HttpStatus.OK);
        } catch (BeaconException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/geofence/{geofenceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find the active GeofenceEvent for Geofence",
            httpMethod = "GET",
            nickname = "findGeofenceEventForGeofence",
            position = 6)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find BeaconEvent request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "An active GeofenceEvent was found for the provided geofenceId",
                    response = BeaconEventDto.class),
            @ApiResponse(code = 404, message = "An active GeofenceEvent was not found for the provided geofenceId",
                    response = BeaconEventDto.class)
    })
    public HttpEntity<List<GeofenceEventDto>> findActiveGeofenceEventsForGeofence(@RequestHeader String apiKey,
                                                                                  @PathVariable Long geofenceId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Find the latest active GeofenceEvent for the provided geofenceId
        try {
            return new ResponseEntity<>(
                    geofenceEventService.findActiveGeofenceEvents(application.getApiKey(), geofenceId),
                    HttpStatus.OK);
        } catch (GeofenceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find all notification events",
            httpMethod = "GET",
            nickname = "findAllEvents",
            position = 7)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find BeaconEvent request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "A list of notification events",
                    response = BeaconEventDto.class),
            @ApiResponse(code = 404, message = "No notification events found",
                    response = BeaconEventDto.class)
    })
    public HttpEntity<List<NotificationEventDto>> findAllNotificationEvents() {

        // TODO Verify ApiKey is Admin

        // Find all notification events
        try {
            List<NotificationEventDto> events = new ArrayList<>();
            events.addAll(beaconEventService.getAllBeaconEvents());
            events.addAll(geofenceEventService.getAllGeofenceEvents());

            return new ResponseEntity<>(events, HttpStatus.OK);

        } catch (GeofenceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BeaconException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{eventId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find notification event by ID",
            httpMethod = "GET",
            nickname = "findEvent",
            position = 8)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "A notification event",
                    response = BeaconEventDto.class),
            @ApiResponse(code = 404, message = "No notification event found",
                    response = BeaconEventDto.class)
    })
    public HttpEntity<NotificationEventDto> findNotificationEventById(@PathVariable Long eventId) {

        // TODO Verify ApiKey is Admin

        // Find all notification events
        try {
            BeaconEventDto beaconEventDto = beaconEventService.findById(eventId);
            GeofenceEventDto geofenceEventDto = geofenceEventService.findById(eventId);
            if (beaconEventDto != null) {
                return new ResponseEntity<>(beaconEventDto, HttpStatus.OK);
            } else if (geofenceEventDto != null) {
                return new ResponseEntity<>(geofenceEventDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (GeofenceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BeaconException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Determines if only one event trigger type was provided for the {@link NotificationEventDto}
     *
     * @param notificationEvent {@link NotificationEventDto}
     * @return Boolean
     */
    private boolean isValidNotificationEvent(NotificationEventDto notificationEvent) {

        int triggerTypeCount = 0;

        if (notificationEvent.getEnter()) {
            triggerTypeCount++;
        }
        if (notificationEvent.getExit()) {
            triggerTypeCount++;
        }
        if (notificationEvent instanceof GeofenceEventDto
                && ((GeofenceEventDto) notificationEvent).getDwell()) {
            triggerTypeCount++;
        }

        return triggerTypeCount == 1;
    }
}
