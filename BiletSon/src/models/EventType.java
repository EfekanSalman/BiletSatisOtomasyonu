package models;

public enum EventType {
    ALL(1, "Tümü"),
    CONCERT(2, "Konserler"),
    FESTIVAL(3, "Festivaller"),
    STAGE(4, "Sahne"),
    MUSEUM(6, "Müzeler"),
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
        for (EventType type : values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Bilinmeyen etkinlik: " + displayName);
    }

    public static EventType fromId(int id) {
        for (EventType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Bilinmeyen etkinlik tipi: " + id);
    }
}