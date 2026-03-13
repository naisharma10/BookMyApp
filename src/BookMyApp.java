import java.util.*;

/**
 * Book My Stay Application
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * @author Naisha
 * @version 6.1
 */

/* -------- Reservation Class -------- */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/* -------- Booking Request Queue -------- */

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}


/* -------- Inventory Service -------- */

class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}


/* -------- Booking Service -------- */

class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();
    private int roomCounter = 1;

    public void processBookings(BookingRequestQueue queue, InventoryService inventory) {

        while (queue.hasRequests()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();

            System.out.println("\nProcessing request for " + request.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = roomType.replace(" ", "") + "-" + roomCounter++;

                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    roomAllocations
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);

                    inventory.decreaseAvailability(roomType);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest : " + request.getGuestName());
                    System.out.println("Room Type : " + roomType);
                    System.out.println("Assigned Room ID : " + roomId);

                }

            } else {

                System.out.println("Sorry, no rooms available for " + roomType);
            }
        }
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 6.1          ");
        System.out.println("=================================");

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService();

        bookingService.processBookings(queue, inventory);

        inventory.displayInventory();
    }
}