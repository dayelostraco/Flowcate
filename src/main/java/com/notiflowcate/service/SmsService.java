package com.notiflowcate.service;

import com.notiflowcate.exception.SmsMessagingException;
import com.notiflowcate.model.dto.MmsDto;
import com.notiflowcate.model.dto.SmsDto;
import org.springframework.stereotype.Component;

/**
 * @author Dayel Ostraco
 * 10/3/15.
 */
@Component(value = "smsService")
public interface SmsService {

    void sendSmsMessage(String apiKey, SmsDto smsDto) throws SmsMessagingException;

    void sendMmsMessage(String apiKey, MmsDto mmsDto) throws SmsMessagingException;
}
