-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema demo_bank_v2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema demo_bank_v2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `demo_bank_v2` DEFAULT CHARACTER SET utf8 ;
USE `demo_bank_v2` ;

-- -----------------------------------------------------
-- Table `demo_bank_v2`.`persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`persons` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`users` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `enabled` TINYINT NOT NULL,
  `verified` TINYINT NOT NULL,
  `verified_at` TIMESTAMP NULL DEFAULT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` VARCHAR(45) NOT NULL DEFAULT 'CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP',
  `token` VARCHAR(250) NULL DEFAULT NULL,
  `code` INT NULL DEFAULT NULL,
  `persons_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `fk_users_persons1_idx` (`persons_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_persons1`
    FOREIGN KEY (`persons_id`)
    REFERENCES `demo_bank_v2`.`persons` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`accounts` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `users_id` BIGINT UNSIGNED NOT NULL,
  `number` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `balance` DECIMAL(18,2) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_accounts_users1_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_accounts_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `demo_bank_v2`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`payments` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `beneficiary` VARCHAR(250) NOT NULL,
  `beneficiary_account_number` VARCHAR(250) NOT NULL,
  `reference_number` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`roles` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`transactions` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `accounts_id` BIGINT UNSIGNED NOT NULL,
  `amount` DOUBLE NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `reason_code` VARCHAR(250) NULL DEFAULT NULL,
  `source` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `payments_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_transactions_accounts1_idx` (`accounts_id` ASC) VISIBLE,
  INDEX `fk_transactions_payments1_idx` (`payments_id` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_accounts1`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `demo_bank_v2`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_transactions_payments1`
    FOREIGN KEY (`payments_id`)
    REFERENCES `demo_bank_v2`.`payments` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `demo_bank_v2`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `demo_bank_v2`.`users_roles` (
  `users_id` BIGINT UNSIGNED NOT NULL,
  `roles_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`users_id`, `roles_id`),
  INDEX `fk_users_has_roles_roles1_idx` (`roles_id` ASC) VISIBLE,
  INDEX `fk_users_has_roles_users_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_has_roles_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `demo_bank_v2`.`roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_roles_users`
    FOREIGN KEY (`users_id`)
    REFERENCES `demo_bank_v2`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
