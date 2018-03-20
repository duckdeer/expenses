-- MySQL Script generated by MySQL Workbench
-- 01/16/18 20:32:03
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema expense
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema expense
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `expense` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `expense` ;

-- -----------------------------------------------------
-- Table `expense`.`CATEGORY`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `expense`.`CATEGORY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(255) NOT NULL,
  `TYPE` VARCHAR(20) NOT NULL COMMENT 'INCOME, EXPENSE',
  `ACTIVE` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `expense`.`EXPENSE`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `expense`.`EXPENSE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `DATE` DATE NOT NULL,
  `VALUE` DECIMAL(10,2) NOT NULL,
  `NOTE` VARCHAR(255) NULL,
  `CATEGORY_ID` INT NOT NULL,
  `TYPE` VARCHAR(25) NULL COMMENT 'FIXED, NORMAL',
  PRIMARY KEY (`ID`),
  INDEX `FK1_EXPENSE_idx` (`CATEGORY_ID` ASC),
  CONSTRAINT `FK1_EXPENSE`
    FOREIGN KEY (`CATEGORY_ID`)
    REFERENCES `expense`.`CATEGORY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `expense`.`INCOME`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `expense`.`INCOME` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `VALUE` DECIMAL NOT NULL,
  `NAME` VARCHAR(255) NOT NULL,
  `NOTE` VARCHAR(255) NULL,
  `VALID_FROM` DATE NOT NULL,
  `VALID_THRU` DATE NOT NULL,
  `TYPE` VARCHAR(20) NOT NULL COMMENT 'MONTHLY, YEARLY, EXTRA',
  `CATEGORY_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FK1_INCOME_idx` (`CATEGORY_ID` ASC),
  CONSTRAINT `FK1_INCOME`
    FOREIGN KEY (`CATEGORY_ID`)
    REFERENCES `expense`.`CATEGORY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
