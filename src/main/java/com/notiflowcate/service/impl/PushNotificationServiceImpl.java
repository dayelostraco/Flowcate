package com.notiflowcate.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notiflowcate.exception.NotificationException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.ApplicationNotificationDto;
import com.notiflowcate.model.dto.BeaconNotificationDto;
import com.notiflowcate.model.dto.DataPushNotification;
import com.notiflowcate.model.dto.GeofenceNotificationDto;
import com.notiflowcate.model.dto.GeofenceRecentUsersNotification;
import com.notiflowcate.model.dto.GeofenceTriggerDto;
import com.notiflowcate.model.dto.NotificationDto;
import com.notiflowcate.model.dto.NotificationTriggerDto;
import com.notiflowcate.model.dto.TopicNotificationDto;
import com.notiflowcate.model.entity.ApplicationNotification;
import com.notiflowcate.model.entity.BeaconNotification;
import com.notiflowcate.model.entity.DeviceToken;
import com.notiflowcate.model.entity.GeofenceNotification;
import com.notiflowcate.model.entity.Topic;
import com.notiflowcate.model.entity.TopicNotification;
import com.notiflowcate.model.notification.MultiDevicePushNotification;
import com.notiflowcate.model.notification.NotificationData;
import com.notiflowcate.model.notification.PushNotification;
import com.notiflowcate.model.notification.StandardNotificationBody;
import com.notiflowcate.model.notification.TopicPushNotification;
import com.notiflowcate.repository.ApplicationNotificationRepository;
import com.notiflowcate.repository.BeaconNotificationRepository;
import com.notiflowcate.repository.DeviceTokenRepository;
import com.notiflowcate.repository.GeofenceNotificationRepository;
import com.notiflowcate.repository.GeofenceTriggerRepository;
import com.notiflowcate.repository.NotificationRepository;
import com.notiflowcate.repository.TopicNotificationRepository;
import com.notiflowcate.repository.TopicRepository;
import com.notiflowcate.service.PushNotificationService;
import com.notiflowcate.service.TransformService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dayel Ostraco
 *         5/24/16
 */
@Service("fcmPushNotificationService")
public class PushNotificationServiceImpl implements PushNotificationService {

    private static final Long ANDROID_PLATFORM_ID = 2L;
    private static final Long IOS_PLATFORM_ID = 1L;

    @Value("${fcm.url}")
    private String fcmUrl;

    @Value("${fcm.fcmMaxIdNumber}")
    private Integer fcmMaxIdNumber;

    @Value("${notification.notificationWindowInMinutes}")
    private Integer notificationWindowInMinutes;

    @Value("${notification.dailyUserNotificationLimit}")
    private Integer dailyUserNotificationLimit;

    private final BeaconNotificationRepository beaconNotificationRepository;
    private final GeofenceNotificationRepository geofenceNotificationRepository;
    private final TopicNotificationRepository topicNotificationRepository;
    private final ApplicationNotificationRepository applicationNotificationRepository;
    private final NotificationRepository notificationRepository;
    private final TopicRepository topicRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final GeofenceTriggerRepository geofenceTriggerRepository;
    private final TransformService orikaTransformationService;

    @Autowired
    public PushNotificationServiceImpl(BeaconNotificationRepository beaconNotificationRepository,
                                       GeofenceNotificationRepository geofenceNotificationRepository,
                                       TopicNotificationRepository topicNotificationRepository,
                                       ApplicationNotificationRepository applicationNotificationRepository,
                                       NotificationRepository notificationRepository,
                                       TopicRepository topicRepository,
                                       DeviceTokenRepository deviceTokenRepository,
                                       GeofenceTriggerRepository geofenceTriggerRepository,
                                       TransformService orikaTransformationService) {

        this.beaconNotificationRepository = beaconNotificationRepository;
        this.geofenceNotificationRepository = geofenceNotificationRepository;
        this.topicNotificationRepository = topicNotificationRepository;
        this.applicationNotificationRepository = applicationNotificationRepository;
        this.notificationRepository = notificationRepository;
        this.topicRepository = topicRepository;
        this.deviceTokenRepository = deviceTokenRepository;
        this.geofenceTriggerRepository = geofenceTriggerRepository;
        this.orikaTransformationService = orikaTransformationService;
    }

    /**
     * Asynchronous method that handle the logic and user Push Notifications.
     *
     * @param application  {@link ApplicationDto}
     * @param notification Super class NotificationDto request
     * @return NotificationDto (NOTE: Since this method is asynchronous, the persisted NotificationDto will
     * not be available to the requester.
     * @throws NotificationException
     */
    @Async
    public NotificationDto sendNotification(ApplicationDto application, NotificationDto notification)
            throws NotificationException {

        if (notification instanceof BeaconNotificationDto) {
            return sendDeviceTriggerNotification(application, notification);
        } else if (notification instanceof ApplicationNotificationDto) {
            return sendApplicationNotification(application, (ApplicationNotificationDto) notification);
        } else if (notification instanceof TopicNotificationDto) {
            return sendTopicNotification(application, (TopicNotificationDto) notification);
        } else if (notification instanceof GeofenceRecentUsersNotification) {
            return sendGeofenceRecentUserNotification(application, (GeofenceRecentUsersNotification) notification);
        } else if (notification instanceof GeofenceNotificationDto) {
            return sendDeviceTriggerNotification(application, notification);
        } else {
            throw new NotificationException("Unknown Notification Type");
        }
    }

    /**
     * Returns the Notification Audit history for a user with deviceToken.
     *
     * @param application {@link ApplicationDto}
     * @param deviceToken {@link String}
     * @param startTime   {@link LocalDateTime}
     * @param endTime     {@link LocalDateTime}
     * @return Collection of all Notifications sent directly to a user's device
     */
    @Override
    public List<NotificationDto> findUserNotificationsWithinTimeWindow(ApplicationDto application,
                                                                       String deviceToken,
                                                                       LocalDateTime startTime,
                                                                       LocalDateTime endTime) {

        List<NotificationDto> userNotifications = new ArrayList<>();

        // Add all User Geofence and Beacon Notifications
        if (StringUtils.isNotEmpty(deviceToken)) {
            userNotifications.addAll(
                    orikaTransformationService.mapList(
                            geofenceNotificationRepository.findUserNotificationsWithinSentTimeWindow(application.getApplicationId(), deviceToken, startTime, endTime),
                            GeofenceNotificationDto.class));
            userNotifications.addAll(
                    orikaTransformationService.mapList(
                            beaconNotificationRepository.findUserNotificationsWithinSentTimeWindow(application.getApplicationId(), deviceToken, startTime, endTime),
                            BeaconNotificationDto.class));
        }

        // Add all Application Notifications
        userNotifications.addAll(
                orikaTransformationService.mapList(
                        applicationNotificationRepository.findNotificationsWithinSentTimeWindow(application.getApplicationId(), startTime, endTime),
                        ApplicationNotificationDto.class));

        // Add all Topic Notifications
        userNotifications.addAll(
                orikaTransformationService.mapList(
                        topicNotificationRepository.findNotificationsWithinSentTimeWindow(application.getApplicationId(), startTime, endTime),
                        TopicNotificationDto.class));

        userNotifications.sort((n1, n2) -> n1.getSentTime().compareTo(n2.getSentTime()) * -1);

        return userNotifications;
    }

    /**
     * Sends a Notification via Firebase Cloud Messaging.
     *
     * @param application     {@link ApplicationDto}
     * @param notificationDto {@link NotificationDto}
     * @return {@link NotificationDto}
     * @throws NotificationException
     */
    private NotificationDto sendDeviceTriggerNotification(ApplicationDto application, NotificationDto notificationDto)
            throws NotificationException {

        // Build the FCM requests
        HttpEntity pushRequest = buildDeviceNotificationRequest(application,
                deviceTokenRepository.findByDeviceToken(application.getApplicationId(), notificationDto.getDeviceToken()), notificationDto);

        // Make the FCM Notification Request after creating the notification record
        ResponseEntity<String> responseEntity;
        try {

            LocalDateTime sentTime = LocalDateTime.now(ZoneId.of("UTC"));

            // Create Notification Audit
            if (notificationDto instanceof BeaconNotificationDto) {

                // Duplicate or Notification Limit Check
                if (!hasUserApproachedDailyNotificationLimit(application, notificationDto.getDeviceToken())
                        && !hasUserReceivedBeaconNotification((BeaconNotificationDto) notificationDto, notificationWindowInMinutes)) {

                    // Convert NotificationDto to BeaconNotification Entity
                    BeaconNotification beaconNotification =
                            orikaTransformationService.map(notificationDto, BeaconNotification.class);

                    // Persist BeaconNotification Entity
                    beaconNotification.setSentTime(sentTime);
                    beaconNotification.setApplicationId(application.getApplicationId());

                    try {
                        beaconNotification = beaconNotificationRepository.save(beaconNotification);
                    } catch (DataIntegrityViolationException e) {
                        throw new NotificationException("Invalid BeaconNotification request.", e);
                    }

                    // After verifying that the Notification record was captured, make Firebase Push Notification request.
                    responseEntity = new RestTemplate().postForEntity(fcmUrl, pushRequest, String.class);

                    return orikaTransformationService.map(beaconNotification, BeaconNotificationDto.class);

                } else {
                    return null;
                }
            }

            // Create Notification Audit
            if (notificationDto instanceof GeofenceNotificationDto) {

                if (!hasUserApproachedDailyNotificationLimit(application, notificationDto.getDeviceToken())
                        && !hasUserReceivedGeofenceNotification((GeofenceNotificationDto) notificationDto, notificationWindowInMinutes)) {

                    // Convert NotificationDto to BeaconNotification Entity
                    GeofenceNotification geofenceNotification =
                            orikaTransformationService.map(notificationDto, GeofenceNotification.class);

                    // Persist BeaconNotification Entity
                    geofenceNotification.setSentTime(sentTime);
                    geofenceNotification.setApplicationId(application.getApplicationId());

                    try {
                        geofenceNotification = geofenceNotificationRepository.save(geofenceNotification);
                    } catch (DataIntegrityViolationException e) {
                        throw new NotificationException("Invalid GeofenceNotification request.", e);
                    }

                    // After verifying that the Notification record was captured, make Firebase Push Notification request.
                    responseEntity = new RestTemplate().postForEntity(fcmUrl, pushRequest, String.class);

                    return orikaTransformationService.map(geofenceNotification, GeofenceNotificationDto.class);

                } else {
                    return null;
                }
            }

            return notificationDto;

        } catch (HttpClientErrorException e) {
            throw new NotificationException("Firebase Push Notification Request was rejected.");
        }
    }

    /**
     * Sends a Topic Notification via Firebase Cloud Messaging.
     *
     * @param application     {@link ApplicationDto}
     * @param notificationDto {@link TopicNotificationDto}
     * @return {@link NotificationDto}
     * @throws NotificationException
     */
    private NotificationDto sendTopicNotification(ApplicationDto application, TopicNotificationDto notificationDto)
            throws NotificationException {

        // Validate that the provided Topic exists for the application
        Topic topic = topicRepository.findTopicByTopicName(application.getApplicationId(), notificationDto.getTopicName());
        if (topic == null) {
            throw new NotificationException("Topic does not exist");
        }

        // Build the FCM requests
        HttpEntity pushRequest = buildTopicNotificationRequest(application, notificationDto);

        // Make the FCM Notification Request
        ResponseEntity<String> responseEntity;
        try {
            LocalDateTime sentTime = LocalDateTime.now(ZoneId.of("UTC"));
            responseEntity = new RestTemplate().postForEntity(fcmUrl, pushRequest, String.class);

            // Convert NotificationDto to BeaconNotification Entity
            TopicNotification topicNotification =
                    orikaTransformationService.map(notificationDto, TopicNotification.class);

            // Persist TopicNotification Entity
            if (notificationDto.getTopicId() == null) {
                topicNotification.setTopicId(topic.getTopicId());
            }
            topicNotification.setSentTime(sentTime);
            topicNotification.setApplicationId(application.getApplicationId());
            topicNotification = topicNotificationRepository.save(topicNotification);

            return orikaTransformationService.map(topicNotification, TopicNotificationDto.class);

        } catch (HttpClientErrorException e) {
            throw new NotificationException("Firebase Topic Notification Request was rejected.");
        }
    }

    /**
     * Sends a Application Notification via Firebase Cloud Messaging.
     *
     * @param application     {@link ApplicationDto}
     * @param notificationDto {@link ApplicationNotificationDto}
     * @return {@link NotificationDto}
     * @throws NotificationException
     */
    private NotificationDto sendApplicationNotification(ApplicationDto application, ApplicationNotificationDto notificationDto)
            throws NotificationException {

        // Retrieve Application Device Tokens
        List<DeviceToken> applicationDeviceTokens = deviceTokenRepository.findAllByApplicationId(application.getApplicationId());

        // Build Standard Push Notification Request and Send
        LocalDateTime sentTime = LocalDateTime.now(ZoneId.of("UTC"));
        for (DeviceToken deviceToken : applicationDeviceTokens) {

            // Only send the Push Notification if the device has not approached the daily notification limit
            if (!hasUserApproachedDailyNotificationLimit(application, deviceToken.getDeviceToken())) {

                HttpEntity pushRequest = buildDeviceNotificationRequest(application, deviceToken, notificationDto);
                ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(fcmUrl, pushRequest, String.class);

                // Delete the DeviceToken if FCM returns a NotRegistered error
                if (responseEntity.getBody().contains("NotRegistered")) {
                    deviceTokenRepository.delete(deviceToken);
                }
            }
        }

        // Create Notification Audit History
        try {
            // Convert NotificationDto to ApplicationNotification Entity
            ApplicationNotification applicationNotification =
                    orikaTransformationService.map(notificationDto, ApplicationNotification.class);

            applicationNotification.setSentTime(sentTime);
            applicationNotification.setApplicationId(application.getApplicationId());
            applicationNotification = applicationNotificationRepository.save(applicationNotification);

            return orikaTransformationService.map(applicationNotification, ApplicationNotificationDto.class);

        } catch (HttpClientErrorException e) {
            throw new NotificationException("Firebase Application Device Notification Request was rejected.");
        }
    }

    /**
     * Sends a Geofence Recent User Notification via Firebase Cloud Messaging.
     *
     * @param application     {@link ApplicationDto}
     * @param notificationDto {@link GeofenceNotificationDto}
     * @return {@link NotificationDto}
     * @throws NotificationException
     */
    private NotificationDto sendGeofenceRecentUserNotification(ApplicationDto application, GeofenceRecentUsersNotification notificationDto)
            throws NotificationException {

        LocalDateTime notificationWindowEnd = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime notificationWindowStart = LocalDateTime.now(ZoneId.of("UTC"));
        notificationWindowStart = notificationWindowStart.minusHours(notificationDto.getTimeframeInHours());

        List<GeofenceTriggerDto> geofenceTriggerDtos = orikaTransformationService.mapList(
                geofenceTriggerRepository.findUsersWithinGeofence(application.getApplicationId(), notificationDto.getGeofenceId(), notificationWindowStart, notificationWindowEnd), GeofenceTriggerDto.class);

        List<String> deviceTokens = geofenceTriggerDtos.stream().map(NotificationTriggerDto::getDeviceToken).collect(Collectors.toList());
        if (deviceTokens.isEmpty()) return notificationDto;

        try {
            // Build the FCM requests
            HttpEntity pushRequest = buildMultiDeviceNotificationRequest(application, deviceTokens, notificationDto);

            // Make the FCM Notification Request
            ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(fcmUrl, pushRequest, String.class);

            return orikaTransformationService.map(notificationDto, GeofenceNotificationDto.class);

        } catch (HttpClientErrorException e) {
            throw new NotificationException("Firebase Notification Request was rejected.");
        }
    }

    /**
     * Builds the {@link HttpEntity} Standard Notification request.
     *
     * @param application  {@link ApplicationDto}
     * @param deviceToken  {@link String}
     * @param notification {@link NotificationDto}
     * @return {@link HttpEntity}
     * @throws NotificationException
     */
    @SuppressWarnings("unchecked")
    private HttpEntity buildDeviceNotificationRequest(ApplicationDto application,
                                                      DeviceToken deviceToken,
                                                      NotificationDto notification) throws NotificationException {

        // Build FCM http headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "key=" + application.getFcmApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        DataPushNotification pushNotification;
        if (deviceToken.getPlatform().getPlatformId().equals(ANDROID_PLATFORM_ID)) {
            NotificationData notificationData = new NotificationData(notification.getMessage(), notification.getListingId());
            pushNotification = new DataPushNotification(deviceToken.getDeviceToken(), notificationData);
        } else {
            StandardNotificationBody notificationBody =
                    new StandardNotificationBody(notification.getMessage(), application.getApplicationName());
            NotificationData notificationData = new NotificationData(notification.getMessage(), notification.getListingId());
            pushNotification = new PushNotification(deviceToken.getDeviceToken(), notificationBody, notificationData);
        }


        // Convert FcmNotificationDto to JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(pushNotification);

            return new HttpEntity(json, headers);
        } catch (JsonProcessingException e) {
            throw new NotificationException("Could not convert Notification request to an FCM request with the notification key");
        }
    }

    /**
     * Builds the {@link HttpEntity} Firebase Cloud Messaging Device Notification request.
     *
     * @param application  {@link ApplicationDto}
     * @param deviceTokens Collection of deviceTokens to be notified
     * @param notification {@link NotificationDto}
     * @return {@link HttpEntity}
     * @throws NotificationException
     */
    @SuppressWarnings("unchecked")
    private HttpEntity buildMultiDeviceNotificationRequest(ApplicationDto application,
                                                           List<String> deviceTokens,
                                                           NotificationDto notification) throws NotificationException {

        // Build FCM http headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "key=" + application.getFcmApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        StandardNotificationBody notificationBody =
                new StandardNotificationBody(notification.getMessage(), application.getApplicationName());
        NotificationData notificationData = new NotificationData(notification.getMessage(), notification.getListingId());
        MultiDevicePushNotification pushNotification =
                new MultiDevicePushNotification(deviceTokens, notificationBody, notificationData);

        // Convert FcmNotificationDto to JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(pushNotification);

            return new HttpEntity(json, headers);
        } catch (JsonProcessingException e) {
            throw new NotificationException("Could not convert Notification request to an FCM request");
        }
    }

    /**
     * Builds the {@link HttpEntity} Firebase Cloud Messaging Topic request.
     *
     * @param application  {@link ApplicationDto}
     * @param notification {@link NotificationDto}
     * @return {@link HttpEntity}
     * @throws NotificationException
     */
    @SuppressWarnings("unchecked")
    private HttpEntity buildTopicNotificationRequest(ApplicationDto application,
                                                     TopicNotificationDto notification) throws NotificationException {

        // Build FCM http headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "key=" + application.getFcmApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create TopicPushNotification
        StandardNotificationBody notificationBody =
                new StandardNotificationBody(notification.getMessage(), application.getApplicationName());
        NotificationData notificationData = new NotificationData(notification.getMessage(), notification.getListingId());
        TopicPushNotification topicPushNotification =
                new TopicPushNotification(notification.getTopicName(), notificationBody, notificationData);

        // Convert FcmNotificationDto to JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(topicPushNotification);

            return new HttpEntity(json, headers);
        } catch (JsonProcessingException e) {
            throw new NotificationException("Could not convert Topic Notification request to an FCM request");
        }
    }

    /**
     * Queries the Notification Audit tables for a given device token and determines if the user has received
     * the allowed number of daily Geofence, Beacon and Application notifications.
     *
     * @param application {@link ApplicationDto}
     * @param deviceToken {@link String}
     * @return True if the user has already received the daily limit of push notifications
     */
    private Boolean hasUserApproachedDailyNotificationLimit(ApplicationDto application, String deviceToken) {

        LocalDateTime startWindow = LocalDateTime.now(ZoneId.of("UTC"));
        startWindow = startWindow.withHour(0);
        startWindow = startWindow.withMinute(0);
        startWindow = startWindow.withSecond(0);

        LocalDateTime endWindow = LocalDateTime.now(ZoneId.of("UTC"));
        endWindow = endWindow.withHour(23);
        endWindow = endWindow.withMinute(59);
        endWindow = endWindow.withSecond(59);

        Long userNotificationCount =
                notificationRepository.findUserNotificationsWithinTimeWindow(application.getApplicationId(),
                        startWindow, endWindow, deviceToken);

        return userNotificationCount.intValue() > dailyUserNotificationLimit;
    }

    /**
     * Checks to see if a User has already received a {@link GeofenceNotification} in the
     * last notificationWindowInMinutes.
     *
     * @param geofenceNotificationDto     {@link GeofenceNotificationDto}
     * @param notificationWindowInMinutes Long
     * @return True if the user has received a {@link GeofenceNotification} within the notification window
     */
    private Boolean hasUserReceivedGeofenceNotification(GeofenceNotificationDto geofenceNotificationDto,
                                                        Integer notificationWindowInMinutes) {

        LocalDateTime notificationWindowEnd = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime notificationWindowStart = LocalDateTime.now(ZoneId.of("UTC"));
        notificationWindowStart = notificationWindowStart.minusMinutes(notificationWindowInMinutes);

        List<GeofenceNotification> geofenceNotifications =
                geofenceNotificationRepository.findGeofenceUserNotificationsWithinSentTimeWindow(
                        geofenceNotificationDto.getApplicationId(),
                        geofenceNotificationDto.getGeofenceId(),
                        geofenceNotificationDto.getDeviceToken(),
                        notificationWindowStart,
                        notificationWindowEnd);

        return geofenceNotifications.size() > 0;
    }

    /**
     * Checks to see if a User has already received a {@link BeaconNotification} in the
     * last notificationWindowInMinutes.
     *
     * @param beaconNotificationDto       {@link BeaconNotificationDto}
     * @param notificationWindowInMinutes Long
     * @return True if the user has received a {@link BeaconNotification} within the notification window
     */
    private Boolean hasUserReceivedBeaconNotification(BeaconNotificationDto beaconNotificationDto,
                                                      Integer notificationWindowInMinutes) {

        LocalDateTime notificationWindowEnd = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDateTime notificationWindowStart = LocalDateTime.now(ZoneId.of("UTC"));
        notificationWindowStart = notificationWindowStart.minusMinutes(notificationWindowInMinutes);


        List<BeaconNotification> beaconNotifications =
                beaconNotificationRepository.findBeaconUserNotificationsWithinSentTimeWindow(
                        beaconNotificationDto.getApplicationId(),
                        beaconNotificationDto.getBeaconId(),
                        beaconNotificationDto.getDeviceToken(),
                        notificationWindowStart,
                        notificationWindowEnd);

        return beaconNotifications.size() > 0;
    }
}
