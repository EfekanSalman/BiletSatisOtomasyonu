package models;

import java.util.Date;
import java.math.BigDecimal;

public class AmusementPark extends BaseEvent {
    private String parkName;

    public AmusementPark(int id, int cityId, String parkName, Date eventDate, 
                        String location, BigDecimal price) {
        super(id, cityId, eventDate, location, price);
        this.parkName = parkName;
    }

    public String getParkName() { return parkName; }
    public void setParkName(String parkName) { this.parkName = parkName; }
}