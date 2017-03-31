package com.notiflowcate.service.impl;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.notiflowcate.exception.PhoneNumberException;
import com.notiflowcate.exception.SmsMessagingException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.MmsDto;
import com.notiflowcate.model.dto.SmsDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.SmsService;
import com.notiflowcate.validator.PhoneNumberValidator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 9/28/15.
 */
@Service("twilioSmsService")
public class TwilioSmsServiceImpl implements SmsService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ApplicationService applicationService;

    @Autowired
    public TwilioSmsServiceImpl(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Sends an SMS message via Twilio for an application.
     *
     * @param apiKey {@link String}
     * @param smsDto {@link SmsDto}
     * @throws SmsMessagingException
     */
    @Override
    public void sendSmsMessage(String apiKey, SmsDto smsDto) throws SmsMessagingException {

        // Retrieve application record by apiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);

        if (application == null) {
            throw new SmsMessagingException("You did not provide a valid ApiKey.");
        }

        // Send SMS via Twilio
        sendSmsMessage(application, smsDto.getPhoneNumber(), smsDto.getMessage());
    }

    /**
     * Sends an SMS message via Twilio for an application.
     *
     * @param apiKey {@link String}
     * @param mmsDto {@link MmsDto}
     * @throws SmsMessagingException
     */
    @Override
    public void sendMmsMessage(String apiKey, MmsDto mmsDto) throws SmsMessagingException {

        // Retrieve application record by apiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);

        if (application == null) {
            throw new SmsMessagingException("You did not provide a valid ApiKey.");
        }

        sendMmsMessage(application, mmsDto.getPhoneNumber(), mmsDto.getMessage(), mmsDto.getMediaUrls());
    }

    /**
     * Sends a SMS message to the provided phoneNumber via Twilio.
     *
     * @param application Registered Application
     * @param phoneNumber Phone number String with no special characters
     * @param messageBody SMS message String between 1-140 characters
     */
    public void sendSmsMessage(ApplicationDto application, String phoneNumber, String messageBody)
            throws SmsMessagingException {

        if (messageBody.length() > 140 || messageBody.length() == 0) {
            throw new SmsMessagingException(
                    new IllegalArgumentException("The provided messageBody needs to be between 1-140 characters."));
        }

        try {
            // Build the parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("To",
                    PhoneNumberValidator.convertToMinimizedPhoneNumber(phoneNumber,
                            PhoneNumberValidator.PhoneNumberRegion.NORTH_AMERICA)));
            params.add(new BasicNameValuePair("From", application.getTwilioSmsPhone()));
            params.add(new BasicNameValuePair("Body", messageBody));

            // Create TwilioClient
            TwilioRestClient twilioClient = createTwilioClient(application);

            // Create and send message
            MessageFactory messageFactory = twilioClient.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);

        } catch (TwilioRestException e) {
            LOGGER.error("Could not send SMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException("Could not send SMS Text Message to " + phoneNumber);
        } catch (PhoneNumberException e) {
            LOGGER.error("Could not send SMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException(e.getMessage(), e);
        }
    }

    /**
     * Sends a MMS message with Media URLs to the provided phoneNumber via Twilio.
     *
     * @param application Registered Application
     * @param phoneNumber Phone number String with no special characters
     * @param messageBody SMS message String between 1-140 characters
     * @param mediaUrl    String URL of the media you want included in the MMS
     */
    public void sendMmsMessage(ApplicationDto application, String phoneNumber, String messageBody, String mediaUrl)
            throws SmsMessagingException {

        String[] mediaUrls = new String[1];
        mediaUrls[0] = mediaUrl;

        sendMmsMessage(application, phoneNumber, messageBody, mediaUrls);
    }

    /**
     * Sends a MMS message with Media URLs to the provided phoneNumber via Twilio.
     *
     * @param application Registered Application
     * @param phoneNumber Phone number String with no special characters
     * @param messageBody SMS message String between 1-140 characters
     * @param mediaUrls   Collection of up to 10 mediaUrls to be included in the MMS
     */
    public void sendMmsMessage(ApplicationDto application, String phoneNumber, String messageBody, String[] mediaUrls)
            throws SmsMessagingException {

        if (messageBody.length() > 140 || messageBody.length() == 0) {
            throw new SmsMessagingException(
                    new IllegalArgumentException("The provided messageBody needs to be between 1-140 characters."));
        }

        if (mediaUrls.length > 10) {
            throw new SmsMessagingException("You can only have 1-10 media URLs included on a MMS message");
        }

        try {
            // Build the parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("To",
                    PhoneNumberValidator.convertToMinimizedPhoneNumber(phoneNumber,
                            PhoneNumberValidator.PhoneNumberRegion.NORTH_AMERICA)));
            params.add(new BasicNameValuePair("From", application.getTwilioMmsPhone()));
            params.add(new BasicNameValuePair("Body", messageBody));
            for (String mediaUrl : mediaUrls) {
                params.add(new BasicNameValuePair("MediaUrl", mediaUrl));
            }

            // Create TwilioClient
            TwilioRestClient twilioClient = createTwilioClient(application);

            // Create and send message
            MessageFactory messageFactory = twilioClient.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);

        } catch (TwilioRestException e) {
            LOGGER.error("Could not send MMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException("Could not send MMS Text Message to " + phoneNumber);
        } catch (PhoneNumberException e) {
            LOGGER.error("Could not send MMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException(e.getMessage(), e);
        }
    }

    /**
     * Create a new TwilioRestClient from a registered Application.
     *
     * @param applicationDto Registered Application
     * @return TwilioRestClient
     */
    private TwilioRestClient createTwilioClient(ApplicationDto applicationDto) {
        return new TwilioRestClient(applicationDto.getTwilioAccountSid(), applicationDto.getTwilioPrimaryToken());
    }
}
