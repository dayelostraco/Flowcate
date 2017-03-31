package com.notiflowcate.controller;

import com.notiflowcate.exception.GeofenceException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.GeofenceDto;
import com.notiflowcate.model.dto.GeofenceEventDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.GeofenceEventService;
import com.notiflowcate.service.GeofenceService;
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
@RestController(value = "geofenceController")
@RequestMapping(path = "/api/1/")
@Api(value = "geofence", description = "Operations on Geofence")
public class GeofenceController {

    private final ApplicationService applicationService;
    private final GeofenceService geofenceService;
    private final GeofenceEventService geofenceEventService;

    @Autowired
    public GeofenceController(ApplicationService applicationService,
                              GeofenceService geofenceService,
                              GeofenceEventService geofenceEventService) {

        this.applicationService = applicationService;
        this.geofenceService = geofenceService;
        this.geofenceEventService = geofenceEventService;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        System.out.println();
    }

    @RequestMapping(path = "geofence", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Create a Geofence record",
            httpMethod = "POST",
            nickname = "registerBeaconTrigger",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Geofence request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The Geofence was registered.",
                    response = GeofenceDto.class),
            @ApiResponse(code = 409, message = "The Geofence provided appears to already have been saved. " +
                    "Please make a new Geofence request.",
                    response = Void.class),
    })
    public HttpEntity<GeofenceDto> createGeofence(@RequestHeader String apiKey,
                                                  @Valid @RequestBody GeofenceDto geofenceDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Verify that the Geofence has not been persisted
        if (geofenceDto.getGeofenceId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Save the GeofenceDto
        try {
            geofenceDto = geofenceService.save(application, geofenceDto);
        } catch (GeofenceException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(geofenceDto, HttpStatus.CREATED);
    }


    @RequestMapping(path = "geofence/nearby",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find max nearby Geofences",
            httpMethod = "GET",
            nickname = "findNearbyGeofences",
            position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Geofence request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "Geofences Found",
                    response = GeofenceDto.class, responseContainer = "List"),
    })
    public HttpEntity<List<GeofenceDto>> findNearbyGeofences(@RequestHeader String apiKey,
                                                             @RequestParam(value = "latitude") Double latitude,
                                                             @RequestParam(value = "longitude") Double longitude,
                                                             @RequestParam(value = "platformId") Long platformId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Make find Geofences service call
        List<GeofenceDto> geofenceSearchResults = geofenceService.findGeofencesByLocation(application.getApplicationId(),
                latitude, longitude, platformId);

        return new ResponseEntity<>(geofenceSearchResults, HttpStatus.OK);
    }

    @RequestMapping(path = "geofence/{geofenceId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Update a Geofence record",
            httpMethod = "PUT",
            nickname = "update",
            position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Geofence request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The Geofence was updated.",
                    response = GeofenceDto.class),
            @ApiResponse(code = 404, message = "The Geofence provided does not exist. Please create it first.",
                    response = Void.class),
    })
    public HttpEntity<GeofenceDto> updateGeofence(@RequestHeader String apiKey,
                                                  @PathVariable("geofenceId") Long geofenceId,
                                                  @Valid @RequestBody GeofenceDto geofenceDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Verify that the geofenceIds match
        if (!geofenceId.equals(geofenceDto.getGeofenceId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Save the GeofenceDto
        try {
            geofenceDto = geofenceService.update(application, geofenceDto);
        } catch (GeofenceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(geofenceDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "geofence/{geofenceId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Delete a Geofence record",
            httpMethod = "DELETE",
            nickname = "delete",
            position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Geofence request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "The Geofence and the associated GeofenceEvents have been deleted.",
                    response = GeofenceDto.class),
            @ApiResponse(code = 404, message = "The Geofence provided does not exist. Please create it first.",
                    response = Void.class),
    })
    public HttpEntity<GeofenceDto> deleteGeofence(@RequestHeader String apiKey,
                                                  @PathVariable("geofenceId") Long geofenceId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            geofenceService.delete(application, geofenceId);
        } catch (GeofenceException e) {
            GeofenceDto geofenceDto = new GeofenceDto();
            geofenceDto.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(geofenceDto, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "geofence/{geofenceId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Geofence by id",
            httpMethod = "GET",
            nickname = "findGeofence",
            position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find Geofence request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "Found Geofence",
                    response = GeofenceDto.class),
            @ApiResponse(code = 404, message = "An active Geofence was not found for the provided geofenceId",
                    response = GeofenceDto.class)
    })
    public HttpEntity<GeofenceDto> findGeofenceById(@RequestHeader String apiKey,
                                                    @PathVariable Long geofenceId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Retrieve the Geofence by the passed in geofenceId
        return new ResponseEntity<>(geofenceService.findById(application, geofenceId), HttpStatus.OK);
    }

    @RequestMapping(path = "geofence",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Geofences",
            httpMethod = "GET",
            nickname = "findCampaignGeofences",
            position = 6)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Geofence request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "Geofences Found",
                    response = GeofenceDto.class, responseContainer = "List"),
    })
    public HttpEntity<List<GeofenceDto>> findGeofences(@RequestHeader String apiKey,
                                                       @RequestParam(value = "listingId", required = false) String listingId) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<GeofenceDto> geofenceSearchResults;
        if (listingId == null) {
            geofenceSearchResults = geofenceService.findGeofencesByApplication(application);
        } else {
            geofenceSearchResults = geofenceService.findGeofencesByListing(application, listingId);
        }

        return new ResponseEntity<>(geofenceSearchResults, HttpStatus.OK);
    }

    @RequestMapping(path = "geofence/{geofenceId}/geofenceEvents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find the active GeofenceEvent for Geofence",
            httpMethod = "GET",
            nickname = "findGeofenceEventForGeofence",
            position = 7)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find GeofenceEvent request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "An active GeofenceEvent was found for the provided geofenceId",
                    response = GeofenceDto.class),
            @ApiResponse(code = 404, message = "An active GeofenceEvent was not found for the provided geofenceId",
                    response = GeofenceDto.class)
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
}
