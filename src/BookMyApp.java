import java.util.*;

/**
 * Book My Stay Application
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * @author Naisha
 * @version 10.1
 */


/* -------- Reservation Class -------- */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
}


/* -------- Inventory Service -------- */

class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}


/* -------- Booking History -------- */

class BookingHistory {

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        reservations.remove(reservationId);
    }
}


/* -------- Cancellation Service -------- */

class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelReservation(String reservationId,
                                  BookingHistory history,
                                  InventoryService inventory) {

        Reservation reservation = history.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        String roomId = reservation.getRoomId();
        String roomType = reservation.getRoomType();

        // Record rollback
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increaseAvailability(roomType);

        // Remove reservation from history
        history.removeReservation(reservationId);

        System.out.println("Reservation " + reservationId + " cancelled.");
        System.out.println("Room released: " + rollbackStack.peek());
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 10.1         ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService();

        // Simulated confirmed reservation
        Reservation r1 = new Reservation("RES-101", "Alice", "Single Room", "SR-1");

        history.addReservation(r1);

        // Cancel booking
        cancellationService.cancelReservation("RES-101", history, inventory);

        inventory.displayInventory();
    }
}