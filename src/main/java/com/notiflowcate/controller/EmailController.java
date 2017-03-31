package com.notiflowcate.controller;

import com.notiflowcate.exception.EmailMessagingException;
import com.notiflowcate.model.dto.EmailDto;
import com.notiflowcate.service.EmailService;
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
@RequestMapping(path = "/api/1/email")
@Api(value = "/email", description = "Email Controller API.")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(
            value = "Send a email",
            httpMethod = "POST",
            nickname = "sendEmail",
            position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The email was sent.",
                    response = EmailDto.class),
            @ApiResponse(code = 500, message = "The email could not be sent.",
                    response = Void.class)
    })
    public HttpEntity<EmailDto> sendEmail(@RequestHeader String apiKey,
                                          @Valid @RequestBody EmailDto emailDto) {

        try {
            emailService.sendEmail(apiKey, emailDto);
        } catch (EmailMessagingException e) {
            emailDto.setError(e.getMessage());
            return new ResponseEntity<>(emailDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(emailDto, HttpStatus.OK);
    }
}
