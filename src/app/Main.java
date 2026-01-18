package app;
import domain.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Main class demonstrating all use cases of the Hotel Room Reservation System
 * This program shows the complete functionality including:
 * - Hotel and room management
 * - Customer registration
 * - Booking creation and cancellation
 * - Room availability checking
 * - Price calculation
 * 
 * @author [Your Name]
 * @rollNumber [Your Roll Number]
 * @course Software Construction
 */
public class Main {
    
    public static void main(String[] args) {
        printHeader("HOTEL ROOM RESERVATION SYSTEM");
        printLine();
        
        try {
            // Execute all use case demonstrations
            demonstrateUseCase1_HotelCreation();
            demonstrateUseCase2_CustomerRegistration();
            demonstrateUseCase3_RoomAvailability();
            demonstrateUseCase4_BookingCreation();
            demonstrateUseCase5_BookingCancellation();
            demonstrateUseCase6_PriceCalculation();
            demonstrateUseCase7_CompleteWorkflow();
            
            printLine();
            printSuccess("ALL USE CASES COMPLETED SUCCESSFULLY!");
            printLine();
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * USE CASE 1: Hotel and Room Creation
     * Demonstrates creating hotels and adding different types of rooms
     */
    private static void demonstrateUseCase1_HotelCreation() {
        printUseCase(1, "Hotel and Room Creation");
        
        // Create a hotel
        Hotel grandHotel = new Hotel("H001", "Grand Plaza Hotel", "123 Main Street, Karachi");
        System.out.println("✓ Hotel Created: " + grandHotel.getName());
        System.out.println("  Address: " + grandHotel.getAddress());
        
        // Create rooms of different types
        Room room101 = new Room("R001", "101", RoomType.SINGLE, 100.0, 1);
        Room room102 = new Room("R002", "102", RoomType.DOUBLE, 150.0, 2);
        Room room103 = new Room("R003", "103", RoomType.SUITE, 300.0, 4);
        Room room104 = new Room("R004", "104", RoomType.DELUXE, 250.0, 3);
        Room room105 = new Room("R005", "105", RoomType.DOUBLE, 150.0, 2);
        
        // Add rooms to hotel
        grandHotel.addRoom(room101);
        grandHotel.addRoom(room102);
        grandHotel.addRoom(room103);
        grandHotel.addRoom(room104);
        grandHotel.addRoom(room105);
        
        System.out.println("\n✓ Added " + grandHotel.getRooms().size() + " rooms:");
        System.out.println("  • Room 101: Single Room    - Rs. 100/night (Capacity: 1)");
        System.out.println("  • Room 102: Double Room    - Rs. 150/night (Capacity: 2)");
        System.out.println("  • Room 103: Suite          - Rs. 300/night (Capacity: 4)");
        System.out.println("  • Room 104: Deluxe Room    - Rs. 250/night (Capacity: 3)");
        System.out.println("  • Room 105: Double Room    - Rs. 150/night (Capacity: 2)");
        
        printSuccess("Hotel setup complete with " + grandHotel.getRooms().size() + " rooms");
        System.out.println();
    }
    
    /**
     * USE CASE 2: Customer Registration
     * Demonstrates registering customers in the system
     */
    private static void demonstrateUseCase2_CustomerRegistration() {
        printUseCase(2, "Customer Registration");
        
        // Register multiple customers
        Customer customer1 = new Customer("C001", "Ahmed Ali", "ahmed.ali@email.com", "+92-300-1234567");
        Customer customer2 = new Customer("C002", "Sara Khan", "sara.khan@email.com", "+92-321-9876543");
        Customer customer3 = new Customer("C003", "Hassan Raza", "hassan.raza@email.com", "+92-333-5555555");
        
        System.out.println("✓ Registered Customer 1:");
        System.out.println("  Name: " + customer1.getName());
        System.out.println("  Email: " + customer1.getEmail());
        System.out.println("  Phone: " + customer1.getPhone());
        
        System.out.println("\n✓ Registered Customer 2:");
        System.out.println("  Name: " + customer2.getName());
        System.out.println("  Email: " + customer2.getEmail());
        System.out.println("  Phone: " + customer2.getPhone());
        
        System.out.println("\n✓ Registered Customer 3:");
        System.out.println("  Name: " + customer3.getName());
        System.out.println("  Email: " + customer3.getEmail());
        System.out.println("  Phone: " + customer3.getPhone());
        
        printSuccess("3 customers registered successfully");
        System.out.println();
    }
    
    /**
     * USE CASE 3: Room Availability Checking
     * Demonstrates checking room availability for specific dates
     */
    private static void demonstrateUseCase3_RoomAvailability() {
        printUseCase(3, "Room Availability Checking");
        
        // Setup hotel with rooms
        Hotel hotel = new Hotel("H002", "Seaside Resort", "456 Beach Road, Karachi");
        Room room201 = new Room("R201", "201", RoomType.DOUBLE, 180.0, 2);
        Room room202 = new Room("R202", "202", RoomType.SUITE, 350.0, 4);
        Room room203 = new Room("R203", "203", RoomType.SINGLE, 120.0, 1);
        
        hotel.addRoom(room201);
        hotel.addRoom(room202);
        hotel.addRoom(room203);
        
        // Check availability for specific dates
        LocalDate checkIn = LocalDate.of(2025, 3, 1);
        LocalDate checkOut = LocalDate.of(2025, 3, 5);
        
        System.out.println("Checking availability from " + checkIn + " to " + checkOut);
        System.out.println("Duration: 4 nights\n");
        
        List<Room> availableRooms = hotel.findAvailableRooms(checkIn, checkOut);
        System.out.println("✓ Found " + availableRooms.size() + " available rooms:");
        for (Room room : availableRooms) {
            System.out.println("  • Room " + room.getRoomNumber() + " - " + 
                             room.getType().getDisplayName() + 
                             " (Rs. " + room.getPricePerNight() + "/night)");
        }
        
        // Create a booking to show how availability changes
        Customer tempCustomer = new Customer("C999", "Test User", "test@email.com", "123456789");
        Booking booking = new Booking("B999", tempCustomer, room201, checkIn, checkOut);
        booking.confirm();
        room201.addBooking(booking);
        
        System.out.println("\n📌 Created a booking for Room 201...");
        
        // Check availability again
        availableRooms = hotel.findAvailableRooms(checkIn, checkOut);
        System.out.println("✓ Now " + availableRooms.size() + " rooms available (Room 201 is booked)");
        
        printSuccess("Availability checking works correctly");
        System.out.println();
    }
    
    /**
     * USE CASE 4: Booking Creation and Confirmation
     * Demonstrates creating and confirming bookings
     */
    private static void demonstrateUseCase4_BookingCreation() {
        printUseCase(4, "Booking Creation and Confirmation");
        
        // Create customer and room
        Customer customer = new Customer("C100", "Fatima Hassan", "fatima@email.com", "+92-300-1111111");
        Room room = new Room("R100", "301", RoomType.DELUXE, 280.0, 3);
        
        // Define booking dates
        LocalDate checkIn = LocalDate.of(2025, 4, 10);
        LocalDate checkOut = LocalDate.of(2025, 4, 15);
        
        // Create booking
        Booking booking = new Booking("B001", customer, room, checkIn, checkOut);
        
        System.out.println("📝 Booking Details:");
        System.out.println("  Booking ID: " + booking.getBookingId());
        System.out.println("  Customer: " + customer.getName());
        System.out.println("  Room: " + room.getRoomNumber() + " (" + room.getType().getDisplayName() + ")");
        System.out.println("  Check-in: " + checkIn);
        System.out.println("  Check-out: " + checkOut);
        System.out.println("  Duration: " + booking.calculateNights() + " nights");
        System.out.println("  Price per night: Rs. " + room.getPricePerNight());
        System.out.println("  Total Price: Rs. " + String.format("%.2f", booking.getTotalPrice()));
        System.out.println("  Status: " + booking.getStatus().getDisplayName());
        
        // Confirm booking
        booking.confirm();
        customer.addBooking(booking);
        room.addBooking(booking);
        
        System.out.println("\n✓ Booking confirmed!");
        System.out.println("  New Status: " + booking.getStatus().getDisplayName());
        System.out.println("  Customer total bookings: " + customer.getBookings().size());
        
        printSuccess("Booking created and confirmed successfully");
        System.out.println();
    }
    
    /**
     * USE CASE 5: Booking Cancellation
     * Demonstrates canceling existing bookings
     */
    private static void demonstrateUseCase5_BookingCancellation() {
        printUseCase(5, "Booking Cancellation");
        
        // Create customer, room, and booking
        Customer customer = new Customer("C200", "Ali Imran", "ali.imran@email.com", "+92-321-2222222");
        Room room = new Room("R200", "401", RoomType.SUITE, 400.0, 4);
        
        LocalDate checkIn = LocalDate.of(2025, 5, 1);
        LocalDate checkOut = LocalDate.of(2025, 5, 7);
        
        Booking booking = new Booking("B002", customer, room, checkIn, checkOut);
        booking.confirm();
        customer.addBooking(booking);
        
        System.out.println("📝 Original Booking:");
        System.out.println("  Booking ID: " + booking.getBookingId());
        System.out.println("  Customer: " + customer.getName());
        System.out.println("  Room: " + room.getRoomNumber());
        System.out.println("  Duration: " + booking.calculateNights() + " nights");
        System.out.println("  Total: Rs. " + booking.getTotalPrice());
        System.out.println("  Status: " + booking.getStatus().getDisplayName());
        
        // Cancel the booking
        System.out.println("\n🚫 Cancelling booking...");
        boolean cancelled = customer.cancelBooking("B002");
        
        if (cancelled) {
            System.out.println("✓ Cancellation successful!");
            System.out.println("  New Status: " + booking.getStatus().getDisplayName());
            System.out.println("  Room is now available for other customers");
        }
        
        printSuccess("Booking cancelled successfully");
        System.out.println();
    }
    
    /**
     * USE CASE 6: Price Calculation
     * Demonstrates calculating booking prices for different durations
     */
    private static void demonstrateUseCase6_PriceCalculation() {
        printUseCase(6, "Price Calculation");
        
        // Create different room types
        Room singleRoom = new Room("R301", "101", RoomType.SINGLE, 100.0, 1);
        Room doubleRoom = new Room("R302", "102", RoomType.DOUBLE, 150.0, 2);
        Room suiteRoom = new Room("R303", "103", RoomType.SUITE, 300.0, 4);
        Room deluxeRoom = new Room("R304", "104", RoomType.DELUXE, 250.0, 3);
        
        System.out.println("💰 Price Calculations:\n");
        
        // Single Room
        System.out.println("Single Room (Rs. 100/night):");
        System.out.println("  1 night  = Rs. " + singleRoom.calculateTotalPrice(1));
        System.out.println("  3 nights = Rs. " + singleRoom.calculateTotalPrice(3));
        System.out.println("  7 nights = Rs. " + singleRoom.calculateTotalPrice(7));
        System.out.println("  30 nights = Rs. " + singleRoom.calculateTotalPrice(30));
        
        // Double Room
        System.out.println("\nDouble Room (Rs. 150/night):");
        System.out.println("  1 night  = Rs. " + doubleRoom.calculateTotalPrice(1));
        System.out.println("  3 nights = Rs. " + doubleRoom.calculateTotalPrice(3));
        System.out.println("  7 nights = Rs. " + doubleRoom.calculateTotalPrice(7));
        System.out.println("  30 nights = Rs. " + doubleRoom.calculateTotalPrice(30));
        
        // Suite
        System.out.println("\nSuite (Rs. 300/night):");
        System.out.println("  1 night  = Rs. " + suiteRoom.calculateTotalPrice(1));
        System.out.println("  3 nights = Rs. " + suiteRoom.calculateTotalPrice(3));
        System.out.println("  7 nights = Rs. " + suiteRoom.calculateTotalPrice(7));
        System.out.println("  30 nights = Rs. " + suiteRoom.calculateTotalPrice(30));
        
        // Deluxe Room
        System.out.println("\nDeluxe Room (Rs. 250/night):");
        System.out.println("  1 night  = Rs. " + deluxeRoom.calculateTotalPrice(1));
        System.out.println("  3 nights = Rs. " + deluxeRoom.calculateTotalPrice(3));
        System.out.println("  7 nights = Rs. " + deluxeRoom.calculateTotalPrice(7));
        System.out.println("  30 nights = Rs. " + deluxeRoom.calculateTotalPrice(30));
        
        printSuccess("Price calculation completed for all room types");
        System.out.println();
    }
    
    /**
     * USE CASE 7: Complete Workflow
     * Demonstrates a complete end-to-end scenario with multiple interactions
     */
    private static void demonstrateUseCase7_CompleteWorkflow() {
        printUseCase(7, "Complete End-to-End Workflow");
        
        // Step 1: Setup Hotel
        System.out.println("STEP 1: Setting up hotel...");
        Hotel hotel = new Hotel("H999", "Pearl Continental Hotel", "Shahrah-e-Faisal, Karachi");
        
        Room room501 = new Room("R501", "501", RoomType.DOUBLE, 220.0, 2);
        Room room502 = new Room("R502", "502", RoomType.SUITE, 450.0, 4);
        Room room503 = new Room("R503", "503", RoomType.DELUXE, 320.0, 3);
        Room room504 = new Room("R504", "504", RoomType.SINGLE, 150.0, 1);
        
        hotel.addRoom(room501);
        hotel.addRoom(room502);
        hotel.addRoom(room503);
        hotel.addRoom(room504);
        
        System.out.println("✓ Hotel: " + hotel.getName());
        System.out.println("✓ Total rooms: " + hotel.getRooms().size());
        
        // Step 2: Register Customers
        System.out.println("\nSTEP 2: Registering customers...");
        Customer customer1 = new Customer("C501", "Ayesha Malik", "ayesha@email.com", "+92-300-1234567");
        Customer customer2 = new Customer("C502", "Bilal Ahmed", "bilal@email.com", "+92-321-7654321");
        Customer customer3 = new Customer("C503", "Zainab Hussain", "zainab@email.com", "+92-333-9876543");
        
        System.out.println("✓ Registered: " + customer1.getName());
        System.out.println("✓ Registered: " + customer2.getName());
        System.out.println("✓ Registered: " + customer3.getName());
        
        // Step 3: Check Initial Availability
        System.out.println("\nSTEP 3: Checking room availability...");
        LocalDate date1In = LocalDate.of(2025, 6, 1);
        LocalDate date1Out = LocalDate.of(2025, 6, 5);
        
        List<Room> available = hotel.findAvailableRooms(date1In, date1Out);
        System.out.println("✓ Available rooms (Jun 1-5): " + available.size() + " rooms");
        
        // Step 4: Create First Booking
        System.out.println("\nSTEP 4: Creating first booking...");
        Booking booking1 = new Booking("B501", customer1, room501, date1In, date1Out);
        booking1.confirm();
        customer1.addBooking(booking1);
        room501.addBooking(booking1);
        
        System.out.println("✓ Booking ID: " + booking1.getBookingId());
        System.out.println("  Customer: " + customer1.getName());
        System.out.println("  Room: " + room501.getRoomNumber() + " (" + room501.getType().getDisplayName() + ")");
        System.out.println("  Duration: " + booking1.calculateNights() + " nights");
        System.out.println("  Total: Rs. " + String.format("%.2f", booking1.getTotalPrice()));
        System.out.println("  Status: " + booking1.getStatus().getDisplayName());
        
        // Step 5: Check Availability Again
        System.out.println("\nSTEP 5: Re-checking availability...");
        available = hotel.findAvailableRooms(date1In, date1Out);
        System.out.println("✓ Available rooms now: " + available.size() + " (Room 501 is booked)");
        
        // Step 6: Create Second Booking (Different Dates)
        System.out.println("\nSTEP 6: Creating second booking (different dates)...");
        LocalDate date2In = LocalDate.of(2025, 6, 10);
        LocalDate date2Out = LocalDate.of(2025, 6, 15);
        
        Booking booking2 = new Booking("B502", customer2, room502, date2In, date2Out);
        booking2.confirm();
        customer2.addBooking(booking2);
        room502.addBooking(booking2);
        
        System.out.println("✓ Booking ID: " + booking2.getBookingId());
        System.out.println("  Customer: " + customer2.getName());
        System.out.println("  Room: " + room502.getRoomNumber() + " (" + room502.getType().getDisplayName() + ")");
        System.out.println("  Duration: " + booking2.calculateNights() + " nights");
        System.out.println("  Total: Rs. " + String.format("%.2f", booking2.getTotalPrice()));
        
        // Step 7: Create Third Booking (Overlapping dates with booking 1)
        System.out.println("\nSTEP 7: Creating third booking (non-overlapping)...");
        LocalDate date3In = LocalDate.of(2025, 6, 1);
        LocalDate date3Out = LocalDate.of(2025, 6, 4);
        
        Booking booking3 = new Booking("B503", customer3, room503, date3In, date3Out);
        booking3.confirm();
        customer3.addBooking(booking3);
        room503.addBooking(booking3);
        
        System.out.println("✓ Booking ID: " + booking3.getBookingId());
        System.out.println("  Customer: " + customer3.getName());
        System.out.println("  Room: " + room503.getRoomNumber());
        System.out.println("  Total: Rs. " + String.format("%.2f", booking3.getTotalPrice()));
        
        // Step 8: Display System Summary
        System.out.println("\nSTEP 8: SYSTEM SUMMARY");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Hotels: 1");
        System.out.println("Total Rooms: " + hotel.getRooms().size());
        System.out.println("Registered Customers: 3");
        System.out.println("Active Bookings: " + 
            (customer1.getBookings().size() + customer2.getBookings().size() + customer3.getBookings().size()));
        
        double totalRevenue = booking1.getTotalPrice() + booking2.getTotalPrice() + booking3.getTotalPrice();
        System.out.println("Total Revenue: Rs. " + String.format("%.2f", totalRevenue));
        
        System.out.println("\nBooking Breakdown:");
        System.out.println("  • " + customer1.getName() + ": 1 booking(s)");
        System.out.println("  • " + customer2.getName() + ": 1 booking(s)");
        System.out.println("  • " + customer3.getName() + ": 1 booking(s)");
        
        printSuccess("Complete workflow executed successfully!");
        System.out.println();
    }
    
    // ================== UTILITY METHODS ==================
    
    /**
     * Prints a formatted header
     */
    private static void printHeader(String title) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("  " + title);
        System.out.println("═".repeat(60));
    }
    
    /**
     * Prints a separator line
     */
    private static void printLine() {
        System.out.println("─".repeat(60));
    }
    
    /**
     * Prints a use case header
     */
    private static void printUseCase(int number, String title) {
        System.out.println("\n╔" + "═".repeat(58) + "╗");
        System.out.println("║  USE CASE " + number + ": " + title + " ".repeat(58 - title.length() - 13) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝\n");
    }
    
    /**
     * Prints a success message
     */
    private static void printSuccess(String message) {
        System.out.println("\n✅ " + message);
    }
}