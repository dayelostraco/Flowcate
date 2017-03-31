package com.notiflowcate.model.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author Dayel Ostraco
 * 5/23/16
 */
public class BeaconIntegrationTest extends BaseEntityIntegrationTest {

    @Test
    public void testJpaAutowiring() {
        Assert.assertNotNull(beaconRepository);
    }

    @Test
    public void testCreateBeacon() {

        Application application = applicationRepository.findActiveApplicationByApiKey("ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH");

        Beacon beacon = new Beacon();
        beacon.setName(RandomStringUtils.randomAlphanumeric(64));
        beacon.setDescription(RandomStringUtils.randomAlphanumeric(1024));
        beacon.setBeaconUUID(UUID.randomUUID().toString());
        beacon.setBeaconMajor(random.nextInt(65535));
        beacon.setBeaconMinor(random.nextInt(65535));
        beacon.setLatitude(new BigDecimal(32.808997));
        beacon.setLongitude(new BigDecimal(-79.8827437));
        beacon.setApplicationId(application.getApplicationId());

        beacon = beaconRepository.save(beacon);

        Assert.assertNotNull(beacon.getBeaconId());
    }

    @Test
    @Ignore //Caching was disabled so ignoring test for now.
    public void testBeaconCache() {

        Application application = applicationRepository.findActiveApplicationByApiKey("ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH");

        Beacon beacon = new Beacon();
        beacon.setName(RandomStringUtils.randomAlphanumeric(64));
        beacon.setDescription(RandomStringUtils.randomAlphanumeric(1024));
        beacon.setBeaconUUID(UUID.randomUUID().toString());
        beacon.setBeaconMajor(random.nextInt(65535));
        beacon.setBeaconMinor(random.nextInt(65535));
        beacon.setLatitude(new BigDecimal(32.808997));
        beacon.setLongitude(new BigDecimal(-79.8827437));
        beacon.setApplicationId(application.getApplicationId());

        beacon = beaconRepository.save(beacon);
        Assert.assertNotNull(beacon.getBeaconId());

        // Confirm that the persisted Beacon does not exist in the Cache
        Assert.assertNotNull(cacheManager.getCache("beacons"));
        Assert.assertNull(cacheManager.getCache("beacons").get(application.getApplicationId()));

        // Find beacon and verify that it is added to the cache
        beacon = beaconRepository.findBeacon(application.getApplicationId(), beacon.getBeaconUUID(),
                beacon.getBeaconMajor(), beacon.getBeaconMinor());
        Assert.assertNotNull(cacheManager.getCache("beacons")
                .get(beacon.getApplicationId().toString()
                        .concat("-")
                        .concat(beacon.getBeaconUUID())
                        .concat("-")
                        .concat(beacon.getBeaconMajor().toString())
                        .concat("-")
                        .concat(beacon.getBeaconMinor().toString())));

        // Find application Beacons and verify that it is added to the cache
        List<Beacon> applicationBeacons = beaconRepository.findBeaconsByApplication(application.getApplicationId());
        Assert.assertTrue(applicationBeacons.size() > 0);
        Assert.assertNotNull(cacheManager.getCache("beacons")
                .get("Application".concat(application.getApplicationId().toString()).concat("-Beacons")));

        // Find Beacon by applicationId and beaconId and verify that it was added to the cache
        Beacon applicationBeacon = beaconRepository.findBeaconByBeaconIdAndApplicationId(application.getApplicationId(), beacon.getBeaconId());
        Assert.assertNotNull(applicationBeacon);
        Assert.assertNotNull(cacheManager.getCache("beacons").get(beacon.getBeaconId()));

        // Let's add a different beacon to the cache and verify that it is not removed on the next save
        Beacon applicationBeacon2 = beaconRepository.findBeaconByBeaconIdAndApplicationId(application.getApplicationId(), 1L);
        Assert.assertNotNull(cacheManager.getCache("beacons").get(applicationBeacon2.getBeaconId()));

        // Update Beacon and ensure that the three records were deleted from the cache
        beacon.setActive(false);
        beacon = beaconRepository.save(beacon);
        Assert.assertFalse(beacon.getActive());
        Assert.assertNull(cacheManager.getCache("beacons")
                .get(beacon.getApplicationId().toString()
                        .concat("-")
                        .concat(beacon.getBeaconUUID())
                        .concat("-")
                        .concat(beacon.getBeaconMajor().toString())
                        .concat("-")
                        .concat(beacon.getBeaconMinor().toString())));
        Assert.assertNull(cacheManager.getCache("beacons")
                .get("applicationBeacons-".concat(application.getApplicationId().toString())));
        Assert.assertNull(cacheManager.getCache("beacons").get(beacon.getBeaconId().toString()));
        Assert.assertNotNull(cacheManager.getCache("beacons").get(applicationBeacon2.getBeaconId()));
    }
}
