package com.notiflowcate.repository;

import com.notiflowcate.model.entity.BeaconNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component(value = "becaonNotificationRepository")
public interface BeaconNotificationRepository extends JpaRepository<BeaconNotification, Long> {

    @Query("SELECT bn FROM BeaconNotification bn WHERE bn.applicationId = :applicationId AND bn.beaconId = :beaconId AND bn.deviceToken = :deviceToken AND bn.sentTime BETWEEN :notificationWindowStart AND :notificationWindowEnd")
    List<BeaconNotification> findBeaconUserNotificationsWithinSentTimeWindow(@Param("applicationId") Long applicationId,
                                                                             @Param("beaconId") Long beaconId,
                                                                             @Param("deviceToken") String deviceToken,
                                                                             @Param("notificationWindowStart") LocalDateTime notificationWindowStart,
                                                                             @Param("notificationWindowEnd") LocalDateTime notificationWindowEnd);

    @Query("SELECT bn FROM BeaconNotification bn WHERE bn.applicationId = :applicationId AND bn.deviceToken = :deviceToken AND bn.sentTime BETWEEN :notificationWindowStart AND :notificationWindowEnd")
    List<BeaconNotification> findUserNotificationsWithinSentTimeWindow(@Param("applicationId") Long applicationId,
                                                                       @Param("deviceToken") String deviceToken,
                                                                       @Param("notificationWindowStart") LocalDateTime notificationWindowStart,
                                                                       @Param("notificationWindowEnd") LocalDateTime notificationWindowEnd);
}
