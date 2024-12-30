package models;

import java.util.Date;
import java.math.BigDecimal;

public class Stage extends BaseEvent {
    private String stageType;
    private String eventName;

    public Stage(int id, int cityId, String stageType, String eventName, 
                Date eventDate, String location, BigDecimal price, int capacity) {
        super(id, cityId, eventDate, location, price, capacity);
        this.stageType = stageType;
        this.eventName = eventName;
    }

    public String getStageType() { return stageType; }
    public void setStageType(String stageType) { this.stageType = stageType; }
    
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
}
