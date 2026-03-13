import java.util.HashMap;

/**
 * Book My Stay Application
 * Use Case 9: Error Handling & Validation
 *
 * @author Naisha
 * @version 9.1
 */


/* -------- Custom Exception -------- */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/* -------- Inventory Service -------- */

class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}


/* -------- Booking Validator -------- */

class InvalidBookingValidator {

    public static void validate(String roomType, InventoryService inventory)
            throws InvalidBookingException {

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }
}


/* -------- Booking Service -------- */

class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBooking(String guestName, String roomType) {

        try {

            // Validation step
            InvalidBookingValidator.validate(roomType, inventory);

            // Allocation logic
            inventory.decreaseAvailability(roomType);

            System.out.println("Booking confirmed for " + guestName +
                    " (" + roomType + ")");

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        }
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 9.1          ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        bookingService.processBooking("Alice", "Single Room");

        // Invalid room type
        bookingService.processBooking("Bob", "Luxury Room");

        // No availability case
        bookingService.processBooking("Charlie", "Suite Room");

        inventory.displayInventory();
    }
}