-- -----------------------------------------------------
-- Insert `Application`
-- -----------------------------------------------------
INSERT INTO `Application` (applicationName, apiKey, fcmApiKey, fcmSenderId, active)
VALUES ('Notiflocate', 'ZI5BMBRJKNBDBCKTAGNIELW0RW4LQNT906MH', 'ZAzaSyDuEcfU2NSxJfzFebbJO6OObPJfcge7ftI', 404404404404, 1);

-- -----------------------------------------------------
-- Insert `Platform`
-- -----------------------------------------------------
INSERT INTO `Platform` (platformName)
VALUES ('iOS'),('Android'),('Windows'),('Blackberry');

-- -----------------------------------------------------
-- Insert `Beacon`
-- -----------------------------------------------------
INSERT INTO `Beacon` (`beaconId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `beaconUUID`, `beaconMajor`, `beaconMinor`, `latitude`, `longitude`, `active`)
VALUES (1, 1, '1234', '5678', 'Test Beacon', 'This is a Beacon used for testing', 'B9407F30-F5F8-466E-AFF9-25556B57FE6D', 38141, 50242, 32.808997, -79.8827437, 1);

INSERT INTO `Beacon` (`beaconId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `beaconUUID`, `beaconMajor`, `beaconMinor`, `latitude`, `longitude`, `active`)
VALUES (2, 1, NULL, NULL, 'Ice 1', 'Ice 1', '3E035B8E-EF0F-E877-5799-4D071D9C3E65', 1, 1, 32.808997, -79.8827437, 1);

INSERT INTO `Beacon` (`beaconId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `beaconUUID`, `beaconMajor`, `beaconMinor`, `latitude`, `longitude`, `active`)
VALUES (3, 1, NULL, NULL, 'Mint 1', 'Mint 1', '3E035B8E-EF0F-E877-5799-4D071D9C3E65', 1, 2, 32.808997, -79.8827437, 1);

INSERT INTO `Beacon` (`beaconId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `beaconUUID`, `beaconMajor`, `beaconMinor`, `latitude`, `longitude`, `active`)
VALUES (4, 1, NULL, NULL, 'Blueberry 1', 'Blueberry 1', '3E035B8E-EF0F-E877-5799-4D071D9C3E65', 1, 3, 32.808997, -79.8827437, 1);

INSERT INTO `Beacon` (`beaconId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `beaconUUID`, `beaconMajor`, `beaconMinor`, `latitude`, `longitude`, `active`)
VALUES (5, 1, NULL, NULL, 'Ice 2', 'Ice 2', '3E035B8E-EF0F-E877-5799-4D071D9C3E65', 1, 4, 32.808997, -79.8827437, 1);

INSERT INTO `Beacon` (`beaconId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `beaconUUID`, `beaconMajor`, `beaconMinor`, `latitude`, `longitude`, `active`)
VALUES (6, 1, NULL, NULL, 'Blueberry 2', 'Blueberry 2', '3E035B8E-EF0F-E877-5799-4D071D9C3E65', 1, 5, 32.808997, -79.8827437, 1);

INSERT INTO `Beacon` (`beaconId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `beaconUUID`, `beaconMajor`, `beaconMinor`, `latitude`, `longitude`, `active`)
VALUES (7, 1, NULL, NULL, 'Mint 2', 'Mint 2', '3E035B8E-EF0F-E877-5799-4D071D9C3E65', 1, 6, 32.808997, -79.8827437, 1);

-- -----------------------------------------------------
-- Insert `DeviceToken`
-- -----------------------------------------------------
INSERT INTO `DeviceToken` (`deviceTokenId`, `applicationId`, `platformId`, `userId`)
VALUES ('dyV_pmgUtio:APA91bFoqykCGXvTs4zm-Rg4Sp3WMYKWYupL0iBuLQKdrKGGRrQL0BwJpHe-5wEqaQrNECtSF8J7dVIHGf8KiRZAu0-gvEMTt2qXT-jE8J86B9lGPgP4ODImGUE-uwadGepGHmeIJzR1', 1, 2, '1234');

-- -----------------------------------------------------
-- Insert `Topic`
-- -----------------------------------------------------
INSERT INTO `Topic` (`topicId`, `applicationId`, `topicName`, `active`)
VALUES (1, 1, 'testtopic', 1);

-- -----------------------------------------------------
-- Insert `Geofence`
-- -----------------------------------------------------
INSERT INTO `Geofence` (`geofenceId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `latitude`, `longitude`, `radiusInMeters`, `active`, `expiration`)
VALUES (1, 1, '1234', '5678', 'The Ostraco Daycare', 'Want to sleep? Good luck with that...', 32.90984, -79.8246197, 150.55, 1, NULL);

INSERT INTO `Geofence` (`geofenceId`, `applicationId`, `campaignId`, `listingId`, `name`, `description`, `latitude`, `longitude`, `radiusInMeters`, `active`, `expiration`)
VALUES (2, 1, '1234', '5678', 'Qonceptual', 'The daycare for smart people', 32.808997, -79.8827437, 100.55, 1, NULL);

-- -----------------------------------------------------
-- Insert `NotificationEvent`
-- -----------------------------------------------------
INSERT INTO `NotificationEvent` (`notificationEventId`, `notificationEventType`, `applicationId`, `beaconId`, `campaignId`, `eventName`, `eventDescription`, `eventNotificationText`, `startDate`, `endDate`, `active`, `enter`, `exit`)
VALUES (1, 'BEACON', 1, 1, '1234', 'Beacon Test Event', 'Beacon Test Event for Beacon 1', 'You have entered the region of Beacon 1. Huzzah!!!', CURRENT_TIMESTAMP(3), '2017-12-31 23:59:59.999', 1, 1, 0);

INSERT INTO `NotificationEvent` (`notificationEventId`, `notificationEventType`, `applicationId`, `geofenceId`, `campaignId`, `eventName`, `eventDescription`, `eventNotificationText`, `startDate`, `endDate`, `active`, `enter`, `exit`, `dwell`, `dwellTimeInMilliseconds`)
VALUES (2, 'GEOFENCE', 1, 1, '1234', 'Geofence Test Event', 'Geofence Test Event for Geofence 1', 'You have entered the region of Geofence 1. Huzzah!!!', CURRENT_TIMESTAMP(3), '2017-12-31 23:59:59.999', 1, 1, 0, 0, 300000);
