package com.notiflowcate.repository;

import com.notiflowcate.model.entity.BeaconEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BeaconEventRepository extends JpaRepository<BeaconEvent, Long> {

    @Query("SELECT be FROM BeaconEvent be WHERE be.deleted IS NULL")
    List<BeaconEvent> findAllApplicationBeaconEvents();

    @Query("SELECT be FROM BeaconEvent be WHERE be.applicationId = :applicationId AND be.beaconId = :beaconId AND :currentDate BETWEEN be.startDate AND be.endDate AND be.active = true AND be.deleted IS NULL ORDER BY be.endDate ASC")
    List<BeaconEvent> findActiveBeaconEventByBeaconId(@Param("applicationId") Long applicationId,
                                                      @Param("beaconId") Long beaconId,
                                                      @Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT be FROM BeaconEvent be WHERE be.applicationId = :applicationId AND be.notificationEventId = :notificationEventId")
    BeaconEvent findBeaconEventByApplicationIdAndNotificationEventId(@Param("applicationId") Long applicationId,
                                                                     @Param("notificationEventId") Long notificationEventId);

    @Query("SELECT be FROM BeaconEvent be WHERE be.applicationId = :applicationId AND be.beaconId = :beaconId AND be.deleted IS NULL")
    List<BeaconEvent> findAllBeaconEventsByBeaconId(@Param("applicationId") Long applicationId,
                                                    @Param("beaconId") Long beaconId);
}
