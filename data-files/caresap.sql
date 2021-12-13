-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: caresap
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AFBCUSRMAP_MASTER`
--

DROP TABLE IF EXISTS `AFBCUSRMAP_MASTER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AFBCUSRMAP_MASTER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `master_user` varchar(255) NOT NULL,
  `supervisor_slid` varchar(255) DEFAULT NULL,
  `user_alias` varchar(255) DEFAULT NULL,
  `USERID_ACTIVE` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `USER_TYPE_ID` int(11) DEFAULT NULL,
  `valid_from` datetime DEFAULT NULL,
  `valid_to` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AFBCUSRMAP_MASTER`
--

LOCK TABLES `AFBCUSRMAP_MASTER` WRITE;
/*!40000 ALTER TABLE `AFBCUSRMAP_MASTER` DISABLE KEYS */;
INSERT INTO `AFBCUSRMAP_MASTER` VALUES (1,NULL,'Joel','De Granda','JED0UQI','ETB0KEI',NULL,'Y',NULL,4,NULL,NULL),(2,NULL,'Rory','Farrell','C009260','JED0UQI',NULL,'N','Contractor',2,'2011-02-09 00:00:00','2011-03-02 00:00:00'),(3,NULL,'James','Evelyn','C009200','JED0UQI',NULL,'N','Contractor',2,'2011-02-09 00:00:00','2011-03-02 00:00:00');
/*!40000 ALTER TABLE `AFBCUSRMAP_MASTER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AFBC_CUSTOM_ENTITY`
--

DROP TABLE IF EXISTS `AFBC_CUSTOM_ENTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AFBC_CUSTOM_ENTITY` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ATTESTCERTTYPE_ID` varchar(255) DEFAULT NULL,
  `ENTITY_DESC` longtext,
  `ENTITY_LONG_DESC` longtext,
  `ENTITY_MODEL_ID` varchar(255) DEFAULT NULL,
  `ENTITY_NAME` varchar(255) DEFAULT NULL,
  `ENTITY_STATUS` varchar(255) DEFAULT NULL,
  `ENTITY_TYPEID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AFBC_CUSTOM_ENTITY`
--

LOCK TABLES `AFBC_CUSTOM_ENTITY` WRITE;
/*!40000 ALTER TABLE `AFBC_CUSTOM_ENTITY` DISABLE KEYS */;
INSERT INTO `AFBC_CUSTOM_ENTITY` VALUES (1,'50','Annual training course for personnel having authorized cyber or authorized unescorted physical access to Critical Cyber Assets that are owned or managed by Power Generation Division (PGD)',NULL,'54','CIP ANNUAL CCA ACCESS - PGD','1','1100'),(2,'50','Annual training course for personnel having authorized cyber or authorized unescorted physical access to Critical Cyber Assets that are owned or managed by Corporate Security or Information Managment',NULL,'54','CIP ANNUAL CCA ACCESS - CORP SEC/ IM','1','1100'),(3,'50','Annual training course for personnel having authorized cyber or authorized unescorted physical access to Critical Cyber Assets that are owned or managed by the System Control Center (SCC)',NULL,'54','CIP ANNUAL CCA ACCESS - SCC','1','1100'),(4,'50','Annual training course for personnel having authorized cyber or authorized unescorted physical access to Critical Cyber Assets that are owned or managed by Transmission -Substation Operations (TSO)',NULL,'54','CIP ANNUAL CCA ACCESS - TSO','1','1100'),(5,'50','Annual training course for all Supervisors/Sponsors who are responsible for individuals with authorized cyber or authorized unescorted physical access to Critical Cyber Assets',NULL,'54','CIP ANNUAL CCA ACCESS - APPROVER',NULL,'1100'),(6,'51','EMPLOYEE PRA',NULL,'54','EMPLOYEE PRA','1','1103'),(7,NULL,'Annual training course for contractors having authorized unescorted physical access to Critical Cyber Assets',NULL,'54','CIP ANNUAL CCA ACCESS - PHYSICAL CONTRACTOR','1','1100'),(8,NULL,'Annual training course for contractors having authorized cyber access to Critical Cyber Assets',NULL,'54','CIP ANNUAL CCA ACCESS - CYBER CONTRACTOR','1','1100'),(9,NULL,'CONTRACTOR PRA',NULL,'54','CONTRACTOR PRA','1','1103'),(10,'50','TR1_TEST',NULL,'50','TR1_TEST','1','1100');
/*!40000 ALTER TABLE `AFBC_CUSTOM_ENTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ARBT_USR_ENT_DTLS`
--

DROP TABLE IF EXISTS `ARBT_USR_ENT_DTLS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ARBT_USR_ENT_DTLS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) DEFAULT NULL,
  `CUST_ENT_ID` int(11) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `IS_LATEST` varchar(255) DEFAULT NULL,
  `REQ_NO` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARBT_USR_ENT_DTLS`
--

LOCK TABLES `ARBT_USR_ENT_DTLS` WRITE;
/*!40000 ALTER TABLE `ARBT_USR_ENT_DTLS` DISABLE KEYS */;
INSERT INTO `ARBT_USR_ENT_DTLS` VALUES (1,'CIP ANNUAL CCA ACCESS - CYBER CONTRACTOR',17,'2011-05-11 00:00:00','1',NULL,'2010-05-18 00:00:00','Y','C000267'),(2,'CIP ANNUAL CCA ACCESS - PHYSICAL CONTRACTOR',16,'2012-03-14 00:00:00','1',NULL,'2011-03-22 00:00:00','Y','C001305'),(3,'CONTRACTOR PRA',18,'2018-01-27 00:00:00','1',NULL,'2011-02-28 00:00:00','Y','C001305'),(4,'CONTRACTOR PRA',18,'2012-04-08 00:00:00','1',NULL,'2005-05-10 00:00:00','Y','C016182'),(5,'EMPLOYEE PRA',15,'2016-02-02 00:00:00','1',NULL,'2009-03-05 00:00:00','Y','ABL0IL9'),(6,'CIP ANNUAL CCA ACCESS - TSO',13,'2011-04-18 00:00:00','1',NULL,'2010-04-25 00:00:00','N','MXO035R'),(7,'CIP ANNUAL CCA ACCESS - PGD',10,'2011-04-19 00:00:00','0',NULL,'2010-04-26 00:00:00','N','BXT0NA7'),(8,'CIP ANNUAL CCA ACCESS - CORP SEC/ IM',11,'2011-04-19 00:00:00','0',NULL,'2010-04-26 00:00:00','N','JXB0ZQD');
/*!40000 ALTER TABLE `ARBT_USR_ENT_DTLS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BACKGROUND_CHECK`
--

DROP TABLE IF EXISTS `BACKGROUND_CHECK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BACKGROUND_CHECK` (
  `NERC_LOC_CODE` varchar(80) NOT NULL,
  `PERNR` int(8) NOT NULL,
  `NAME` varchar(254) NOT NULL,
  `STATUS` varchar(24) DEFAULT NULL,
  `EMP_GROUP` varchar(50) DEFAULT NULL,
  `LAST_HIRE_DATE` date DEFAULT NULL,
  `LAST_BG_CHECK` date DEFAULT NULL,
  `PRA_STATUS` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`NERC_LOC_CODE`,`PERNR`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BACKGROUND_CHECK`
--

LOCK TABLES `BACKGROUND_CHECK` WRITE;
/*!40000 ALTER TABLE `BACKGROUND_CHECK` DISABLE KEYS */;
INSERT INTO `BACKGROUND_CHECK` VALUES ('Fortney',5001,'EMP_NAME-5001','5001 - Active','D - Non Exempt-Variable','2008-02-22','2010-12-06','Active'),('Fortney',5002,'EMP_NAME-5002','5002 - Active','D - Non Exempt-Variable','2007-11-03','2011-01-14','Active'),('Fortney',5004,'EMP_NAME-5004','5004 - Active','D - Non Exempt-Variable','2007-11-02','2010-12-04','Active'),('Fortney',5005,'EMP_NAME-5005','5005 - Active','D - Non Exempt-Variable','2007-05-14','2010-12-19','Active'),('Fortney',5006,'EMP_NAME-5006','5006 - Active','D - Non Exempt-Variable','2007-05-18','2010-12-18','Active'),('Fortney',5007,'EMP_NAME-5007','5007 - Active','D - Non Exempt-Variable','2009-05-04','2010-12-09','Active'),('Fortney',5008,'EMP_NAME-5008','5008 - Active','D - Non Exempt-Variable','2009-06-23','2011-01-09','Active'),('Fortney',5010,'EMP_NAME-5010','5010 - Active','D - Non Exempt-Variable','2009-05-13','2010-12-29','Active'),('Fortney',5011,'EMP_NAME-5011','5011 - Active','D - Non Exempt-Variable','2007-04-13','2010-11-28','Active'),('Fortney',5012,'EMP_NAME-5012','5012 - Active','D - Non Exempt-Variable','2008-05-28','2011-01-13','Active'),('Fortney',5013,'EMP_NAME-5013','5013 - Active','D - Non Exempt-Variable','2007-09-14','2011-01-13','Active'),('Fortney',5014,'EMP_NAME-5014','5014 - Active','D - Non Exempt-Variable','2007-02-27','2010-12-23','Active'),('Fortney',5015,'EMP_NAME-5015','5015 - Active','D - Non Exempt-Variable','2008-12-28','2010-12-09','Active'),('Fortney',5016,'EMP_NAME-5016','5016 - Active','D - Non Exempt-Variable','2009-05-20','2010-12-11','Active');
/*!40000 ALTER TABLE `BACKGROUND_CHECK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `F5_SCC_CCAUSERS`
--

DROP TABLE IF EXISTS `F5_SCC_CCAUSERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `F5_SCC_CCAUSERS` (
  `SLID_ID` varchar(20) DEFAULT NULL,
  `PERNR` int(10) NOT NULL,
  `FULL_NAME` varchar(254) DEFAULT NULL,
  `POSITION_TITLE` varchar(254) DEFAULT NULL,
  `SUPV_FULL_NAME` varchar(254) DEFAULT NULL,
  `PERS_SUBAREA_DESC` varchar(254) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `ACCESS_TYPE` varchar(255) DEFAULT NULL,
  `BADGE_NUMBER` varchar(255) DEFAULT NULL,
  `PRA_STATUS` varchar(40) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `F5_SCC_CCAUSERS`
--

LOCK TABLES `F5_SCC_CCAUSERS` WRITE;
/*!40000 ALTER TABLE `F5_SCC_CCAUSERS` DISABLE KEYS */;
INSERT INTO `F5_SCC_CCAUSERS` VALUES ('emp-1-1-1-1',5001,'EMP_LN_1 EMP_FN_1','Position0','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-1-1-1@gmail.com','BOTH','BADGE-1','ACTIVE'),('emp-1-1-1-2',5002,'EMP_LN_2 EMP_FN_2','Position0','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-1-1-2@gmail.com','ELECTRONIC','BADGE-2','ACTIVE'),('emp-1-1-2-1',5003,'EMP_LN_1 EMP_FN_1','Position0','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-1-2-1@gmail.com','PHYSICAL','BADGE-3','ACTIVE'),('emp-1-1-2-2',5004,'EMP_LN_2 EMP_FN_2','Position0','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-1-2-2@gmail.com','ELECTRONIC',NULL,'ACTIVE'),('emp-1-2-1-1',5005,'EMP_LN_1 EMP_FN_1','Position0','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-2-1-1@gmail.com','ELECTRONIC','BADGE-5','ACTIVE'),('emp-1-2-1-2',5006,'EMP_LN_2 EMP_FN_2','Position0','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-2-1-2@gmail.com','PHYSICAL','BADGE-6','ACTIVE'),('emp-1-2-2-1',5007,'EMP_LN_1 EMP_FN_1','Position0','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-2-2-1@gmail.com','PHYSICAL',NULL,'ACTIVE'),('emp-1-2-2-2',5008,'EMP_LN_2 EMP_FN_2','Position0','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM0 Description','care.force5+emp-1-2-2-2@gmail.com','ELECTRONIC','BADGE-8','ACTIVE'),('emp-2-1-1-1',5009,'EMP_LN_1 EMP_FN_1','Position1','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-1-1-1@gmail.com','BOTH','BADGE-9','ACTIVE'),('emp-2-1-1-2',5010,'EMP_LN_2 EMP_FN_2','Position1','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-1-1-2@gmail.com','ELECTRONIC','BADGE-10','ACTIVE'),('emp-2-1-2-1',5011,'EMP_LN_1 EMP_FN_1','Position1','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-1-2-1@gmail.com','BOTH','BADGE-11','ACTIVE'),('emp-2-1-2-2',5012,'EMP_LN_2 EMP_FN_2','Position1','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-1-2-2@gmail.com','ELECTRONIC','BADGE-12','ACTIVE'),('emp-2-2-1-1',5013,'EMP_LN_1 EMP_FN_1','Position1','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-2-1-1@gmail.com','PHYSICAL','BADGE-13','ACTIVE'),('emp-2-2-1-2',5014,'EMP_LN_2 EMP_FN_2','Position1','SUP_LN_1 SUP_FN_1','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-2-1-2@gmail.com','PHYSICAL','BADGE-14','ACTIVE'),('emp-2-2-2-1',5015,'EMP_LN_1 EMP_FN_1','Position1','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-2-2-1@gmail.com','PHYSICAL','BADGE-15','ACTIVE'),('emp-2-2-2-2',5016,'EMP_LN_2 EMP_FN_2','Position1','SUP_LN_2 SUP_FN_2','PERS_SUB_AREA_NUM1 Description','care.force5+emp-2-2-2-2@gmail.com','PHYSICAL','BADGE-16','ACTIVE');
/*!40000 ALTER TABLE `F5_SCC_CCAUSERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FERC_CLASS`
--

DROP TABLE IF EXISTS `FERC_CLASS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FERC_CLASS` (
  `ID_PRNL` varchar(10) NOT NULL,
  `ID_CRS` varchar(10) NOT NULL,
  `DT_CRS_SESN_END` date DEFAULT NULL,
  `NM_1ST` varchar(20) NOT NULL,
  `NM_LAST` varchar(20) NOT NULL,
  `ID_SSEC` varchar(20) NOT NULL,
  `DS_CRS_TITL` varchar(40) NOT NULL,
  `CD_CRS_STAT` varchar(1) DEFAULT NULL,
  `CD_PRSN_PAY_LOC` varchar(30) DEFAULT NULL,
  `DS_PRSN_PAY_LOC` varchar(30) DEFAULT NULL,
  `JOB_TITLE` varchar(40) DEFAULT NULL,
  `LOGIN_ID` varchar(20) DEFAULT NULL,
  `DS_BU` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID_PRNL`,`ID_CRS`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FERC_CLASS`
--

LOCK TABLES `FERC_CLASS` WRITE;
/*!40000 ALTER TABLE `FERC_CLASS` DISABLE KEYS */;
INSERT INTO `FERC_CLASS` VALUES ('PRNL-1','CN-1','2011-01-06','FirstName-1','LastName-1','SSEC_ID-1','DS_CRS_TITLE-1',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-2','CN-2','2011-01-06','FirstName-1','LastName-1','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-3','CN-3','2011-01-06','FirstName-1','LastName-1','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-4','CN-5','2011-01-06','FirstName-1','LastName-1','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-5','CN-6','2011-01-06','FirstName-1','LastName-1','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-6','CN-7','2011-01-06','FirstName-1','LastName-1','SSEC_ID-7','DS_CRS_TITLE-7',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-7','CN-8','2011-01-06','FirstName-1','LastName-1','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-8','CN-9','2011-01-06','FirstName-1','LastName-1','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-1-1-1-1',NULL),('PRNL-9','CN-1','2011-01-06','FirstName-2','LastName-2','SSEC_ID-1','DS_CRS_TITLE-1',NULL,NULL,NULL,NULL,'EMP-1-1-1-2',NULL),('PRNL-10','CN-2','2011-01-06','FirstName-2','LastName-2','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-1-1-1-2',NULL),('PRNL-11','CN-3','2011-01-06','FirstName-2','LastName-2','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-1-1-1-2',NULL),('PRNL-12','CN-5','2011-01-06','FirstName-2','LastName-2','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-1-1-2',NULL),('PRNL-13','CN-9','2011-01-06','FirstName-2','LastName-2','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-1-1-1-2',NULL),('PRNL-14','CN-1','2011-01-06','FirstName-3','LastName-3','SSEC_ID-1','DS_CRS_TITLE-1',NULL,NULL,NULL,NULL,'EMP-1-1-2-1',NULL),('PRNL-15','CN-2','2011-01-06','FirstName-3','LastName-3','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-1-1-2-1',NULL),('PRNL-16','CN-3','2011-01-06','FirstName-3','LastName-3','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-1-1-2-1',NULL),('PRNL-17','CN-5','2011-01-06','FirstName-3','LastName-3','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-1-2-1',NULL),('PRNL-18','CN-6','2011-01-06','FirstName-3','LastName-3','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-1-1-2-1',NULL),('PRNL-19','CN-8','2011-01-06','FirstName-3','LastName-3','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-1-1-2-1',NULL),('PRNL-20','CN-1','2011-01-06','FirstName-4','LastName-4','SSEC_ID-1','DS_CRS_TITLE-1',NULL,NULL,NULL,NULL,'EMP-1-1-2-2',NULL),('PRNL-21','CN-2','2011-01-06','FirstName-4','LastName-4','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-1-1-2-2',NULL),('PRNL-22','CN-5','2011-01-06','FirstName-4','LastName-4','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-1-2-2',NULL),('PRNL-23','CN-6','2011-01-06','FirstName-4','LastName-4','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-1-1-2-2',NULL),('PRNL-24','CN-9','2011-01-06','FirstName-4','LastName-4','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-1-1-2-2',NULL),('PRNL-25','CN-1','2011-01-06','FirstName-5','LastName-5','SSEC_ID-1','DS_CRS_TITLE-1',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-26','CN-2','2011-01-06','FirstName-5','LastName-5','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-27','CN-3','2011-01-06','FirstName-5','LastName-5','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-28','CN-5','2011-01-06','FirstName-5','LastName-5','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-29','CN-6','2011-01-06','FirstName-5','LastName-5','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-30','CN-7','2011-01-06','FirstName-5','LastName-5','SSEC_ID-7','DS_CRS_TITLE-7',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-31','CN-9','2011-01-06','FirstName-5','LastName-5','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-32','CN-10','2011-01-06','FirstName-5','LastName-5','SSEC_ID-10','DS_CRS_TITLE-10',NULL,NULL,NULL,NULL,'EMP-1-2-1-1',NULL),('PRNL-33','CN-5','2011-01-06','FirstName-6','LastName-6','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-2-1-2',NULL),('PRNL-34','CN-8','2011-01-06','FirstName-6','LastName-6','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-1-2-1-2',NULL),('PRNL-35','CN-9','2011-01-06','FirstName-6','LastName-6','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-1-2-1-2',NULL),('PRNL-36','CN-3','2011-01-06','FirstName-7','LastName-7','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-1-2-2-1',NULL),('PRNL-37','CN-4','2011-01-06','FirstName-7','LastName-7','SSEC_ID-4','DS_CRS_TITLE-4',NULL,NULL,NULL,NULL,'EMP-1-2-2-1',NULL),('PRNL-38','CN-5','2011-01-06','FirstName-7','LastName-7','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-2-2-1',NULL),('PRNL-39','CN-8','2011-01-06','FirstName-7','LastName-7','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-1-2-2-1',NULL),('PRNL-40','CN-9','2011-01-06','FirstName-7','LastName-7','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-1-2-2-1',NULL),('PRNL-41','CN-3','2011-01-06','FirstName-8','LastName-8','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-1-2-2-2',NULL),('PRNL-42','CN-4','2011-01-06','FirstName-8','LastName-8','SSEC_ID-4','DS_CRS_TITLE-4',NULL,NULL,NULL,NULL,'EMP-1-2-2-2',NULL),('PRNL-43','CN-5','2011-01-06','FirstName-8','LastName-8','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-1-2-2-2',NULL),('PRNL-44','CN-6','2011-01-06','FirstName-8','LastName-8','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-1-2-2-2',NULL),('PRNL-45','CN-8','2011-01-06','FirstName-8','LastName-8','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-1-2-2-2',NULL),('PRNL-46','CN-9','2011-01-06','FirstName-8','LastName-8','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-1-2-2-2',NULL),('PRNL-47','CN-2','2011-01-06','FirstName-9','LastName-9','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-2-1-1-1',NULL),('PRNL-48','CN-3','2011-01-06','FirstName-9','LastName-9','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-2-1-1-1',NULL),('PRNL-49','CN-4','2011-01-06','FirstName-9','LastName-9','SSEC_ID-4','DS_CRS_TITLE-4',NULL,NULL,NULL,NULL,'EMP-2-1-1-1',NULL),('PRNL-50','CN-5','2011-01-06','FirstName-9','LastName-9','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-2-1-1-1',NULL),('PRNL-51','CN-6','2011-01-06','FirstName-9','LastName-9','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-2-1-1-1',NULL),('PRNL-52','CN-7','2011-01-06','FirstName-9','LastName-9','SSEC_ID-7','DS_CRS_TITLE-7',NULL,NULL,NULL,NULL,'EMP-2-1-1-1',NULL),('PRNL-53','CN-1','2011-01-06','FirstName-10','LastName-10','SSEC_ID-1','DS_CRS_TITLE-1',NULL,NULL,NULL,NULL,'EMP-2-1-1-2',NULL),('PRNL-54','CN-2','2011-01-06','FirstName-10','LastName-10','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-2-1-1-2',NULL),('PRNL-55','CN-3','2011-01-06','FirstName-10','LastName-10','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-2-1-1-2',NULL),('PRNL-56','CN-4','2011-01-06','FirstName-10','LastName-10','SSEC_ID-4','DS_CRS_TITLE-4',NULL,NULL,NULL,NULL,'EMP-2-1-1-2',NULL),('PRNL-57','CN-6','2011-01-06','FirstName-10','LastName-10','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-2-1-1-2',NULL),('PRNL-58','CN-7','2011-01-06','FirstName-10','LastName-10','SSEC_ID-7','DS_CRS_TITLE-7',NULL,NULL,NULL,NULL,'EMP-2-1-1-2',NULL),('PRNL-59','CN-3','2011-01-06','FirstName-11','LastName-11','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-2-1-2-1',NULL),('PRNL-60','CN-4','2011-01-06','FirstName-11','LastName-11','SSEC_ID-4','DS_CRS_TITLE-4',NULL,NULL,NULL,NULL,'EMP-2-1-2-1',NULL),('PRNL-61','CN-6','2011-01-06','FirstName-11','LastName-11','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-2-1-2-1',NULL),('PRNL-62','CN-7','2011-01-06','FirstName-11','LastName-11','SSEC_ID-7','DS_CRS_TITLE-7',NULL,NULL,NULL,NULL,'EMP-2-1-2-1',NULL),('PRNL-63','CN-9','2011-01-06','FirstName-11','LastName-11','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-2-1-2-1',NULL),('PRNL-64','CN-10','2011-01-06','FirstName-11','LastName-11','SSEC_ID-10','DS_CRS_TITLE-10',NULL,NULL,NULL,NULL,'EMP-2-1-2-1',NULL),('PRNL-65','CN-3','2011-01-06','FirstName-12','LastName-12','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-2-1-2-2',NULL),('PRNL-66','CN-4','2011-01-06','FirstName-12','LastName-12','SSEC_ID-4','DS_CRS_TITLE-4',NULL,NULL,NULL,NULL,'EMP-2-1-2-2',NULL),('PRNL-67','CN-5','2011-01-06','FirstName-12','LastName-12','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-2-1-2-2',NULL),('PRNL-68','CN-8','2011-01-06','FirstName-12','LastName-12','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-2-1-2-2',NULL),('PRNL-69','CN-5','2011-01-06','FirstName-13','LastName-13','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-2-2-1-1',NULL),('PRNL-70','CN-6','2011-01-06','FirstName-13','LastName-13','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-2-2-1-1',NULL),('PRNL-71','CN-8','2011-01-06','FirstName-13','LastName-13','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-2-2-1-1',NULL),('PRNL-72','CN-9','2011-01-06','FirstName-13','LastName-13','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-2-2-1-1',NULL),('PRNL-73','CN-10','2011-01-06','FirstName-13','LastName-13','SSEC_ID-10','DS_CRS_TITLE-10',NULL,NULL,NULL,NULL,'EMP-2-2-1-1',NULL),('PRNL-74','CN-3','2011-01-06','FirstName-14','LastName-14','SSEC_ID-3','DS_CRS_TITLE-3',NULL,NULL,NULL,NULL,'EMP-2-2-1-2',NULL),('PRNL-75','CN-8','2011-01-06','FirstName-14','LastName-14','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-2-2-1-2',NULL),('PRNL-76','CN-9','2011-01-06','FirstName-14','LastName-14','SSEC_ID-9','DS_CRS_TITLE-9',NULL,NULL,NULL,NULL,'EMP-2-2-1-2',NULL),('PRNL-77','CN-10','2011-01-06','FirstName-14','LastName-14','SSEC_ID-10','DS_CRS_TITLE-10',NULL,NULL,NULL,NULL,'EMP-2-2-1-2',NULL),('PRNL-78','CN-1','2011-01-06','FirstName-15','LastName-15','SSEC_ID-1','DS_CRS_TITLE-1',NULL,NULL,NULL,NULL,'EMP-2-2-2-1',NULL),('PRNL-79','CN-2','2011-01-06','FirstName-15','LastName-15','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-2-2-2-1',NULL),('PRNL-80','CN-5','2011-01-06','FirstName-15','LastName-15','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-2-2-2-1',NULL),('PRNL-81','CN-2','2011-01-06','FirstName-16','LastName-16','SSEC_ID-2','DS_CRS_TITLE-2',NULL,NULL,NULL,NULL,'EMP-2-2-2-2',NULL),('PRNL-82','CN-5','2011-01-06','FirstName-16','LastName-16','SSEC_ID-5','DS_CRS_TITLE-5',NULL,NULL,NULL,NULL,'EMP-2-2-2-2',NULL),('PRNL-83','CN-6','2011-01-06','FirstName-16','LastName-16','SSEC_ID-6','DS_CRS_TITLE-6',NULL,NULL,NULL,NULL,'EMP-2-2-2-2',NULL),('PRNL-84','CN-7','2011-01-06','FirstName-16','LastName-16','SSEC_ID-7','DS_CRS_TITLE-7',NULL,NULL,NULL,NULL,'EMP-2-2-2-2',NULL),('PRNL-85','CN-8','2011-01-06','FirstName-16','LastName-16','SSEC_ID-8','DS_CRS_TITLE-8',NULL,NULL,NULL,NULL,'EMP-2-2-2-2',NULL),('PRNL-86','CN-10','2011-01-06','FirstName-16','LastName-16','SSEC_ID-10','DS_CRS_TITLE-10',NULL,NULL,NULL,NULL,'EMP-2-2-2-2',NULL);
/*!40000 ALTER TABLE `FERC_CLASS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PS_READAREACATS`
--

DROP TABLE IF EXISTS `PS_READAREACATS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PS_READAREACATS` (
  `AREA` varchar(255) NOT NULL,
  `CATEGORY` varchar(255) NOT NULL,
  `READER` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PS_READAREACATS`
--

LOCK TABLES `PS_READAREACATS` WRITE;
/*!40000 ALTER TABLE `PS_READAREACATS` DISABLE KEYS */;
INSERT INTO `PS_READAREACATS` VALUES ('Area-1','Feed Entitlement-1','Reader-1'),('Area-2','Feed Entitlement-2','Reader-2'),('Area-3','Feed Entitlement-3','Reader-3'),('Area-4','Feed Entitlement-4','Reader-4'),('Area-5','Feed Entitlement-5','Reader-5'),('Area-6','Feed Entitlement-6','Reader-6'),('Area-7','Feed Entitlement-7','Reader-7'),('Area-8','Feed Entitlement-8','Reader-8'),('Area-9','Feed Entitlement-9','Reader-9'),('Area-10','Feed Entitlement-10','Reader-10'),('Area-11','Feed Entitlement-11','Reader-11'),('Area-12','Feed Entitlement-12','Reader-12'),('Area-13','Feed Entitlement-13','Reader-13'),('Area-14','Feed Entitlement-14','Reader-14'),('Area-15','Feed Entitlement-15','Reader-15');
/*!40000 ALTER TABLE `PS_READAREACATS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SECURITY_INFO`
--

DROP TABLE IF EXISTS `SECURITY_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SECURITY_INFO` (
  `LAST_NAME` varchar(255) NOT NULL,
  `FIRST_NAME` varchar(255) NOT NULL,
  `PERSONNEL_NUMBER` varchar(255) DEFAULT NULL,
  `CATEGORY` varchar(255) NOT NULL,
  `BADGE` int(22) NOT NULL,
  `JOB_TITLE` varchar(255) DEFAULT NULL,
  `MAIL_CODE` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SECURITY_INFO`
--

LOCK TABLES `SECURITY_INFO` WRITE;
/*!40000 ALTER TABLE `SECURITY_INFO` DISABLE KEYS */;
INSERT INTO `SECURITY_INFO` VALUES ('Employee_FN-2','Employee_LN-2','5002','Feed Entitlement-11',50002,'Employee Role-2','500002'),('Employee_FN-2','Employee_LN-2','5002','Feed Entitlement-14',50002,'Employee Role-2','500002'),('Employee_FN-3','Employee_LN-3','5003','Feed Entitlement-5',50003,'Employee Role-3','500003'),('Employee_FN-3','Employee_LN-3','5003','Feed Entitlement-9',50003,'Employee Role-3','500003'),('Employee_FN-3','Employee_LN-3','5003','Feed Entitlement-14',50003,'Employee Role-3','500003'),('Employee_FN-4','Employee_LN-4','5004','Feed Entitlement-3',50004,'Employee Role-4','500004'),('Employee_FN-4','Employee_LN-4','5004','Feed Entitlement-13',50004,'Employee Role-4','500004'),('Employee_FN-4','Employee_LN-4','5004','Feed Entitlement-5',50004,'Employee Role-4','500004'),('Employee_FN-6','Employee_LN-6','5006','Feed Entitlement-7',50006,'Employee Role-6','500006'),('Employee_FN-6','Employee_LN-6','5006','Feed Entitlement-13',50006,'Employee Role-6','500006'),('Employee_FN-6','Employee_LN-6','5006','Feed Entitlement-6',50006,'Employee Role-6','500006'),('Employee_FN-6','Employee_LN-6','5006','Feed Entitlement-1',50006,'Employee Role-6','500006'),('Employee_FN-8','Employee_LN-8','5008','Feed Entitlement-3',50008,'Employee Role-8','500008'),('Employee_FN-8','Employee_LN-8','5008','Feed Entitlement-11',50008,'Employee Role-8','500008'),('Employee_FN-11','Employee_LN-11','5011','Feed Entitlement-4',50011,'Employee Role-11','500011'),('Employee_FN-11','Employee_LN-11','5011','Feed Entitlement-2',50011,'Employee Role-11','500011'),('Employee_FN-14','Employee_LN-14','5014','Feed Entitlement-10',50014,'Employee Role-14','500014');
/*!40000 ALTER TABLE `SECURITY_INFO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cip_feed`
--

DROP TABLE IF EXISTS `cip_feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cip_feed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `pernr` bigint(20) NOT NULL,
  `course_number` varchar(255) NOT NULL,
  `date_completed` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cip_feed`
--

LOCK TABLES `cip_feed` WRITE;
/*!40000 ALTER TABLE `cip_feed` DISABLE KEYS */;
INSERT INTO `cip_feed` VALUES (25,0,5016,'CN-1210025','2010-04-26 12:48:31'),(26,0,5015,'CN-1210026','2010-04-07 12:48:31'),(27,0,5014,'CN-1210027','2010-05-02 12:48:31'),(28,0,5013,'CN-1210028','2010-04-09 12:48:31'),(29,0,5012,'CN-1210029','2010-04-15 12:48:31'),(30,0,5011,'CN-1210030','2010-04-12 12:48:31'),(31,0,5010,'CN-1210031','2010-04-15 12:48:31'),(32,0,5009,'CN-1210032','2010-05-01 12:48:31'),(33,0,5008,'CN-1210033','2010-04-11 12:48:31'),(34,0,5007,'CN-1210034','2010-04-24 12:48:31'),(35,0,5006,'CN-1210035','2010-04-24 12:48:31'),(36,0,5005,'CN-1210036','2010-04-09 12:48:32'),(37,0,5004,'CN-1210037','2010-04-25 12:48:32'),(38,0,5003,'CN-1210038','2010-04-19 12:48:32'),(39,0,5002,'CN-1210039','2010-04-07 12:48:32'),(40,0,5001,'CN-1210040','2010-05-03 12:48:32');
/*!40000 ALTER TABLE `cip_feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dwfs_contractor_hr_info`
--

DROP TABLE IF EXISTS `dwfs_contractor_hr_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dwfs_contractor_hr_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_unit` varchar(255) DEFAULT NULL,
  `contractor_number` varchar(255) DEFAULT NULL,
  `contractor_slid` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `emergency_contact` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `personal_email` varchar(255) DEFAULT NULL,
  `supervisor_slid` varchar(255) DEFAULT NULL,
  `vendor_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dwfs_contractor_hr_info`
--

LOCK TABLES `dwfs_contractor_hr_info` WRITE;
/*!40000 ALTER TABLE `dwfs_contractor_hr_info` DISABLE KEYS */;
INSERT INTO `dwfs_contractor_hr_info` VALUES (1,'PGD','C000123','yyy0yyy','yyy0yyy@fpl.com','1112223333','John','Doe',NULL,'gxp0k9n','GE'),(2,'SCC','C000123','yyy1yyy','yyy1yyy@fpl.com','3331112222','Jane','Doe',NULL,'gxp0k9n','IBM');
/*!40000 ALTER TABLE `dwfs_contractor_hr_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dwfs_course_and_pra`
--

DROP TABLE IF EXISTS `dwfs_course_and_pra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dwfs_course_and_pra` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contractor_number` varchar(255) DEFAULT NULL,
  `contractor_slid` varchar(255) DEFAULT NULL,
  `course_name` varchar(255) NOT NULL,
  `employee_number` varchar(255) DEFAULT NULL,
  `employee_slid` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dwfs_course_and_pra`
--

LOCK TABLES `dwfs_course_and_pra` WRITE;
/*!40000 ALTER TABLE `dwfs_course_and_pra` DISABLE KEYS */;
INSERT INTO `dwfs_course_and_pra` VALUES (1,NULL,NULL,'Employee PRA','10001','xxx0xxx','2018-01-06 00:00:00','2011-07-27 05:59:06'),(2,'C000123','yyy0yyy','Contractor PRA',NULL,NULL,'2015-12-05 00:00:00','2011-07-27 05:59:07'),(3,'C000123','yyy0yyy','Contractor PRA',NULL,NULL,'2015-01-04 00:00:00','2011-07-27 05:59:07'),(4,NULL,NULL,'Employee PRA','10002','www0www','2014-01-07 00:00:00','2011-07-27 05:59:07'),(5,NULL,NULL,'CIP Annual Access CCA - SCC','10001','xxx0xxx','2012-01-06 00:00:00','2011-07-27 05:59:07'),(6,'C000123','yyy0yyy','CIP Annual Access CCA - Physical Contractor',NULL,NULL,'2012-12-05 00:00:00','2011-07-27 05:59:07'),(7,'C000123','yyy0yyy','CIP Annual Access CCA - Cyber Contractor',NULL,NULL,'2012-01-04 00:00:00','2011-07-27 05:59:07'),(8,NULL,NULL,'CIP Annual Access CCA - PGD','10002','www0www','2012-01-07 00:00:00','2011-07-27 05:59:07'),(9,NULL,NULL,'CIP Annual Access CCA - TSO','10002','www0www','2012-01-07 00:00:00','2011-07-27 05:59:07');
/*!40000 ALTER TABLE `dwfs_course_and_pra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_info`
--

DROP TABLE IF EXISTS `hr_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hr_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cell_phone_num` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `insert_dt` datetime DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `office_phone_num` varchar(255) DEFAULT NULL,
  `orgunit_desc` varchar(255) DEFAULT NULL,
  `orgunit_num` varchar(255) DEFAULT NULL,
  `pager_num` varchar(255) DEFAULT NULL,
  `person_status` varchar(255) DEFAULT NULL,
  `pers_area_desc` varchar(255) DEFAULT NULL,
  `pers_area_num` varchar(255) DEFAULT NULL,
  `pers_subarea_desc` varchar(255) DEFAULT NULL,
  `pers_subarea_num` varchar(255) DEFAULT NULL,
  `position_title` varchar(255) DEFAULT NULL,
  `supvsupv_full_name` varchar(255) DEFAULT NULL,
  `supvsupv_slid_id` varchar(255) DEFAULT NULL,
  `supv_full_name` varchar(255) DEFAULT NULL,
  `update_dt` datetime DEFAULT NULL,
  `PERNR` bigint(20) DEFAULT NULL,
  `SLID_ID` varchar(255) DEFAULT NULL,
  `SUPV_SLID_ID` varchar(255) DEFAULT NULL,
  `POSITION_NUM` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_info`
--

LOCK TABLES `hr_info` WRITE;
/*!40000 ALTER TABLE `hr_info` DISABLE KEYS */;
INSERT INTO `hr_info` VALUES (1,'461-694-1001','CEO_FN','CEO_LN CEO_FN','2011-01-04 19:12:56','CEO_LN','561-694-1001','1001 Description','1001','PAGER_NUM1','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM','PERS_SUB_AREA_NUM Description','PERS_SUB_AREA_NUM','Position',NULL,NULL,NULL,NULL,1001,'CEO',NULL,NULL),(2,'461-694-2001','VP_FN_1','VP_LN_1 VP_FN_1','2011-01-04 19:12:56','VP_LN_1','561-694-2001','2001 Description','2001','PAGER_NUM2','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0',NULL,NULL,'CEO_LN CEO_FN',NULL,2001,'VP-1','CEO',NULL),(3,'461-694-3001','GM_FN_1_1','GM_LN_1_1 GM_FN_1_1','2011-01-04 19:12:56','GM_LN_1_1','561-694-3001','3001 Description','3001','PAGER_NUM3','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','CEO_LN CEO_FN','CEO','VP_LN_1 VP_FN_1',NULL,3001,'GM-1-1','VP-1',NULL),(4,'461-694-4001','SUP_FN_1_1_1','SUP_LN_1_1_1 SUP_FN_1_1_1','2011-01-04 19:12:56','SUP_LN_1_1_1','561-694-4001','4001 Description','4001','PAGER_NUM4','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','VP_LN_1 VP_FN_1','VP-1','GM_LN_1 GM_FN_1',NULL,4001,'SUP-1-1-1','GM-1-1',NULL),(5,'461-694-5001','EMP_FN_1_1_1_1','EMP_LN_1_1_1_1 EMP_FN_1_1_1_1','2011-01-04 19:12:56','EMP_LN_1_1_1_1','561-694-5001','5001 Description','5001','PAGER_NUM5','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_1 GM_FN_1','GM-1-1','SUP_LN_1 SUP_FN_1',NULL,5001,'EMP-1-1-1-1','SUP-1-1-1',NULL),(6,'461-694-5002','EMP_FN_1_1_1_2','EMP_LN_1_1_1_2 EMP_FN_1_1_1_2','2011-01-04 19:12:56','EMP_LN_1_1_1_2','561-694-5002','5002 Description','5002','PAGER_NUM6','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_1 GM_FN_1','GM-1-1','SUP_LN_1 SUP_FN_1',NULL,5002,'EMP-1-1-1-2','SUP-1-1-1',NULL),(7,'461-694-4002','SUP_FN_1_1_2','SUP_LN_1_1_2 SUP_FN_1_1_2','2011-01-04 19:12:56','SUP_LN_1_1_2','561-694-4002','4002 Description','4002','PAGER_NUM7','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','VP_LN_1 VP_FN_1','VP-1','GM_LN_1 GM_FN_1',NULL,4002,'SUP-1-1-2','GM-1-1',NULL),(8,'461-694-5003','EMP_FN_1_1_2_1','EMP_LN_1_1_2_1 EMP_FN_1_1_2_1','2011-01-04 19:12:56','EMP_LN_1_1_2_1','561-694-5003','5003 Description','5003','PAGER_NUM8','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_1 GM_FN_1','GM-1-1','SUP_LN_2 SUP_FN_2',NULL,5003,'EMP-1-1-2-1','SUP-1-1-2',NULL),(9,'461-694-5004','EMP_FN_1_1_2_2','EMP_LN_1_1_2_2 EMP_FN_1_1_2_2','2011-01-04 19:12:56','EMP_LN_1_1_2_2','561-694-5004','5004 Description','5004','PAGER_NUM9','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_1 GM_FN_1','GM-1-1','SUP_LN_2 SUP_FN_2',NULL,5004,'EMP-1-1-2-2','SUP-1-1-2',NULL),(10,'461-694-3002','GM_FN_1_2','GM_LN_1_2 GM_FN_1_2','2011-01-04 19:12:56','GM_LN_1_2','561-694-3002','3002 Description','3002','PAGER_NUM10','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','CEO_LN CEO_FN','CEO','VP_LN_1 VP_FN_1',NULL,3002,'GM-1-2','VP-1',NULL),(11,'461-694-4003','SUP_FN_1_2_1','SUP_LN_1_2_1 SUP_FN_1_2_1','2011-01-04 19:12:56','SUP_LN_1_2_1','561-694-4003','4003 Description','4003','PAGER_NUM11','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','VP_LN_1 VP_FN_1','VP-1','GM_LN_2 GM_FN_2',NULL,4003,'SUP-1-2-1','GM-1-2',NULL),(12,'461-694-5005','EMP_FN_1_2_1_1','EMP_LN_1_2_1_1 EMP_FN_1_2_1_1','2011-01-04 19:12:56','EMP_LN_1_2_1_1','561-694-5005','5005 Description','5005','PAGER_NUM12','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_2 GM_FN_2','GM-1-2','SUP_LN_1 SUP_FN_1',NULL,5005,'EMP-1-2-1-1','SUP-1-2-1',NULL),(13,'461-694-5006','EMP_FN_1_2_1_2','EMP_LN_1_2_1_2 EMP_FN_1_2_1_2','2011-01-04 19:12:56','EMP_LN_1_2_1_2','561-694-5006','5006 Description','5006','PAGER_NUM13','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_2 GM_FN_2','GM-1-2','SUP_LN_1 SUP_FN_1',NULL,5006,'EMP-1-2-1-2','SUP-1-2-1',NULL),(14,'461-694-4004','SUP_FN_1_2_2','SUP_LN_1_2_2 SUP_FN_1_2_2','2011-01-04 19:12:56','SUP_LN_1_2_2','561-694-4004','4004 Description','4004','PAGER_NUM14','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','VP_LN_1 VP_FN_1','VP-1','GM_LN_2 GM_FN_2',NULL,4004,'SUP-1-2-2','GM-1-2',NULL),(15,'461-694-5007','EMP_FN_1_2_2_1','EMP_LN_1_2_2_1 EMP_FN_1_2_2_1','2011-01-04 19:12:56','EMP_LN_1_2_2_1','561-694-5007','5007 Description','5007','PAGER_NUM15','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_2 GM_FN_2','GM-1-2','SUP_LN_2 SUP_FN_2',NULL,5007,'EMP-1-2-2-1','SUP-1-2-2',NULL),(16,'461-694-5008','EMP_FN_1_2_2_2','EMP_LN_1_2_2_2 EMP_FN_1_2_2_2','2011-01-04 19:12:56','EMP_LN_1_2_2_2','561-694-5008','5008 Description','5008','PAGER_NUM16','Inactive','PERS_AREA_NUM0 Description','PERS_AREA_NUM0','PERS_SUB_AREA_NUM0 Description','PERS_SUB_AREA_NUM0','Position0','GM_LN_2 GM_FN_2','GM-1-2','SUP_LN_2 SUP_FN_2',NULL,5008,'EMP-1-2-2-2','SUP-1-2-2',NULL),(17,'461-694-2002','VP_FN_2','VP_LN_2 VP_FN_2','2011-01-04 19:12:56','VP_LN_2','561-694-2002','2002 Description','2002','PAGER_NUM17','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1',NULL,NULL,'CEO_LN CEO_FN',NULL,2002,'VP-2','CEO',NULL),(18,'461-694-3003','GM_FN_2_1','GM_LN_2_1 GM_FN_2_1','2011-01-04 19:12:56','GM_LN_2_1','561-694-3003','3003 Description','3003','PAGER_NUM18','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','CEO_LN CEO_FN','CEO','VP_LN_2 VP_FN_2',NULL,3003,'GM-2-1','VP-2',NULL),(19,'461-694-4005','SUP_FN_2_1_1','SUP_LN_2_1_1 SUP_FN_2_1_1','2011-01-04 19:12:56','SUP_LN_2_1_1','561-694-4005','4005 Description','4005','PAGER_NUM19','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','VP_LN_2 VP_FN_2','VP-2','GM_LN_1 GM_FN_1',NULL,4005,'SUP-2-1-1','GM-2-1',NULL),(20,'461-694-5009','EMP_FN_2_1_1_1','EMP_LN_2_1_1_1 EMP_FN_2_1_1_1','2011-01-04 19:12:56','EMP_LN_2_1_1_1','561-694-5009','5009 Description','5009','PAGER_NUM20','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_1 GM_FN_1','GM-2-1','SUP_LN_1 SUP_FN_1',NULL,5009,'EMP-2-1-1-1','SUP-2-1-1',NULL),(21,'461-694-5010','EMP_FN_2_1_1_2','EMP_LN_2_1_1_2 EMP_FN_2_1_1_2','2011-01-04 19:12:56','EMP_LN_2_1_1_2','561-694-5010','5010 Description','5010','PAGER_NUM21','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_1 GM_FN_1','GM-2-1','SUP_LN_1 SUP_FN_1',NULL,5010,'EMP-2-1-1-2','SUP-2-1-1',NULL),(22,'461-694-4006','SUP_FN_2_1_2','SUP_LN_2_1_2 SUP_FN_2_1_2','2011-01-04 19:12:56','SUP_LN_2_1_2','561-694-4006','4006 Description','4006','PAGER_NUM22','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','VP_LN_2 VP_FN_2','VP-2','GM_LN_1 GM_FN_1',NULL,4006,'SUP-2-1-2','GM-2-1',NULL),(23,'461-694-5011','EMP_FN_2_1_2_1','EMP_LN_2_1_2_1 EMP_FN_2_1_2_1','2011-01-04 19:12:56','EMP_LN_2_1_2_1','561-694-5011','5011 Description','5011','PAGER_NUM23','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_1 GM_FN_1','GM-2-1','SUP_LN_2 SUP_FN_2',NULL,5011,'EMP-2-1-2-1','SUP-2-1-2',NULL),(24,'461-694-5012','EMP_FN_2_1_2_2','EMP_LN_2_1_2_2 EMP_FN_2_1_2_2','2011-01-04 19:12:56','EMP_LN_2_1_2_2','561-694-5012','5012 Description','5012','PAGER_NUM24','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_1 GM_FN_1','GM-2-1','SUP_LN_2 SUP_FN_2',NULL,5012,'EMP-2-1-2-2','SUP-2-1-2',NULL),(25,'461-694-3004','GM_FN_2_2','GM_LN_2_2 GM_FN_2_2','2011-01-04 19:12:56','GM_LN_2_2','561-694-3004','3004 Description','3004','PAGER_NUM25','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','CEO_LN CEO_FN','CEO','VP_LN_2 VP_FN_2',NULL,3004,'GM-2-2','VP-2',NULL),(26,'461-694-4007','SUP_FN_2_2_1','SUP_LN_2_2_1 SUP_FN_2_2_1','2011-01-04 19:12:56','SUP_LN_2_2_1','561-694-4007','4007 Description','4007','PAGER_NUM26','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','VP_LN_2 VP_FN_2','VP-2','GM_LN_2 GM_FN_2',NULL,4007,'SUP-2-2-1','GM-2-2',NULL),(27,'461-694-5013','EMP_FN_2_2_1_1','EMP_LN_2_2_1_1 EMP_FN_2_2_1_1','2011-01-04 19:12:56','EMP_LN_2_2_1_1','561-694-5013','5013 Description','5013','PAGER_NUM27','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_2 GM_FN_2','GM-2-2','SUP_LN_1 SUP_FN_1',NULL,5013,'EMP-2-2-1-1','SUP-2-2-1',NULL),(28,'461-694-5014','EMP_FN_2_2_1_2','EMP_LN_2_2_1_2 EMP_FN_2_2_1_2','2011-01-04 19:12:56','EMP_LN_2_2_1_2','561-694-5014','5014 Description','5014','PAGER_NUM28','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_2 GM_FN_2','GM-2-2','SUP_LN_1 SUP_FN_1',NULL,5014,'EMP-2-2-1-2','SUP-2-2-1',NULL),(29,'461-694-4008','SUP_FN_2_2_2','SUP_LN_2_2_2 SUP_FN_2_2_2','2011-01-04 19:12:56','SUP_LN_2_2_2','561-694-4008','4008 Description','4008','PAGER_NUM29','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','VP_LN_2 VP_FN_2','VP-2','GM_LN_2 GM_FN_2',NULL,4008,'SUP-2-2-2','GM-2-2',NULL),(30,'461-694-5015','EMP_FN_2_2_2_1','EMP_LN_2_2_2_1 EMP_FN_2_2_2_1','2011-01-04 19:12:56','EMP_LN_2_2_2_1','561-694-5015','5015 Description','5015','PAGER_NUM30','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_2 GM_FN_2','GM-2-2','SUP_LN_2 SUP_FN_2',NULL,5015,'EMP-2-2-2-1','SUP-2-2-2',NULL),(31,'461-694-5016','EMP_FN_2_2_2_2','EMP_LN_2_2_2_2 EMP_FN_2_2_2_2','2011-01-04 19:12:56','EMP_LN_2_2_2_2','561-694-5016','5016 Description','5016','PAGER_NUM31','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM1','PERS_SUB_AREA_NUM1 Description','PERS_SUB_AREA_NUM1','Position1','GM_LN_2 GM_FN_2','GM-2-2','SUP_LN_2 SUP_FN_2',NULL,5016,'EMP-2-2-2-2','SUP-2-2-2',NULL),(32,'461-694-6001','BUR_FN_1','BUR_LN_1 BUR_FN_1','2011-01-04 19:12:56','BUR_LN_1','561-694-6001','6001 Description','6001','PAGER_NUM32','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM','PERS_SUB_AREA_NUM Description','PERS_SUB_AREA_NUM','Position',NULL,NULL,NULL,NULL,6001,'BUR-1',NULL,NULL),(33,'461-694-6001','BUR_FN_2','BUR_LN_2 BUR_FN_2','2011-01-04 19:12:56','BUR_LN_2','561-694-6001','6001 Description','6001','PAGER_NUM33','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM','PERS_SUB_AREA_NUM Description','PERS_SUB_AREA_NUM','Position',NULL,NULL,NULL,NULL,6001,'BUR-2',NULL,NULL),(34,'461-694-6001','BUR_FN_3','BUR_LN_3 BUR_FN_3','2011-01-04 19:12:56','BUR_LN_3','561-694-6001','6001 Description','6001','PAGER_NUM34','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM','PERS_SUB_AREA_NUM Description','PERS_SUB_AREA_NUM','Position',NULL,NULL,NULL,NULL,6001,'BUR-3',NULL,NULL),(35,'461-694-6001','BUR_FN_4','BUR_LN_4 BUR_FN_4','2011-01-04 19:12:56','BUR_LN_4','561-694-6001','6001 Description','6001','PAGER_NUM35','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM','PERS_SUB_AREA_NUM Description','PERS_SUB_AREA_NUM','Position',NULL,NULL,NULL,NULL,6001,'BUR-4',NULL,NULL),(36,'461-694-6001','BUR_FN_5','BUR_LN_5 BUR_FN_5','2011-01-04 19:12:56','BUR_LN_5','561-694-6001','6001 Description','6001','PAGER_NUM36','Inactive','PERS_AREA_NUM1 Description','PERS_AREA_NUM','PERS_SUB_AREA_NUM Description','PERS_SUB_AREA_NUM','Position',NULL,NULL,NULL,NULL,6001,'BUR-5',NULL,NULL);
/*!40000 ALTER TABLE `hr_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-07-27 18:01:56
