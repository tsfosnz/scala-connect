-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.39 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for connect
DROP DATABASE IF EXISTS `connect`;
CREATE DATABASE IF NOT EXISTS `connect` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `connect`;




-- Dumping structure for table connect.administrator
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE IF NOT EXISTS `administrator` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_uuid` int(11) NOT NULL DEFAULT '0',
  `username` varchar(32) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `first_name` varchar(255) NOT NULL DEFAULT '',
  `last_name` varchar(255) NOT NULL DEFAULT '',
  `display_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `admin_uuid` (`admin_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table connect.mentor
DROP TABLE IF EXISTS `mentor`;
CREATE TABLE IF NOT EXISTS `mentor` (
  `mentor_id` int(11) NOT NULL AUTO_INCREMENT,
  `mentor_uuid` int(11) NOT NULL DEFAULT '0',
  `username` varchar(32) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `first_name` varchar(255) NOT NULL DEFAULT '',
  `last_name` varchar(255) NOT NULL DEFAULT '',
  `display_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`mentor_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `mentor_uuid` (`mentor_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table connect.member
DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `first_name` varchar(255) NOT NULL DEFAULT '',
  `last_name` varchar(255) NOT NULL DEFAULT '',
  `display_name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(512) NOT NULL,
  `created_at` datetime NULL,
  `updated_at` datetime NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table connect.post
DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` int(11) NOT NULL,
  `title` varchar(128) NOT NULL,
  `excerpt` varchar(4096) NOT NULL,
  `html_body` varchar(65535) NOT NULL,
  `text_body` varchar(65535) NOT NULL,
  `status` char(32) NOT NULL DEFAULT 'plan',
  `is_archived` enum('true','false') NOT NULL DEFAULT 'false',
  `created_at` datetime NULL,
  `updated_at` datetime NULL,
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- Dumping structure for table connect.team
DROP TABLE IF EXISTS `team`;
CREATE TABLE IF NOT EXISTS `team` (
  `team_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '0',
  `description` text NOT NULL DEFAULT '',
  `created_at` datetime NULL,
  `updated_at` datetime NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping structure for table connect.team
DROP TABLE IF EXISTS `team_member`;
CREATE TABLE IF NOT EXISTS `team_member` (
  `team_member_id` int(11) NOT NULL AUTO_INCREMENT,
  `team_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  PRIMARY KEY (`team_member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
