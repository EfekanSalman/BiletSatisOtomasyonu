package models;

import java.math.BigDecimal;
import java.util.Date;

public class Ticket {
    private int ticketId;
    private int userId;
    private int eventTypeId;
    private int eventId;
    private Date purchaseDate;
    private BigDecimal price;

    public Ticket(int ticketId, int userId, int eventTypeId, int eventId, Date purchaseDate, BigDecimal price) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.eventTypeId = eventTypeId;
        this.eventId = eventId;
        this.purchaseDate = purchaseDate;
        this.price = price;
    }

    public int getTicketId() { return ticketId; }
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getEventTypeId() { return eventTypeId; }
    public void setEventTypeId(int eventTypeId) { this.eventTypeId = eventTypeId; }
    
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    
    public Date getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}