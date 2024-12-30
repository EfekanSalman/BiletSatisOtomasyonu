# BiletSatisOtomasyonu
Java tabanlı, Swing GUI arayüzü ile oluşturulmuş kapsamlı bir etkinlik bileti yönetim sistemi. Bu sistem, kullanıcıların konserler, müzeler, festivaller, sahne gösterileri ve eğlence parkları gibi çeşitli etkinliklere göz atmalarını, bilet satın almalarını ve yönetmelerini sağlar.

## 🌟 Temel Özellikler

### Kullanıcı Yönetimi
- Güvenli kullanıcı kaydı ve kimlik doğrulama
- Güvenlik soruları ile şifre sıfırlama işlevi
- Rol tabanlı erişim kontrolü (Yönetici/Müşteri)
- Profil yönetimi ve hesap ayarları

### Etkinlik Yönetimi
- Çoklu etkinlik kategorileri:
  - 🎵 Konserler
  - 🏛️ Müzeler
  - 🎪 Festivaller
  - 🎭 Sahne Gösterileri
  - 🎡 Eğlence Parkları
- Gelişmiş etkinlik filtreleme:
  - Şehir tarafından
  - Etkinlik türüne göre
  - Fiyata göre (artan/azalan)
- Ayrıntılı etkinlik bilgileri

### Bilet İşlemleri
- Güvenli bilet satın alma
- Dijital bilet yönetimi
- Satın alma geçmişi takibi
- Bilet iptali

### Yönetici Özellikleri
- Kullanıcı yönetimi paneli
- Etkinlik CRUD işlemleri
- Sistem izleme
- Rol yönetimi

## 🛠️ Teknik Yapı

- Dil:** Java
- **GUI Çerçevesi:** Swing
- Veritabanı:** MySQL
- Mimari:** MVC Modeli
- E-posta Hizmeti:** JavaMail API


## 🔧 Kurulum

1. Depoyu klonlama
```bash
git clone https://github.com/EfekanSalman/BiletSatisOtomasyonu.git
```

2. MySQL veritabanını ayarlama
```sql
CREATE DATABASE ticketFinal;
```

3. Veritabanı bağlantısını `src/database/DatabaseConnection.java` içinde yapılandırın
```java
private static final String URL = “jdbc:mysql://localhost:3306/ticketFinal”;
private static final String USER = “ismin”;
private static final String PASSWORD = “şifren”;
```

4. Uygulamayı çalıştırma
```bash
java -jar Bilet.jar
```

## 📋 Ön Koşullar

- Java JDK 8 veya üstü
- MySQL 5.7 veya üstü
- IDE (Eclipse/IntelliJ IDEA önerilir)

## 🎯 Proje Yapısı

```
src/
├── database/ # Veritabanı bağlantısı ve işlemleri
├── models/ # Veri modelleri ve varlıkları
├── gui/ # Swing GUI bileşenleri
├── services/ # İş mantığı ve hizmetler
└── utils/ # Yardımcı sınıflar ve yardımcılar
```

## 🔐 Güvenlik Özellikleri

- Güvenli şifre kullanımı
- Girdi doğrulama
- SQL enjeksiyonu önleme
- Oturum yönetimi


## 📝 Lisans

Bu proje MIT Lisansı altında lisanslanmıştır - ayrıntılar için [LICENSE](LICENSE) dosyasına bakın.

---
⭐️ Bu projeyi yararlı bulduysanız, lütfen bir yıldız vermeyi unutmayın :)
