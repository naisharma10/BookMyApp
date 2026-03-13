import java.util.*;

/**
 * Book My Stay Application
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * @author Naisha
 * @version 11.1
 */

/* -------- Reservation Class -------- */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}


/* -------- Shared Booking Queue -------- */

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }
}


/* -------- Thread-Safe Inventory -------- */

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}


/* -------- Concurrent Booking Processor -------- */

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        Reservation request = queue.getNextRequest();

        if (request == null) return;

        String guest = request.getGuestName();
        String roomType = request.getRoomType();

        synchronized (inventory) {

            if (inventory.allocateRoom(roomType)) {

                System.out.println("Booking confirmed for " +
                        guest + " (" + roomType + ")");

            } else {

                System.out.println("Booking failed for " +
                        guest + " — No " + roomType + " available");
            }
        }
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 11.1         ");
        System.out.println("=================================");

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulate multiple guest requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Double Room"));

        // Create concurrent threads
        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);
        Thread t3 = new BookingProcessor(queue, inventory);
        Thread t4 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}