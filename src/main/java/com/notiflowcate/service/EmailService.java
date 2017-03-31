package com.notiflowcate.service;

import com.notiflowcate.exception.EmailMessagingException;
import com.notiflowcate.model.dto.EmailDto;
import org.springframework.stereotype.Component;

/**
 * @author Dayel Ostraco
 * 10/3/15.
 */
@Component(value = "emailService")
public interface EmailService {

    void sendEmail(String apiKey, EmailDto emailDto) throws EmailMessagingException;
}
