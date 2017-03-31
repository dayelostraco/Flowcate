package com.notiflowcate.controller;

import com.notiflowcate.exception.SmsMessagingException;
import com.notiflowcate.model.dto.MmsDto;
import com.notiflowcate.service.SmsService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/1/mms")
@Api(value = "/mms", description = "MMS Controller API.")
public class MmsController {

    private final SmsService twilioSmsService;

    @Autowired
    public MmsController(SmsService twilioSmsService) {
        this.twilioSmsService = twilioSmsService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Send an MMS",
            httpMethod = "POST",
            nickname = "sendMms",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The MMS was sent.",
                    response = MmsDto.class),
            @ApiResponse(code = 500, message = "The MMS could not be sent.",
                    response = Void.class)
    })
    public HttpEntity<MmsDto> sendMms(@RequestHeader String apiKey,
                                      @Valid @RequestBody MmsDto mmsDto) {

        try {
            twilioSmsService.sendMmsMessage(apiKey, mmsDto);
        } catch (SmsMessagingException e) {
            mmsDto.setError(e.getMessage());
            return new ResponseEntity<>(mmsDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
