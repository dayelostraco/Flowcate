-- -----------------------------------------------------
-- Table `Application`
-- -----------------------------------------------------
CREATE TABLE `Application` (
  `applicationId`           BIGINT NOT NULL AUTO_INCREMENT,
  `applicationName`         VARCHAR(64) NOT NULL,
  `apiKey`                  VARCHAR(36) NOT NULL,
  `fcmApiKey`               VARCHAR(64) DEFAULT NULL,
  `fcmSenderId`             BIGINT DEFAULT NULL,
  `sesAccessKey`            VARCHAR(45) DEFAULT NULL,
  `sesSecretKey`            VARCHAR(45) DEFAULT NULL,
  `s3AccessKey`             VARCHAR(45) DEFAULT NULL,
  `s3SecretKey`             VARCHAR(45) DEFAULT NULL,
  `twilioSmsPhone`          VARCHAR(45) DEFAULT NULL,
  `twilioMmsPhone`          VARCHAR(45) DEFAULT NULL,
  `twilioVoicePhone`        VARCHAR(45) DEFAULT NULL,
  `twilioAccountSid`        VARCHAR(45) DEFAULT NULL,
  `twilioPrimaryToken`      VARCHAR(45) DEFAULT NULL,
  `twilioSecondaryToken`    VARCHAR(45) DEFAULT NULL,
  `twilioTestAccountSid`    VARCHAR(45) DEFAULT NULL,
  `twilioTestToken`         VARCHAR(45) DEFAULT NULL,
  `active`                  BIT NOT NULL DEFAULT 1,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`applicationId`)
);

-- -----------------------------------------------------
-- Table `Platform`
-- -----------------------------------------------------
CREATE TABLE `Platform` (
  `platformId`              BIGINT NOT NULL AUTO_INCREMENT,
  `platformName`            VARCHAR(32) NOT NULL,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`platformId`)
);

-- -----------------------------------------------------
-- Table `Beacon`
-- -----------------------------------------------------
CREATE TABLE `Beacon` (
  `beaconId`                BIGINT NOT NULL AUTO_INCREMENT,
  `applicationId`           BIGINT NOT NULL,
  `campaignId`              VARCHAR(64) DEFAULT NULL,
  `listingId`               VARCHAR(64) DEFAULT NULL,
  `name`                    VARCHAR(64) NOT NULL,
  `description`             VARCHAR(1024) NOT NULL,
  `beaconUUID`              VARCHAR(36) NOT NULL,
  `beaconMajor`             INT NOT NULL,
  `beaconMinor`             INT NOT NULL,
  `latitude`                DECIMAL(10,8) DEFAULT NULL,
  `longitude`               DECIMAL(10,8) DEFAULT NULL,
  `active`                  BIT NOT NULL DEFAULT 1,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`beaconId`),
  UNIQUE KEY `uc_Beacon_idx` (`applicationId`,`beaconUUID`,`beaconMajor`,`beaconMinor`),
  CONSTRAINT `fk_Beacon_ApplicationId`
    FOREIGN KEY (`applicationId`)
    REFERENCES `Application` (`applicationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `Geofence`
-- -----------------------------------------------------
CREATE TABLE `Geofence` (
  `geofenceId`              BIGINT NOT NULL AUTO_INCREMENT,
  `applicationId`           BIGINT NOT NULL,
  `campaignId`              VARCHAR(64) DEFAULT NULL,
  `listingId`               VARCHAR(64) DEFAULT NULL,
  `name`                    VARCHAR(64) NOT NULL,
  `description`             VARCHAR(1024) NOT NULL,
  `latitude`                DECIMAL(10,8) NOT NULL,
  `longitude`               DECIMAL(10,8) NOT NULL,
  `radiusInMeters`          DECIMAL(5,2) NOT NULL,
  `active`                  BIT NOT NULL DEFAULT 1,
  `expiration`              TIMESTAMP(3) NULL DEFAULT NULL,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`geofenceId`),
    KEY `fk_Geofence_Application_idx` (`applicationId`),
  CONSTRAINT `fk_Geofence_Application`
    FOREIGN KEY (`applicationId`)
    REFERENCES `Application` (`applicationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `Topic`
-- -----------------------------------------------------
CREATE TABLE `Topic` (
  `topicId`                 BIGINT NOT NULL AUTO_INCREMENT,
  `applicationId`           BIGINT NOT NULL,
  `topicName`               VARCHAR(64) NOT NULL,
  `active`                  BIT NOT NULL DEFAULT 1,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`topicId`),
  KEY `fk_Topic_Application_idx` (`applicationId`),
  CONSTRAINT `fk_Topic_Application`
    FOREIGN KEY (`applicationId`)
    REFERENCES `Application` (`applicationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `NotificationEvent`
-- -----------------------------------------------------
CREATE TABLE `NotificationEvent` (
  `notificationEventId`     BIGINT NOT NULL AUTO_INCREMENT,
  `notificationEventType`   VARCHAR(64) NOT NULL,
  `applicationId`           BIGINT NOT NULL,
  `campaignId`              VARCHAR(64) DEFAULT NULL,
  `eventName`               VARCHAR(64) NOT NULL,
  `eventDescription`        VARCHAR(1024) NOT NULL,
  `eventNotificationText`   VARCHAR(236) NOT NULL,
  `active`                  BIT NOT NULL DEFAULT 1,
  `enter`                   BIT NOT NULL DEFAULT 1,
  `dwell`                   BIT NOT NULL DEFAULT 1,
  `dwellTimeInMilliseconds` INT NULL,
  `exit`                    BIT NOT NULL DEFAULT 1,
  `startDate`               TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `endDate`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `beaconId`                BIGINT NULL,
  `geofenceId`              BIGINT NULL,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`notificationEventId`),
    KEY `fk_NotificationEvent_Beacon_idx` (`beaconId`),
    KEY `fk_NotificationEvent_Geofence_idx` (`geofenceId`),
    KEY `fk_NotificationEvent_ApplicationId` (`applicationId`),
  CONSTRAINT `fk_NotificationEvent_ApplicationId`
    FOREIGN KEY (`applicationId`)
    REFERENCES `Application` (`applicationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotificationEvent_Beacon`
    FOREIGN KEY (`beaconId`)
    REFERENCES `Beacon` (`beaconId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotificationEvent_Geofence`
    FOREIGN KEY (`geofenceId`)
    REFERENCES `Geofence` (`geofenceId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `NotificationTrigger`
-- -----------------------------------------------------
CREATE TABLE `NotificationTrigger` (
  `notificationTriggerId`   BIGINT NOT NULL AUTO_INCREMENT,
  `notificationTriggerType` VARCHAR(64) NOT NULL,
  `applicationId`           BIGINT NOT NULL,
  `notificationEventId`     BIGINT DEFAULT NULL,
  `userId`                  VARCHAR(64) NOT NULL,
  `deviceToken`             VARCHAR(255) NOT NULL,
  `latitude`                DECIMAL(10,8) DEFAULT NULL,
  `longitude`               DECIMAL(10,8) DEFAULT NULL,
  `enterTime`               TIMESTAMP(3) NULL DEFAULT NULL,
  `dwellTime`               TIMESTAMP(3) NULL DEFAULT NULL,
  `exitTime`                TIMESTAMP(3) NULL DEFAULT NULL,
  `serverTime`              TIMESTAMP(3) NULL DEFAULT NULL,
  `beaconId`                BIGINT NULL,
  `geofenceId`              BIGINT NULL,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`notificationTriggerId`),
    KEY `fk_NotificationTrigger_Beacon_idx` (`beaconId`),
    KEY `fk_NotificationTrigger_Geofence_idx` (`geofenceId`),
    KEY `fk_NotificationTrigger_NotificationEvent` (`notificationEventId`),
    KEY `fk_NotificationTrigger_ApplicationId` (`applicationId`),
  CONSTRAINT `fk_NotificationTrigger_ApplicationId`
    FOREIGN KEY (`applicationId`)
    REFERENCES `Application` (`applicationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotificationTrigger_Beacon`
    FOREIGN KEY (`beaconId`)
    REFERENCES `Beacon` (`beaconId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotificationTrigger_Geofence`
    FOREIGN KEY (`geofenceId`)
    REFERENCES `Geofence` (`geofenceId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotificationTrigger_NotificationEvent`
    FOREIGN KEY (`notificationEventId`)
    REFERENCES `NotificationEvent` (`notificationEventId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `DeviceToken`
-- -----------------------------------------------------
CREATE TABLE `DeviceToken` (
  `deviceTokenId`           VARCHAR(255) NOT NULL DEFAULT '',
  `applicationId`           BIGINT NOT NULL,
  `platformId`              BIGINT NOT NULL,
  `userId`                  VARCHAR(64) NOT NULL,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`deviceTokenId`),
    KEY `fk_DeviceToken_PlatformId` (`platformId`),
    KEY `fk_DeviceToken_ApplicationId` (`applicationId`),
  CONSTRAINT `fk_DeviceToken_ApplicationId`
    FOREIGN KEY (`applicationId`)
    REFERENCES `Application` (`applicationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DeviceToken_PlatformId`
    FOREIGN KEY (`platformId`)
    REFERENCES `Platform` (`platformId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `Notification`
-- -----------------------------------------------------
CREATE TABLE `Notification` (
  `notificationId`          BIGINT NOT NULL AUTO_INCREMENT,
  `triggerType`             VARCHAR(64) NOT NULL,
  `applicationId`           BIGINT NOT NULL,
  `sentTime`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `message`                 VARCHAR(2048) NOT NULL,
  `userId`                  VARCHAR(64) DEFAULT NULL,
  `deviceToken`             VARCHAR(255) DEFAULT NULL,
  `notificationTriggerId`   BIGINT DEFAULT NULL,
  `notificationEventId`     BIGINT DEFAULT NULL,
  `beaconId`                BIGINT DEFAULT NULL,
  `topicId`                 BIGINT DEFAULT NULL,
  `geofenceId`              BIGINT DEFAULT NULL,
  `created`                 TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `modified`                TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`notificationId`),
    KEY `fk_Notification_Application_idx` (`applicationId`),
    KEY `fk_Notification_Beacon_idx` (`beaconId`),
    KEY `fk_Notification_NotificationTrigger_idx` (`notificationTriggerId`),
    KEY `fk_Notification_NotificationEvent_idx` (`notificationEventId`),
    KEY `fk_Notification_Topic_idx` (`topicId`),
    KEY `fk_Notification_Geofence_idx` (`geofenceId`),
  CONSTRAINT `fk_Notification_Application`
    FOREIGN KEY (`applicationId`)
    REFERENCES `Application` (`applicationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_Beacon`
    FOREIGN KEY (`beaconId`)
    REFERENCES `Beacon` (`beaconId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_NotificationEvent`
    FOREIGN KEY (`notificationEventId`)
    REFERENCES `NotificationEvent` (`notificationEventId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_NotificationTrigger`
    FOREIGN KEY (`notificationTriggerId`)
    REFERENCES `NotificationTrigger` (`notificationTriggerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_Geofence`
    FOREIGN KEY (`geofenceId`)
    REFERENCES `Geofence` (`geofenceId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_Topic`
    FOREIGN KEY (`topicId`)
    REFERENCES `Topic` (`topicId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
