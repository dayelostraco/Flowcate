package com.notiflowcate.repository;

import com.notiflowcate.model.entity.GeofenceNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component(value = "geofenceNotificationRepository")
public interface GeofenceNotificationRepository extends JpaRepository<GeofenceNotification, Long> {

    @Query("SELECT gn FROM GeofenceNotification gn WHERE gn.applicationId = :applicationId AND gn.geofenceId = :geofenceId AND gn.deviceToken = :deviceToken AND gn.sentTime BETWEEN :notificationWindowStart AND :notificationWindowEnd")
    List<GeofenceNotification> findGeofenceUserNotificationsWithinSentTimeWindow(@Param("applicationId") Long applicationId,
                                                                                 @Param("geofenceId") Long geofenceId,
                                                                                 @Param("deviceToken") String deviceToken,
                                                                                 @Param("notificationWindowStart") LocalDateTime notificationWindowStart,
                                                                                 @Param("notificationWindowEnd") LocalDateTime notificationWindowEnd);

    @Query("SELECT gn FROM GeofenceNotification gn WHERE gn.applicationId = :applicationId AND gn.deviceToken = :deviceToken AND gn.sentTime BETWEEN :notificationWindowStart AND :notificationWindowEnd")
    List<GeofenceNotification> findUserNotificationsWithinSentTimeWindow(@Param("applicationId") Long applicationId,
                                                                         @Param("deviceToken") String deviceToken,
                                                                         @Param("notificationWindowStart") LocalDateTime notificationWindowStart,
                                                                         @Param("notificationWindowEnd") LocalDateTime notificationWindowEnd);
}
