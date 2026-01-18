package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.Booking;
import domain.Customer;
import domain.Room;
import domain.RoomType;

import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    
    private Room room;
    private Customer customer;
    
    @BeforeEach
    public void setUp() {
        room = new Room("R001", "101", RoomType.DOUBLE, 150.0, 2);
        customer = new Customer("C001", "John Doe", "john@example.com", "123-456-7890");
    }
    
    // ========== Constructor Tests ==========
    
    @Test
    public void testConstructor_ValidParameters_CreatesRoom() {
        // Arrange & Act
        Room r = new Room("R002", "102", RoomType.SUITE, 250.0, 4);
        
        // Assert
        assertEquals("R002", r.getRoomId());
        assertEquals("102", r.getRoomNumber());
        assertEquals(RoomType.SUITE, r.getType());
        assertEquals(250.0, r.getPricePerNight());
        assertEquals(4, r.getCapacity());
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.0})
    public void testConstructor_InvalidPrice_ThrowsException(double price) {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Room("R001", "101", RoomType.DOUBLE, price, 2);
        });
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    public void testConstructor_InvalidCapacity_ThrowsException(int capacity) {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Room("R001", "101", RoomType.DOUBLE, 150.0, capacity);
        });
    }
    
    // ========== Availability Tests ==========
    
    @Test
    public void testIsAvailable_NoBookings_ReturnsTrue() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2025, 2, 1);
        LocalDate checkOut = LocalDate.of(2025, 2, 5);
        
        // Act
        boolean available = room.isAvailable(checkIn, checkOut);
        
        // Assert
        assertTrue(available);
    }
    
    @Test
    public void testIsAvailable_WithOverlappingBooking_ReturnsFalse() {
        // Arrange
        LocalDate checkIn1 = LocalDate.of(2025, 2, 1);
        LocalDate checkOut1 = LocalDate.of(2025, 2, 5);
        Booking booking = new Booking("B001", customer, room, checkIn1, checkOut1);
        booking.confirm();
        room.addBooking(booking);
        
        LocalDate checkIn2 = LocalDate.of(2025, 2, 3);
        LocalDate checkOut2 = LocalDate.of(2025, 2, 7);
        
        // Act
        boolean available = room.isAvailable(checkIn2, checkOut2);
        
        // Assert
        assertFalse(available);
    }
    
    @Test
    public void testIsAvailable_WithNonOverlappingBooking_ReturnsTrue() {
        // Arrange
        LocalDate checkIn1 = LocalDate.of(2025, 2, 1);
        LocalDate checkOut1 = LocalDate.of(2025, 2, 5);
        Booking booking = new Booking("B001", customer, room, checkIn1, checkOut1);
        booking.confirm();
        room.addBooking(booking);
        
        LocalDate checkIn2 = LocalDate.of(2025, 2, 10);
        LocalDate checkOut2 = LocalDate.of(2025, 2, 15);
        
        // Act
        boolean available = room.isAvailable(checkIn2, checkOut2);
        
        // Assert
        assertTrue(available);
    }
    
    @Test
    public void testIsAvailable_WithCancelledBooking_ReturnsTrue() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2025, 2, 1);
        LocalDate checkOut = LocalDate.of(2025, 2, 5);
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        booking.cancel();
        room.addBooking(booking);
        
        // Act
        boolean available = room.isAvailable(checkIn, checkOut);
        
        // Assert
        assertTrue(available);
    }
    
    // ========== Calculate Total Price Tests ==========
    
    @ParameterizedTest
    @CsvSource({
        "1, 150.0",
        "2, 300.0",
        "7, 1050.0",
        "30, 4500.0"
    })
    public void testCalculateTotalPrice_ValidNights_ReturnsCorrectPrice(int nights, double expected) {
        // Act
        double total = room.calculateTotalPrice(nights);
        
        // Assert
        assertEquals(expected, total, 0.01);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    public void testCalculateTotalPrice_InvalidNights_ThrowsException(int nights) {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            room.calculateTotalPrice(nights);
        });
    }
}
