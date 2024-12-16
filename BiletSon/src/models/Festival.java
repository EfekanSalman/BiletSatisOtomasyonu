package models;

import java.util.Date;
import java.math.BigDecimal;

public class Festival extends BaseEvent {
    private String festivalType;

    public Festival(int id, int cityId, String festivalType, Date eventDate, 
                   String location, BigDecimal price) {
        super(id, cityId, eventDate, location, price);
        this.festivalType = festivalType;
    }

    public String getFestivalType() { return festivalType; }
    public void setFestivalType(String festivalType) { this.festivalType = festivalType; }
}