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
-- Table structure for table `concerts`
--

DROP TABLE IF EXISTS `concerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concerts` (
  `concert_id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `concert_name` varchar(100) NOT NULL,
  `event_date` date NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`concert_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `concerts_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concerts`
--

LOCK TABLES `concerts` WRITE;
/*!40000 ALTER TABLE `concerts` DISABLE KEYS */;
INSERT INTO `concerts` VALUES (1,1,'Rock Fest 2024','2024-12-20','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',150.00),(2,2,'Jazz Night','2024-12-21','Ankara, Büyükkapı Sahnesi',120.00),(3,3,'Pop Müzik Konseri','2024-12-22','İzmir, Kültürpark Açık Hava Sahnesi',130.00),(4,4,'Sahne Konseri','2024-12-23','Bursa, Merinos AKKM',140.00),(5,5,'Klasik Müzik Gecesi','2024-12-24','Antalya, Aspendos Antik Tiyatrosu',160.00),(8,1,'Fontaines D.C.','2024-12-29','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',160.00),(9,2,'Padme','2024-12-30','Ankara, Büyükkapı Sahnesi',130.00),(10,3,'Lin Pesto','2024-12-31','İzmir, Kültürpark Açık Hava Sahnesi',140.00),(11,4,'Stormae','2024-12-30','Bursa, Merinos AKKM',150.00),(12,5,'Angèle','2024-12-31','Antalya, Aspendos Antik Tiyatrosu',170.00),(13,6,'Motive','2024-12-29','Adana, Atatürk Parkı',110.00),(14,7,'Fazıl Say','2024-12-30','Gaziantep, Kültür Merkezi',180.00),(15,8,'Hayko Cepkin','2024-12-31','Konya, Mevlana Kültür Merkezi',130.00),(16,9,'Evgeny Grinko','2024-12-29','Mersin, Mersin Arena',140.00),(17,1,'Hogwarts Senfoni Gösterisi','2024-12-29','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',200.00),(18,2,'Zakkum','2024-12-30','Ankara, Büyükkapı Sahnesi',130.00),(19,3,'Dedulüman','2024-12-31','İzmir, Kültürpark Açık Hava Sahnesi',110.00),(20,4,'Can Gox','2024-12-29','Bursa, Merinos AKKM',120.00),(21,5,'Yıldız Tilbe','2024-12-30','Antalya, Aspendos Antik Tiyatrosu',150.00),(22,6,'Nazan Öncel','2024-12-31','Adana, Seyhan',140.00),(23,7,'Hande Yener','2024-12-29','Gaziantep, Kültür Merkezi',130.00);
/*!40000 ALTER TABLE `concerts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 21:57:05
