package com.notiflowcate.repository;

import com.notiflowcate.model.entity.BeaconTrigger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeaconTriggerRepository extends JpaRepository<BeaconTrigger, Long> {

    @Query("SELECT bt FROM BeaconTrigger bt WHERE bt.applicationId = :applicationId AND bt.notificationTriggerId = :notificationTriggerId")
    BeaconTrigger findBeaconTriggerByApplicationIdAndNotificationTriggerId(@Param("applicationId") Long applicationId,
                                                                           @Param("notificationTriggerId") Long notificationTriggerId);

    @Query("SELECT bt FROM BeaconTrigger bt WHERE bt.applicationId = :applicationId ORDER BY bt.notificationTriggerId DESC")
    List<BeaconTrigger> findLatestBeaconTriggers(@Param("applicationId") Long applicationId, Pageable limit);
}
