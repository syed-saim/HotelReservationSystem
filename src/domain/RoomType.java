package domain;

public enum RoomType {
    SINGLE(1, "Single Room"),
    DOUBLE(2, "Double Room"),
    SUITE(4, "Suite"),
    DELUXE(3, "Deluxe Room");
    
    private final int standardCapacity;
    private final String displayName;
    
    RoomType(int standardCapacity, String displayName) {
        this.standardCapacity = standardCapacity;
        this.displayName = displayName;
    }
    
    public int getStandardCapacity() {
        return standardCapacity;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}