-- MySQL dump 10.13  Distrib 5.1.55, for Win32 (ia32)
--
-- Host: localhost    Database: bhtec
-- ------------------------------------------------------
-- Server version	5.1.55-community

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
-- Table structure for table `common_folders`
--

DROP TABLE IF EXISTS `common_folders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `common_folders` (
  `FOLDER_ID` int(11) NOT NULL,
  `FOLDER_NAME` varchar(50) DEFAULT NULL,
  `UP_FOLDER` int(11) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `FOLDER_TYPE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`FOLDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_folders`
--

LOCK TABLES `common_folders` WRITE;
/*!40000 ALTER TABLE `common_folders` DISABLE KEYS */;
/*!40000 ALTER TABLE `common_folders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal`
--

DROP TABLE IF EXISTS `portal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal` (
  `PORTAL_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PORTAL_USERID` bigint(20) DEFAULT NULL,
  `PORTLET1` varchar(20) DEFAULT NULL,
  `PORTLET2` varchar(20) DEFAULT NULL,
  `PORTLET3` varchar(20) DEFAULT NULL,
  `PORTLET4` varchar(20) DEFAULT NULL,
  `PORTLET5` varchar(20) DEFAULT NULL,
  `PORTLET6` varchar(20) DEFAULT NULL,
  `PORTLET7` varchar(20) DEFAULT NULL,
  `PORTLET8` varchar(20) DEFAULT NULL,
  `PORTLET9` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PORTAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal`
--

LOCK TABLES `portal` WRITE;
/*!40000 ALTER TABLE `portal` DISABLE KEYS */;
/*!40000 ALTER TABLE `portal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_accessory`
--

DROP TABLE IF EXISTS `syspl_accessory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_accessory` (
  `ACCESSORY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `INFO_ID` bigint(20) DEFAULT NULL,
  `ACCESSORY_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ACCESSORY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_accessory`
--

LOCK TABLES `syspl_accessory` WRITE;
/*!40000 ALTER TABLE `syspl_accessory` DISABLE KEYS */;
INSERT INTO `syspl_accessory` VALUES (1,1,'bRF2DU_1_jacbo.jpg');
/*!40000 ALTER TABLE `syspl_accessory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_affiche`
--

DROP TABLE IF EXISTS `syspl_affiche`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_affiche` (
  `AFFICHE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AFFICHE_TITLE` varchar(100) DEFAULT NULL,
  `AFFICHE_INVALIDATE` date DEFAULT NULL,
  `AFFICHE_PULISH` smallint(6) DEFAULT NULL,
  `AFFICHE_CONTENT` text,
  PRIMARY KEY (`AFFICHE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk COMMENT='系统公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_affiche`
--

LOCK TABLES `syspl_affiche` WRITE;
/*!40000 ALTER TABLE `syspl_affiche` DISABLE KEYS */;
INSERT INTO `syspl_affiche` VALUES (1,'jacob_liang2发布啊','2017-03-06',0,'好啊');
/*!40000 ALTER TABLE `syspl_affiche` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_code`
--

DROP TABLE IF EXISTS `syspl_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_code` (
  `CODE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_ENG_NAME` varchar(20) DEFAULT NULL,
  `CODE_NAME` varchar(20) DEFAULT NULL,
  `MODULE_NAME` varchar(20) DEFAULT NULL,
  `DELIMITE` varchar(2) DEFAULT NULL,
  `PART_NUM` int(11) DEFAULT NULL,
  `PART1` varchar(20) DEFAULT NULL,
  `PART1_CON` varchar(20) DEFAULT NULL,
  `PART2` varchar(20) DEFAULT NULL,
  `PART2_CON` varchar(20) DEFAULT NULL,
  `PART3` varchar(20) DEFAULT NULL,
  `PART3_CON` varchar(20) DEFAULT NULL,
  `PART4` varchar(20) DEFAULT NULL,
  `PART4_CON` varchar(20) DEFAULT NULL,
  `CODE_EFFECT` varchar(50) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CODE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_code`
--

LOCK TABLES `syspl_code` WRITE;
/*!40000 ALTER TABLE `syspl_code` DISABLE KEYS */;
INSERT INTO `syspl_code` VALUES (1,'RKD','入库单','工作流','_',4,'char','RKD','date','yyyyMMdd','number','001','sysPara','userName','RKD_20120229_001_zhangsan','','enable','admin','2012-02-29 03:41:25');
/*!40000 ALTER TABLE `syspl_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_db_opt_log`
--

DROP TABLE IF EXISTS `syspl_db_opt_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_db_opt_log` (
  `OPT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPT_MOD_NAME` varchar(20) DEFAULT NULL,
  `OPT_NAME` varchar(10) DEFAULT NULL,
  `OPT_TIME` date DEFAULT NULL,
  `OPT_PC_NAME` varchar(25) DEFAULT NULL,
  `OPT_PC_IP` varchar(25) DEFAULT NULL,
  `OPT_SQL` varchar(100) DEFAULT NULL,
  `OPT_USER_NAME` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`OPT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_db_opt_log`
--

LOCK TABLES `syspl_db_opt_log` WRITE;
/*!40000 ALTER TABLE `syspl_db_opt_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `syspl_db_opt_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_dic_big_type`
--

DROP TABLE IF EXISTS `syspl_dic_big_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_dic_big_type` (
  `BIG_TYPE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BIG_TYPE_NAME` varchar(20) DEFAULT NULL,
  `BIG_TYPE_CODE` varchar(15) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`BIG_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_dic_big_type`
--

LOCK TABLES `syspl_dic_big_type` WRITE;
/*!40000 ALTER TABLE `syspl_dic_big_type` DISABLE KEYS */;
INSERT INTO `syspl_dic_big_type` VALUES (21,'系统类别','systemType','','enable','admin','2011-12-20 06:32:22'),(22,'地区级别','district','','enable','admin','2011-12-20 07:11:29'),(23,'机构类型','organType','','enable','admin','2011-12-21 01:33:12'),(25,'页面类型','modPageType','','enable','admin','2012-01-13 04:09:36'),(26,'test','test','','enable','admin','2012-03-01 03:33:35'),(27,'aa','aa','','enable','admin','2012-03-01 03:40:01');
/*!40000 ALTER TABLE `syspl_dic_big_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_dic_small_type`
--

DROP TABLE IF EXISTS `syspl_dic_small_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_dic_small_type` (
  `SMALL_TYPE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BIG_TYPE_ID` bigint(20) DEFAULT NULL,
  `SMALL_TYPE_NAME` varchar(20) DEFAULT NULL,
  `SMALL_TYPE_CODE` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`SMALL_TYPE_ID`),
  KEY `BIG_TYPE_ID_INDEX` (`BIG_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_dic_small_type`
--

LOCK TABLES `syspl_dic_small_type` WRITE;
/*!40000 ALTER TABLE `syspl_dic_small_type` DISABLE KEYS */;
INSERT INTO `syspl_dic_small_type` VALUES (26,22,'省','province'),(27,22,'县','town'),(28,22,'区','district'),(29,22,'市','city'),(37,21,'系统平台','platform'),(38,21,'统一用户','uum'),(43,25,'js','js'),(44,25,'html','html'),(45,25,'flex','flex'),(46,25,'jsp','jsp'),(47,26,'t','t'),(48,27,'b','b'),(49,27,'a','a'),(54,23,'门店','store'),(55,23,'部门','department'),(56,23,'分公司','branch');
/*!40000 ALTER TABLE `syspl_dic_small_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_district`
--

DROP TABLE IF EXISTS `syspl_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_district` (
  `DISTRICT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DISTRICT_NAME` varchar(20) DEFAULT NULL,
  `DISTRICT_CODE` varchar(20) DEFAULT NULL,
  `DISTRICT_POSTAL` varchar(6) DEFAULT NULL,
  `DISTRICT_TELCODE` varchar(10) DEFAULT NULL,
  `DISTRICT_LEVEL` varchar(20) DEFAULT NULL,
  `DIS_PARENT_ID` bigint(20) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`DISTRICT_ID`),
  KEY `DISTRICT_NAME_INDEX` (`DISTRICT_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_district`
--

LOCK TABLES `syspl_district` WRITE;
/*!40000 ALTER TABLE `syspl_district` DISABLE KEYS */;
INSERT INTO `syspl_district` VALUES (0,'地区树','0',NULL,NULL,NULL,0,NULL,'enable',NULL,'2012-01-19 08:24:52'),(1,'北京','','','','province',0,'','enable','admin','2012-03-05 01:53:48'),(2,'上海','','','','city',0,'','enable','admin','2012-03-05 01:54:09'),(3,'深圳','','','','city',0,'','enable','admin','2012-03-05 01:54:41'),(4,'天津','','','','city',0,'','enable','admin','2012-03-05 01:55:05'),(5,'海淀区','','','','province',1,'','enable','admin','2012-03-06 02:47:07');
/*!40000 ALTER TABLE `syspl_district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_mod_opt_ref`
--

DROP TABLE IF EXISTS `syspl_mod_opt_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_mod_opt_ref` (
  `MOD_OPT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULE_ID` bigint(20) DEFAULT NULL,
  `OPERATE_ID` bigint(20) DEFAULT NULL,
  `MOD_OPT_LINK` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`MOD_OPT_ID`),
  KEY `MODULE_ID_INDEX` (`MODULE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_mod_opt_ref`
--

LOCK TABLES `syspl_mod_opt_ref` WRITE;
/*!40000 ALTER TABLE `syspl_mod_opt_ref` DISABLE KEYS */;
INSERT INTO `syspl_mod_opt_ref` VALUES (1,4,1,NULL),(2,4,2,NULL),(3,4,3,NULL),(4,4,4,NULL),(5,7,1,NULL),(6,7,2,NULL),(7,7,4,NULL),(10,8,1,NULL),(11,8,2,NULL),(12,8,4,NULL),(13,8,5,NULL),(14,8,6,NULL),(15,10,1,NULL),(16,10,2,NULL),(17,10,4,NULL),(18,10,5,NULL),(19,10,6,NULL),(25,13,1,NULL),(26,13,2,NULL),(27,13,4,NULL),(28,13,5,NULL),(29,13,6,NULL),(30,14,1,NULL),(31,14,2,NULL),(33,14,4,NULL),(34,14,5,NULL),(35,14,6,NULL),(36,15,1,NULL),(37,15,2,NULL),(38,15,4,NULL),(39,15,5,NULL),(40,15,6,NULL),(44,18,4,NULL),(45,32,1,NULL),(46,32,2,NULL),(47,32,4,NULL),(48,32,5,NULL),(49,32,6,NULL),(50,33,1,NULL),(51,33,2,NULL),(52,33,4,NULL),(53,33,5,NULL),(54,33,6,NULL),(55,33,8,NULL),(56,35,9,NULL),(57,35,10,NULL),(58,34,1,NULL),(59,34,2,NULL),(60,34,4,NULL),(61,34,5,NULL),(62,34,6,NULL),(64,34,8,NULL),(65,27,1,NULL),(66,27,2,NULL),(68,27,5,NULL),(69,27,6,NULL),(87,7,7,NULL),(91,34,11,NULL),(97,34,13,NULL),(98,49,1,NULL),(99,49,2,NULL),(100,49,3,NULL),(101,49,4,NULL),(102,7,14,NULL),(104,53,1,NULL),(105,53,2,NULL),(106,53,3,NULL),(107,53,4,NULL),(108,53,16,NULL),(109,53,17,NULL),(110,53,18,NULL),(111,53,19,NULL),(112,48,1,NULL),(113,48,2,NULL),(114,48,5,NULL),(115,48,6,NULL),(116,28,1,NULL),(117,28,2,NULL),(118,28,5,NULL),(119,28,6,NULL),(120,61,4,NULL);
/*!40000 ALTER TABLE `syspl_mod_opt_ref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_module_memu`
--

DROP TABLE IF EXISTS `syspl_module_memu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_module_memu` (
  `MODULE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MOD_NAME` varchar(10) DEFAULT NULL,
  `MOD_EN_ID` varchar(40) DEFAULT NULL,
  `MOD_IMG_CLS` varchar(20) DEFAULT NULL,
  `MOD_LEVEL` varchar(2) DEFAULT NULL,
  `MOD_ORDER` int(11) DEFAULT NULL,
  `MOD_PAGE_TYPE` varchar(20) DEFAULT NULL COMMENT '模块页面类型js html jsp ',
  `BELONG_TO_SYS` varchar(15) DEFAULT NULL COMMENT '所属子系统',
  `MOD_PARENT_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MODULE_ID`),
  KEY `MOD_NAME_INDEX` (`MOD_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_module_memu`
--

LOCK TABLES `syspl_module_memu` WRITE;
/*!40000 ALTER TABLE `syspl_module_memu` DISABLE KEYS */;
INSERT INTO `syspl_module_memu` VALUES (0,'模块树','moduletree','modtree','0',1,'','',0,'enable','','admin','2012-01-19 08:25:40'),(1,'系统平台','pltmgr','platform','1',1,'js','platform',0,'enable','','admin','2010-08-20 05:43:34'),(2,'统一用户','uusmgr','uus','1',2,'js','uum',0,'enable','','admin','2010-08-20 05:44:29'),(3,'主页框架管理','mainframemgr','frameMgr','2',2,'js','platform',1,'enable','','admin','2010-08-20 05:51:26'),(4,'页面功能区管理','mainFrameFunMgr','pageFun','3',1,'jsp','platform',3,'enable','','admin','2010-08-20 06:03:57'),(6,'模块操作管理','modoptmgr','moduleOp','2',3,'js','platform',1,'enable','','admin','2010-08-20 06:20:15'),(7,'模块菜单管理','moduleMgr','moduleMgr','3',1,'js','platform',6,'enable','','admin','2010-08-20 06:21:50'),(8,'操作按钮管理','operateMgr','oprateMgr','3',2,'js','platform',6,'enable','','admin','2010-08-20 06:22:45'),(9,'字典管理','dicmgr','dicMgr','2',4,'js','platform',1,'enable','','admin','2010-08-20 06:27:12'),(10,'类别字典管理','typeDicMgr','typeDicMgr','3',1,'js','platform',9,'enable','','admin','2010-08-20 06:30:13'),(12,'系统管理','systemMgr','systemMgr','2',1,'js','platform',1,'enable','','admin','2010-08-20 06:32:28'),(13,'系统编码管理','codeMgr','codMgr','3',2,'js','platform',12,'enable','','admin','2010-08-20 06:33:31'),(14,'省市地区管理','priCityMgr','priCityMgr','3',1,'js','platform',52,'enable','','admin','2010-08-20 06:34:52'),(15,'系统参数管理','systemParaMgr','systemParaMgr','3',3,'js','platform',12,'enable','','admin','2010-08-20 06:35:39'),(17,'日志管理','logMgr','logMgr','2',5,'js','platform',1,'enable','','admin','2010-08-20 06:38:46'),(18,'系统日志管理','sysLogMgr','sysLogMgr','3',1,'js','platform',17,'enable','','admin','2010-08-20 06:39:33'),(19,'机构管理','organmgr','organmgrtitle','2',1,'js','uum',2,'enable','','admin','2010-08-21 05:33:29'),(21,'角色管理','rolemgr','rolemgrtitle','2',2,'js','uum',2,'enable','','admin','2010-09-13 13:04:30'),(22,'用户管理','usermgr','usermgrtitle','2',3,'js','uum',2,'enable','','admin','2010-09-13 13:05:20'),(23,'群组管理','groupmgr','groupmgr','2',4,'js','uum',2,'enable','','admin','2010-09-13 13:07:28'),(25,'系统配置管理','sysConMgr','sysConMgr','3',1,'js','platform',12,'enable','','admin','2010-09-13 13:09:13'),(27,'用户组管理','userGroupMgr','usergroupmgr','3',1,'js','uum',23,'enable','','admin','2010-09-13 13:10:41'),(28,'普通组管理','generalGroupMgr','gernalgroupmgr','3',3,'js','uum',23,'disable','','admin','2010-09-13 13:11:40'),(32,'机构信息管理','organMgr','organmgr','3',1,'js','uum',19,'enable','','admin','2010-09-13 13:14:44'),(33,'角色信息管理','roleMgr','rolemgr','3',1,'js','uum',21,'enable','','admin','2010-09-13 13:15:10'),(34,'用户信息管理','userMgr','usermgr','3',1,'js','uum',22,'enable','','admin','2010-09-13 13:16:03'),(35,'角色分配管理','roleOrganMgr','roleassignmgr','3',1,'js','uum',21,'enable','','admin','2010-09-14 02:29:14'),(48,'角色组管理','roleGroupMgr','rolegroupmgr','3',2,'js','uum',23,'enable','','3','2010-09-22 05:08:11'),(49,'系统公告管理','afficheMgr','afficheMgr','3',4,'js','platform',12,'enable','','admin','2010-12-01 02:47:27'),(50,'工作流','workflowId','workflow','1',3,'','',0,'enable','','admin','2010-12-09 07:08:40'),(52,'省市地区','priCityMgr','priCityMgrTitle','2',3,'js','platform',1,'enable','','admin','2011-01-17 02:16:14'),(53,'系统调度管理','schedulerMgr','schedulerMgr','3',5,'js','platform',12,'enable','','admin','2011-01-17 02:34:43'),(60,'测试','test','aaa','2',1,'js','platform',50,'enable','','admin','2011-12-20 05:56:54'),(61,'待办任务','underwayTaskId','','3',1,'js','platform',60,'enable','','admin','2011-12-21 03:02:50');
/*!40000 ALTER TABLE `syspl_module_memu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_operate`
--

DROP TABLE IF EXISTS `syspl_operate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_operate` (
  `OPERATE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPERATE_NAME` varchar(10) DEFAULT NULL,
  `OPT_FUN_LINK` varchar(20) DEFAULT NULL,
  `OPT_IMG_LINK` varchar(20) DEFAULT NULL,
  `OPT_ORDER` int(11) DEFAULT NULL,
  `OPT_GROUP` int(11) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`OPERATE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_operate`
--

LOCK TABLES `syspl_operate` WRITE;
/*!40000 ALTER TABLE `syspl_operate` DISABLE KEYS */;
INSERT INTO `syspl_operate` VALUES (1,'增加','add','table_add',1,1,'增加','enable','admin','2010-10-28 07:16:25'),(2,'修改','modify','table_edit',2,1,'','enable','admin','2010-10-28 07:27:36'),(3,'删除','delete','table_delete',3,1,'','enable','admin','2010-10-28 07:30:29'),(4,'查看','view','table',4,1,'','enable','admin','2010-10-28 11:07:11'),(5,'启用','enable','enable',5,1,'','enable','admin','2010-10-29 01:01:42'),(6,'停用','disable','disable',6,1,'停用','enable','admin','2010-10-29 01:02:11'),(7,'模块分配操作','optassign','assign',7,1,'','enable','admin','2010-11-01 12:07:40'),(8,'模块操作权限','optpri','assign',8,1,'','enable','admin','2010-11-02 03:42:42'),(9,'角色分配','roleassign','assign',6,1,'','enable','admin','2010-11-02 03:44:10'),(10,'角色删除','roledel','table_delete',7,1,'','enable','admin','2010-11-02 03:44:39'),(11,'分配角色','assignrole','rolemgr',6,1,'','enable','admin','2010-11-02 03:46:31'),(12,'行权限','rowprivilege','privialmgr',8,1,'','enable','admin','2010-11-07 06:01:16'),(13,'重置密码','resetpwd','resetpwd',7,1,'重置密码','enable','admin','2010-11-07 10:14:23'),(14,'模块标签修改','moduleLabel','module_label',7,1,'','enable','admin','2010-12-26 02:23:29'),(15,'保留时间','saveTime','save_time',5,1,'','enable','admin','2010-12-30 08:05:31'),(16,'启动调度器','schedulerStart','scheduler_start',5,1,'','enable','admin','2011-01-17 06:20:06'),(17,'停止调度器','schedulerStop','scheduler_stop',6,1,'','enable','admin','2011-01-17 06:20:54'),(18,'启动任务','jobStart','job_start',7,1,'','enable','admin','2011-01-17 07:01:19'),(19,'停止任务','jobStop','job_stop',8,1,'','enable','admin','2011-01-17 07:05:07');
/*!40000 ALTER TABLE `syspl_operate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_primarykey_seq`
--

DROP TABLE IF EXISTS `syspl_primarykey_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_primarykey_seq` (
  `POJO_NAME` varchar(20) NOT NULL,
  `PRIMARYKEY_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`POJO_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_primarykey_seq`
--

LOCK TABLES `syspl_primarykey_seq` WRITE;
/*!40000 ALTER TABLE `syspl_primarykey_seq` DISABLE KEYS */;
/*!40000 ALTER TABLE `syspl_primarykey_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_scheduler_job`
--

DROP TABLE IF EXISTS `syspl_scheduler_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_scheduler_job` (
  `JOB_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `JOB_NAME` varchar(30) DEFAULT NULL,
  `JOB_CLASS_DESCRIPT` varchar(100) DEFAULT NULL,
  `TRIGGER_TYPE` varchar(20) DEFAULT NULL,
  `TIME_EXPRESS` varchar(100) DEFAULT NULL,
  `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `REPEAT_TIME` int(11) DEFAULT NULL,
  `SPLIT_TIME` bigint(20) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_scheduler_job`
--

LOCK TABLES `syspl_scheduler_job` WRITE;
/*!40000 ALTER TABLE `syspl_scheduler_job` DISABLE KEYS */;
INSERT INTO `syspl_scheduler_job` VALUES (1,'logJob','com.bhtec.service.impl.platform.job.LoggerJob','Cron','* * 0 15 * ?','2011-01-19 07:28:44','2011-02-19 07:28:44',2,2,'disable','每月15日0点清除日志','admin','2011-01-19 07:28:44'),(3,'postgresqlBbBackup','com.bhtec.service.impl.platform.job.PostgresqlDbBackupJob','Cron','0/10 * * * * ?','2011-01-19 07:28:44','2011-01-19 07:28:44',2,2,'enable','每天0点备份','admin','2011-01-21 03:34:11');
/*!40000 ALTER TABLE `syspl_scheduler_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_sys_opt_log`
--

DROP TABLE IF EXISTS `syspl_sys_opt_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_sys_opt_log` (
  `OPT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPT_MOD_NAME` varchar(20) DEFAULT NULL,
  `OPT_NAME` varchar(10) DEFAULT NULL,
  `OPT_CONTENT` varchar(1000) DEFAULT NULL,
  `OPT_BUSINESS_ID` varchar(20) DEFAULT NULL,
  `OPT_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OPT_PC_NAME` varchar(25) DEFAULT NULL,
  `OPT_PC_IP` varchar(25) DEFAULT NULL,
  `OPT_USER_NAME` varchar(20) DEFAULT NULL,
  `OPT_USER_ROLE` varchar(20) DEFAULT NULL,
  `OPT_USER_OGAN` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`OPT_ID`),
  KEY `OPT_TIME_INDEX` (`OPT_TIME`),
  KEY `OPT_MOD_NAME_INDEX` (`OPT_MOD_NAME`),
  KEY `OPT_USER_NAME_INDEX` (`OPT_USER_NAME`),
  KEY `OPT_USER_ROLE_INDEX` (`OPT_USER_ROLE`),
  KEY `OPT_USER_OGAN_INDEX` (`OPT_USER_OGAN`)
) ENGINE=InnoDB AUTO_INCREMENT=561 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_sys_opt_log`
--

LOCK TABLES `syspl_sys_opt_log` WRITE;
/*!40000 ALTER TABLE `syspl_sys_opt_log` DISABLE KEYS */;
INSERT INTO `syspl_sys_opt_log` VALUES (1,'用户登录','用户登录','总部-管理员-管理员','','2012-01-19 08:30:33','localhost','127.0.0.1','管理员','管理员','总部'),(2,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 02:29:09','localhost','127.0.0.1','管理员','管理员','总部'),(3,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 02:30:49','localhost','127.0.0.1','管理员','管理员','总部'),(4,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 02:39:58','localhost','127.0.0.1','管理员','管理员','总部'),(5,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 02:40:13','localhost','127.0.0.1','管理员','管理员','总部'),(6,'模块分配操作','保存','模块分配操作;','7','2012-01-31 02:41:52','localhost','127.0.0.1','管理员','管理员','总部'),(7,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 02:42:08','localhost','127.0.0.1','管理员','管理员','总部'),(8,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 03:21:23','localhost','127.0.0.1','管理员','管理员','总部'),(9,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 03:34:44','localhost','127.0.0.1','管理员','管理员','总部'),(10,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 03:41:20','localhost','127.0.0.1','管理员','管理员','总部'),(11,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 03:52:19','localhost','127.0.0.1','管理员','管理员','总部'),(12,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 06:54:04','localhost','127.0.0.1','管理员','管理员','总部'),(13,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 06:55:55','localhost','127.0.0.1','管理员','管理员','总部'),(14,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 06:57:33','localhost','127.0.0.1','管理员','管理员','总部'),(15,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 06:58:57','localhost','127.0.0.1','管理员','管理员','总部'),(16,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 08:21:20','localhost','127.0.0.1','管理员','管理员','总部'),(17,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 08:22:24','localhost','127.0.0.1','管理员','管理员','总部'),(18,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 09:21:11','localhost','127.0.0.1','管理员','管理员','总部'),(19,'用户登录','用户登录','总部-管理员-管理员','','2012-01-31 09:28:27','localhost','127.0.0.1','管理员','管理员','总部'),(20,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 03:06:31','localhost','127.0.0.1','管理员','管理员','总部'),(21,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 03:11:29','localhost','127.0.0.1','管理员','管理员','总部'),(22,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:06:27','localhost','127.0.0.1','管理员','管理员','总部'),(23,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:09:33','localhost','127.0.0.1','管理员','管理员','总部'),(24,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:11:59','localhost','127.0.0.1','管理员','管理员','总部'),(25,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:32:41','localhost','127.0.0.1','管理员','管理员','总部'),(26,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:35:11','localhost','127.0.0.1','管理员','管理员','总部'),(27,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:44:36','localhost','127.0.0.1','管理员','管理员','总部'),(28,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:46:22','localhost','127.0.0.1','管理员','管理员','总部'),(29,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 08:46:55','localhost','127.0.0.1','管理员','管理员','总部'),(30,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 09:24:17','localhost','127.0.0.1','管理员','管理员','总部'),(31,'用户登录','用户登录','总部-管理员-管理员','','2012-02-01 09:29:31','localhost','127.0.0.1','管理员','管理员','总部'),(32,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 07:36:34','localhost','127.0.0.1','管理员','管理员','总部'),(33,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 07:46:41','localhost','127.0.0.1','管理员','管理员','总部'),(34,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 07:47:44','localhost','127.0.0.1','管理员','管理员','总部'),(35,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 07:49:21','localhost','127.0.0.1','管理员','管理员','总部'),(36,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 07:59:35','localhost','127.0.0.1','管理员','管理员','总部'),(37,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:02:35','localhost','127.0.0.1','管理员','管理员','总部'),(38,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:03:04','localhost','127.0.0.1','管理员','管理员','总部'),(39,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:03:56','localhost','127.0.0.1','管理员','管理员','总部'),(40,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:27:22','localhost','127.0.0.1','管理员','管理员','总部'),(41,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:27:35','localhost','127.0.0.1','管理员','管理员','总部'),(42,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:28:21','localhost','127.0.0.1','管理员','管理员','总部'),(43,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:30:02','localhost','127.0.0.1','管理员','管理员','总部'),(44,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:31:45','localhost','127.0.0.1','管理员','管理员','总部'),(45,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:34:18','localhost','127.0.0.1','管理员','管理员','总部'),(46,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:34:39','localhost','127.0.0.1','管理员','管理员','总部'),(47,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:37:18','localhost','127.0.0.1','管理员','管理员','总部'),(48,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:37:25','localhost','127.0.0.1','管理员','管理员','总部'),(49,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:50:17','localhost','127.0.0.1','管理员','管理员','总部'),(50,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:52:00','localhost','127.0.0.1','管理员','管理员','总部'),(51,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:52:41','localhost','127.0.0.1','管理员','管理员','总部'),(52,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:53:44','localhost','127.0.0.1','管理员','管理员','总部'),(53,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:54:32','localhost','127.0.0.1','管理员','管理员','总部'),(54,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:54:57','localhost','127.0.0.1','管理员','管理员','总部'),(55,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:56:42','localhost','127.0.0.1','管理员','管理员','总部'),(56,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:57:23','localhost','127.0.0.1','管理员','管理员','总部'),(57,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 08:58:40','localhost','127.0.0.1','管理员','管理员','总部'),(58,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:05:03','localhost','127.0.0.1','管理员','管理员','总部'),(59,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:05:48','localhost','127.0.0.1','管理员','管理员','总部'),(60,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:07:26','localhost','127.0.0.1','管理员','管理员','总部'),(61,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:07:37','localhost','127.0.0.1','管理员','管理员','总部'),(62,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:08:16','localhost','127.0.0.1','管理员','管理员','总部'),(63,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:09:06','localhost','127.0.0.1','管理员','管理员','总部'),(64,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:09:40','localhost','127.0.0.1','管理员','管理员','总部'),(65,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:21:47','localhost','127.0.0.1','管理员','管理员','总部'),(66,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:22:28','localhost','127.0.0.1','管理员','管理员','总部'),(67,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:24:56','localhost','127.0.0.1','管理员','管理员','总部'),(68,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:31:52','localhost','127.0.0.1','管理员','管理员','总部'),(69,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:33:33','localhost','127.0.0.1','管理员','管理员','总部'),(70,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:35:36','localhost','127.0.0.1','管理员','管理员','总部'),(71,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:41:01','localhost','127.0.0.1','管理员','管理员','总部'),(72,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:41:48','localhost','127.0.0.1','管理员','管理员','总部'),(73,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:44:29','localhost','127.0.0.1','管理员','管理员','总部'),(74,'用户登录','用户登录','总部-管理员-管理员','','2012-02-02 09:47:14','localhost','127.0.0.1','管理员','管理员','总部'),(75,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 00:54:35','localhost','127.0.0.1','管理员','管理员','总部'),(76,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 00:58:41','localhost','127.0.0.1','管理员','管理员','总部'),(77,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 01:04:24','localhost','127.0.0.1','管理员','管理员','总部'),(78,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 01:17:07','localhost','127.0.0.1','管理员','管理员','总部'),(79,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 01:20:43','localhost','127.0.0.1','管理员','管理员','总部'),(80,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 08:26:36','localhost','127.0.0.1','管理员','管理员','总部'),(81,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 08:26:49','localhost','127.0.0.1','管理员','管理员','总部'),(82,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 13:50:39','localhost','127.0.0.1','管理员','管理员','总部'),(83,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 13:50:48','localhost','127.0.0.1','管理员','管理员','总部'),(84,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 13:51:15','localhost','127.0.0.1','管理员','管理员','总部'),(85,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 13:52:23','localhost','127.0.0.1','管理员','管理员','总部'),(86,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 13:54:34','localhost','127.0.0.1','管理员','管理员','总部'),(87,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 13:54:46','localhost','127.0.0.1','管理员','管理员','总部'),(88,'用户登录','用户登录','总部-管理员-管理员','','2012-02-03 13:55:36','localhost','127.0.0.1','管理员','管理员','总部'),(89,'用户登录','用户登录','总部-管理员-管理员','','2012-02-06 02:25:19','localhost','127.0.0.1','管理员','管理员','总部'),(90,'用户登录','用户登录','总部-管理员-管理员','','2012-02-06 02:26:10','localhost','127.0.0.1','管理员','管理员','总部'),(91,'用户登录','用户登录','总部-管理员-管理员','','2012-02-06 02:29:55','localhost','127.0.0.1','管理员','管理员','总部'),(92,'用户登录','用户登录','总部-管理员-管理员','','2012-02-06 02:32:03','localhost','127.0.0.1','管理员','管理员','总部'),(93,'用户登录','用户登录','总部-管理员-管理员','','2012-02-06 02:32:08','localhost','127.0.0.1','管理员','管理员','总部'),(94,'用户登录','用户登录','总部-管理员-管理员','','2012-02-06 02:32:26','localhost','127.0.0.1','管理员','管理员','总部'),(95,'用户登录','用户登录','总部-管理员-管理员','','2012-02-06 02:34:41','localhost','127.0.0.1','管理员','管理员','总部'),(96,'用户登录','用户登录','总部-管理员-管理员','','2012-02-28 08:46:45','localhost','127.0.0.1','管理员','管理员','总部'),(97,'用户登录','用户登录','总部-管理员-管理员','','2012-02-28 08:48:11','localhost','127.0.0.1','管理员','管理员','总部'),(98,'系统配置管理','保存','设置系统名称:jacob_liang平台管理统一用户;','','2012-02-28 08:48:37','localhost','127.0.0.1','管理员','管理员','总部'),(99,'系统配置管理','保存','设置系统名称:jacob_liang平台管理统一用户;','','2012-02-28 08:54:25','localhost','127.0.0.1','管理员','管理员','总部'),(100,'系统配置管理','保存','设置日志级别:1;','','2012-02-28 08:54:30','localhost','127.0.0.1','管理员','管理员','总部'),(101,'系统配置管理','保存','设置日志保留天数:30;','','2012-02-28 08:54:32','localhost','127.0.0.1','管理员','管理员','总部'),(102,'系统配置管理','保存','设置权限级别:org;','','2012-02-28 08:54:35','localhost','127.0.0.1','管理员','管理员','总部'),(103,'用户登录','用户登录','总部-管理员-管理员','','2012-02-28 09:24:37','localhost','127.0.0.1','管理员','管理员','总部'),(104,'用户登录','用户登录','总部-管理员-管理员','','2012-02-28 09:53:19','localhost','127.0.0.1','管理员','管理员','总部'),(105,'用户登录','用户登录','总部-管理员-管理员','','2012-02-28 09:54:13','localhost','127.0.0.1','管理员','管理员','总部'),(106,'用户登录','用户登录','总部-管理员-管理员','','2012-02-28 09:57:23','localhost','127.0.0.1','管理员','管理员','总部'),(107,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:39:47','localhost','127.0.0.1','管理员','管理员','总部'),(108,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:41:11','localhost','127.0.0.1','管理员','管理员','总部'),(109,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:42:03','localhost','127.0.0.1','管理员','管理员','总部'),(110,'系统模块操作管理','修改','模块标签修改;module_label;moduleLabel;','14','2012-02-29 02:44:00','localhost','127.0.0.1','管理员','管理员','总部'),(111,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:44:29','localhost','127.0.0.1','管理员','管理员','总部'),(112,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:44:54','localhost','127.0.0.1','管理员','管理员','总部'),(113,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:46:19','localhost','127.0.0.1','管理员','管理员','总部'),(114,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:47:51','localhost','127.0.0.1','管理员','管理员','总部'),(115,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 02:51:36','localhost','127.0.0.1','管理员','管理员','总部'),(116,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 03:04:31','localhost','127.0.0.1','管理员','管理员','总部'),(117,'系统配置管理','保存','设置系统名称:!@#$%^&*()_+\"L}{{}<M;','','2012-02-29 03:05:15','localhost','127.0.0.1','管理员','管理员','总部'),(118,'系统配置管理','保存','设置系统名称:jacob_liang平台管理统一用户;','','2012-02-29 03:05:43','localhost','127.0.0.1','管理员','管理员','总部'),(119,'系统配置管理','保存','设置日志级别:1;','','2012-02-29 03:08:18','localhost','127.0.0.1','管理员','管理员','总部'),(120,'系统配置管理','保存','设置日志级别:1;','','2012-02-29 03:26:13','localhost','127.0.0.1','管理员','管理员','总部'),(121,'系统配置管理','保存','设置日志保留天数:60;','','2012-02-29 03:26:26','localhost','127.0.0.1','管理员','管理员','总部'),(122,'系统配置管理','保存','设置权限级别:rol;','','2012-02-29 03:26:50','localhost','127.0.0.1','管理员','管理员','总部'),(123,'系统配置管理','保存','设置权限级别:org;','','2012-02-29 03:27:00','localhost','127.0.0.1','管理员','管理员','总部'),(124,'系统配置管理','保存','删除管理员:admin;','','2012-02-29 03:29:31','localhost','127.0.0.1','管理员','管理员','总部'),(125,'系统配置管理','保存','增加管理员:admin;','','2012-02-29 03:29:52','localhost','127.0.0.1','管理员','管理员','总部'),(126,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 03:39:45','localhost','127.0.0.1','管理员','管理员','总部'),(127,'系统编码管理','保存','编码英文名称：RKD;编码中文名称:入库单;编码所属模块名称:入库单;编码效果:RKD_20120229_001_zhangsan;','1','2012-02-29 03:41:25','localhost','127.0.0.1','管理员','管理员','总部'),(128,'系统编码管理','修改','编码英文名称：RKD;编码中文名称:入库单编码所属模块名称:入库单编码效果:RKD_20120229_001_zhangsan','1','2012-02-29 03:42:28','localhost','127.0.0.1','管理员','管理员','总部'),(129,'系统编码管理','停用','编码停用：编码英文名称：RKD;编码中文名称:入库单编码所属模块名称:入库单编码效果:RKD_20120229_001_zhangsan','1','2012-02-29 03:42:28','localhost','127.0.0.1','管理员','管理员','总部'),(130,'系统编码管理','修改','编码英文名称：RKD;编码中文名称:入库单编码所属模块名称:入库单编码效果:RKD_20120229_001_zhangsan','1','2012-02-29 03:42:32','localhost','127.0.0.1','管理员','管理员','总部'),(131,'系统编码管理','启用','编码启用：编码英文名称：RKD;编码中文名称:入库单编码所属模块名称:入库单编码效果:RKD_20120229_001_zhangsan','1','2012-02-29 03:42:32','localhost','127.0.0.1','管理员','管理员','总部'),(132,'系统参数管理','修改','参数名称：用户默认密码;参数键值：userdefaultpassword;参数值：123456参数类型：uus','1','2012-02-29 03:44:37','localhost','127.0.0.1','管理员','管理员','总部'),(133,'系统参数管理','停用','参数名称：用户默认密码;参数键值：userdefaultpassword;参数值：123456参数类型：uus','1','2012-02-29 03:44:37','localhost','127.0.0.1','管理员','管理员','总部'),(134,'系统参数管理','修改','参数名称：用户默认密码;参数键值：userdefaultpassword;参数值：123456参数类型：uus','1','2012-02-29 03:44:44','localhost','127.0.0.1','管理员','管理员','总部'),(135,'系统参数管理','启用','参数名称：用户默认密码;参数键值：userdefaultpassword;参数值：123456参数类型：uus','1','2012-02-29 03:44:44','localhost','127.0.0.1','管理员','管理员','总部'),(136,'系统公告管理','保存','保存公告标题：test;有效期限:Wed Feb 29 00:00:00 CST 2012','1','2012-02-29 03:45:50','localhost','127.0.0.1','管理员','管理员','总部'),(137,'系统附件','保存','附件业务表ID：1;附件名称:7qX51q_1_jacbo.jpg','1','2012-02-29 03:45:50','localhost','127.0.0.1','管理员','管理员','总部'),(138,'系统附件','删除','删除附件ID：1','','2012-02-29 03:46:06','localhost','127.0.0.1','管理员','管理员','总部'),(139,'系统附件','保存','附件业务表ID：1;附件名称:5p834V_1_jacbo.jpg','2','2012-02-29 03:46:13','localhost','127.0.0.1','管理员','管理员','总部'),(140,'系统公告管理','修改','修改公告ID：1;修改公告标题：test;有效期：Wed Feb 29 00:00:00 CST 2012;是否发布：0;','1','2012-02-29 03:46:13','localhost','127.0.0.1','管理员','管理员','总部'),(141,'系统附件','删除','删除附件ID：2','','2012-02-29 03:46:24','localhost','127.0.0.1','管理员','管理员','总部'),(142,'系统公告管理','删除','删除的公告ID：1','','2012-02-29 03:46:24','localhost','127.0.0.1','管理员','管理员','总部'),(143,'系统参数管理','修改','参数名称：用户有效期;参数键值：uservalidate;参数值：6参数类型：uum','2','2012-02-29 03:47:24','localhost','127.0.0.1','管理员','管理员','总部'),(144,'系统参数管理','修改','参数名称：用户默认密码;参数键值：userdefaultpassword;参数值：123456参数类型：platform','1','2012-02-29 03:47:38','localhost','127.0.0.1','管理员','管理员','总部'),(145,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 03:49:43','localhost','127.0.0.1','管理员','管理员','总部'),(146,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 03:55:29','localhost','127.0.0.1','管理员','管理员','总部'),(147,'角色管理','保存','经理;','2','2012-02-29 07:47:23','localhost','127.0.0.1','管理员','管理员','总部'),(148,'角色分配','保存','机构id:0;角色id:2;','','2012-02-29 07:47:49','localhost','127.0.0.1','管理员','管理员','总部'),(149,'角色组管理','保存','组名称：test;组类型：roleGroup;','1','2012-02-29 07:49:06','localhost','127.0.0.1','管理员','管理员','总部'),(150,'角色组管理','保存','组名称：a;组类型：roleGroup;','2','2012-02-29 07:51:41','localhost','127.0.0.1','管理员','管理员','总部'),(151,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 07:54:23','localhost','127.0.0.1','管理员','管理员','总部'),(152,'角色组管理','保存','组名称：b;组类型：roleGroup;','3','2012-02-29 07:55:18','localhost','127.0.0.1','管理员','管理员','总部'),(153,'角色组管理','保存','组名称：c;组类型：roleGroup;','4','2012-02-29 07:57:45','localhost','127.0.0.1','管理员','管理员','总部'),(154,'角色组管理','保存','组名称：d;组类型：roleGroup;','5','2012-02-29 07:58:55','localhost','127.0.0.1','管理员','管理员','总部'),(155,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 08:42:43','localhost','127.0.0.1','管理员','管理员','总部'),(156,'角色组管理','保存','组名称：e;组类型：roleGroup;','6','2012-02-29 08:45:06','localhost','127.0.0.1','管理员','管理员','总部'),(157,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 09:09:35','localhost','127.0.0.1','管理员','管理员','总部'),(158,'用户登录','用户登录','总部-管理员-管理员','','2012-02-29 09:10:53','localhost','127.0.0.1','管理员','管理员','总部'),(159,'用户登录','用户登录','总部-管理员-管理员','','2012-03-01 03:00:48','localhost','127.0.0.1','管理员','管理员','总部'),(160,'角色组管理','保存','组名称：g;组类型：roleGroup;','8','2012-03-01 03:02:40','localhost','127.0.0.1','管理员','管理员','总部'),(161,'用户登录','用户登录','总部-管理员-管理员','','2012-03-01 03:09:33','localhost','127.0.0.1','管理员','管理员','总部'),(162,'角色组管理','保存','组名称：h;组类型：roleGroup;','9','2012-03-01 03:09:59','localhost','127.0.0.1','管理员','管理员','总部'),(163,'角色组管理','保存','组名称：t;组类型：roleGroup;','10','2012-03-01 03:19:48','localhost','127.0.0.1','管理员','管理员','总部'),(164,'角色组管理','保存','组名称：y;组类型：roleGroup;','11','2012-03-01 03:21:01','localhost','127.0.0.1','管理员','管理员','总部'),(165,'角色组管理','保存','组名称：u;组类型：roleGroup;','12','2012-03-01 03:21:52','localhost','127.0.0.1','管理员','管理员','总部'),(166,'角色组管理','保存','组名称：q;组类型：roleGroup;','13','2012-03-01 03:27:32','localhost','127.0.0.1','管理员','管理员','总部'),(167,'类别字典管理','保存','字典大类名称：test;','26','2012-03-01 03:33:35','localhost','127.0.0.1','管理员','管理员','总部'),(168,'用户登录','用户登录','总部-管理员-管理员','','2012-03-01 03:39:23','localhost','127.0.0.1','管理员','管理员','总部'),(169,'类别字典管理','保存','字典大类名称：aa;','27','2012-03-01 03:40:01','localhost','127.0.0.1','管理员','管理员','总部'),(170,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 02:04:14','localhost','127.0.0.1','管理员','管理员','总部'),(171,'机构管理','保存','机构名称：蓄意;机构代码：;上级机构：总部;','1','2012-03-02 02:05:28','localhost','127.0.0.1','管理员','管理员','总部'),(172,'机构管理','修改','机构名称：北京;机构代码：;上级机构：总部;','1','2012-03-02 02:05:39','localhost','127.0.0.1','管理员','管理员','总部'),(173,'角色分配','保存','机构id:1;角色id:2;','','2012-03-02 02:05:54','localhost','127.0.0.1','管理员','管理员','总部'),(174,'用户分配角色','保存','4','2','2012-03-02 02:06:33','localhost','127.0.0.1','管理员','管理员','总部'),(175,'用户管理','保存','jacob;雅各布;经理;','2','2012-03-02 02:06:33','localhost','127.0.0.1','管理员','管理员','总部'),(176,'角色分配','删除','删除机构下的角色ID：2;','0','2012-03-02 02:06:58','localhost','127.0.0.1','管理员','管理员','总部'),(177,'用户代理','保存','2;1;2;','2','2012-03-02 02:09:01','localhost','127.0.0.1','管理员','管理员','总部'),(178,'用户代理','保存','3;1;2;','3','2012-03-02 02:13:17','localhost','127.0.0.1','管理员','管理员','总部'),(179,'用户登录','用户登录','总部-管理员-管理员(雅各布D)','','2012-03-02 02:13:51','localhost','127.0.0.1','管理员(雅各布D)','管理员','总部'),(180,'用户登录','用户登录','北京-经理-雅各布','','2012-03-02 02:18:06','localhost','127.0.0.1','雅各布','经理','北京'),(181,'用户登录','用户登录','北京-经理-雅各布','','2012-03-02 02:23:46','localhost','127.0.0.1','雅各布','经理','北京'),(182,'用户登录','用户登录','北京-经理-雅各布','','2012-03-02 02:25:04','localhost','127.0.0.1','雅各布','经理','北京'),(183,'用户登录','用户登录','北京-经理-雅各布','','2012-03-02 02:25:24','localhost','127.0.0.1','雅各布','经理','北京'),(184,'用户登录','用户登录','北京-经理-雅各布','','2012-03-02 02:26:04','localhost','127.0.0.1','雅各布','经理','北京'),(185,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 02:26:54','localhost','127.0.0.1','管理员','管理员','总部'),(186,'用户管理','修改','jacob;雅各布;null;','2','2012-03-02 02:27:29','localhost','127.0.0.1','管理员','管理员','总部'),(187,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 03:02:19','localhost','127.0.0.1','管理员','管理员','总部'),(188,'用户分配角色','保存','0','2','2012-03-02 03:09:49','localhost','127.0.0.1','管理员','管理员','总部'),(189,'用户管理','修改','jacob;雅各布;经理;','2','2012-03-02 03:09:49','localhost','127.0.0.1','管理员','管理员','总部'),(190,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 03:09:49','localhost','127.0.0.1','管理员','管理员','总部'),(191,'用户管理','修改','jacob;雅各布;无角色用户;','2','2012-03-02 03:13:24','localhost','127.0.0.1','管理员','管理员','总部'),(192,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 03:13:24','localhost','127.0.0.1','管理员','管理员','总部'),(193,'用户分配角色','保存','4','2','2012-03-02 03:13:46','localhost','127.0.0.1','管理员','管理员','总部'),(196,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 03:25:53','localhost','127.0.0.1','管理员','管理员','总部'),(198,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 03:29:50','localhost','127.0.0.1','管理员','管理员','总部'),(200,'用户分配角色','保存','0','2','2012-03-02 03:32:45','localhost','127.0.0.1','管理员','管理员','总部'),(201,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 03:32:45','localhost','127.0.0.1','管理员','管理员','总部'),(202,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 03:34:20','localhost','127.0.0.1','管理员','管理员','总部'),(203,'用户分配角色','保存','0','2','2012-03-02 03:34:47','localhost','127.0.0.1','管理员','管理员','总部'),(204,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 03:34:47','localhost','127.0.0.1','管理员','管理员','总部'),(205,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 03:34:59','localhost','127.0.0.1','管理员','管理员','总部'),(206,'用户分配角色','保存','4','2','2012-03-02 03:35:22','localhost','127.0.0.1','管理员','管理员','总部'),(207,'用户分配角色','保存','0','2','2012-03-02 03:35:38','localhost','127.0.0.1','管理员','管理员','总部'),(208,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 03:35:38','localhost','127.0.0.1','管理员','管理员','总部'),(209,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 03:36:33','localhost','127.0.0.1','管理员','管理员','总部'),(210,'用户分配角色','保存','4','2','2012-03-02 03:36:52','localhost','127.0.0.1','管理员','管理员','总部'),(211,'用户分配角色','保存','0','2','2012-03-02 03:37:01','localhost','127.0.0.1','管理员','管理员','总部'),(212,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 03:37:01','localhost','127.0.0.1','管理员','管理员','总部'),(213,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 03:37:33','localhost','127.0.0.1','管理员','管理员','总部'),(214,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 03:37:56','localhost','127.0.0.1','管理员','管理员','总部'),(215,'用户分配角色','保存','4','2','2012-03-02 03:38:09','localhost','127.0.0.1','管理员','管理员','总部'),(216,'用户常用功能设置','保存','用户常用功能保存:用户ID1;模块ID：25,13,15','','2012-03-02 06:10:50','localhost','127.0.0.1','管理员','管理员','总部'),(217,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;','2;opt','2012-03-02 06:11:12','localhost','127.0.0.1','管理员','管理员','总部'),(218,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 06:12:41','localhost','127.0.0.1','管理员','管理员','总部'),(219,'用户登录','用户登录','北京-经理-雅各布','','2012-03-02 06:13:29','localhost','127.0.0.1','雅各布','经理','北京'),(220,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 06:13:51','localhost','127.0.0.1','管理员','管理员','总部'),(221,'用户分配角色','保存','0','2','2012-03-02 06:14:16','localhost','127.0.0.1','管理员','管理员','总部'),(222,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 06:14:16','localhost','127.0.0.1','管理员','管理员','总部'),(223,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 06:18:27','localhost','127.0.0.1','管理员','管理员','总部'),(224,'用户分配角色','保存','4','2','2012-03-02 06:18:35','localhost','127.0.0.1','管理员','管理员','总部'),(225,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;50;51;52;53;54;55;56;57;','2;opt','2012-03-02 06:18:51','localhost','127.0.0.1','管理员','管理员','总部'),(226,'用户分配角色','保存','0','2','2012-03-02 06:19:10','localhost','127.0.0.1','管理员','管理员','总部'),(227,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 06:19:10','localhost','127.0.0.1','管理员','管理员','总部'),(228,'用户管理','停用','jacob;雅各布;无角色用户;','2','2012-03-02 06:22:49','localhost','127.0.0.1','管理员','管理员','总部'),(229,'用户分配角色','保存','4','2','2012-03-02 06:23:00','localhost','127.0.0.1','管理员','管理员','总部'),(230,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 06:24:17','localhost','127.0.0.1','管理员','管理员','总部'),(231,'用户管理','停用','jacob;雅各布;null;','2','2012-03-02 06:33:36','localhost','127.0.0.1','管理员','管理员','总部'),(232,'用户分配角色','保存','4','2','2012-03-02 06:33:44','localhost','127.0.0.1','管理员','管理员','总部'),(233,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;','2;opt','2012-03-02 06:33:59','localhost','127.0.0.1','管理员','管理员','总部'),(234,'用户管理','停用','jacob;雅各布;经理;','2','2012-03-02 06:34:12','localhost','127.0.0.1','管理员','管理员','总部'),(235,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 06:48:19','localhost','127.0.0.1','管理员','管理员','总部'),(236,'用户管理','保存','zs;张三;经理;','3','2012-03-02 06:49:01','localhost','127.0.0.1','管理员','管理员','总部'),(237,'用户管理','停用','zs;张三;经理;','3','2012-03-02 06:50:35','localhost','127.0.0.1','管理员','管理员','总部'),(238,'用户管理','修改','admin;管理员;null;','1','2012-03-02 07:12:47','localhost','127.0.0.1','管理员','管理员','总部'),(239,'用户管理','修改','admin;管理员;null;','1','2012-03-02 07:13:26','localhost','127.0.0.1','管理员','管理员','总部'),(240,'用户管理','修改','admin;管理员;null;','1','2012-03-02 07:14:11','localhost','127.0.0.1','管理员','管理员','总部'),(241,'角色管理','修改','经理;','2','2012-03-02 07:21:09','localhost','127.0.0.1','管理员','管理员','总部'),(242,'角色管理','停用','经理;','2','2012-03-02 07:21:09','localhost','127.0.0.1','管理员','管理员','总部'),(243,'用户管理','停用','jacob;雅各布;null;','2','2012-03-02 07:21:19','localhost','127.0.0.1','管理员','管理员','总部'),(244,'角色管理','修改','经理;','2','2012-03-02 07:21:34','localhost','127.0.0.1','管理员','管理员','总部'),(245,'角色管理','启用','经理;','2','2012-03-02 07:21:34','localhost','127.0.0.1','管理员','管理员','总部'),(246,'用户管理','停用','zs;张三;无角色用户;','3','2012-03-02 07:21:53','localhost','127.0.0.1','管理员','管理员','总部'),(247,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 07:22:13','localhost','127.0.0.1','管理员','管理员','总部'),(248,'角色分配','保存','机构id:1;角色id:2;','','2012-03-02 07:22:39','localhost','127.0.0.1','管理员','管理员','总部'),(249,'用户分配角色','保存','5','3','2012-03-02 07:22:51','localhost','127.0.0.1','管理员','管理员','总部'),(250,'角色管理','保存','店长;','3','2012-03-02 07:29:59','localhost','127.0.0.1','管理员','管理员','总部'),(251,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;','3;opt','2012-03-02 07:30:10','localhost','127.0.0.1','管理员','管理员','总部'),(252,'角色管理','修改','店长;','3','2012-03-02 07:30:25','localhost','127.0.0.1','管理员','管理员','总部'),(253,'角色管理','停用','店长;','3','2012-03-02 07:30:25','localhost','127.0.0.1','管理员','管理员','总部'),(254,'角色管理','修改','店长;','3','2012-03-02 07:32:17','localhost','127.0.0.1','管理员','管理员','总部'),(255,'角色管理','启用','店长;','3','2012-03-02 07:32:17','localhost','127.0.0.1','管理员','管理员','总部'),(256,'角色管理','修改','店长;','3','2012-03-02 07:32:55','localhost','127.0.0.1','管理员','管理员','总部'),(257,'角色管理','停用','店长;','3','2012-03-02 07:33:06','localhost','127.0.0.1','管理员','管理员','总部'),(258,'角色管理','修改','店长;','3','2012-03-02 07:34:59','localhost','127.0.0.1','管理员','管理员','总部'),(259,'角色管理','启用','店长;','3','2012-03-02 07:35:00','localhost','127.0.0.1','管理员','管理员','总部'),(260,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 07:36:41','localhost','127.0.0.1','管理员','管理员','总部'),(261,'角色分配','保存','机构id:1;角色id:3;','','2012-03-02 07:37:12','localhost','127.0.0.1','管理员','管理员','总部'),(262,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 07:40:17','localhost','127.0.0.1','管理员','管理员','总部'),(263,'角色管理','停用','店长;','3','2012-03-02 07:40:36','localhost','127.0.0.1','管理员','管理员','总部'),(264,'角色管理','启用','店长;','3','2012-03-02 07:40:59','localhost','127.0.0.1','管理员','管理员','总部'),(265,'模块分配操作','保存','模块操作关系ID:1;','3;row','2012-03-02 07:58:18','localhost','127.0.0.1','管理员','管理员','总部'),(266,'模块分配操作','保存','模块操作关系ID:org;','3;row','2012-03-02 07:59:39','localhost','127.0.0.1','管理员','管理员','总部'),(267,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 08:18:56','localhost','127.0.0.1','管理员','管理员','总部'),(268,'用户管理','停用','zs;张三;经理;','3','2012-03-02 08:19:35','localhost','127.0.0.1','管理员','管理员','总部'),(269,'角色分配','删除','删除机构下的角色ID：2;','1','2012-03-02 08:19:42','localhost','127.0.0.1','管理员','管理员','总部'),(270,'机构管理','停用','机构名称：北京;机构代码：;上级机构：总部;','1','2012-03-02 08:19:53','localhost','127.0.0.1','管理员','管理员','总部'),(271,'机构管理','启用','机构名称：北京;机构代码：;上级机构：总部;','1','2012-03-02 08:21:40','localhost','127.0.0.1','管理员','管理员','总部'),(272,'用户管理','停用','zs;张三;无角色用户;','3','2012-03-02 08:45:34','localhost','127.0.0.1','管理员','管理员','总部'),(273,'角色分配','保存','机构id:1;角色id:3;机构id:1;角色id:2;','','2012-03-02 08:45:57','localhost','127.0.0.1','管理员','管理员','总部'),(274,'用户分配角色','保存','8','3','2012-03-02 08:46:07','localhost','127.0.0.1','管理员','管理员','总部'),(275,'模块分配操作','保存','模块操作关系ID:5;6;7;87;89;90;102;10;11;12;13;14;','3;opt','2012-03-02 08:46:44','localhost','127.0.0.1','管理员','管理员','总部'),(276,'用户分配角色','保存','7','2','2012-03-02 08:47:02','localhost','127.0.0.1','管理员','管理员','总部'),(277,'模块分配操作','保存','模块操作关系ID:44;','2;opt','2012-03-02 08:47:38','localhost','127.0.0.1','管理员','管理员','总部'),(278,'用户登录','用户登录','北京-经理-张三','','2012-03-02 08:48:12','localhost','127.0.0.1','张三','经理','北京'),(279,'用户代理','保存','4;3;2;','4','2012-03-02 08:48:44','localhost','127.0.0.1','张三','经理','北京'),(280,'用户登录','用户登录','北京-经理-张三(雅各布D)','','2012-03-02 08:49:20','localhost','127.0.0.1','张三(雅各布D)','经理','北京'),(281,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 08:49:37','localhost','127.0.0.1','管理员','管理员','总部'),(282,'模块分配操作','保存','模块操作关系ID:5;6;7;87;89;90;102;','3;opt','2012-03-02 08:49:55','localhost','127.0.0.1','管理员','管理员','总部'),(283,'用户登录','用户登录','北京-经理-张三','','2012-03-02 08:50:10','localhost','127.0.0.1','张三','经理','北京'),(284,'系统模块管理','修改','系统日志管理;sysLogMgr;','18','2012-03-02 08:50:39','localhost','127.0.0.1','张三','经理','北京'),(285,'用户登录','用户登录','北京-经理-张三(雅各布D)','','2012-03-02 08:50:56','localhost','127.0.0.1','张三(雅各布D)','经理','北京'),(286,'用户登录','用户登录','北京-经理-张三(雅各布D)','','2012-03-02 08:51:38','localhost','127.0.0.1','张三(雅各布D)','经理','北京'),(287,'用户登录','用户登录','北京-经理-张三','','2012-03-02 08:53:33','localhost','127.0.0.1','张三','经理','北京'),(288,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 09:14:29','localhost','127.0.0.1','管理员','管理员','总部'),(289,'模块分配操作','保存','模块操作关系ID:5;6;7;87;89;90;102;10;11;12;13;14;','3;opt','2012-03-02 09:14:47','localhost','127.0.0.1','管理员','管理员','总部'),(290,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 12:42:25','localhost','127.0.0.1','管理员','管理员','总部'),(291,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 12:48:08','localhost','127.0.0.1','管理员','管理员','总部'),(292,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;','3;opt','2012-03-02 12:48:28','localhost','127.0.0.1','管理员','管理员','总部'),(293,'用户登录','用户登录','北京-经理-张三','','2012-03-02 12:48:45','localhost','127.0.0.1','张三','经理','北京'),(294,'用户登录','用户登录','总部-管理员-管理员(雅各布D)','','2012-03-02 12:49:32','localhost','127.0.0.1','管理员(雅各布D)','管理员','总部'),(295,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 12:49:57','localhost','127.0.0.1','管理员','管理员','总部'),(296,'用户登录','用户登录','北京-经理-张三','','2012-03-02 12:50:45','localhost','127.0.0.1','张三','经理','北京'),(297,'用户登录','用户登录','北京-经理-张三','','2012-03-02 12:51:55','localhost','127.0.0.1','张三','经理','北京'),(298,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 12:52:07','localhost','127.0.0.1','管理员','管理员','总部'),(299,'用户登录','用户登录','北京-经理-张三','','2012-03-02 12:56:04','localhost','127.0.0.1','张三','经理','北京'),(300,'用户登录','用户登录','北京-经理-张三','','2012-03-02 12:57:09','localhost','127.0.0.1','张三','经理','北京'),(301,'用户登录','用户登录','北京-经理-张三','','2012-03-02 12:58:07','localhost','127.0.0.1','张三','经理','北京'),(302,'用户登录','用户登录','北京-经理-张三','','2012-03-02 12:59:32','localhost','127.0.0.1','张三','经理','北京'),(303,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:00:47','localhost','127.0.0.1','张三','经理','北京'),(304,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:00:53','localhost','127.0.0.1','张三','经理','北京'),(305,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:01:02','localhost','127.0.0.1','张三','经理','北京'),(306,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:01:09','localhost','127.0.0.1','管理员','管理员','总部'),(307,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;','3;opt','2012-03-02 13:01:30','localhost','127.0.0.1','管理员','管理员','总部'),(308,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:01:37','localhost','127.0.0.1','管理员','管理员','总部'),(309,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:01:45','localhost','127.0.0.1','张三','经理','北京'),(310,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:02:46','localhost','127.0.0.1','张三','经理','北京'),(311,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:03:08','localhost','127.0.0.1','管理员','管理员','总部'),(312,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:03:20','localhost','127.0.0.1','张三','经理','北京'),(313,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:03:32','localhost','127.0.0.1','管理员','管理员','总部'),(314,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:03:39','localhost','127.0.0.1','管理员','管理员','总部'),(315,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:04:04','localhost','127.0.0.1','张三','经理','北京'),(316,'用户代理','保存','5;3;2;','5','2012-03-02 13:04:34','localhost','127.0.0.1','张三','经理','北京'),(317,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:04:51','localhost','127.0.0.1','管理员','管理员','总部'),(318,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;50;51;52;53;54;55;56;57;','3;opt','2012-03-02 13:05:46','localhost','127.0.0.1','管理员','管理员','总部'),(319,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:12:45','localhost','127.0.0.1','张三','经理','北京'),(320,'用户代理','保存','6;3;2;','6','2012-03-02 13:13:17','localhost','127.0.0.1','张三','经理','北京'),(321,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:13:29','localhost','127.0.0.1','管理员','管理员','总部'),(322,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;50;51;52;53;54;55;56;57;5;6;7;87;89;90;102;10;11;12;13;14;','3;opt','2012-03-02 13:14:09','localhost','127.0.0.1','管理员','管理员','总部'),(323,'用户登录','用户登录','总部-管理员-管理员','','2012-03-02 13:23:31','localhost','127.0.0.1','管理员','管理员','总部'),(324,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;50;51;52;53;54;55;56;57;58;59;60;61;62;64;91;97;65;66;68;69;116;117;118;119;112;113;114;115;','2;opt','2012-03-02 13:23:52','localhost','127.0.0.1','管理员','管理员','总部'),(325,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;50;51;52;53;54;55;56;57;58;59;60;61;62;64;91;97;65;66;68;69;116;117;118;119;112;113;114;115;44;','2;opt','2012-03-02 13:24:23','localhost','127.0.0.1','管理员','管理员','总部'),(326,'用户登录','用户登录','北京-经理-张三','','2012-03-02 13:24:31','localhost','127.0.0.1','张三','经理','北京'),(327,'用户代理','保存','7;3;2;','7','2012-03-02 13:25:35','localhost','127.0.0.1','张三','经理','北京'),(328,'模块分配操作','保存','模块操作关系ID:44;','2;opt','2012-03-02 13:26:20','localhost','127.0.0.1','张三','经理','北京'),(329,'模块分配操作','保存','模块操作关系ID:44;','2;opt','2012-03-02 13:29:27','localhost','127.0.0.1','张三','经理','北京'),(330,'模块分配操作','保存','模块操作关系ID:44;','2;opt','2012-03-02 13:29:46','localhost','127.0.0.1','张三','经理','北京'),(331,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 01:45:27','localhost','127.0.0.1','管理员','管理员','总部'),(332,'机构管理','保存','机构名称：人力资源部;机构代码：YLZY;上级机构：北京;','2','2012-03-05 01:53:03','localhost','127.0.0.1','管理员','管理员','总部'),(333,'省市地区管理','保存','北京;province;','1','2012-03-05 01:53:48','localhost','127.0.0.1','管理员','管理员','总部'),(334,'省市地区管理','保存','上海;city;','2','2012-03-05 01:54:09','localhost','127.0.0.1','管理员','管理员','总部'),(335,'省市地区管理','保存','深圳;city;','3','2012-03-05 01:54:41','localhost','127.0.0.1','管理员','管理员','总部'),(336,'省市地区管理','保存','天津;city;','4','2012-03-05 01:55:05','localhost','127.0.0.1','管理员','管理员','总部'),(337,'机构管理','修改','机构名称：人力资源部;机构代码：YLZY;上级机构：总部;','2','2012-03-05 01:55:43','localhost','127.0.0.1','管理员','管理员','总部'),(338,'机构管理','保存','机构名称：培训部;机构代码：;上级机构：人力资源部;','3','2012-03-05 01:56:25','localhost','127.0.0.1','管理员','管理员','总部'),(339,'机构管理','保存','机构名称：后台部;机构代码：;上级机构：人力资源部;','4','2012-03-05 01:57:13','localhost','127.0.0.1','管理员','管理员','总部'),(340,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 01:57:51','localhost','127.0.0.1','管理员','管理员','总部'),(341,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 02:01:29','localhost','127.0.0.1','管理员','管理员','总部'),(342,'机构管理','保存','机构名称：运营部;机构代码：YYB;上级机构：总部;','5','2012-03-05 02:03:04','localhost','127.0.0.1','管理员','管理员','总部'),(343,'机构管理','保存','机构名称：北京运营部;机构代码：;上级机构：运营部;','6','2012-03-05 02:03:51','localhost','127.0.0.1','管理员','管理员','总部'),(344,'机构管理','保存','机构名称：上海运营部;机构代码：;上级机构：运营部;','7','2012-03-05 02:04:18','localhost','127.0.0.1','管理员','管理员','总部'),(345,'机构管理','保存','机构名称：天津运营部;机构代码：;上级机构：运营部;','8','2012-03-05 02:04:52','localhost','127.0.0.1','管理员','管理员','总部'),(346,'机构管理','修改','机构名称：厨房部;机构代码：CFB;上级机构：总部;','1','2012-03-05 02:06:20','localhost','127.0.0.1','管理员','管理员','总部'),(347,'机构管理','保存','机构名称：加工中心;机构代码：;上级机构：厨房部;','9','2012-03-05 02:06:48','localhost','127.0.0.1','管理员','管理员','总部'),(348,'机构管理','保存','机构名称：储运部;机构代码：;上级机构：厨房部;','10','2012-03-05 02:07:45','localhost','127.0.0.1','管理员','管理员','总部'),(349,'机构管理','保存','机构名称：配送中心;机构代码：;上级机构：厨房部;','11','2012-03-05 02:08:23','localhost','127.0.0.1','管理员','管理员','总部'),(350,'机构管理','保存','机构名称：北京配送中心;机构代码：;上级机构：厨房部;','12','2012-03-05 02:09:04','localhost','127.0.0.1','管理员','管理员','总部'),(351,'机构管理','保存','机构名称：五道品中心;机构代码：;上级机构：北京配送中心;','13','2012-03-05 02:10:00','localhost','127.0.0.1','管理员','管理员','总部'),(352,'机构管理','修改','机构名称：储运部;机构代码：;上级机构：总部;','10','2012-03-05 02:10:40','localhost','127.0.0.1','管理员','管理员','总部'),(353,'机构管理','修改','机构名称：配送中心;机构代码：;上级机构：储运部;','11','2012-03-05 02:11:13','localhost','127.0.0.1','管理员','管理员','总部'),(354,'机构管理','修改','机构名称：北京配送中心;机构代码：;上级机构：配送中心;','12','2012-03-05 02:11:28','localhost','127.0.0.1','管理员','管理员','总部'),(355,'机构管理','保存','机构名称：上海配送中心;机构代码：;上级机构：配送中心;','14','2012-03-05 02:12:27','localhost','127.0.0.1','管理员','管理员','总部'),(356,'机构管理','保存','机构名称：清华大学中心;机构代码：;上级机构：北京配送中心;','15','2012-03-05 02:13:30','localhost','127.0.0.1','管理员','管理员','总部'),(357,'机构管理','修改','机构名称：五道品中心;机构代码：;上级机构：北京配送中心;','13','2012-03-05 02:13:56','localhost','127.0.0.1','管理员','管理员','总部'),(358,'角色管理','修改','总经理;','2','2012-03-05 02:17:13','localhost','127.0.0.1','管理员','管理员','总部'),(359,'角色管理','修改','副总经理;','3','2012-03-05 02:17:30','localhost','127.0.0.1','管理员','管理员','总部'),(360,'角色管理','保存','行政人事经理;','4','2012-03-05 02:18:01','localhost','127.0.0.1','管理员','管理员','总部'),(361,'角色管理','保存','储运部经理;','5','2012-03-05 02:18:30','localhost','127.0.0.1','管理员','管理员','总部'),(362,'角色管理','保存','运营经理;','6','2012-03-05 02:18:49','localhost','127.0.0.1','管理员','管理员','总部'),(363,'角色管理','保存','财务经理;','7','2012-03-05 02:19:14','localhost','127.0.0.1','管理员','管理员','总部'),(364,'角色管理','保存','培训经理;','8','2012-03-05 02:19:34','localhost','127.0.0.1','管理员','管理员','总部'),(365,'角色管理','保存','采购经理;','9','2012-03-05 02:19:52','localhost','127.0.0.1','管理员','管理员','总部'),(366,'角色管理','保存','总经理助理;','10','2012-03-05 02:20:49','localhost','127.0.0.1','管理员','管理员','总部'),(367,'角色管理','保存','人事主管;','11','2012-03-05 02:21:12','localhost','127.0.0.1','管理员','管理员','总部'),(368,'角色管理','保存','储运主管;','12','2012-03-05 02:21:33','localhost','127.0.0.1','管理员','管理员','总部'),(369,'角色管理','保存','运营主管;','13','2012-03-05 02:21:42','localhost','127.0.0.1','管理员','管理员','总部'),(370,'角色管理','保存','财务主管;','14','2012-03-05 02:21:58','localhost','127.0.0.1','管理员','管理员','总部'),(371,'角色管理','保存','培训主管;','15','2012-03-05 02:22:17','localhost','127.0.0.1','管理员','管理员','总部'),(372,'角色管理','保存','采购主管;','16','2012-03-05 02:22:34','localhost','127.0.0.1','管理员','管理员','总部'),(373,'角色管理','保存','店长;','17','2012-03-05 02:23:23','localhost','127.0.0.1','管理员','管理员','总部'),(374,'角色管理','保存','会计;','18','2012-03-05 02:23:38','localhost','127.0.0.1','管理员','管理员','总部'),(375,'角色管理','保存','出纳;','19','2012-03-05 02:24:02','localhost','127.0.0.1','管理员','管理员','总部'),(376,'角色管理','保存','店员;','20','2012-03-05 02:24:21','localhost','127.0.0.1','管理员','管理员','总部'),(377,'角色分配','保存','机构id:3;角色id:15;机构id:3;角色id:8;','','2012-03-05 02:29:17','localhost','127.0.0.1','管理员','管理员','总部'),(378,'角色分配','保存','机构id:0;角色id:2;机构id:0;角色id:3;','','2012-03-05 02:30:09','localhost','127.0.0.1','管理员','管理员','总部'),(379,'角色分配','保存','机构id:5;角色id:6;','','2012-03-05 02:31:03','localhost','127.0.0.1','管理员','管理员','总部'),(380,'角色分配','保存','机构id:6;角色id:13;','','2012-03-05 02:31:19','localhost','127.0.0.1','管理员','管理员','总部'),(381,'角色分配','保存','机构id:7;角色id:13;机构id:7;角色id:6;','','2012-03-05 02:31:43','localhost','127.0.0.1','管理员','管理员','总部'),(382,'角色分配','保存','机构id:6;角色id:6;','','2012-03-05 02:31:54','localhost','127.0.0.1','管理员','管理员','总部'),(383,'角色分配','保存','机构id:2;角色id:4;','','2012-03-05 02:32:10','localhost','127.0.0.1','管理员','管理员','总部'),(384,'角色分配','保存','机构id:3;角色id:11;','','2012-03-05 02:32:27','localhost','127.0.0.1','管理员','管理员','总部'),(385,'角色分配','保存','机构id:10;角色id:5;机构id:10;角色id:12;','','2012-03-05 02:32:38','localhost','127.0.0.1','管理员','管理员','总部'),(386,'角色分配','保存','机构id:12;角色id:5;机构id:12;角色id:12;','','2012-03-05 02:33:11','localhost','127.0.0.1','管理员','管理员','总部'),(387,'角色分配','保存','机构id:13;角色id:16;','','2012-03-05 02:33:50','localhost','127.0.0.1','管理员','管理员','总部'),(388,'角色分配','保存','机构id:6;角色id:17;机构id:6;角色id:18;机构id:6;角色id:19;机构id:6;角色id:20;','','2012-03-05 02:34:02','localhost','127.0.0.1','管理员','管理员','总部'),(389,'角色分配','保存','机构id:7;角色id:17;机构id:7;角色id:18;机构id:7;角色id:19;机构id:7;角色id:20;','','2012-03-05 02:34:13','localhost','127.0.0.1','管理员','管理员','总部'),(390,'角色分配','删除','删除机构下的角色ID：20;19;18;17;','6','2012-03-05 02:35:17','localhost','127.0.0.1','管理员','管理员','总部'),(391,'机构管理','保存','机构名称：五道口;机构代码：;上级机构：北京运营部;','16','2012-03-05 02:35:55','localhost','127.0.0.1','管理员','管理员','总部'),(392,'角色分配','保存','机构id:16;角色id:17;机构id:16;角色id:18;机构id:16;角色id:19;机构id:16;角色id:20;','','2012-03-05 02:36:13','localhost','127.0.0.1','管理员','管理员','总部'),(393,'用户管理','停用','jacob;雅各布;副总经理;','2','2012-03-05 02:37:49','localhost','127.0.0.1','管理员','管理员','总部'),(394,'用户管理','停用','zs;张三;总经理;','3','2012-03-05 02:37:53','localhost','127.0.0.1','管理员','管理员','总部'),(395,'角色分配','删除','删除机构下的角色ID：2;3;','1','2012-03-05 02:37:58','localhost','127.0.0.1','管理员','管理员','总部'),(397,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 02:44:48','localhost','127.0.0.1','管理员','管理员','总部'),(398,'用户管理','修改','sjh;石纪红;null;','2','2012-03-05 02:45:19','localhost','127.0.0.1','管理员','管理员','总部'),(399,'用户管理','停用','sjh;石纪红;无角色用户;','2','2012-03-05 02:45:29','localhost','127.0.0.1','管理员','管理员','总部'),(400,'用户分配角色','保存','11','2','2012-03-05 02:45:40','localhost','127.0.0.1','管理员','管理员','总部'),(401,'用户管理','修改','zsp;张世鹏;null;','3','2012-03-05 02:46:42','localhost','127.0.0.1','管理员','管理员','总部'),(402,'用户管理','停用','zsp;张世鹏;无角色用户;','3','2012-03-05 02:46:46','localhost','127.0.0.1','管理员','管理员','总部'),(403,'用户分配角色','保存','12','3','2012-03-05 02:46:58','localhost','127.0.0.1','管理员','管理员','总部'),(404,'用户管理','保存','gby;高兵阳;运营经理;','4','2012-03-05 02:48:26','localhost','127.0.0.1','管理员','管理员','总部'),(405,'用户管理','保存','zj;张军;运营经理;','5','2012-03-05 02:50:02','localhost','127.0.0.1','管理员','管理员','总部'),(406,'用户管理','保存','yy;杨勇;运营主管;','6','2012-03-05 02:51:05','localhost','127.0.0.1','管理员','管理员','总部'),(407,'用户管理','保存','wx;吴雪;店长;','7','2012-03-05 02:53:00','localhost','127.0.0.1','管理员','管理员','总部'),(408,'用户管理','保存','zmh;张明辉;会计;','8','2012-03-05 02:53:46','localhost','127.0.0.1','管理员','管理员','总部'),(409,'用户管理','保存','wg;吴刚;出纳;','9','2012-03-05 02:54:34','localhost','127.0.0.1','管理员','管理员','总部'),(410,'用户管理','保存','blq;白力强;店员;','10','2012-03-05 02:55:22','localhost','127.0.0.1','管理员','管理员','总部'),(411,'用户管理','保存','wl;王乐;店员;','11','2012-03-05 02:56:46','localhost','127.0.0.1','管理员','管理员','总部'),(412,'用户管理','修改','wx;吴雪;null;','7','2012-03-05 02:57:02','localhost','127.0.0.1','管理员','管理员','总部'),(413,'系统配置管理','保存','增加管理员:sjh;','','2012-03-05 02:58:21','localhost','127.0.0.1','管理员','管理员','总部'),(414,'模块分配操作','保存','模块操作关系ID:1;2;3;4;5;6;7;87;89;90;102;10;11;12;13;14;15;16;17;18;19;25;26;27;28;29;36;37;38;39;40;98;99;100;101;104;105;106;107;108;109;110;111;44;30;31;33;34;35;','4;opt','2012-03-05 02:59:08','localhost','127.0.0.1','管理员','管理员','总部'),(415,'模块分配操作','保存','模块操作关系ID:rol;','4;row','2012-03-05 02:59:14','localhost','127.0.0.1','管理员','管理员','总部'),(416,'模块分配操作','保存','模块操作关系ID:45;46;47;48;49;50;51;52;53;54;55;56;57;58;59;60;61;62;64;91;97;65;66;68;69;116;117;118;119;112;113;114;115;','6;opt','2012-03-05 02:59:31','localhost','127.0.0.1','管理员','管理员','总部'),(417,'模块分配操作','保存','模块操作关系ID:1;2;3;4;15;16;17;18;19;25;26;27;28;29;36;37;38;39;40;98;99;100;101;104;105;106;107;108;109;110;111;45;46;47;48;49;','13;opt','2012-03-05 03:00:02','localhost','127.0.0.1','管理员','管理员','总部'),(418,'模块分配操作','保存','模块操作关系ID:5;6;7;87;89;90;102;10;11;12;13;14;25;26;27;28;29;36;37;38;39;40;98;99;100;101;104;105;106;107;108;109;110;111;','17;opt','2012-03-05 03:00:24','localhost','127.0.0.1','管理员','管理员','总部'),(419,'模块分配操作','保存','模块操作关系ID:5;6;7;87;89;90;102;10;11;12;13;14;','18;opt','2012-03-05 03:00:37','localhost','127.0.0.1','管理员','管理员','总部'),(420,'模块分配操作','保存','模块操作关系ID:7;11;12;13;14;','19;opt','2012-03-05 03:01:05','localhost','127.0.0.1','管理员','管理员','总部'),(421,'模块分配操作','保存','模块操作关系ID:1;2;3;4;5;6;7;87;89;90;102;10;11;12;13;14;15;16;17;18;19;25;26;27;28;29;36;37;38;39;40;98;99;100;101;104;105;106;107;108;109;110;111;30;31;33;34;35;45;46;47;48;49;50;51;52;53;54;55;56;57;58;59;61;62;64;91;97;65;66;68;69;116;117;118;119;112;113;114;115;','20;opt','2012-03-05 03:02:07','localhost','127.0.0.1','管理员','管理员','总部'),(422,'用户登录','用户登录','总部-总经理-石纪红','','2012-03-05 03:02:38','localhost','127.0.0.1','石纪红','总经理','总部'),(423,'用户登录','用户登录','总部-总经理-石纪红','','2012-03-05 03:03:26','localhost','127.0.0.1','石纪红','总经理','总部'),(424,'用户登录','用户登录','总部-副总经理-张世鹏','','2012-03-05 03:37:08','localhost','127.0.0.1','张世鹏','副总经理','总部'),(425,'用户登录','用户登录','总部-副总经理-张世鹏','','2012-03-05 03:38:15','localhost','127.0.0.1','张世鹏','副总经理','总部'),(426,'用户登录','用户登录','运营部-运营经理-高兵阳','','2012-03-05 03:38:26','localhost','127.0.0.1','高兵阳','运营经理','运营部'),(427,'用户登录','用户登录','运营部-运营经理-高兵阳','','2012-03-05 03:38:55','localhost','127.0.0.1','高兵阳','运营经理','运营部'),(428,'用户登录','用户登录','北京运营部-运营经理-张军','','2012-03-05 03:39:05','localhost','127.0.0.1','张军','运营经理','北京运营部'),(429,'用户登录','用户登录','北京运营部-运营主管-杨勇','','2012-03-05 03:39:21','localhost','127.0.0.1','杨勇','运营主管','北京运营部'),(430,'用户登录','用户登录','五道口-店长-吴雪','','2012-03-05 03:39:39','localhost','127.0.0.1','吴雪','店长','五道口'),(431,'用户登录','用户登录','五道口-会计-张明辉','','2012-03-05 03:39:57','localhost','127.0.0.1','张明辉','会计','五道口'),(432,'用户登录','用户登录','五道口-出纳-吴刚','','2012-03-05 03:40:11','localhost','127.0.0.1','吴刚','出纳','五道口'),(433,'用户登录','用户登录','五道口-店员-白力强','','2012-03-05 03:40:27','localhost','127.0.0.1','白力强','店员','五道口'),(434,'用户登录','用户登录','五道口-店员-王乐','','2012-03-05 03:40:49','localhost','127.0.0.1','王乐','店员','五道口'),(435,'用户登录','用户登录','五道口-出纳-吴刚','','2012-03-05 03:41:16','localhost','127.0.0.1','吴刚','出纳','五道口'),(436,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 03:43:25','localhost','127.0.0.1','管理员','管理员','总部'),(437,'用户登录','用户登录','五道口-店员-王乐','','2012-03-05 03:44:32','localhost','127.0.0.1','王乐','店员','五道口'),(438,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 03:44:43','localhost','127.0.0.1','管理员','管理员','总部'),(439,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 05:04:00','localhost','127.0.0.1','管理员','管理员','总部'),(440,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:10:35','localhost','127.0.0.1','管理员','管理员','总部'),(441,'模块分配操作','保存','模块操作关系ID:60;','11;opt','2012-03-05 06:11:09','localhost','127.0.0.1','管理员','管理员','总部'),(442,'用户登录','用户登录','五道口-店员-王乐','','2012-03-05 06:11:16','localhost','127.0.0.1','王乐','店员','五道口'),(443,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:12:10','localhost','127.0.0.1','管理员','管理员','总部'),(444,'用户登录','用户登录','五道口-店员-王乐','','2012-03-05 06:12:53','localhost','127.0.0.1','王乐','店员','五道口'),(445,'用户登录','用户登录','五道口-店员-王乐','','2012-03-05 06:13:10','localhost','127.0.0.1','王乐','店员','五道口'),(446,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:21:32','localhost','127.0.0.1','管理员','管理员','总部'),(447,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:26:31','localhost','127.0.0.1','管理员','管理员','总部'),(448,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:27:30','localhost','127.0.0.1','管理员','管理员','总部'),(449,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:29:18','localhost','127.0.0.1','管理员','管理员','总部'),(450,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:30:44','localhost','127.0.0.1','管理员','管理员','总部'),(451,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:31:36','localhost','127.0.0.1','管理员','管理员','总部'),(452,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:32:12','localhost','127.0.0.1','管理员','管理员','总部'),(453,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:34:02','localhost','127.0.0.1','管理员','管理员','总部'),(454,'类别字典管理','修改','字典大类名称：机构类型;','23','2012-03-05 06:40:11','localhost','127.0.0.1','管理员','管理员','总部'),(455,'机构管理','修改','机构名称：五道口;机构代码：;上级机构：北京运营部;','16','2012-03-05 06:41:06','localhost','127.0.0.1','管理员','管理员','总部'),(456,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:43:28','localhost','127.0.0.1','管理员','管理员','总部'),(457,'类别字典管理','修改','字典大类名称：机构类型;','23','2012-03-05 06:44:03','localhost','127.0.0.1','管理员','管理员','总部'),(458,'机构管理','停用','机构名称：天津运营部;机构代码：;上级机构：运营部;','8','2012-03-05 06:44:26','localhost','127.0.0.1','管理员','管理员','总部'),(459,'机构管理','启用','机构名称：天津运营部;机构代码：;上级机构：运营部;','8','2012-03-05 06:44:42','localhost','127.0.0.1','管理员','管理员','总部'),(460,'机构管理','停用','机构名称：天津运营部;机构代码：;上级机构：运营部;','8','2012-03-05 06:44:44','localhost','127.0.0.1','管理员','管理员','总部'),(461,'机构管理','启用','机构名称：天津运营部;机构代码：;上级机构：运营部;','8','2012-03-05 06:44:47','localhost','127.0.0.1','管理员','管理员','总部'),(462,'机构管理','修改','机构名称：运营部;机构代码：YYB;上级机构：总部;','5','2012-03-05 06:45:29','localhost','127.0.0.1','管理员','管理员','总部'),(463,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:52:31','localhost','127.0.0.1','管理员','管理员','总部'),(464,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:53:30','localhost','127.0.0.1','管理员','管理员','总部'),(465,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:54:33','localhost','127.0.0.1','管理员','管理员','总部'),(466,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 06:56:53','localhost','127.0.0.1','管理员','管理员','总部'),(467,'机构管理','修改','机构名称：五道品中心;机构代码：;上级机构：北京配送中心;','13','2012-03-05 06:58:12','localhost','127.0.0.1','管理员','管理员','总部'),(468,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:17:41','localhost','127.0.0.1','管理员','管理员','总部'),(469,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:20:04','localhost','127.0.0.1','管理员','管理员','总部'),(470,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:26:59','localhost','127.0.0.1','管理员','管理员','总部'),(471,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:29:02','localhost','127.0.0.1','管理员','管理员','总部'),(472,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:31:59','localhost','127.0.0.1','管理员','管理员','总部'),(473,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:32:59','localhost','127.0.0.1','管理员','管理员','总部'),(474,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:33:17','localhost','127.0.0.1','管理员','管理员','总部'),(475,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:34:28','localhost','127.0.0.1','管理员','管理员','总部'),(476,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:37:00','localhost','127.0.0.1','管理员','管理员','总部'),(477,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:37:16','localhost','127.0.0.1','管理员','管理员','总部'),(478,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:38:19','localhost','127.0.0.1','管理员','管理员','总部'),(479,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:39:24','localhost','127.0.0.1','管理员','管理员','总部'),(480,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:41:46','localhost','127.0.0.1','管理员','管理员','总部'),(481,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:43:27','localhost','127.0.0.1','管理员','管理员','总部'),(482,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:45:16','localhost','127.0.0.1','管理员','管理员','总部'),(483,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:46:38','localhost','127.0.0.1','管理员','管理员','总部'),(484,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:48:20','localhost','127.0.0.1','管理员','管理员','总部'),(485,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:51:14','localhost','127.0.0.1','管理员','管理员','总部'),(486,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:52:23','localhost','127.0.0.1','管理员','管理员','总部'),(487,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 07:59:51','localhost','127.0.0.1','管理员','管理员','总部'),(488,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 08:01:15','localhost','127.0.0.1','管理员','管理员','总部'),(489,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 08:07:30','localhost','127.0.0.1','管理员','管理员','总部'),(490,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 08:12:27','localhost','127.0.0.1','管理员','管理员','总部'),(491,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 09:16:12','localhost','127.0.0.1','管理员','管理员','总部'),(492,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 09:19:25','localhost','127.0.0.1','管理员','管理员','总部'),(493,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 09:19:49','localhost','127.0.0.1','管理员','管理员','总部'),(494,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 09:21:51','localhost','127.0.0.1','管理员','管理员','总部'),(495,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 09:22:29','localhost','127.0.0.1','管理员','管理员','总部'),(496,'用户登录','用户登录','总部-管理员-管理员','','2012-03-05 09:23:05','localhost','127.0.0.1','管理员','管理员','总部'),(497,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 01:29:29','localhost','127.0.0.1','管理员','管理员','总部'),(498,'用户(普通)组管理','保存','组名称：店长组;组类型：userGroup;','14','2012-03-06 01:31:44','localhost','127.0.0.1','管理员','管理员','总部'),(499,'角色组管理','保存','组名称：店员组;组类型：roleGroup;','15','2012-03-06 01:32:41','localhost','127.0.0.1','管理员','管理员','总部'),(500,'用户(普通)组管理','保存','组名称：经理组;组类型：generalGroup;','16','2012-03-06 01:33:59','localhost','127.0.0.1','管理员','管理员','总部'),(501,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 01:34:58','localhost','127.0.0.1','管理员','管理员','总部'),(502,'用户分配角色','保存','36,33','11','2012-03-06 01:37:07','localhost','127.0.0.1','管理员','管理员','总部'),(503,'用户管理','重置密码','重置密码用户：王乐','11','2012-03-06 01:37:13','localhost','127.0.0.1','管理员','管理员','总部'),(504,'模块分配操作','保存','模块操作关系ID:1;2;3;','11;row','2012-03-06 01:38:47','localhost','127.0.0.1','管理员','管理员','总部'),(505,'用户代理','保存','1;1;9;','1','2012-03-06 01:39:22','localhost','127.0.0.1','管理员','管理员','总部'),(506,'用户登录','用户登录','总部-管理员-管理员(吴刚D)','','2012-03-06 01:39:46','localhost','127.0.0.1','管理员(吴刚D)','管理员','总部'),(507,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 01:40:34','localhost','127.0.0.1','管理员','管理员','总部'),(508,'用户登录','用户登录','五道口-五道口-店长(默认)-王乐','','2012-03-06 01:40:48','localhost','127.0.0.1','王乐','五道口-店长(默认)','五道口'),(509,'用户登录','用户登录','五道口-五道口-店员-王乐','','2012-03-06 01:42:19','localhost','127.0.0.1','王乐','五道口-店员','五道口'),(510,'用户登录','用户登录','五道口-店长-王乐','','2012-03-06 01:42:32','localhost','127.0.0.1','王乐','店长','五道口'),(511,'用户代理','保存','2;11;9;','2','2012-03-06 01:43:58','localhost','127.0.0.1','王乐','店长','五道口'),(512,'用户登录','用户登录','五道口-五道口-店长(默认)-王乐','','2012-03-06 01:50:18','localhost','127.0.0.1','王乐','五道口-店长(默认)','五道口'),(513,'用户登录','用户登录','五道口-五道口-店长(默认)-王乐','','2012-03-06 01:51:22','localhost','127.0.0.1','王乐','五道口-店长(默认)','五道口'),(514,'用户登录','用户登录','五道口-店员-王乐','','2012-03-06 01:51:27','localhost','127.0.0.1','王乐','店员','五道口'),(515,'用户登录','用户登录','五道口-店长-王乐','','2012-03-06 01:51:34','localhost','127.0.0.1','王乐','店长','五道口'),(516,'用户登录','用户登录','五道口-店员-王乐','','2012-03-06 01:51:39','localhost','127.0.0.1','王乐','店员','五道口'),(517,'用户登录','用户登录','五道口-店员-王乐(吴刚D)','','2012-03-06 01:52:09','localhost','127.0.0.1','王乐(吴刚D)','店员','五道口'),(518,'用户登录','用户登录','五道口-出纳-吴刚','','2012-03-06 01:53:24','localhost','127.0.0.1','吴刚','出纳','五道口'),(519,'用户代理','保存','3;9;11;','3','2012-03-06 01:54:16','localhost','127.0.0.1','吴刚','出纳','五道口'),(520,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 01:54:38','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(521,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 01:59:18','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(522,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:06:41','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(523,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:07:13','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(524,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:08:45','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(525,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:10:42','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(526,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:10:53','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(527,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:11:02','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(528,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:18:09','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(529,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:18:21','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(530,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:19:26','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(531,'用户登录','用户登录','五道口-五道口-店员-王乐','','2012-03-06 02:23:29','localhost','127.0.0.1','王乐','五道口-店员','五道口'),(532,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:23:38','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(533,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:23:59','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(534,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 02:27:05','localhost','127.0.0.1','管理员','管理员','总部'),(535,'用户登录','用户登录','五道口-五道口-店长(默认)-王乐','','2012-03-06 02:27:46','localhost','127.0.0.1','王乐','五道口-店长(默认)','五道口'),(536,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:29:09','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(537,'用户登录','用户登录','五道口-五道口-店员-王乐','','2012-03-06 02:29:20','localhost','127.0.0.1','王乐','五道口-店员','五道口'),(538,'用户登录','用户登录','五道口-五道口-店长(默认)-王乐','','2012-03-06 02:29:30','localhost','127.0.0.1','王乐','五道口-店长(默认)','五道口'),(539,'用户登录','用户登录','五道口-出纳-吴刚(王乐D)','','2012-03-06 02:29:42','localhost','127.0.0.1','吴刚(王乐D)','出纳','五道口'),(540,'用户登录','用户登录','五道口-五道口-店长(默认)-王乐','','2012-03-06 02:29:53','localhost','127.0.0.1','王乐','五道口-店长(默认)','五道口'),(541,'用户登录','用户登录','五道口-五道口-店长(默认)-王乐','','2012-03-06 02:30:59','localhost','127.0.0.1','王乐','五道口-店长(默认)','五道口'),(542,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 02:36:55','localhost','127.0.0.1','管理员','管理员','总部'),(543,'系统公告管理','保存','保存公告标题：jacob_liang2发布啊;有效期限:Mon Mar 06 00:00:00 CST 2017','1','2012-03-06 02:39:06','localhost','127.0.0.1','管理员','管理员','总部'),(544,'系统附件','保存','附件业务表ID：1;附件名称:bRF2DU_1_jacbo.jpg','1','2012-03-06 02:39:06','localhost','127.0.0.1','管理员','管理员','总部'),(545,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 02:39:11','localhost','127.0.0.1','管理员','管理员','总部'),(546,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 02:39:35','localhost','127.0.0.1','管理员','管理员','总部'),(547,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 02:40:55','localhost','127.0.0.1','管理员','管理员','总部'),(548,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 02:45:29','localhost','127.0.0.1','管理员','管理员','总部'),(549,'省市地区管理','保存','海淀区;province;','5','2012-03-06 02:47:07','localhost','127.0.0.1','管理员','管理员','总部'),(550,'系统模块管理','修改','测试;test;','60','2012-03-06 02:53:07','localhost','127.0.0.1','管理员','管理员','总部'),(551,'模块分配操作','保存','模块分配操作;','7','2012-03-06 03:03:04','localhost','127.0.0.1','管理员','管理员','总部'),(552,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 03:03:14','localhost','127.0.0.1','管理员','管理员','总部'),(553,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 03:10:12','localhost','127.0.0.1','管理员','管理员','总部'),(554,'模块分配操作','保存','模块分配操作;4','61','2012-03-06 03:10:41','localhost','127.0.0.1','管理员','管理员','总部'),(555,'用户分配角色','保存','36,33,0','11','2012-03-06 03:20:21','localhost','127.0.0.1','管理员','管理员','总部'),(556,'用户登录','用户登录','总部-管理员-管理员','','2012-03-06 03:29:57','localhost','127.0.0.1','管理员','管理员','总部'),(557,'用户常用功能设置','保存','用户常用功能保存:用户ID1;模块ID：32','','2012-03-06 03:30:09','localhost','127.0.0.1','管理员','管理员','总部'),(558,'模块分配操作','保存','模块操作关系ID:1;2;3;4;5;6;7;87;102;10;11;12;13;14;15;16;17;18;19;25;26;27;28;29;36;37;38;39;40;98;99;100;101;104;105;106;107;108;109;110;111;30;31;33;34;35;45;46;47;48;49;50;51;52;53;54;55;56;57;58;59;61;62;64;91;97;65;66;68;69;116;117;118;119;112;113;114;115;120;','20;opt','2012-03-06 03:30:23','localhost','127.0.0.1','管理员','管理员','总部'),(559,'用户分配角色','保存','33,11','7','2012-03-06 03:30:34','localhost','127.0.0.1','管理员','管理员','总部'),(560,'模块分配操作','保存','模块操作关系ID:1;','7;row','2012-03-06 03:30:47','localhost','127.0.0.1','管理员','管理员','总部');
/*!40000 ALTER TABLE `syspl_sys_opt_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syspl_sys_parameter`
--

DROP TABLE IF EXISTS `syspl_sys_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syspl_sys_parameter` (
  `PARA_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PARA_NAME` varchar(20) DEFAULT NULL,
  `PARA_KEY_NAME` varchar(20) DEFAULT NULL,
  `PARA_VALUE` varchar(20) DEFAULT NULL,
  `PARA_TYPE` varchar(20) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PARA_ID`),
  KEY `PARA_KEY_NAME_INDEX` (`PARA_KEY_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syspl_sys_parameter`
--

LOCK TABLES `syspl_sys_parameter` WRITE;
/*!40000 ALTER TABLE `syspl_sys_parameter` DISABLE KEYS */;
INSERT INTO `syspl_sys_parameter` VALUES (1,'用户默认密码','userdefaultpassword','123456','platform','','enable','admin','2010-12-11 02:26:23'),(2,'用户有效期','uservalidate','6','uum','','enable','admin','2010-12-11 02:53:32');
/*!40000 ALTER TABLE `syspl_sys_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_group`
--

DROP TABLE IF EXISTS `uum_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_group` (
  `GROUP_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GROUP_TYPE` varchar(15) DEFAULT NULL,
  `GROUP_NAME` varchar(20) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_group`
--

LOCK TABLES `uum_group` WRITE;
/*!40000 ALTER TABLE `uum_group` DISABLE KEYS */;
INSERT INTO `uum_group` VALUES (1,'roleGroup','test','','enable','admin','2012-02-29 07:49:06'),(2,'roleGroup','a','','enable','admin','2012-02-29 07:51:41'),(3,'roleGroup','b','','enable','admin','2012-02-29 07:55:18'),(4,'roleGroup','c','','enable','admin','2012-02-29 07:57:45'),(5,'roleGroup','d','','enable','admin','2012-02-29 07:58:45'),(6,'roleGroup','e','','enable','admin','2012-02-29 08:44:51'),(7,'roleGroup','f','','enable','admin','2012-02-29 09:11:20'),(8,'roleGroup','g','','enable','admin','2012-03-01 03:02:40'),(9,'roleGroup','h','','enable','admin','2012-03-01 03:09:59'),(10,'roleGroup','t','','enable','admin','2012-03-01 03:19:48'),(11,'roleGroup','y','','enable','admin','2012-03-01 03:21:01'),(12,'roleGroup','u','','enable','admin','2012-03-01 03:21:52'),(13,'roleGroup','q','','enable','admin','2012-03-01 03:27:32'),(14,'userGroup','店长组','店长','enable','admin','2012-03-06 01:31:44'),(15,'roleGroup','店员组','','enable','admin','2012-03-06 01:32:41'),(16,'generalGroup','经理组','','enable','admin','2012-03-06 01:33:59');
/*!40000 ALTER TABLE `uum_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_group_member`
--

DROP TABLE IF EXISTS `uum_group_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_group_member` (
  `GROUP_MEMBER_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `GROUP_MEMBER_TYPE` varchar(15) DEFAULT NULL COMMENT '用户角色',
  `MEMBER_RESOURCE_ID` bigint(20) DEFAULT NULL COMMENT '用户角色ID',
  `GROUP_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`GROUP_MEMBER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_group_member`
--

LOCK TABLES `uum_group_member` WRITE;
/*!40000 ALTER TABLE `uum_group_member` DISABLE KEYS */;
INSERT INTO `uum_group_member` VALUES (1,'role',1,1),(2,'role',2,1),(3,'role',2,2),(4,'role',1,2),(5,'role',2,7),(6,'role',1,7),(7,'role',2,NULL),(8,'role',1,NULL),(9,'role',2,11),(10,'role',1,11),(11,'role',2,12),(12,'role',1,12),(13,'role',2,NULL),(14,'role',1,NULL),(15,'user',4,14),(16,'role',19,15),(17,'role',20,15),(18,'role',17,15),(19,'role',18,15),(20,'user',4,16),(21,'role',6,16);
/*!40000 ALTER TABLE `uum_group_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_org_role_ref`
--

DROP TABLE IF EXISTS `uum_org_role_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_org_role_ref` (
  `ORG_ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ORG_ID` bigint(20) DEFAULT NULL,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ORG_ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_org_role_ref`
--

LOCK TABLES `uum_org_role_ref` WRITE;
/*!40000 ALTER TABLE `uum_org_role_ref` DISABLE KEYS */;
INSERT INTO `uum_org_role_ref` VALUES (0,0,0),(2,0,1),(9,3,15),(10,3,8),(11,0,2),(12,0,3),(13,5,6),(14,6,13),(15,7,13),(16,7,6),(17,6,6),(18,2,4),(19,3,11),(20,10,5),(21,10,12),(22,12,5),(23,12,12),(24,13,16),(29,7,17),(30,7,18),(31,7,19),(32,7,20),(33,16,17),(34,16,18),(35,16,19),(36,16,20);
/*!40000 ALTER TABLE `uum_org_role_ref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_organ`
--

DROP TABLE IF EXISTS `uum_organ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_organ` (
  `ORG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ORG_SIMPLE_NAME` varchar(10) DEFAULT NULL,
  `ORG_FULL_NAME` varchar(60) DEFAULT NULL,
  `ORG_CODE` varchar(10) DEFAULT NULL,
  `ORG_ADDRESS1` varchar(100) DEFAULT NULL,
  `ORG_ADDRESS2` varchar(100) DEFAULT NULL,
  `ORG_TEL1` varchar(25) DEFAULT NULL,
  `ORG_TEL2` varchar(25) DEFAULT NULL,
  `ORG_BEGIN_DATE` date DEFAULT NULL,
  `ORG_TYPE` varchar(20) DEFAULT NULL,
  `ORG_FAX` varchar(20) DEFAULT NULL,
  `ORG_POSTAL` varchar(6) DEFAULT NULL,
  `ORG_LEGAL` varchar(20) DEFAULT NULL,
  `ORG_TAX_NO` varchar(25) DEFAULT NULL,
  `ORG_REG_NO` varchar(25) DEFAULT NULL,
  `ORG_BELONG_DIST` bigint(20) DEFAULT NULL,
  `ORG_PARENT` bigint(20) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ORG_ID`),
  KEY `ORG_SIMPLE_NAME_INDEX` (`ORG_SIMPLE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_organ`
--

LOCK TABLES `uum_organ` WRITE;
/*!40000 ALTER TABLE `uum_organ` DISABLE KEYS */;
INSERT INTO `uum_organ` VALUES (0,'总部',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'enable',NULL,NULL,'2012-01-19 08:23:05'),(1,'厨房部','厨房部','CFB','','','','',NULL,'HQ',NULL,'','','','',1,0,'enable','','admin','2012-03-02 02:05:28'),(2,'人力资源部','人力资源部','YLZY','','','','',NULL,'city',NULL,'','','','',1,0,'enable','','admin','2012-03-05 01:53:03'),(3,'培训部','','','','','','',NULL,'HQ',NULL,'','','','',1,2,'enable','','admin','2012-03-05 01:56:25'),(4,'后台部','','','','','','',NULL,'city',NULL,'','','','',1,2,'enable','','admin','2012-03-05 01:57:13'),(5,'运营部','运营部','YYB','','','','',NULL,'department',NULL,'','','','',1,0,'enable','','admin','2012-03-05 02:03:04'),(6,'北京运营部','北京运营部','','','','','',NULL,'city',NULL,'','','','',1,5,'enable','','admin','2012-03-05 02:03:51'),(7,'上海运营部','','','','','','',NULL,'city',NULL,'','','','',2,5,'enable','','admin','2012-03-05 02:04:18'),(8,'天津运营部','','','','','','',NULL,'city',NULL,'','','','',4,5,'enable','','admin','2012-03-05 02:04:52'),(9,'加工中心','','','','','','',NULL,'HQ',NULL,'','','','',1,1,'enable','','admin','2012-03-05 02:06:48'),(10,'储运部','储运部','','','','','',NULL,'HQ',NULL,'','','','',1,0,'enable','','admin','2012-03-05 02:07:45'),(11,'配送中心','','','','','','',NULL,'HQ',NULL,'','','','',1,10,'enable','','admin','2012-03-05 02:08:23'),(12,'北京配送中心','','','','','','',NULL,'HQ',NULL,'','','','',1,11,'enable','','admin','2012-03-05 02:09:04'),(13,'五道品中心','','','','','','',NULL,'department',NULL,'','','','',1,12,'enable','','admin','2012-03-05 02:10:00'),(14,'上海配送中心','','','','','','',NULL,'HQ',NULL,'','','','',2,11,'enable','','admin','2012-03-05 02:12:27'),(15,'清华大学中心','','','','','','',NULL,'HQ',NULL,'','','','',1,12,'enable','','admin','2012-03-05 02:13:30'),(16,'五道口','','','','','','',NULL,'department',NULL,'','','','',1,6,'enable','','admin','2012-03-05 02:35:55');
/*!40000 ALTER TABLE `uum_organ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_privilege`
--

DROP TABLE IF EXISTS `uum_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_privilege` (
  `PRIVILEGE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RESOURCE_ID` varchar(20) DEFAULT NULL,
  `OWNER_ID` bigint(20) DEFAULT NULL,
  `OWNER_TYPE` varchar(10) DEFAULT NULL,
  `PRIVILEGE_SCOPE` varchar(10) DEFAULT NULL,
  `PRIVILEGE_TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`PRIVILEGE_ID`),
  KEY `MOD_OPT_ID_INDEX` (`RESOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=515 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_privilege`
--

LOCK TABLES `uum_privilege` WRITE;
/*!40000 ALTER TABLE `uum_privilege` DISABLE KEYS */;
INSERT INTO `uum_privilege` VALUES (180,'44',2,'rol','inc','opt'),(181,'all',3,'rol','all','opt'),(182,'1',4,'rol','inc','opt'),(183,'2',4,'rol','inc','opt'),(184,'3',4,'rol','inc','opt'),(185,'4',4,'rol','inc','opt'),(186,'5',4,'rol','inc','opt'),(187,'6',4,'rol','inc','opt'),(188,'7',4,'rol','inc','opt'),(189,'87',4,'rol','inc','opt'),(192,'102',4,'rol','inc','opt'),(193,'10',4,'rol','inc','opt'),(194,'11',4,'rol','inc','opt'),(195,'12',4,'rol','inc','opt'),(196,'13',4,'rol','inc','opt'),(197,'14',4,'rol','inc','opt'),(198,'15',4,'rol','inc','opt'),(199,'16',4,'rol','inc','opt'),(200,'17',4,'rol','inc','opt'),(201,'18',4,'rol','inc','opt'),(202,'19',4,'rol','inc','opt'),(203,'25',4,'rol','inc','opt'),(204,'26',4,'rol','inc','opt'),(205,'27',4,'rol','inc','opt'),(206,'28',4,'rol','inc','opt'),(207,'29',4,'rol','inc','opt'),(208,'36',4,'rol','inc','opt'),(209,'37',4,'rol','inc','opt'),(210,'38',4,'rol','inc','opt'),(211,'39',4,'rol','inc','opt'),(212,'40',4,'rol','inc','opt'),(213,'98',4,'rol','inc','opt'),(214,'99',4,'rol','inc','opt'),(215,'100',4,'rol','inc','opt'),(216,'101',4,'rol','inc','opt'),(217,'104',4,'rol','inc','opt'),(218,'105',4,'rol','inc','opt'),(219,'106',4,'rol','inc','opt'),(220,'107',4,'rol','inc','opt'),(221,'108',4,'rol','inc','opt'),(222,'109',4,'rol','inc','opt'),(223,'110',4,'rol','inc','opt'),(224,'111',4,'rol','inc','opt'),(225,'44',4,'rol','inc','opt'),(226,'30',4,'rol','inc','opt'),(227,'31',4,'rol','inc','opt'),(228,'33',4,'rol','inc','opt'),(229,'34',4,'rol','inc','opt'),(230,'35',4,'rol','inc','opt'),(231,'rol',4,'rol','rol','row'),(232,'45',6,'rol','inc','opt'),(233,'46',6,'rol','inc','opt'),(234,'47',6,'rol','inc','opt'),(235,'48',6,'rol','inc','opt'),(236,'49',6,'rol','inc','opt'),(237,'50',6,'rol','inc','opt'),(238,'51',6,'rol','inc','opt'),(239,'52',6,'rol','inc','opt'),(240,'53',6,'rol','inc','opt'),(241,'54',6,'rol','inc','opt'),(242,'55',6,'rol','inc','opt'),(243,'56',6,'rol','inc','opt'),(244,'57',6,'rol','inc','opt'),(245,'58',6,'rol','inc','opt'),(246,'59',6,'rol','inc','opt'),(247,'60',6,'rol','inc','opt'),(248,'61',6,'rol','inc','opt'),(249,'62',6,'rol','inc','opt'),(250,'64',6,'rol','inc','opt'),(251,'91',6,'rol','inc','opt'),(252,'97',6,'rol','inc','opt'),(253,'65',6,'rol','inc','opt'),(254,'66',6,'rol','inc','opt'),(255,'68',6,'rol','inc','opt'),(256,'69',6,'rol','inc','opt'),(257,'116',6,'rol','inc','opt'),(258,'117',6,'rol','inc','opt'),(259,'118',6,'rol','inc','opt'),(260,'119',6,'rol','inc','opt'),(261,'112',6,'rol','inc','opt'),(262,'113',6,'rol','inc','opt'),(263,'114',6,'rol','inc','opt'),(264,'115',6,'rol','inc','opt'),(265,'1',13,'rol','exc','opt'),(266,'2',13,'rol','exc','opt'),(267,'3',13,'rol','exc','opt'),(268,'4',13,'rol','exc','opt'),(269,'15',13,'rol','exc','opt'),(270,'16',13,'rol','exc','opt'),(271,'17',13,'rol','exc','opt'),(272,'18',13,'rol','exc','opt'),(273,'19',13,'rol','exc','opt'),(274,'25',13,'rol','exc','opt'),(275,'26',13,'rol','exc','opt'),(276,'27',13,'rol','exc','opt'),(277,'28',13,'rol','exc','opt'),(278,'29',13,'rol','exc','opt'),(279,'36',13,'rol','exc','opt'),(280,'37',13,'rol','exc','opt'),(281,'38',13,'rol','exc','opt'),(282,'39',13,'rol','exc','opt'),(283,'40',13,'rol','exc','opt'),(284,'98',13,'rol','exc','opt'),(285,'99',13,'rol','exc','opt'),(286,'100',13,'rol','exc','opt'),(287,'101',13,'rol','exc','opt'),(288,'104',13,'rol','exc','opt'),(289,'105',13,'rol','exc','opt'),(290,'106',13,'rol','exc','opt'),(291,'107',13,'rol','exc','opt'),(292,'108',13,'rol','exc','opt'),(293,'109',13,'rol','exc','opt'),(294,'110',13,'rol','exc','opt'),(295,'111',13,'rol','exc','opt'),(296,'45',13,'rol','exc','opt'),(297,'46',13,'rol','exc','opt'),(298,'47',13,'rol','exc','opt'),(299,'48',13,'rol','exc','opt'),(300,'49',13,'rol','exc','opt'),(301,'5',17,'rol','inc','opt'),(302,'6',17,'rol','inc','opt'),(303,'7',17,'rol','inc','opt'),(304,'87',17,'rol','inc','opt'),(307,'102',17,'rol','inc','opt'),(308,'10',17,'rol','inc','opt'),(309,'11',17,'rol','inc','opt'),(310,'12',17,'rol','inc','opt'),(311,'13',17,'rol','inc','opt'),(312,'14',17,'rol','inc','opt'),(313,'25',17,'rol','inc','opt'),(314,'26',17,'rol','inc','opt'),(315,'27',17,'rol','inc','opt'),(316,'28',17,'rol','inc','opt'),(317,'29',17,'rol','inc','opt'),(318,'36',17,'rol','inc','opt'),(319,'37',17,'rol','inc','opt'),(320,'38',17,'rol','inc','opt'),(321,'39',17,'rol','inc','opt'),(322,'40',17,'rol','inc','opt'),(323,'98',17,'rol','inc','opt'),(324,'99',17,'rol','inc','opt'),(325,'100',17,'rol','inc','opt'),(326,'101',17,'rol','inc','opt'),(327,'104',17,'rol','inc','opt'),(328,'105',17,'rol','inc','opt'),(329,'106',17,'rol','inc','opt'),(330,'107',17,'rol','inc','opt'),(331,'108',17,'rol','inc','opt'),(332,'109',17,'rol','inc','opt'),(333,'110',17,'rol','inc','opt'),(334,'111',17,'rol','inc','opt'),(335,'5',18,'rol','inc','opt'),(336,'6',18,'rol','inc','opt'),(337,'7',18,'rol','inc','opt'),(338,'87',18,'rol','inc','opt'),(341,'102',18,'rol','inc','opt'),(342,'10',18,'rol','inc','opt'),(343,'11',18,'rol','inc','opt'),(344,'12',18,'rol','inc','opt'),(345,'13',18,'rol','inc','opt'),(346,'14',18,'rol','inc','opt'),(347,'7',19,'rol','inc','opt'),(348,'11',19,'rol','inc','opt'),(349,'12',19,'rol','inc','opt'),(350,'13',19,'rol','inc','opt'),(351,'14',19,'rol','inc','opt'),(432,'1',11,'usr','org','row'),(433,'2',11,'usr','org','row'),(434,'3',11,'usr','org','row'),(435,'1',20,'rol','exc','opt'),(436,'2',20,'rol','exc','opt'),(437,'3',20,'rol','exc','opt'),(438,'4',20,'rol','exc','opt'),(439,'5',20,'rol','exc','opt'),(440,'6',20,'rol','exc','opt'),(441,'7',20,'rol','exc','opt'),(442,'87',20,'rol','exc','opt'),(443,'102',20,'rol','exc','opt'),(444,'10',20,'rol','exc','opt'),(445,'11',20,'rol','exc','opt'),(446,'12',20,'rol','exc','opt'),(447,'13',20,'rol','exc','opt'),(448,'14',20,'rol','exc','opt'),(449,'15',20,'rol','exc','opt'),(450,'16',20,'rol','exc','opt'),(451,'17',20,'rol','exc','opt'),(452,'18',20,'rol','exc','opt'),(453,'19',20,'rol','exc','opt'),(454,'25',20,'rol','exc','opt'),(455,'26',20,'rol','exc','opt'),(456,'27',20,'rol','exc','opt'),(457,'28',20,'rol','exc','opt'),(458,'29',20,'rol','exc','opt'),(459,'36',20,'rol','exc','opt'),(460,'37',20,'rol','exc','opt'),(461,'38',20,'rol','exc','opt'),(462,'39',20,'rol','exc','opt'),(463,'40',20,'rol','exc','opt'),(464,'98',20,'rol','exc','opt'),(465,'99',20,'rol','exc','opt'),(466,'100',20,'rol','exc','opt'),(467,'101',20,'rol','exc','opt'),(468,'104',20,'rol','exc','opt'),(469,'105',20,'rol','exc','opt'),(470,'106',20,'rol','exc','opt'),(471,'107',20,'rol','exc','opt'),(472,'108',20,'rol','exc','opt'),(473,'109',20,'rol','exc','opt'),(474,'110',20,'rol','exc','opt'),(475,'111',20,'rol','exc','opt'),(476,'30',20,'rol','exc','opt'),(477,'31',20,'rol','exc','opt'),(478,'33',20,'rol','exc','opt'),(479,'34',20,'rol','exc','opt'),(480,'35',20,'rol','exc','opt'),(481,'45',20,'rol','exc','opt'),(482,'46',20,'rol','exc','opt'),(483,'47',20,'rol','exc','opt'),(484,'48',20,'rol','exc','opt'),(485,'49',20,'rol','exc','opt'),(486,'50',20,'rol','exc','opt'),(487,'51',20,'rol','exc','opt'),(488,'52',20,'rol','exc','opt'),(489,'53',20,'rol','exc','opt'),(490,'54',20,'rol','exc','opt'),(491,'55',20,'rol','exc','opt'),(492,'56',20,'rol','exc','opt'),(493,'57',20,'rol','exc','opt'),(494,'58',20,'rol','exc','opt'),(495,'59',20,'rol','exc','opt'),(496,'61',20,'rol','exc','opt'),(497,'62',20,'rol','exc','opt'),(498,'64',20,'rol','exc','opt'),(499,'91',20,'rol','exc','opt'),(500,'97',20,'rol','exc','opt'),(501,'65',20,'rol','exc','opt'),(502,'66',20,'rol','exc','opt'),(503,'68',20,'rol','exc','opt'),(504,'69',20,'rol','exc','opt'),(505,'116',20,'rol','exc','opt'),(506,'117',20,'rol','exc','opt'),(507,'118',20,'rol','exc','opt'),(508,'119',20,'rol','exc','opt'),(509,'112',20,'rol','exc','opt'),(510,'113',20,'rol','exc','opt'),(511,'114',20,'rol','exc','opt'),(512,'115',20,'rol','exc','opt'),(513,'120',20,'rol','exc','opt'),(514,'1',7,'usr','org','row');
/*!40000 ALTER TABLE `uum_privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_role`
--

DROP TABLE IF EXISTS `uum_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_role` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(10) DEFAULT NULL,
  `ROLE_LEVEL` smallint(6) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_role`
--

LOCK TABLES `uum_role` WRITE;
/*!40000 ALTER TABLE `uum_role` DISABLE KEYS */;
INSERT INTO `uum_role` VALUES (0,'无角色用户',NULL,'enable','','admin','2012-01-19 08:23:20'),(1,'管理员',1,'enable','','admin','2012-01-19 08:23:47'),(2,'总经理',1,'enable','','admin','2012-02-29 07:47:22'),(3,'副总经理',2,'enable','','admin','2012-03-02 07:29:59'),(4,'行政人事经理',3,'enable','','admin','2012-03-05 02:18:01'),(5,'储运部经理',3,'enable','','admin','2012-03-05 02:18:30'),(6,'运营经理',3,'enable','','admin','2012-03-05 02:18:49'),(7,'财务经理',3,'enable','','admin','2012-03-05 02:19:14'),(8,'培训经理',3,'enable','','admin','2012-03-05 02:19:34'),(9,'采购经理',3,'enable','','admin','2012-03-05 02:19:52'),(10,'总经理助理',3,'enable','','admin','2012-03-05 02:20:49'),(11,'人事主管',4,'enable','','admin','2012-03-05 02:21:12'),(12,'储运主管',4,'enable','','admin','2012-03-05 02:21:33'),(13,'运营主管',4,'enable','','admin','2012-03-05 02:21:42'),(14,'财务主管',4,'enable','','admin','2012-03-05 02:21:58'),(15,'培训主管',4,'enable','','admin','2012-03-05 02:22:17'),(16,'采购主管',4,'enable','','admin','2012-03-05 02:22:34'),(17,'店长',5,'enable','','admin','2012-03-05 02:23:23'),(18,'会计',6,'enable','','admin','2012-03-05 02:23:38'),(19,'出纳',7,'enable','','admin','2012-03-05 02:24:02'),(20,'店员',8,'enable','','admin','2012-03-05 02:24:21');
/*!40000 ALTER TABLE `uum_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_role_user_ref`
--

DROP TABLE IF EXISTS `uum_role_user_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_role_user_ref` (
  `ORG_ROLE_USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) DEFAULT NULL,
  `ORG_ROLE_ID` bigint(20) DEFAULT NULL,
  `IS_DEFAULT` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`ORG_ROLE_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_role_user_ref`
--

LOCK TABLES `uum_role_user_ref` WRITE;
/*!40000 ALTER TABLE `uum_role_user_ref` DISABLE KEYS */;
INSERT INTO `uum_role_user_ref` VALUES (1,1,2,'Y'),(29,2,11,'Y'),(30,3,12,'Y'),(31,4,13,'Y'),(32,5,17,'Y'),(33,6,14,'Y'),(35,8,34,'Y'),(36,9,35,'Y'),(37,10,36,'Y'),(41,11,36,'N'),(42,11,33,'Y'),(43,11,0,'N'),(44,7,33,'Y'),(45,7,11,'N');
/*!40000 ALTER TABLE `uum_role_user_ref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_user`
--

DROP TABLE IF EXISTS `uum_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_CODE` varchar(20) DEFAULT NULL,
  `USER_NAME` varchar(20) DEFAULT NULL,
  `USER_PASSWORD` varchar(20) DEFAULT NULL,
  `USER_GENDER` varchar(2) DEFAULT NULL,
  `USER_POSITION` varchar(30) DEFAULT NULL,
  `USER_PHOTO_URL` varchar(200) DEFAULT NULL,
  `USER_QQ` varchar(20) DEFAULT NULL,
  `USER_MSN` varchar(20) DEFAULT NULL,
  `USER_MOBILE` varchar(20) DEFAULT NULL,
  `USER_MOBILE2` varchar(20) DEFAULT NULL,
  `USER_OFFICE_TEL` varchar(20) DEFAULT NULL,
  `USER_ADDRESS` varchar(100) DEFAULT NULL,
  `USER_FAMILY_TEL` varchar(20) DEFAULT NULL,
  `USER_EMAIL` varchar(30) DEFAULT NULL,
  `USER_AVIDATE` date DEFAULT NULL,
  `USER_IS_AGENT` varchar(2) DEFAULT NULL,
  `USER_BELONGTO_ORG` bigint(20) DEFAULT NULL,
  `MEMO` varchar(100) DEFAULT NULL,
  `STATUS` varchar(15) NOT NULL,
  `CREATOR` varchar(15) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `USER_NAME_PY` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_user`
--

LOCK TABLES `uum_user` WRITE;
/*!40000 ALTER TABLE `uum_user` DISABLE KEYS */;
INSERT INTO `uum_user` VALUES (1,'admin','管理员','123456',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'enable','admin','2012-01-19 08:24:43',NULL),(2,'sjh','石纪红','UlFQV1ZV','0','总经理','D:\\tomcat-7-1\\webapps\\bhtsys\\uploadFile\\img\\E3gdIr_1_jacbo.jpg','','','','','','','','','2012-09-02',NULL,0,'','enable','admin','2012-03-02 08:47:02','sjh'),(3,'zsp','张世鹏','UlFQV1ZV','0','副总经理','D:\\tomcat-7-1\\webapps\\bhtsys\\uploadFile\\img\\','','','','','','','','','2012-09-02',NULL,0,'','enable','admin','2012-03-02 08:46:07','zsp'),(4,'gby','高兵阳','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,5,'','enable','admin','2012-03-05 02:48:26','gby'),(5,'zj','张军','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,6,'','enable','admin','2012-03-05 02:50:02','zj'),(6,'yy','杨勇','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,6,'','enable','admin','2012-03-05 02:51:05','yy'),(7,'wx','吴雪','UlFQV1ZV','1','','D:\\tomcat-7-1\\webapps\\bhtsys\\uploadFile\\img\\','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:53:00','wx'),(8,'zmh','张明辉','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:53:46','zmh'),(9,'wg','吴刚','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:54:34','wg'),(10,'blq','白力强','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:55:22','blq'),(11,'wl','王乐','UlFQV1ZV','0','','','','','','','','','','','2012-09-05',NULL,16,'','enable','admin','2012-03-05 02:56:46','wl');
/*!40000 ALTER TABLE `uum_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_user_agent`
--

DROP TABLE IF EXISTS `uum_user_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_user_agent` (
  `AGENT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) DEFAULT NULL,
  `AGENT_USER_ID` bigint(20) DEFAULT NULL,
  `ORG_ROLE_ID` bigint(20) DEFAULT NULL,
  `BEGIN_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `REASON` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`AGENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_user_agent`
--

LOCK TABLES `uum_user_agent` WRITE;
/*!40000 ALTER TABLE `uum_user_agent` DISABLE KEYS */;
INSERT INTO `uum_user_agent` VALUES (1,1,9,2,'2012-03-06','2012-03-15',''),(3,9,11,35,'2012-03-06','2012-03-08','');
/*!40000 ALTER TABLE `uum_user_agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_user_agent_privilege`
--

DROP TABLE IF EXISTS `uum_user_agent_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_user_agent_privilege` (
  `USER_AGENT_PRI_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AGENT_ID` bigint(20) DEFAULT NULL,
  `PRIVILEGE_ID` varchar(20) DEFAULT NULL,
  `PRIVILEGE_TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`USER_AGENT_PRI_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_user_agent_privilege`
--

LOCK TABLES `uum_user_agent_privilege` WRITE;
/*!40000 ALTER TABLE `uum_user_agent_privilege` DISABLE KEYS */;
INSERT INTO `uum_user_agent_privilege` VALUES (1,1,'all','opt'),(36,3,'12','opt');
/*!40000 ALTER TABLE `uum_user_agent_privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uum_user_commfun`
--

DROP TABLE IF EXISTS `uum_user_commfun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uum_user_commfun` (
  `USER_ID` bigint(20) NOT NULL,
  `MODULE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`MODULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uum_user_commfun`
--

LOCK TABLES `uum_user_commfun` WRITE;
/*!40000 ALTER TABLE `uum_user_commfun` DISABLE KEYS */;
INSERT INTO `uum_user_commfun` VALUES (1,32);
/*!40000 ALTER TABLE `uum_user_commfun` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-07 11:19:54
