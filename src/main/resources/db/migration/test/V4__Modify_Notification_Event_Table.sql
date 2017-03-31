-- -----------------------------------------------------
-- Alter `NotificationEvent`
-- -----------------------------------------------------
ALTER TABLE `NotificationEvent`
CHANGE COLUMN `enter` `onEnter` BIT NOT NULL DEFAULT 1;

ALTER TABLE `NotificationEvent`
CHANGE COLUMN `dwell` `onDwell` BIT NOT NULL DEFAULT 1;

ALTER TABLE `NotificationEvent`
CHANGE COLUMN `exit` `onExit` BIT NOT NULL DEFAULT 1;
