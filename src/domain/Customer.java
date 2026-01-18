package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
    private final String customerId;
    private String name;
    private String email;
    private String phone;
    private final List<Booking> bookings;
    
    /**
     * Creates a new Customer instance
     * @param customerId Unique identifier
     * @param name Customer name
     * @param email Email address
     * @param phone Phone number
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Customer(String customerId, String name, String email, String phone) {
        validateString(customerId, "Customer ID");
        validateString(name, "Customer name");
        validateEmail(email);
        validateString(phone, "Phone number");
        
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bookings = new ArrayList<>();
    }
    
    /**
     * Adds a booking for this customer
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
     * Gets all bookings for this customer
     * @return Unmodifiable list of bookings
     */
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }
    
    /**
     * Cancels a booking by ID
     * @param bookingId ID of booking to cancel
     * @return true if cancelled, false if not found
     */
    public boolean cancelBooking(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            return false;
        }
        
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                booking.cancel();
                return true;
            }
        }
        return false;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        validateString(name, "Customer name");
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        validateString(phone, "Phone number");
        this.phone = phone;
    }
    
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
    
    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @");
        }
    }
    
    @Override
    public String toString() {
        return String.format("Customer{id='%s', name='%s', email='%s', bookings=%d}", 
            customerId, name, email, bookings.size());
    }
}