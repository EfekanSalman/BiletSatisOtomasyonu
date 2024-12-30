package database;

import models.City;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityDB {
    private static Map<Integer, String> cityCache = new HashMap<>();
    private static Map<String, Integer> cityNameCache = new HashMap<>();
    
    public List<City> getAllCities() throws SQLException {
        List<City> cities = new ArrayList<>();
        String query = "SELECT * FROM Cities ORDER BY city_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                City city = new City(
                    rs.getInt("city_id"),
                    rs.getString("city_name")
                );
                cities.add(city);
                cityCache.put(city.getCityId(), city.getCityName());
                cityNameCache.put(city.getCityName(), city.getCityId());
            }
        }
        return cities;
    }
    
    public static String getCityName(int cityId) throws SQLException {
        if (cityCache.containsKey(cityId)) {
            return cityCache.get(cityId);
        }
        
        String query = "SELECT city_name FROM Cities WHERE city_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String cityName = rs.getString("city_name");
                    cityCache.put(cityId, cityName);
                    return cityName;
                }
            }
        }
        return "Unknown";
    }
    
    public City getCityById(int cityId) throws SQLException {
        String query = "SELECT * FROM Cities WHERE city_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new City(
                        rs.getInt("city_id"),
                        rs.getString("city_name")
                    );
                }
            }
        }
        return null;
    }

    public City getCityByName(String cityName) throws SQLException {
        if (cityNameCache.containsKey(cityName)) {
            int cityId = cityNameCache.get(cityName);
            return new City(cityId, cityName);
        }

        String query = "SELECT * FROM Cities WHERE city_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, cityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    City city = new City(
                        rs.getInt("city_id"),
                        rs.getString("city_name")
                    );
                    cityNameCache.put(cityName, city.getCityId());
                    return city;
                }
            }
        }
        return null;
    }
    
    public static void clearCache() {
        cityCache.clear();
        cityNameCache.clear();
    }
}