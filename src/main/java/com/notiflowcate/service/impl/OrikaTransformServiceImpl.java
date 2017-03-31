 package com.notiflowcate.service.impl;

import com.notiflowcate.converter.LocalDateTimeToDateConverter;
import com.notiflowcate.converter.LocalDateTimeToLocalDateTime;
import com.notiflowcate.converter.LocalDateTimeToLongConverter;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.dto.ApplicationNotificationDto;
import com.notiflowcate.model.dto.BeaconDto;
import com.notiflowcate.model.dto.BeaconEventDto;
import com.notiflowcate.model.dto.BeaconNotificationDto;
import com.notiflowcate.model.dto.BeaconTriggerDto;
import com.notiflowcate.model.dto.DeviceTokenDto;
import com.notiflowcate.model.dto.GeofenceDto;
import com.notiflowcate.model.dto.GeofenceEventDto;
import com.notiflowcate.model.dto.GeofenceNotificationDto;
import com.notiflowcate.model.dto.GeofenceTriggerDto;
import com.notiflowcate.model.dto.TopicDto;
import com.notiflowcate.model.dto.TopicNotificationDto;
import com.notiflowcate.model.entity.Application;
import com.notiflowcate.model.entity.ApplicationNotification;
import com.notiflowcate.model.entity.Beacon;
import com.notiflowcate.model.entity.BeaconEvent;
import com.notiflowcate.model.entity.BeaconNotification;
import com.notiflowcate.model.entity.BeaconTrigger;
import com.notiflowcate.model.entity.DeviceToken;
import com.notiflowcate.model.entity.Geofence;
import com.notiflowcate.model.entity.GeofenceEvent;
import com.notiflowcate.model.entity.GeofenceNotification;
import com.notiflowcate.model.entity.GeofenceTrigger;
import com.notiflowcate.model.entity.Topic;
import com.notiflowcate.model.entity.TopicNotification;
import com.notiflowcate.service.TransformService;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service(value = "orikaTransformService")
@SuppressWarnings({"unused", "unchecked"})
public class OrikaTransformServiceImpl implements TransformService {

    private MapperFacade mapper;

    /**
     * Builds and configures the Orika MapperFactory with Converts and Class Mappings
     */
    public OrikaTransformServiceImpl() {

        // Instantiate the Mapper Factory
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        // Add Custom Converters
        mapperFactory.getConverterFactory().registerConverter(new LocalDateTimeToLongConverter());
        mapperFactory.getConverterFactory().registerConverter(new LocalDateTimeToDateConverter());
        mapperFactory.getConverterFactory().registerConverter(new LocalDateTimeToLocalDateTime());

        // Application to ApplicationDto
        mapperFactory.classMap(
                Application.class,
                ApplicationDto.class)
                .field("applicationId", "applicationId")
                .field("applicationName", "applicationName")
                .field("apiKey", "apiKey")
                .field("active", "active")
                .field("sesAccessKey", "sesAccessKey")
                .field("sesSecretKey", "sesSecretKey")
                .field("fcmApiKey", "fcmApiKey")
                .field("fcmSenderId", "fcmSenderId")
                .field("twilioSmsPhone", "twilioSmsPhone")
                .field("twilioMmsPhone", "twilioMmsPhone")
                .field("twilioVoicePhone", "twilioVoicePhone")
                .field("twilioAccountSid", "twilioAccountSid")
                .field("twilioPrimaryToken", "twilioPrimaryToken")
                .field("twilioSecondaryToken", "twilioSecondaryToken")
                .field("twilioTestAccountSid", "twilioTestAccountSid")
                .field("twilioTestToken", "twilioTestToken")
                .field("deleted", "deleted")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // DeviceToken to DeviceTokenDto
        mapperFactory.classMap(
                DeviceToken.class,
                DeviceTokenDto.class)
                .field("deviceTokenId", "deviceTokenId")
                .field("deviceToken", "deviceToken")
                .field("application.applicationId", "applicationId")
                .field("platform.platformId", "platformId")
                .field("userId", "userId")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // Beacon to BeaconDto
        mapperFactory.classMap(
                Beacon.class,
                BeaconDto.class)
                .field("beaconId", "beaconId")
                .field("applicationId", "applicationId")
                .field("listingId", "listingId")
                .field("beaconUUID", "beaconUUID")
                .field("beaconMajor", "beaconMajor")
                .field("beaconMinor", "beaconMinor")
                .field("name", "name")
                .field("description", "description")
                .field("latitude", "latitude")
                .field("longitude", "longitude")
                .field("active", "active")
                .field("deleted", "deleted")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // Geofence to GeofenceDto
        mapperFactory.classMap(
                Geofence.class,
                GeofenceDto.class)
                .field("geofenceId", "geofenceId")
                .field("applicationId", "applicationId")
                .field("listingId", "listingId")
                .field("name", "name")
                .field("description", "description")
                .field("latitude", "latitude")
                .field("longitude", "longitude")
                .field("radiusInMeters", "radiusInMeters")
                .field("active", "active")
                .field("expiration", "expiration")
                .field("deleted", "deleted")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // BeaconEvent to BeaconEventDto
        mapperFactory.classMap(
                BeaconEvent.class,
                BeaconEventDto.class)
                .field("notificationEventId", "notificationEventId")
                .field("listingId", "listingId")
                .field("applicationId", "applicationId")
                .field("eventName", "eventName")
                .field("eventDescription", "eventDescription")
                .field("eventNotificationText", "eventNotificationText")
                .field("active", "active")
                .field("enter", "enter")
                .field("exit", "exit")
                .field("startDate", "startDate")
                .field("endDate", "endDate")
                .field("beaconId", "beaconId")
                .field("deleted", "deleted")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // GeofenceEvent to GeofenceEventDto
        mapperFactory.classMap(
                GeofenceEvent.class,
                GeofenceEventDto.class)
                .field("applicationId", "applicationId")
                .field("notificationEventId", "notificationEventId")
                .field("listingId", "listingId")
                .field("eventName", "eventName")
                .field("eventDescription", "eventDescription")
                .field("eventNotificationText", "eventNotificationText")
                .field("active", "active")
                .field("enter", "enter")
                .field("dwell", "dwell")
                .field("exit", "exit")
                .field("startDate", "startDate")
                .field("endDate", "endDate")
                .field("geofenceId", "geofenceId")
                .field("deleted", "deleted")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // BeaconTrigger to BeaconTriggerDto
        mapperFactory.classMap(
                BeaconTrigger.class,
                BeaconTriggerDto.class)
                .field("notificationTriggerId", "notificationTriggerId")
                .field("applicationId", "applicationId")
                .field("notificationEventId", "notificationEventId")
                .field("userId", "userId")
                .field("deviceToken", "deviceToken")
                .field("latitude", "latitude")
                .field("longitude", "longitude")
                .field("enterTime", "enterTime")
                .field("exitTime", "exitTime")
                .field("serverTime", "serverTime")
                .field("beaconId", "beaconId")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // BeaconTrigger to BeaconTriggerDto
        mapperFactory.classMap(
                GeofenceTrigger.class,
                GeofenceTriggerDto.class)
                .field("notificationTriggerId", "notificationTriggerId")
                .field("applicationId", "applicationId")
                .field("notificationEventId", "notificationEventId")
                .field("userId", "userId")
                .field("deviceToken", "deviceToken")
                .field("latitude", "latitude")
                .field("longitude", "longitude")
                .field("enterTime", "enterTime")
                .field("dwellTime", "dwellTime")
                .field("exitTime", "exitTime")
                .field("serverTime", "serverTime")
                .field("geofenceId", "geofenceId")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // Topic to TopicDto
        mapperFactory.classMap(
                Topic.class,
                TopicDto.class)
                .field("topicId", "topicId")
                .field("applicationId", "applicationId")
                .field("topicName", "topicName")
                .field("active", "active")
                .field("deleted", "deleted")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // BeaconNotification to BeaconNotificationDto
        mapperFactory.classMap(
                BeaconNotification.class,
                BeaconNotificationDto.class)
                .field("notificationId", "notificationId")
                .field("applicationId", "applicationId")
                .field("notificationEventId", "notificationEventId")
                .field("notificationTriggerId", "notificationTriggerId")
                .field("message", "message")
                .field("sentTime", "sentTime")
                .field("userId", "userId")
                .field("listingId", "listingId")
                .field("deviceToken", "deviceToken")
                .field("beaconId", "beaconId")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // GeofenceNotification to GeofenceNotificationDto
        mapperFactory.classMap(
                GeofenceNotification.class,
                GeofenceNotificationDto.class)
                .field("notificationId", "notificationId")
                .field("applicationId", "applicationId")
                .field("notificationEventId", "notificationEventId")
                .field("notificationTriggerId", "notificationTriggerId")
                .field("message", "message")
                .field("sentTime", "sentTime")
                .field("userId", "userId")
                .field("listingId", "listingId")
                .field("deviceToken", "deviceToken")
                .field("geofenceId", "geofenceId")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // TopicNotification to TopicNotificationDto
        mapperFactory.classMap(
                TopicNotification.class,
                TopicNotificationDto.class)
                .field("notificationId", "notificationId")
                .field("applicationId", "applicationId")
                .field("message", "message")
                .field("sentTime", "sentTime")
                .field("topicId", "topicId")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        // ApplicationNotification to ApplicationNotificationDto
        mapperFactory.classMap(
                ApplicationNotification.class,
                ApplicationNotificationDto.class)
                .field("notificationId", "notificationId")
                .field("applicationId", "applicationId")
                .field("message", "message")
                .field("sentTime", "sentTime")
                .field("created", "created")
                .field("modified", "modified")
                .mapNulls(true)
                .register();

        mapper = mapperFactory.getMapperFacade();
    }

    /**
     * Maps a single object to instance of a Class.
     *
     * @param mapFromObject Object to be mapped
     * @param mapToClass    Class object to be mapped to
     * @param <F>           From Object Class
     * @param <T>           To Object Class
     * @return Mapped single instance
     */
    public <F, T> T map(F mapFromObject, Class mapToClass) {
        return (T) mapper.map(mapFromObject, mapToClass);
    }

    /**
     * Maps a collection of objects to a collection of instances of the mapToClass
     *
     * @param mapFromList Collection of objects to be mapped
     * @param mapToClass  Class type to map objects in mapFromList to
     * @param <T>         To Object Class
     * @return Mapped Collection
     */
    public <T> T mapList(List<?> mapFromList, Class mapToClass) {
        return (T) mapper.mapAsList(mapFromList, mapToClass);
    }

    /**
     * Maps a set of objects to a collection of instances of the mapToClass
     *
     * @param mapFromSet Set of objects to be mapped
     * @param mapToClass Class type to map objects in mapFromSet to
     * @param <T>        To Object Class
     * @return Mapped Set
     */
    public <T> T mapSet(Set<?> mapFromSet, Class mapToClass) {
        return (T) mapper.mapAsSet(mapFromSet, mapToClass);
    }
}
