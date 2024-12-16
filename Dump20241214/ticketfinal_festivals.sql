-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: ticketfinal
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `festivals`
--

DROP TABLE IF EXISTS `festivals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `festivals` (
  `festival_id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `festival_type` varchar(50) NOT NULL,
  `event_date` date NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`festival_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `festivals_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `festivals`
--

LOCK TABLES `festivals` WRITE;
/*!40000 ALTER TABLE `festivals` DISABLE KEYS */;
INSERT INTO `festivals` VALUES (1,1,'İstanbul Film Festivali','2024-12-20','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',200.00),(2,2,'Ankara Uluslararası Tiyatro Festivali','2024-12-21','Ankara, Atakule',180.00),(3,3,'İzmir Uluslararası Müzik Festivali','2024-12-22','İzmir, Kültürpark Açık Hava Sahnesi',170.00),(4,4,'Bursa Sahne Sanatları Festivali','2024-12-23','Bursa, Merinos AKKM',190.00),(5,5,'Antalya Uluslararası Film Festivali','2024-12-24','Antalya, Aspendos Antik Tiyatrosu',210.00),(6,6,'Adana Altın Koza Film Festivali','2024-12-25','Adana, Adana Belediye Konservatuvarı',180.00),(7,7,'Gaziantep Mutlu Günler Festivali','2024-12-26','Gaziantep, Ali Nihat Tarlan Parkı',150.00),(8,8,'Konya Mevlana Festivali','2024-12-27','Konya, Mevlana Meydanı',200.00),(9,9,'Mersin Uluslararası Müzik Festivali','2024-12-28','Mersin, Mersin Kültür Merkezi',190.00);
/*!40000 ALTER TABLE `festivals` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 21:57:06
