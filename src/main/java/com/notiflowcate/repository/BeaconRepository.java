package com.notiflowcate.repository;

import com.notiflowcate.model.entity.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "beaconRepository")
public interface BeaconRepository extends JpaRepository<Beacon, Long> {

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "beacons", key = "#p0.applicationId.toString().concat('-').concat(#p0.beaconUUID).concat('-').concat(#p0.beaconMajor).toString().concat('-').concat(#p0.beaconMinor).toString()"),
//            @CacheEvict(value = "beacons", key = "'Application'.concat(#p0.applicationId.toString()).concat('-Beacons')"),
//            @CacheEvict(value = "beacons", key = "#p0.beaconId", condition = "#p0.beaconId!=null")
//    })
    <S extends Beacon> S save(S beacon);


//    @Cacheable(value = "beacons", key = "#p0.toString().concat('-').concat(#p1).concat('-').concat(#p2).toString().concat('-').concat(#p3).toString()")
    @Query("SELECT b FROM Beacon b WHERE b.applicationId = :applicationId AND b.beaconUUID = :beaconUUID AND b.beaconMajor = :beaconMajor AND b.beaconMinor = :beaconMinor AND b.deleted IS NULL")
    Beacon findBeacon(@Param("applicationId") Long applicationId,
                      @Param("beaconUUID") String beaconUUID,
                      @Param("beaconMajor") Integer beaconMajor,
                      @Param("beaconMinor") Integer beaconMinor);

//    @Cacheable(value = "beacons", key = "#p0.toString().concat('-').concat(#p1).concat('-').concat(#p2).toString().concat('-').concat(#p3).toString()")
    @Query("SELECT b FROM Beacon b WHERE b.applicationId = :applicationId AND b.beaconUUID = :beaconUUID AND b.beaconMajor = :beaconMajor AND b.beaconMinor = :beaconMinor AND b.active = true AND b.deleted IS NULL")
    Beacon findActiveBeacon(@Param("applicationId") Long applicationId,
                            @Param("beaconUUID") String beaconUUID,
                            @Param("beaconMajor") Integer beaconMajor,
                            @Param("beaconMinor") Integer beaconMinor);

//    @Cacheable(value = "beacons", key = "'Application'.concat(#p0.toString()).concat('-Beacons')")
    @Query("SELECT b FROM Beacon b WHERE b.applicationId = :applicationId AND b.deleted IS NULL")
    List<Beacon> findBeaconsByApplication(@Param("applicationId") Long applicationId);

//    @Cacheable(value = "beacons", key = "#p1")
    @Query("SELECT b FROM Beacon b WHERE b.applicationId = :applicationId AND b.beaconId = :beaconId")
    Beacon findBeaconByBeaconIdAndApplicationId(@Param("applicationId") Long applicationId, @Param("beaconId") Long beaconId);

    @Query("SELECT b FROM Beacon b WHERE b.applicationId = :applicationId AND b.listingId = :listingId AND b.deleted IS NULL")
    List<Beacon> findBeaconsByListing(@Param("applicationId") Long applicationId,
                                      @Param("listingId") String listingId);
}
