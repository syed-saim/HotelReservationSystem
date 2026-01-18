package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.Booking;
import domain.BookingStatus;
import domain.Customer;
import domain.Room;
import domain.RoomType;

import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    
    private Customer customer;
    
    @BeforeEach
    public void setUp() {
        customer = new Customer("C001", "John Doe", "john@example.com", "123-456-7890");
    }
    
    // ========== Constructor Tests ==========
    
    @Test
    public void testConstructor_ValidParameters_CreatesCustomer() {
        // Arrange & Act
        Customer c = new Customer("C002", "Jane Smith", "jane@test.com", "555-1234");
        
        // Assert
        assertEquals("C002", c.getCustomerId());
        assertEquals("Jane Smith", c.getName());
        assertEquals("jane@test.com", c.getEmail());
        assertEquals("555-1234", c.getPhone());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    public void testConstructor_InvalidName_ThrowsException(String name) {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("C001", name, "email@test.com", "123-456");
        });
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "invalid-email", "test.com"})
    public void testConstructor_InvalidEmail_ThrowsException(String email) {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("C001", "John", email, "123-456");
        });
    }
    
    // ========== Booking Management Tests ==========
    
    @Test
    public void testAddBooking_ValidBooking_BookingAdded() {
        // Arrange
        Room room = new Room("R001", "101", RoomType.DOUBLE, 150.0, 2);
        LocalDate checkIn = LocalDate.of(2025, 2, 1);
        LocalDate checkOut = LocalDate.of(2025, 2, 5);
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        
        // Act
        customer.addBooking(booking);
        
        // Assert
        assertEquals(1, customer.getBookings().size());
        assertTrue(customer.getBookings().contains(booking));
    }
    
    @Test
    public void testAddBooking_NullBooking_ThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            customer.addBooking(null);
        });
    }
    
    @Test
    public void testCancelBooking_ExistingBooking_ReturnsTrue() {
        // Arrange
        Room room = new Room("R001", "101", RoomType.DOUBLE, 150.0, 2);
        LocalDate checkIn = LocalDate.of(2025, 2, 1);
        LocalDate checkOut = LocalDate.of(2025, 2, 5);
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        customer.addBooking(booking);
        
        // Act
        boolean result = customer.cancelBooking("B001");
        
        // Assert
        assertTrue(result);
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    }
    
    @Test
    public void testCancelBooking_NonExistingBooking_ReturnsFalse() {
        // Act
        boolean result = customer.cancelBooking("B999");
        
        // Assert
        assertFalse(result);
    }
}
