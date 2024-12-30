package models;

import java.util.Date;
import java.math.BigDecimal;

public class Concert extends BaseEvent {
    private String concertName;

    public Concert(int id, int cityId, String concertName, Date eventDate, 
                  String location, BigDecimal price, int capacity) {
        super(id, cityId, eventDate, location, price, capacity);
        this.concertName = concertName;
    }

    public String getConcertName() { return concertName; }
    public void setConcertName(String concertName) { this.concertName = concertName; }
}
