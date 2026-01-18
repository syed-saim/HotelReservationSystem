package domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a hotel with rooms and provides room management functionality
 */
public class Hotel {
    private final String hotelId;
    private String name;
    private String address;
    private final List<Room> rooms;
    
    /**
     * Creates a new Hotel instance
     * @param hotelId Unique identifier for the hotel
     * @param name Name of the hotel
     * @param address Physical address of the hotel
     * @throws IllegalArgumentException if any parameter is null or empty
     */
    public Hotel(String hotelId, String name, String address) {
        validateString(hotelId, "Hotel ID");
        validateString(name, "Hotel name");
        validateString(address, "Hotel address");
        
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.rooms = new ArrayList<>();
    }
    
    /**
     * Adds a room to the hotel
     * @param room The room to add
     * @throws IllegalArgumentException if room is null or duplicate room ID
     */
    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        
        if (getRoom(room.getRoomId()) != null) {
            throw new IllegalArgumentException("Room with ID " + room.getRoomId() + " already exists");
        }
        
        rooms.add(room);
    }
    
    /**
     * Removes a room from the hotel
     * @param roomId ID of the room to remove
     * @return true if room was removed, false if not found
     */
    public boolean removeRoom(String roomId) {
        validateString(roomId, "Room ID");
        return rooms.removeIf(room -> room.getRoomId().equals(roomId));
    }
    
    /**
     * Finds all available rooms for given dates
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     * @return List of available rooms
     * @throws IllegalArgumentException if dates are null or invalid
     */
    public List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        validateDates(checkIn, checkOut);
        
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable(checkIn, checkOut)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    
    /**
     * Gets a room by its ID
     * @param roomId The room ID to search for
     * @return The room if found, null otherwise
     */
    public Room getRoom(String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            return null;
        }
        
        for (Room room : rooms) {
            if (room.getRoomId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
    
    public String getHotelId() {
        return hotelId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        validateString(name, "Hotel name");
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        validateString(address, "Hotel address");
        this.address = address;
    }
    
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }
    
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
    
    private void validateDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }
    
    @Override
    public String toString() {
        return String.format("Hotel{id='%s', name='%s', address='%s', rooms=%d}", 
            hotelId, name, address, rooms.size());
    }
}