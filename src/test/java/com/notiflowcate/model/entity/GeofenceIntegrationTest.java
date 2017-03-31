package com.notiflowcate.model.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/23/16
 */
public class GeofenceIntegrationTest extends BaseEntityIntegrationTest {

    @Test
    public void testJpaAutowiring() {
        Assert.assertNotNull(geofenceRepository);
    }

    @Test
    public void testCreateGeofence() {

        Application application = applicationRepository.findActiveApplicationByApiKey("ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH");

        // Persist new Geofence
        Geofence geofence = new Geofence();
        geofence.setApplicationId(application.getApplicationId());
        geofence.setName(RandomStringUtils.randomAlphanumeric(20));
        geofence.setDescription(RandomStringUtils.randomAlphanumeric(20));
        geofence.setLatitude(new BigDecimal(-37.34342));
        geofence.setLongitude(new BigDecimal(73.34342));

        geofence = geofenceRepository.save(geofence);
        Assert.assertNotNull(geofence.getGeofenceId());
    }

    @Test
    @Ignore //caching was disabled so ignoring test for now.
    public void testGeofenceCache() {

        Application application = applicationRepository.findActiveApplicationByApiKey("ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH");

        // Persist new Geofence
        Geofence geofence = new Geofence();
        geofence.setApplicationId(application.getApplicationId());
        geofence.setName(RandomStringUtils.randomAlphanumeric(20));
        geofence.setDescription(RandomStringUtils.randomAlphanumeric(20));
        geofence.setLatitude(new BigDecimal(-37.34342));
        geofence.setLongitude(new BigDecimal(73.34342));

        geofence = geofenceRepository.save(geofence);
        Assert.assertNotNull(geofence.getGeofenceId());

        // Find Geofence
        geofence = geofenceRepository.findGeofenceByApplicationIdAndGeofenceId(application.getApplicationId(), geofence.getGeofenceId());
        Assert.assertEquals(geofence, cacheManager.getCache("geofences").get(geofence.getGeofenceId(), Geofence.class));

        // Find all Application Geofences
        List<Geofence> geofences = geofenceRepository.findGeofencesByApplication(application.getApplicationId());
        Assert.assertTrue(geofences.size() > 0);
        Assert.assertNotNull(cacheManager.getCache("geofences").get(
                "Application".concat(application.getApplicationId().toString())
                        .concat("-Geofences")));

        // Find another Geofence to be added to the cache
        Geofence geofence2 = geofenceRepository.findGeofenceByApplicationIdAndGeofenceId(application.getApplicationId(), 1L);
        Assert.assertNotNull(cacheManager.getCache("geofences").get(geofence2.getGeofenceId()));

        // Update geofence to test cache evict
        geofence.setActive(false);
        geofenceRepository.save(geofence);
        Assert.assertFalse(geofence.getActive());
        Assert.assertNull(cacheManager.getCache("geofences").get(geofence.getGeofenceId()));
        Assert.assertNull(cacheManager.getCache("geofences").get(
                "Application".concat(application.getApplicationId().toString())
                        .concat("-Geofences")));
        Assert.assertNotNull(cacheManager.getCache("geofences").get(geofence2.getGeofenceId()));
    }
}
