package models;

import java.util.Date;
import java.math.BigDecimal;

public abstract class BaseEvent {
    private int id;
    private int cityId;
    private Date eventDate;
    private String location;
    private BigDecimal price;

    public BaseEvent(int id, int cityId, Date eventDate, String location, BigDecimal price) {
        this.id = id;
        this.cityId = cityId;
        this.eventDate = eventDate;
        this.location = location;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }
    
    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}