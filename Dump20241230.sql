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
  `capacity` int DEFAULT NULL,
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
INSERT INTO `amusementparks` VALUES (1,1,'Wonderland','2024-12-20','İstanbul, Pendik',50.00,200),(2,2,'Adventure Land','2024-12-21','Ankara, Mamak',40.00,150),(3,3,'Fun Park','2024-12-22','İzmir, Konak',45.00,180),(4,4,'Magic World','2024-12-23','Bursa, Nilüfer',55.00,250),(5,5,'Happy Valley','2024-12-24','Antalya, Muratpaşa',60.00,300),(6,6,'Dream World','2024-12-25','Adana, Seyhan',55.00,220),(7,7,'Galaxy Park','2024-12-26','Gaziantep, Şahinbey',50.00,210),(8,8,'Wild Park','2024-12-27','Konya, Selçuklu',60.00,240),(9,9,'Rainbow Land','2024-12-28','Mersin, Toroslar',45.00,270);
/*!40000 ALTER TABLE `amusementparks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities` (
  `city_id` int NOT NULL AUTO_INCREMENT,
  `city_name` varchar(100) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'İstanbul'),(2,'Ankara'),(3,'İzmir'),(4,'Bursa'),(5,'Antalya'),(6,'Adana'),(7,'Gaziantep'),(8,'Konya'),(9,'Mersin'),(10,'Kayseri');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

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
  `capacity` int DEFAULT NULL,
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
INSERT INTO `concerts` VALUES (1,1,'Rock Fest 2024','2024-12-20','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',150.00,3000),(2,2,'Jazz Night','2024-12-21','Ankara, Büyükkapı Sahnesi',120.00,2500),(3,3,'Pop Müzik Konseri','2024-12-22','İzmir, Kültürpark Açık Hava Sahnesi',130.00,2800),(4,4,'Sahne Konseri','2024-12-23','Bursa, Merinos AKKM',140.00,2700),(5,5,'Klasik Müzik Gecesi','2024-12-24','Antalya, Aspendos Antik Tiyatrosu',160.00,3200),(8,1,'Fontaines D.C.','2024-12-29','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',160.00,3000),(9,2,'Padme','2024-12-30','Ankara, Büyükkapı Sahnesi',130.00,2500),(10,3,'Lin Pesto','2024-12-31','İzmir, Kültürpark Açık Hava Sahnesi',140.00,2800),(11,4,'Stormae','2024-12-30','Bursa, Merinos AKKM',150.00,2700),(12,5,'Angèle','2024-12-31','Antalya, Aspendos Antik Tiyatrosu',170.00,3200),(13,6,'Motive','2024-12-29','Adana, Atatürk Parkı',110.00,3000),(14,7,'Fazıl Say','2024-12-30','Gaziantep, Kültür Merkezi',180.00,2500),(15,8,'Hayko Cepkin','2024-12-31','Konya, Mevlana Kültür Merkezi',130.00,2800),(16,9,'Evgeny Grinko','2024-12-29','Mersin, Mersin Arena',140.00,2700),(17,1,'Hogwarts Senfoni Gösterisi','2024-12-29','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',200.00,3200),(18,2,'Zakkum','2024-12-30','Ankara, Büyükkapı Sahnesi',130.00,2500),(19,3,'Dedulüman','2024-12-31','İzmir, Kültürpark Açık Hava Sahnesi',110.00,2800),(20,4,'Can Gox','2024-12-29','Bursa, Merinos AKKM',120.00,2700),(21,5,'Yıldız Tilbe','2024-12-30','Antalya, Aspendos Antik Tiyatrosu',150.00,3200),(22,6,'Nazan Öncel','2024-12-31','Adana, Seyhan',140.00,3000),(23,7,'Hande Yener','2024-12-29','Gaziantep, Kültür Merkezi',130.00,2500);
/*!40000 ALTER TABLE `concerts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventtypes`
--

DROP TABLE IF EXISTS `eventtypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventtypes` (
  `event_type_id` int NOT NULL AUTO_INCREMENT,
  `event_type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`event_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventtypes`
--

LOCK TABLES `eventtypes` WRITE;
/*!40000 ALTER TABLE `eventtypes` DISABLE KEYS */;
INSERT INTO `eventtypes` VALUES (1,'Spor'),(2,'Konser'),(3,'Festival'),(4,'Sahne'),(5,'Ulaşım'),(6,'Müze'),(7,'Lunapark');
/*!40000 ALTER TABLE `eventtypes` ENABLE KEYS */;
UNLOCK TABLES;

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
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`festival_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `festivals_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `festivals`
--

LOCK TABLES `festivals` WRITE;
/*!40000 ALTER TABLE `festivals` DISABLE KEYS */;
INSERT INTO `festivals` VALUES (1,1,'İstanbul Film Festivali','2024-12-20','İstanbul, Harbiye Cemil Topuzlu Açıkhava Tiyatrosu',200.00,5000),(2,2,'Ankara Uluslararası Tiyatro Festivali','2024-12-21','Ankara, Atakule',180.00,4500),(3,3,'İzmir Uluslararası Müzik Festivali','2024-12-22','İzmir, Kültürpark Açık Hava Sahnesi',170.00,4700),(4,4,'Bursa Sahne Sanatları Festivali','2024-12-23','Bursa, Merinos AKKM',190.00,4800),(5,5,'Antalya Uluslararası Film Festivali','2024-12-24','Antalya, Aspendos Antik Tiyatrosu',210.00,5200),(6,6,'Adana Altın Koza Film Festivali','2024-12-25','Adana, Adana Belediye Konservatuvarı',180.00,5000),(7,7,'Gaziantep Mutlu Günler Festivali','2024-12-26','Gaziantep, Ali Nihat Tarlan Parkı',150.00,4800),(8,8,'Konya Mevlana Festivali','2024-12-27','Konya, Mevlana Meydanı',200.00,5100),(9,9,'Mersin Uluslararası Müzik Festivali','2024-12-28','Mersin, Mersin Kültür Merkezi',190.00,4900),(10,2,'Anqara Şehir Festivali','2025-01-02','ÇinÇin',150.00,250);
/*!40000 ALTER TABLE `festivals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `museums`
--

DROP TABLE IF EXISTS `museums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `museums` (
  `museum_id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `museum_name` varchar(100) NOT NULL,
  `event_date` date NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`museum_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `museums_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `museums`
--

LOCK TABLES `museums` WRITE;
/*!40000 ALTER TABLE `museums` DISABLE KEYS */;
INSERT INTO `museums` VALUES (1,1,'İstanbul Modern','2024-12-20','İstanbul, Beyoğlu',30.00,1000),(2,2,'Ankara Resim ve Heykel Müzesi','2024-12-21','Ankara, Ulus',25.00,800),(3,3,'İzmir Arkeoloji Müzesi','2024-12-22','İzmir, Konak',35.00,1200),(4,4,'Bursa Kent Müzesi','2024-12-23','Bursa, Osmangazi',20.00,900),(5,5,'Antalya Arkeoloji Müzesi','2024-12-24','Antalya, Konyaaltı',40.00,1100),(6,6,'Adana Taşköprü Müzesi','2024-12-25','Adana, Seyhan',30.00,1000),(7,7,'Gaziantep Zeugma Mozaik Müzesi','2024-12-26','Gaziantep, Belkıs',35.00,1200),(8,8,'Konya Mevlana Müzesi','2024-12-27','Konya, Mevlana Meydanı',40.00,1100),(9,9,'Mersin Arkeoloji Müzesi','2024-12-28','Mersin, Yenişehir',25.00,950),(10,4,'X etkinliği','2025-01-12','XY merkezi',550.00,NULL);
/*!40000 ALTER TABLE `museums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `securityquestions`
--

DROP TABLE IF EXISTS `securityquestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `securityquestions` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `question_text` varchar(255) NOT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `securityquestions`
--

LOCK TABLES `securityquestions` WRITE;
/*!40000 ALTER TABLE `securityquestions` DISABLE KEYS */;
INSERT INTO `securityquestions` VALUES (1,'Annenizin kızlık soyadı nedir?'),(2,'En sevdiğiniz renk nedir?'),(3,'İlk okulunuzun adı nedir?'),(4,'En sevdiğiniz hayvan nedir?');
/*!40000 ALTER TABLE `securityquestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sports`
--

DROP TABLE IF EXISTS `sports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sports` (
  `sport_id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `sport_type` varchar(50) NOT NULL,
  `event_date` date NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `event_name` varchar(100) NOT NULL,
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`sport_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `sports_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sports`
--

LOCK TABLES `sports` WRITE;
/*!40000 ALTER TABLE `sports` DISABLE KEYS */;
/*!40000 ALTER TABLE `sports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stages`
--

DROP TABLE IF EXISTS `stages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stages` (
  `stage_id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `stage_type` varchar(50) NOT NULL,
  `event_date` date NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `event_name` varchar(100) NOT NULL,
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`stage_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `stages_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stages`
--

LOCK TABLES `stages` WRITE;
/*!40000 ALTER TABLE `stages` DISABLE KEYS */;
INSERT INTO `stages` VALUES (1,1,'Tiyatro','2024-12-20','İstanbul, Şehir Tiyatroları',100.00,'Macbeth',500),(2,2,'Konser','2024-12-21','Ankara, Büyükkapı Sahnesi',120.00,'Caz Gecesi',600),(3,3,'Dans','2024-12-22','İzmir, Kültürpark',90.00,'Ballet Performance',700),(4,4,'Tiyatro','2024-12-23','Bursa, Merinos AKKM',110.00,'Romeo ve Juliet',650),(5,5,'Konser','2024-12-24','Antalya, Aspendos Antik Tiyatrosu',130.00,'Klasik Müzik Konseri',800),(6,6,'Konser','2024-12-25','Adana, Atatürk Parkı',120.00,'Folk Music Performance',500),(7,7,'Tiyatro','2024-12-26','Gaziantep, Gaziantep Kültür Merkezi',100.00,'Gaziantep Sahnesi',600),(8,8,'Dans','2024-12-27','Konya, Mevlana Kültür Merkezi',110.00,'Whirling Dervishes Performance',700),(9,9,'Konser','2024-12-28','Mersin, Mersin Arena',130.00,'Mersin Rock Fest',650);
/*!40000 ALTER TABLE `stages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `event_type_id` int DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  `purchase_date` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `user_id` (`user_id`),
  KEY `event_type_id` (`event_type_id`),
  CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `tickets_ibfk_2` FOREIGN KEY (`event_type_id`) REFERENCES `eventtypes` (`event_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (2,1,6,1,'2024-12-12 18:32:46',30.00),(3,1,3,1,'2024-12-12 18:32:53',200.00),(4,1,2,5,'2024-12-12 18:33:00',160.00),(5,1,6,1,'2024-12-12 18:33:05',30.00),(6,1,2,4,'2024-12-12 18:38:15',140.00),(12,1,6,10,'2024-12-16 23:15:47',450.00),(13,1,6,3,'2024-12-24 16:01:48',35.00),(14,1,6,3,'2024-12-24 16:01:48',35.00),(15,3,4,1,'2024-12-27 23:00:35',100.00),(16,3,4,1,'2024-12-27 23:00:35',100.00),(17,3,4,1,'2024-12-27 23:00:35',100.00),(18,1,2,18,'2024-12-30 17:05:17',130.00),(19,1,2,18,'2024-12-30 17:05:17',130.00),(20,1,2,18,'2024-12-30 17:05:17',130.00),(21,1,2,18,'2024-12-30 17:05:17',130.00);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `security_question_id` int DEFAULT NULL,
  `security_answer` varchar(255) DEFAULT NULL,
  `card_number` varchar(16) NOT NULL,
  `cvv` varchar(3) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` enum('Admin','Customer') NOT NULL DEFAULT 'Customer',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `security_question_id` (`security_question_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`security_question_id`) REFERENCES `securityquestions` (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Efekan','Salman','Efe','1234',2,'yeşil','1234567890123456','554','efekansalman@gmail.com','Customer'),(2,'Admin','Admin','Admin','Admin',1,'Admin','1234567890654321','231','Admin@gmail.com','Admin'),(3,'Ahmet','veli','Ahmet','123',1,'e','1234567890323232','424','Ahmet@gmail.com','Customer');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-30 22:28:33
