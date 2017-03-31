package com.notiflowcate.model.entity;

import com.notiflowcate.BaseIntegrationTest;
import com.notiflowcate.repository.ApplicationRepository;
import com.notiflowcate.repository.BeaconRepository;
import com.notiflowcate.repository.BeaconTriggerRepository;
import com.notiflowcate.repository.GeofenceRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @author Dayel Ostraco
 * 4/19/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseEntityIntegrationTest extends BaseIntegrationTest {

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected BeaconRepository beaconRepository;

    @Autowired
    protected BeaconTriggerRepository beaconTriggerRepository;

    @Autowired
    protected ApplicationRepository applicationRepository;

    @Autowired
    protected GeofenceRepository geofenceRepository;

    protected Random random = new Random();
}
