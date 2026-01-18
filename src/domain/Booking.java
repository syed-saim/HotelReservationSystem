package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private final String bookingId;
    private final Customer customer;
    private final Room room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final double totalPrice;
    private BookingStatus status;
    
    /**
     * Creates a new Booking instance
     * @param bookingId Unique identifier
     * @param customer Customer making the booking
     * @param room Room being booked
     * @param checkInDate Check-in date
     * @param checkOutDate Check-out date
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Booking(String bookingId, Customer customer, Room room, 
                   LocalDate checkInDate, LocalDate checkOutDate) {
        validateString(bookingId, "Booking ID");
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.equals(checkInDate)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
        
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = room.calculateTotalPrice(calculateNights());
        this.status = BookingStatus.PENDING;
    }
    
    /**
     * Calculates number of nights for this booking
     * @return Number of nights
     */
    public int calculateNights() {
        return (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    
    /**
     * Cancels this booking
     * @throws IllegalStateException if booking is already cancelled or completed
     */
    public void cancel() {
        if (status == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }
        if (status == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed booking");
        }
        this.status = BookingStatus.CANCELLED;
    }
    
    /**
     * Confirms this booking
     * @throws IllegalStateException if booking is cancelled or completed
     */
    public void confirm() {
        if (status == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Cannot confirm cancelled booking");
        }
        if (status == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Cannot confirm completed booking");
        }
        this.status = BookingStatus.CONFIRMED;
    }
    
    public String getBookingId() {
        return bookingId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public BookingStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookingStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }
    
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
    
    @Override
    public String toString() {
        return String.format("Booking{id='%s', customer='%s', room='%s', nights=%d, price=%.2f, status=%s}", 
            bookingId, customer.getName(), room.getRoomNumber(), calculateNights(), totalPrice, status);
    }
}
