package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private final String roomId;
    private String roomNumber;
    private RoomType type;
    private double pricePerNight;
    private int capacity;
    private final List<Booking> bookings;
    
    /**
     * Creates a new Room instance
     * @param roomId Unique identifier for the room
     * @param roomNumber Display number for the room
     * @param type Type of room
     * @param pricePerNight Price per night
     * @param capacity Maximum occupancy
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Room(String roomId, String roomNumber, RoomType type, double pricePerNight, int capacity) {
        validateString(roomId, "Room ID");
        validateString(roomNumber, "Room number");
        if (type == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price per night must be positive");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.bookings = new ArrayList<>();
    }
    
    /**
     * Checks if room is available for given dates
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     * @return true if available, false otherwise
     */
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
        
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.CONFIRMED || 
                booking.getStatus() == BookingStatus.PENDING) {
                if (datesOverlap(checkIn, checkOut, booking.getCheckInDate(), booking.getCheckOutDate())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Adds a booking to this room
     * @param booking The booking to add
     * @throws IllegalArgumentException if booking is null
     */
    public void addBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        bookings.add(booking);
    }
    
    /**
     * Calculates total price for given number of nights
     * @param nights Number of nights
     * @return Total price
     * @throws IllegalArgumentException if nights is not positive
     */
    public double calculateTotalPrice(int nights) {
        if (nights <= 0) {
            throw new IllegalArgumentException("Nights must be positive");
        }
        return pricePerNight * nights;
    }
    
    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !end1.isBefore(start2) && !start1.isAfter(end2);
    }
    
    public String getRoomId() {
        return roomId;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        validateString(roomNumber, "Room number");
        this.roomNumber = roomNumber;
    }
    
    public RoomType getType() {
        return type;
    }
    
    public void setType(RoomType type) {
        if (type == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        this.type = type;
    }
    
    public double getPricePerNight() {
        return pricePerNight;
    }
    
    public void setPricePerNight(double pricePerNight) {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.pricePerNight = pricePerNight;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }
    
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }
    
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
    
    @Override
    public String toString() {
        return String.format("Room{id='%s', number='%s', type=%s, price=%.2f, capacity=%d}", 
            roomId, roomNumber, type, pricePerNight, capacity);
    }
}
