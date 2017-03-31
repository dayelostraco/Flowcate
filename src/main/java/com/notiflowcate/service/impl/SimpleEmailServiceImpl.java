package com.notiflowcate.service.impl;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.notiflowcate.exception.EmailMessagingException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.EmailDto;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.EmailService;
import com.notiflowcate.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.core.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Properties;

/**
 * @author Dayel Ostraco
 *         9/28/15.
 */
@SuppressWarnings("unused")
@Service("simpleEmailService")
public class SimpleEmailServiceImpl implements EmailService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ApplicationService applicationService;

    @Autowired
    public SimpleEmailServiceImpl(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Sends an email for an Application based on the provided {@link EmailDto} using AWS SES.
     *
     * @param apiKey   {@link String}
     * @param emailDto {@link EmailDto}
     * @throws EmailMessagingException Wrapped exception to capture javax or AWS SDK exceptions
     */
    @Override
    public void sendEmail(String apiKey, EmailDto emailDto) throws EmailMessagingException {

        // Retrieve application record by apiKey
        ApplicationDto application = applicationService.findActiveApplicationByApiKey(apiKey);

        if (application == null) {
            throw new EmailMessagingException("You did not provide a valid ApiKey.");
        }

        if (emailDto.getFile() != null) {
            sendEmail(application, emailDto.getFrom(), emailDto.getTo(), emailDto.getCc(), emailDto.getBcc(),
                    emailDto.getSubject(), emailDto.getBody(), emailDto.getFile(), emailDto.getHtml());
        } else {
            sendEmail(application, emailDto.getFrom(), emailDto.getTo(), emailDto.getCc(), emailDto.getBcc(),
                    emailDto.getSubject(), emailDto.getBody(), emailDto.getHtml());
        }
    }

    /**
     * Simplified sendEmail allowing for singular from, to, cc and bcc email addresses.
     *
     * @param application Registered Application
     * @param from        Sender email address as a String
     * @param toEmail     Recipient email addresses as a String
     * @param ccEmail     CC recipient email addresses as a String
     * @param bccEmail    BCC recipient email addresses as a String
     * @param subject     Email subject as a String
     * @param body        Email message body as a String
     * @param asHtml      Message body as HTML or Plain Text
     * @throws EmailMessagingException Wrapped exception to capture javax or AWS SDK exceptions
     */
    public void sendEmail(ApplicationDto application, String from, String toEmail, String ccEmail, String bccEmail,
                          String subject, String body, boolean asHtml)
            throws EmailMessagingException {

        String[] ccEmailArray = (ccEmail == null) ? null : new String[]{ccEmail};
        String[] bccEmailArray = (bccEmail == null) ? null : new String[]{bccEmail};

        sendEmail(application, from, new String[]{toEmail}, ccEmailArray, bccEmailArray, subject, body, asHtml);
    }


    /**
     * Simplified sendEmail allowing for singular from, to, cc and bcc email addresses.
     *
     * @param application Registered Application
     * @param from        Sender email address as a String
     * @param toEmail     Recipient email addresses as a String
     * @param ccEmail     CC recipient email addresses as a String
     * @param bccEmail    BCC recipient email addresses as a String
     * @param subject     Email subject as a String
     * @param body        Email message body as a String
     * @param file        File attachment
     * @param asHtml      Message body as HTML or Plain Text
     * @throws EmailMessagingException Wrapped exception to capture javax or AWS SDK exceptions
     */
    public void sendEmail(ApplicationDto application, String from, String toEmail, String ccEmail, String bccEmail,
                          String subject, String body, File file, boolean asHtml)
            throws EmailMessagingException {

        String[] ccEmailArray = (ccEmail == null) ? null : new String[]{ccEmail};
        String[] bccEmailArray = (bccEmail == null) ? null : new String[]{bccEmail};

        sendEmail(application, from, new String[]{toEmail}, ccEmailArray, bccEmailArray, subject, body, file, asHtml);
    }

    /**
     * Sends an Email Message from the passed in fromEmail to the toEmails String[] with CCs
     * to the passed in ccEmails String[] and BCCs to the passed in bccEmails String[].
     *
     * @param application Registered Application
     * @param from        Sender email address as a String
     * @param toEmails    Recipient email addresses as a String[]
     * @param ccEmails    CC recipient email addresses as a String[]
     * @param bccEmails   BCC recipient email addresses as a String[]
     * @param subject     Email subject as a String
     * @param body        Email message body as a String
     * @param asHtml      Message body as HTML or Plain Text
     * @throws EmailMessagingException Wrapped exception to capture javax or AWS SDK exceptions
     */
    private void sendEmail(ApplicationDto application, String from, String[] toEmails, String[] ccEmails, String[] bccEmails,
                           String subject, String body, boolean asHtml)
            throws EmailMessagingException {

        if (!EmailValidator.validate(from)) {
            throw new EmailMessagingException("The provided from address ("
                    + from + ") is not a valid email.");
        }

        if (toEmails == null || subject == null || body == null) {
            throw new EmailMessagingException(
                    new IllegalArgumentException(" You must provide a valid, non-null fromEmail, toEmail, subject and body."));
        }

        Destination destination = new Destination().withToAddresses(toEmails);

        if (ccEmails != null) {
            destination.withCcAddresses(ccEmails);
        }
        if (bccEmails != null) {
            destination.withBccAddresses(bccEmails);
        }

        // Create message subject and body content as HTML
        Content subjectContent = new Content().withData(subject);
        Content messageContent = new Content().withData(body);
        Body bodyContent = new Body();
        if (asHtml) {
            bodyContent.withHtml(messageContent);
        } else {
            bodyContent.withText(messageContent);
        }

        // Create Message with Content parts
        Message message = new Message()
                .withSubject(subjectContent)
                .withBody(bodyContent);

        // Assemble the email
        SendEmailRequest request = new SendEmailRequest()
                .withSource(from)
                .withDestination(destination)
                .withMessage(message);

        // Send Email via the AmazonSimpleEmailServiceClient
        try {
            AmazonSimpleEmailService sesClient = createClient(application);
            sesClient.sendEmail(request);
        } catch (Exception e) {
            LOGGER.error("Could not send email via AWS Simple Email Service.", e);
            throw new EmailMessagingException("Could not send email via AWS Simple Email Service.", e);
        }
    }

    /**
     * Creates a RawMessage using the JavaMail API and AWS Simple Email Service and allows
     * for a File attachment.
     *
     * @param application Registered Application
     * @param from        Sender email address as a String
     * @param toEmails    Recipient email addresses as a String[]
     * @param ccEmails    CC recipient email addresses as a String[]
     * @param bccEmails   BCC recipient email addresses as a String[]
     * @param subject     Email subject as a String
     * @param body        Email message body as a String
     * @param file        File attachment
     * @param asHtml      Message body as HTML or Plain Text
     * @throws EmailMessagingException Wrapped exception to capture javax or AWS SDK exceptions
     */
    private void sendEmail(ApplicationDto application, String from, String[] toEmails, String[] ccEmails, String[] bccEmails,
                           String subject, String body, File file, boolean asHtml)
            throws EmailMessagingException {

        if (toEmails == null || subject == null) {
            throw new EmailMessagingException(
                    new IllegalArgumentException(" You must provide a valid, non-null fromEmail, toEmail, and subject."));
        }

        Destination destination = new Destination().withToAddresses(toEmails);

        if (ccEmails != null) {
            destination.withCcAddresses(ccEmails);
        }
        if (bccEmails != null) {
            destination.withBccAddresses(bccEmails);
        }

        // JavaMail representation of the message
        Session s = Session.getInstance(new Properties(), null);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setSubject(subject);
        } catch (MessagingException e) {
            LOGGER.error("Could not set subject to mime message.", e);
            throw new EmailMessagingException("Could not set subject to mime message.", e);
        }

        // Add Message parts
        MimeMultipart mimeMultipart = new MimeMultipart();
        BodyPart messagePart = new MimeBodyPart();
        try {
            if (asHtml) {
                messagePart.setContent(body, MediaType.TEXT_HTML);
            } else {
                messagePart.setContent(body, MediaType.TEXT_PLAIN);
            }
        } catch (MessagingException e) {
            LOGGER.error("Could not set body to mime message.", e);
            throw new EmailMessagingException("Could not set body to mime message.", e);
        }

        // Add File parts
        try {
            if (file != null) {
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.attachFile(file);
                mimeMultipart.addBodyPart(filePart);
            }
        } catch (MessagingException | IOException e) {
            LOGGER.error("Could not set file attachment to mime body.", e);
            throw new EmailMessagingException("Could not set file attachment to mime body.", e);
        }

        // Set initialized mimeMultipart to the mimeMessage
        try {
            mimeMessage.setContent(mimeMultipart);
        } catch (MessagingException e) {
            LOGGER.error("Mime multipart could not be set to the mime message.", e);
            throw new EmailMessagingException("Mime multipart could not be set to the mime message", e);
        }

        // Build complete destination list
        List<String> destinations = destination.getToAddresses();
        destinations.addAll(destination.getBccAddresses());
        destinations.addAll(destination.getCcAddresses());

        // Create Raw message from MimeMessage
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mimeMessage.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

            // Create Raw Email Request
            SendRawEmailRequest request = new SendRawEmailRequest()
                    .withSource(from)
                    .withDestinations(destinations)
                    .withRawMessage(rawMessage);

            // Send Email via the AmazonSimpleEmailServiceClient
            AmazonSimpleEmailService sesClient = createClient(application);
            sesClient.sendRawEmail(request);

        } catch (MessagingException | IOException e) {
            LOGGER.error("Could not write mime message to a byte array.", e);
        } catch (Exception e) {
            LOGGER.error("Could not send email via AWS Simple Email Service.", e);
            throw new EmailMessagingException("Could not send email via AWS Simple Email Service.", e);
        }
    }

    /**
     * Creates an AWS SES connection client using a registered Application.
     *
     * @param application ApplicationDto containing SES access and secret keys
     * @return AmazonSimpleEmailServiceClient
     */
    private AmazonSimpleEmailService createClient(ApplicationDto application) {

        BasicAWSCredentials awsCredentials =
                new BasicAWSCredentials(application.getSesAccessKey(), application.getSesSecretKey());

        return AmazonSimpleEmailServiceClientBuilder.defaultClient();
    }
}
