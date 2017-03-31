package com.notiflowcate.model.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Dayel Ostraco
 * 5/23/16
 */
public class ApplicationIntegrationTest extends BaseEntityIntegrationTest {

    @Test
    public void testJpaAutowiring() {
        Assert.assertNotNull(applicationRepository);
    }

    @Test
    public void testCreateApplication() {

        Application application = new Application();
        application.setApplicationName(RandomStringUtils.randomAlphabetic(24));
        application.setApiKey(RandomStringUtils.randomAlphanumeric(36));
        application.setActive(true);

        applicationRepository.save(application);
        Assert.assertNotNull(application.getApplicationId());
    }

    @Test
    public void testApplicationCache() {

        // Create new Application
        Application application = new Application();
        application.setApplicationName(RandomStringUtils.randomAlphabetic(24));
        application.setApiKey(RandomStringUtils.randomAlphanumeric(36));
        application.setActive(true);
        applicationRepository.save(application);
        Assert.assertNotNull(application.getApplicationId());

        // Confirm that the persisted Application does not exist in the Cache
        Assert.assertNotNull(cacheManager.getCache("applications"));
        Assert.assertNull(cacheManager.getCache("applications").get(application.getApplicationId()));

        // Perform a findByApiKey to add the application into the cache
        application = applicationRepository.findByApiKey(application.getApiKey());
        Assert.assertEquals(application, cacheManager.getCache("applications").get(application.getApiKey(), Application.class));

        // Update the application
        application.setApplicationName(RandomStringUtils.randomAlphabetic(24));
        applicationRepository.save(application);
        Assert.assertNull(cacheManager.getCache("applications").get(application.getApplicationId()));

        // Perform a findByApiKey to add the application into the cache
        application = applicationRepository.findByApiKey(application.getApiKey());

        // Confirm that the updated Application exists in the Cache
        Assert.assertNotNull(cacheManager.getCache("applications").get(application.getApiKey()));
        Assert.assertEquals(application.getApplicationName(), cacheManager.getCache("applications").get(application.getApiKey(), Application.class).getApplicationName());

        // Perform a findAll to add a collection of all applications into the cache
        List<Application> applications = applicationRepository.findAll();
        Assert.assertTrue(applications.size()>0);

        // Update application and confirm that the all applications map is now null
        application.setFcmApiKey(RandomStringUtils.randomAlphanumeric(10));
        applicationRepository.save(application);
        Assert.assertNotNull(application.getFcmApiKey());
        Assert.assertNull(cacheManager.getCache("applications").get("all"));
    }
}
