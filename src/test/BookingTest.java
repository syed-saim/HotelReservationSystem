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

public class BookingTest {
    
    private Customer customer;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    
    @BeforeEach
    public void setUp() {
        customer = new Customer("C001", "John Doe", "john@example.com", "123-456-7890");
        room = new Room("R001", "101", RoomType.DOUBLE, 150.0, 2);
        checkIn = LocalDate.of(2025, 2, 1);
        checkOut = LocalDate.of(2025, 2, 5);
    }
    
    // ========== Constructor Tests ==========
    
    @Test
    public void testConstructor_ValidParameters_CreatesBooking() {
        // Arrange & Act
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        
        // Assert
        assertEquals("B001", booking.getBookingId());
        assertEquals(customer, booking.getCustomer());
        assertEquals(room, booking.getRoom());
        assertEquals(checkIn, booking.getCheckInDate());
        assertEquals(checkOut, booking.getCheckOutDate());
        assertEquals(BookingStatus.PENDING, booking.getStatus());
    }
    
    @Test
    public void testConstructor_NullCustomer_ThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Booking("B001", null, room, checkIn, checkOut);
        });
    }
    
    @Test
    public void testConstructor_NullRoom_ThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Booking("B001", customer, null, checkIn, checkOut);
        });
    }
    
    @Test
    public void testConstructor_InvalidDates_ThrowsException() {
        // Arrange
        LocalDate invalidCheckOut = LocalDate.of(2025, 1, 31);
        
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Booking("B001", customer, room, checkIn, invalidCheckOut);
        });
    }
    
    // ========== Calculate Nights Tests ==========
    
    @ParameterizedTest
    @CsvSource({
        "2025-02-01, 2025-02-02, 1",
        "2025-02-01, 2025-02-05, 4",
        "2025-02-01, 2025-02-15, 14",
        "2025-02-01, 2025-03-01, 28"
    })
    public void testCalculateNights_VariousDates_ReturnsCorrectNights(String checkInStr, String checkOutStr, int expected) {
        // Arrange
        LocalDate checkIn = LocalDate.parse(checkInStr);
        LocalDate checkOut = LocalDate.parse(checkOutStr);
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        
        // Act
        int nights = booking.calculateNights();
        
        // Assert
        assertEquals(expected, nights);
    }
    
    // ========== Status Management Tests ==========
    
    @Test
    public void testConfirm_PendingBooking_StatusChangedToConfirmed() {
        // Arrange
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        
        // Act
        booking.confirm();
        
        // Assert
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
    }
    
    @Test
    public void testCancel_PendingBooking_StatusChangedToCancelled() {
        // Arrange
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        
        // Act
        booking.cancel();
        
        // Assert
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    }
    
    @Test
    public void testCancel_AlreadyCancelled_ThrowsException() {
        // Arrange
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        booking.cancel();
        
        // Assert
        assertThrows(IllegalStateException.class, () -> {
            booking.cancel();
        });
    }
    
    @Test
    public void testConfirm_CancelledBooking_ThrowsException() {
        // Arrange
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        booking.cancel();
        
        // Assert
        assertThrows(IllegalStateException.class, () -> {
            booking.confirm();
        });
    }
    
    // ========== Total Price Tests ==========
    
    @Test
    public void testGetTotalPrice_FourNights_ReturnsCorrectPrice() {
        // Arrange
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        
        // Act
        double totalPrice = booking.getTotalPrice();
        
        // Assert
        assertEquals(600.0, totalPrice, 0.01); // 4 nights * 150 per night
    }
}