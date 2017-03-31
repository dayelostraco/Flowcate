-- -----------------------------------------------------
-- Alter `NotificationEvent`
-- -----------------------------------------------------
ALTER TABLE `NotificationEvent`
CHANGE COLUMN `onEnter` `onEnter` BIT(1) NOT NULL DEFAULT 0;

ALTER TABLE `NotificationEvent`
CHANGE COLUMN `onExit` `onExit` BIT(1) NOT NULL DEFAULT 0;

ALTER TABLE `NotificationEvent`
CHANGE COLUMN `onDwell` `onDwell` BIT(1) NOT NULL DEFAULT 0;
