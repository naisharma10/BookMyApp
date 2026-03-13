import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 4: Room Search & Availability Check
 *
 * @author Naisha
 * @version 4.1
 */

/* -------- Room Domain Model -------- */

class Room {

    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Price     : $" + price);
        System.out.println("Amenities : " + amenities);
    }
}


/* -------- Centralized Inventory -------- */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // unavailable example
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}


/* -------- Search Service (Read Only) -------- */

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Defensive check
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms : " + available);
                System.out.println("------------------------------");
            }
        }
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 4.1          ");
        System.out.println("=================================");

        // Create room objects (domain model)
        Room single = new Room("Single Room", 100, "1 Bed, Free WiFi");
        Room doubleRoom = new Room("Double Room", 180, "2 Beds, Free WiFi, TV");
        Room suite = new Room("Suite Room", 300, "King Bed, WiFi, TV, Balcony");

        Room[] rooms = {single, doubleRoom, suite};

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Perform search (read-only)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed. Inventory remains unchanged.");
    }
}