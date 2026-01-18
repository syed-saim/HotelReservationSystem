package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Hotel domain class
 */
public class HotelTest {
    
    private Hotel hotel;
    private Room room1;
    private Room room2;
    
    @BeforeEach
    public void setUp() {
        hotel = new Hotel("H001", "Grand Hotel", "123 Main Street");
        room1 = new Room("R001", "101", RoomType.DOUBLE, 150.0, 2);
        room2 = new Room("R002", "102", RoomType.SUITE, 250.0, 4);
    }
    
    // ========== Constructor Tests ==========
    
    @Test
    public void testConstructor_ValidParameters_CreatesHotel() {
        // Arrange & Act
        Hotel h = new Hotel("H002", "Test Hotel", "456 Test Ave");
        
        // Assert
        assertEquals("H002", h.getHotelId());
        assertEquals("Test Hotel", h.getName());
        assertEquals("456 Test Ave", h.getAddress());
        assertEquals(0, h.getRooms().size());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t"})
    public void testConstructor_InvalidHotelId_ThrowsException(String hotelId) {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Hotel(hotelId, "Test", "Address");
        });
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    public void testConstructor_InvalidName_ThrowsException(String name) {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Hotel("H001", name, "Address");
        });
    }
    
    // ========== Add Room Tests ==========
    
    @Test
    public void testAddRoom_ValidRoom_RoomAdded() {
        // Arrange
        // (setUp provides hotel and room1)
        
        // Act
        hotel.addRoom(room1);
        
        // Assert
        assertEquals(1, hotel.getRooms().size());
        assertEquals(room1, hotel.getRoom("R001"));
    }
    
    @Test
    public void testAddRoom_MultipleRooms_AllRoomsAdded() {
        // Arrange & Act
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        
        // Assert
        assertEquals(2, hotel.getRooms().size());
    }
    
    @Test
    public void testAddRoom_NullRoom_ThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.addRoom(null);
        });
    }
    
    @Test
    public void testAddRoom_DuplicateRoomId_ThrowsException() {
        // Arrange
        hotel.addRoom(room1);
        Room duplicate = new Room("R001", "105", RoomType.SINGLE, 100.0, 1);
        
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.addRoom(duplicate);
        });
    }
    
    // ========== Remove Room Tests ==========
    
    @Test
    public void testRemoveRoom_ExistingRoom_ReturnsTrue() {
        // Arrange
        hotel.addRoom(room1);
        
        // Act
        boolean result = hotel.removeRoom("R001");
        
        // Assert
        assertTrue(result);
        assertEquals(0, hotel.getRooms().size());
    }
    
    @Test
    public void testRemoveRoom_NonExistingRoom_ReturnsFalse() {
        // Arrange & Act
        boolean result = hotel.removeRoom("R999");
        
        // Assert
        assertFalse(result);
    }
    
    // ========== Find Available Rooms Tests ==========
    
    @Test
    public void testFindAvailableRooms_NoBookings_AllRoomsAvailable() {
        // Arrange
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        LocalDate checkIn = LocalDate.of(2025, 2, 1);
        LocalDate checkOut = LocalDate.of(2025, 2, 5);
        
        // Act
        var availableRooms = hotel.findAvailableRooms(checkIn, checkOut);
        
        // Assert
        assertEquals(2, availableRooms.size());
    }
    
    @Test
    public void testFindAvailableRooms_InvalidDates_ThrowsException() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2025, 2, 5);
        LocalDate checkOut = LocalDate.of(2025, 2, 1);
        
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.findAvailableRooms(checkIn, checkOut);
        });
    }
    
    @Test
    public void testFindAvailableRooms_NullDates_ThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            hotel.findAvailableRooms(null, LocalDate.now());
        });
    }
}
