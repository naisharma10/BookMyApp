import java.util.*;

/**
 * Book My Stay Application
 * Use Case 8: Booking History & Reporting
 *
 * @author Naisha
 * @version 8.1
 */

/* -------- Reservation Class -------- */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("-------------------------------");
    }
}


/* -------- Booking History -------- */

class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.getReservationId());
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return history;
    }
}


/* -------- Booking Report Service -------- */

class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking History Report =====");

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("Total Bookings : " + reservations.size());
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 8.1          ");
        System.out.println("=================================");

        BookingHistory history = new BookingHistory();

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("RES-101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES-102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES-103", "Charlie", "Suite Room");

        // Store in booking history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin generates report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getReservations());
    }
}