import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 3: Centralized Room Inventory Management
 *
 * @author Naisha
 * @version 3.1
 */

/* -------- RoomInventory Class -------- */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 3.1          ");
        System.out.println("=================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating availability of Single Room...");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}