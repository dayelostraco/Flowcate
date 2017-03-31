package com.notiflowcate.controller;

import com.notiflowcate.model.HealthDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dayel Ostraco
 */
@RestController(value = "healthController")
@RequestMapping(path = "/api/1/")
@Api(value = "health", description = "Application heartbeat")
public class HealthController {

    @RequestMapping(path = "health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Check is the app is alive.",
            httpMethod = "GET",
            nickname = "health",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "App is alive!",
                    response = HealthDto.class)
    })
    public HttpEntity<HealthDto> createGeofence() {

        //ALIVE
        return new ResponseEntity<>(new HealthDto(), HttpStatus.OK);
    }
}
