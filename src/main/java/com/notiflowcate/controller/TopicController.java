package com.notiflowcate.controller;

import com.notiflowcate.exception.TopicException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.TopicDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.TopicService;
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

import java.util.List;

/**
 * @author Dayel Ostraco
 * 11/27/15.
 */
@RestController(value = "topicController")
@RequestMapping(path = "/api/1/")
@Api(value = "topic", description = "Operations on Topic")
public class TopicController {

    private final ApplicationService applicationService;
    private final TopicService topicService;

    @Autowired
    public TopicController(ApplicationService applicationService, TopicService topicService) {

        this.applicationService = applicationService;
        this.topicService = topicService;
    }

    @RequestMapping(path = "topic", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Create a Topic record",
            httpMethod = "POST",
            nickname = "registerBeaconTrigger")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Topic was reported.",
                    response = TopicDto.class),
            @ApiResponse(code = 409, message = "The Topic provided appears to already have been saved. " +
                    "Please make a new Topic request.",
                    response = Void.class),
    })
    public HttpEntity<TopicDto> createTopic(@Valid @RequestBody TopicDto topicDto) {

        // Verify Application Exists
        ApplicationDto application = applicationService.findByApplicationId(topicDto.getApplicationId());
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verify that the Topic has not been persisted
        if (topicDto.getTopicId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Save the TopicDto
        try {
            topicDto = topicService.registerTopic(application, topicDto);
        } catch (TopicException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(topicDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "topic/{topicId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Update a Topic record",
            httpMethod = "PUT",
            nickname = "update",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Topic request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 201, message = "The Topic was reported.",
                    response = TopicDto.class),
            @ApiResponse(code = 404, message = "The Topic provided does not exist. Please create it first.",
                    response = Void.class),
    })
    public HttpEntity<TopicDto> updateTopic(@PathVariable("topicId") Long topicId,
                                            @Valid @RequestBody TopicDto topicDto) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findByApplicationId(topicDto.getApplicationId());
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verify that the topicIds match
        if (!topicId.equals(topicDto.getTopicId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Save the TopicDto
        try {
            topicDto = topicService.updateTopic(application, topicDto);
        } catch (TopicException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(topicDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "topic/{topicId}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Delete Topic by id",
            httpMethod = "DELETE",
            nickname = "deleteTopic",
            position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted Topic",
                    response = TopicDto.class),
            @ApiResponse(code = 404, message = "A Topic was not found for the provided topicId",
                    response = TopicDto.class)
    })
    public HttpEntity<TopicDto> deleteTopicById(@PathVariable Long topicId) {

        try {
            topicService.deleteTopic(topicId);
        } catch (TopicException e) {
            TopicDto topicDto = new TopicDto();
            topicDto.setErrorMsg(e.getMessage());
            return new ResponseEntity<>(topicDto, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "topic/{topicId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Topic by id",
            httpMethod = "GET",
            nickname = "findTopic",
            position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new find Topic request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "Found Topic",
                    response = TopicDto.class),
            @ApiResponse(code = 404, message = "A Topic was not found for the provided topicId",
                    response = TopicDto.class)
    })
    public HttpEntity<TopicDto> findTopicById(@PathVariable Long topicId) {

        // Retrieve the Topic by the passed in topicId
        return new ResponseEntity<>(topicService.findTopicById(topicId), HttpStatus.OK);
    }

    @RequestMapping(path = "topic",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find Application Topics",
            httpMethod = "GET",
            nickname = "findApplicationBeacons",
            position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "The ApiKey you have provided is invalid or is no longer active. " +
                    "Please make a new Beacon request with an active ApiKey.",
                    response = Void.class),
            @ApiResponse(code = 200, message = "Topics Found",
                    response = TopicDto.class, responseContainer = "List"),
    })
    public HttpEntity<List<TopicDto>> findApplicationTopics(@RequestHeader String apiKey) {

        // Verify ApiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(topicService.findTopicsByApplication(application), HttpStatus.OK);
    }

    @RequestMapping(path = "topic/list",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Find All Topics",
            httpMethod = "GET",
            nickname = "findAllApplications",
            position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topics Found",
                    response = TopicDto.class, responseContainer = "List"),
    })
    public HttpEntity<List<TopicDto>> findAllTopics() {

        return new ResponseEntity<>(topicService.findAllTopics(), HttpStatus.OK);
    }
}
