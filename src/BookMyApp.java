import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay Application
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * @author Naisha
 * @version 5.1
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

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
        System.out.println("-----------------------------");
    }
}


/* -------- Booking Request Queue -------- */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all queued requests
    public void displayRequests() {

        System.out.println("\nCurrent Booking Request Queue:\n");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 5.1          ");
        System.out.println("=================================");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue (FIFO order)
        bookingQueue.displayRequests();

        System.out.println("\nRequests stored in arrival order (FIFO).");
        System.out.println("No room allocation performed yet.");
    }
}