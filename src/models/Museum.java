package models;

import java.util.Date;
import java.math.BigDecimal;

public class Museum extends BaseEvent {
    private String museumName;

    public Museum(int id, int cityId, String museumName, Date eventDate, 
                 String location, BigDecimal price, int capacity) {
        super(id, cityId, eventDate, location, price, capacity);
        this.museumName = museumName;
    }

    public String getMuseumName() { return museumName; }
    public void setMuseumName(String museumName) { this.museumName = museumName; }
}
