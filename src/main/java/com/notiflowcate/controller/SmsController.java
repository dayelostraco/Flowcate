package com.notiflowcate.controller;

import com.notiflowcate.exception.SmsMessagingException;
import com.notiflowcate.model.dto.SmsDto;
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
@RequestMapping(path = "/api/1/sms")
@Api(value = "/sms", description = "SMS Controller API.")
public class SmsController {

    private final SmsService twilioSmsService;

    @Autowired
    public SmsController(SmsService twilioSmsService) {
        this.twilioSmsService = twilioSmsService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Send an SMS",
            httpMethod = "POST",
            nickname = "sendSMS",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The SMS was sent.",
                    response = SmsDto.class),
            @ApiResponse(code = 500, message = "The SMS could not be sent.",
                    response = Void.class)
    })
    public HttpEntity<SmsDto> sendSms(@RequestHeader(required = true) String apiKey,
                                      @Valid @RequestBody SmsDto smsDto) {

        try {
            twilioSmsService.sendSmsMessage(apiKey, smsDto);
        } catch (SmsMessagingException e) {
            smsDto.setError(e.getMessage());
            return new ResponseEntity<>(smsDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
