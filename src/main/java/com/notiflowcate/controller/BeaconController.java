package com.notiflowcate.controller;

import com.notiflowcate.exception.BeaconException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.BeaconDto;
import com.notiflowcate.model.dto.BeaconEventDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.BeaconEventService;
import com.notiflowcate.service.BeaconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
@RestController(value = "beaconController")
@RequestMapping(path = "/api/1/beacon")
@Api(value = "beacon", description = "Operations on Beacon")
public class BeaconController {

    private final ApplicationService applicationService;
    private final BeaconService beaconService;
    private final BeaconEventService beaconEventService;

    @Autowired
    public BeaconController(ApplicationService applicationService,
                            BeaconService beaconService,
                            BeaconEventService beaconEventService) {

        this.applicationService = applicationService;
        this.beaconService = beaconService;
        this.beaconEventService = beaconEventService;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        System.out.println();
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Create a Beacon record",
            httpMethod = "POST",
            nickname = "registerBeaconTrigger",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Beacon request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The Beacon was reported.",
                    response = BeaconDto.class),
            @ApiResponse(code = 409, message = "The Beacon provided appears to already have been saved. " +
                    "Please make a new Beacon request.",
                    response = Void.class),
    })
    public HttpEntity<BeaconDto> createBeacon(@RequestHeader String apiKey,
                                              @Valid @RequestBody BeaconDto beaconDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Verify that the Beacon has not been persisted
        if (beaconDto.getBeaconId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Save the BeaconDto
        try {
            beaconDto = beaconService.save(application, beaconDto);
        } catch (BeaconException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(beaconDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{beaconId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Update a Beacon record",
            httpMethod = "PUT",
            nickname = "update",
            position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Beacon request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The Beacon was reported.",
                    response = BeaconDto.class),
            @ApiResponse(code = 404, message = "The Beacon provided does not exist. Please create it first.",
                    response = Void.class),
    })
    public HttpEntity<BeaconDto> updateBeacon(@RequestHeader String apiKey,
                                              @PathVariable("beaconId") Long beaconId,
                                              @Valid @RequestBody BeaconDto beaconDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Verify that the beaconIds match
        if (!beaconId.equals(beaconDto.getBeaconId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Save the BeaconDto
        try {
            beaconDto = beaconService.update(application, beaconDto);
        } catch (BeaconException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(beaconDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{beaconId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Delete a Beacon record",
            httpMethod = "DELETE",
            nickname = "delete",
            position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Geofence request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "The Beacon and the associated BeaconEvents have been deleted.",
                    response = BeaconDto.class),
            @ApiResponse(code = 404, message = "The Beacon provided does not exist.",
                    response = Void.class),
    })
    public HttpEntity<BeaconDto> deleteBeacon(@RequestHeader String apiKey,
                                              @PathVariable("beaconId") Long beaconId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            beaconService.delete(application, beaconId);
        } catch (BeaconException e) {
            BeaconDto geofenceDto = new BeaconDto();
            geofenceDto.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(geofenceDto, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{beaconId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Beacon by id",
            httpMethod = "GET",
            nickname = "findBeacon",
            position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find Beacon request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "Found Beacon",
                    response = BeaconDto.class),
            @ApiResponse(code = 404, message = "An active Beacon was not found for the provided beaconId",
                    response = BeaconDto.class)
    })
    public HttpEntity<BeaconDto> findBeaconById(@RequestHeader String apiKey,
                                                @PathVariable Long beaconId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Retrieve the Beacon by the passed in beaconId
        return new ResponseEntity<>(beaconService.findById(application, beaconId), HttpStatus.OK);
    }

    @RequestMapping(path = "",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Beacons",
            httpMethod = "GET",
            nickname = "findCampaignBeacons",
            position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Beacon request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "Beacons Found",
                    response = BeaconDto.class, responseContainer = "List"),
    })
    public HttpEntity<List<BeaconDto>> findBeacons(@RequestHeader String apiKey,
                                                   @RequestParam(value = "listingId", required = false) String listingId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<BeaconDto> beaconSearchResults;
        if (listingId == null) {
            beaconSearchResults = beaconService.findBeaconsByApplication(application);
        } else {
            beaconSearchResults = beaconService.findBeaconsByListing(application, listingId);
        }

        return new ResponseEntity<>(beaconSearchResults, HttpStatus.OK);
    }

    @RequestMapping(path = "/{beaconId}/beaconEvents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
                    response = BeaconDto.class),
            @ApiResponse(code = 404, message = "An active BeaconEvent was not found for the provided beaconId",
                    response = BeaconDto.class)
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
}
