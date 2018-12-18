-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: bdsys
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_board`
--

DROP TABLE IF EXISTS `tb_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_board` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) DEFAULT NULL,
  `contents` text,
  `id` varchar(20) DEFAULT NULL,
  `ymd` date DEFAULT NULL,
  `clicked` int(11) DEFAULT '0',
  `rec` int(11) DEFAULT '0',
  PRIMARY KEY (`num`),
  KEY `id` (`id`),
  CONSTRAINT `tb_board_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tb_member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_board`
--

LOCK TABLES `tb_board` WRITE;
/*!40000 ALTER TABLE `tb_board` DISABLE KEYS */;
INSERT INTO `tb_board` VALUES (1,'[공지]','공지','manager','2018-12-13',6,13),(2,'게시판 초기화 됐나요??','왜죠??','world0584','2018-12-13',2,7);
/*!40000 ALTER TABLE `tb_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_member`
--

DROP TABLE IF EXISTS `tb_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_member` (
  `id` varchar(15) NOT NULL,
  `pwd` varchar(15) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `tel` varchar(13) DEFAULT NULL,
  `addr` varchar(100) DEFAULT NULL,
  `birth` varchar(8) DEFAULT NULL,
  `job` varchar(10) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `intro` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member`
--

LOCK TABLES `tb_member` WRITE;
/*!40000 ALTER TABLE `tb_member` DISABLE KEYS */;
INSERT INTO `tb_member` VALUES ('manager','manager',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('world0584','eotjwnd1','천재관','010-9001-0584','경산 조영동','19930901','학생','M','jaychun01@naver.com','3점슛!');
/*!40000 ALTER TABLE `tb_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_reccheck`
--

DROP TABLE IF EXISTS `tb_reccheck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_reccheck` (
  `id` varchar(15) NOT NULL,
  `num` int(11) NOT NULL,
  `recCheck` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`num`),
  KEY `tb_reccheck_ibfk_2` (`num`),
  CONSTRAINT `tb_reccheck_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tb_member` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `tb_reccheck_ibfk_2` FOREIGN KEY (`num`) REFERENCES `tb_board` (`num`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_reccheck`
--

LOCK TABLES `tb_reccheck` WRITE;
/*!40000 ALTER TABLE `tb_reccheck` DISABLE KEYS */;
INSERT INTO `tb_reccheck` VALUES ('world0584',1,1),('world0584',2,1);
/*!40000 ALTER TABLE `tb_reccheck` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bdsys'
--

--
-- Dumping routines for database 'bdsys'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-13 17:06:49
