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
-- Table structure for table `amusementparks`
--

DROP TABLE IF EXISTS `amusementparks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `amusementparks` (
  `park_id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `park_name` varchar(100) NOT NULL,
  `event_date` date NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`park_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `amusementparks_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amusementparks`
--

LOCK TABLES `amusementparks` WRITE;
/*!40000 ALTER TABLE `amusementparks` DISABLE KEYS */;
INSERT INTO `amusementparks` VALUES (1,1,'Wonderland','2024-12-20','İstanbul, Pendik',50.00),(2,2,'Adventure Land','2024-12-21','Ankara, Mamak',40.00),(3,3,'Fun Park','2024-12-22','İzmir, Konak',45.00),(4,4,'Magic World','2024-12-23','Bursa, Nilüfer',55.00),(5,5,'Happy Valley','2024-12-24','Antalya, Muratpaşa',60.00),(6,6,'Dream World','2024-12-25','Adana, Seyhan',55.00),(7,7,'Galaxy Park','2024-12-26','Gaziantep, Şahinbey',50.00),(8,8,'Wild Park','2024-12-27','Konya, Selçuklu',60.00),(9,9,'Rainbow Land','2024-12-28','Mersin, Toroslar',45.00);
/*!40000 ALTER TABLE `amusementparks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 21:57:03
