package com.notiflowcate.service;

import com.notiflowcate.exception.NotificationException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.NotificationDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/24/16
 */
public interface PushNotificationService {

    NotificationDto sendNotification(ApplicationDto application, NotificationDto notification)
            throws NotificationException;

    List<NotificationDto> findUserNotificationsWithinTimeWindow(ApplicationDto application, String deviceToken, LocalDateTime startTime, LocalDateTime endTime);

}
