-- -----------------------------------------------------
-- Alter `DeviceToken`
-- -----------------------------------------------------
DROP TABLE `DeviceToken`;

CREATE TABLE `DeviceToken` (
  `deviceTokenId`           BIGINT NOT NULL AUTO_INCREMENT,
  `deviceToken`             VARCHAR(255) NOT NULL DEFAULT '',
  `applicationId`           BIGINT NOT NULL,
  `platformId`              BIGINT NOT NULL,
  `userId`                  VARCHAR(64) NULL DEFAULT NULL,
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
