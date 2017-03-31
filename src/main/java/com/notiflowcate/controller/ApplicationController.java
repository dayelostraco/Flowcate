package com.notiflowcate.controller;

import com.notiflowcate.exception.ApplicationException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.service.ApplicationService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
@RestController(value = "applicationController")
@RequestMapping(path = "/api/1/application")
@Api(value = "application", description = "Operations on Application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Register an Application",
            httpMethod = "POST",
            nickname = "register",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Application was registered.",
                    response = ApplicationDto.class),
            @ApiResponse(code = 409, message = "The Application provided has already been registered. " +
                    "Please make a new Application registration request.",
                    response = Void.class),
    })
    public HttpEntity<ApplicationDto> createApplication(@Valid @RequestBody ApplicationDto applicationDto) {

        // Verify that the Aplication has not been persisted
        if (applicationDto.getApplicationId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Register the Application
        applicationDto = applicationService.save(applicationDto);

        return new ResponseEntity<>(applicationDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{applicationId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Update an Application",
            httpMethod = "PUT",
            nickname = "update",
            position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Beacon request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The Application was updated.",
                    response = ApplicationDto.class),
            @ApiResponse(code = 404, message = "The Application provided does not exist. Please register it first.",
                    response = Void.class),
    })
    public HttpEntity<ApplicationDto> updateApplication(@PathVariable("applicationId") Long applicationId,
                                                        @Valid @RequestBody ApplicationDto applicationDto) {

        // Verify that the applicationIds match
        if (!applicationId.equals(applicationDto.getApplicationId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Update the Application
        try {
            applicationDto = applicationService.update(applicationDto);
        } catch (ApplicationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(applicationDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Applications",
            httpMethod = "GET",
            nickname = "findApplications",
            position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Applications Found",
                    response = ApplicationDto.class, responseContainer = "List"),
    })
    public HttpEntity<List<ApplicationDto>> findAllApplications() {

        // Retrieve all Applications
        return new ResponseEntity<>(applicationService.findAll(), HttpStatus.OK);

    }

    @RequestMapping(path = "/name/{name}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Applications by name",
            httpMethod = "GET",
            nickname = "findApplications",
            position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Applications Found",
                    response = ApplicationDto.class, responseContainer = "List"),
    })
    public HttpEntity<ApplicationDto> findByName(@PathVariable String name) {

        // Retrieve Application for given name
        return new ResponseEntity<>(applicationService.findByName(name), HttpStatus.OK);

    }

    @RequestMapping(path = "/{applicationId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Application by id",
            httpMethod = "GET",
            nickname = "findApplicationById",
            position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found Application",
                    response = ApplicationDto.class),
            @ApiResponse(code = 404, message = "Application could not be found",
                    response = Void.class)
    })
    public HttpEntity<ApplicationDto> findBeaconById(@PathVariable Long applicationId) {

        // Retrieve the Beacon by the passed in beaconId
        return new ResponseEntity<>(applicationService.findByApplicationId(applicationId), HttpStatus.OK);
    }
}
