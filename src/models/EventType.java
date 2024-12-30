package models;

public enum EventType {
    ALL(1, "Tümü"),
    CONCERT(2, "Konser"),
    FESTIVAL(3, "Festival"),
    STAGE(4, "Sahne"),
    MUSEUM(6, "Müze"),
    AMUSEMENT_PARK(7, "Lunapark");

    private final int id;
    private final String displayName;

    EventType(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EventType fromDisplayName(String displayName) {
        if (displayName == null) {
            throw new IllegalArgumentException("Display name cannot be null");
        }
        
        String normalizedInput = displayName.trim();
        
        for (EventType type : values()) {
            if (type.displayName.equalsIgnoreCase(normalizedInput)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + displayName);
    }

    public static EventType fromId(int id) {
        for (EventType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type ID: " + id);
    }
}