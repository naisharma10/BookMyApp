import java.util.*;

/**
 * Book My Stay Application
 * Use Case 7: Add-On Service Selection
 *
 * @author Naisha
 * @version 7.1
 */

/* -------- Add-On Service Class -------- */

class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " ($" + cost + ")");
    }
}


/* -------- Add-On Service Manager -------- */

class AddOnServiceManager {

    // Reservation ID → List of Services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println(service.getServiceName() +
                " added to reservation " + reservationId);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (Service s : services) {
            s.displayService();
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}


/* -------- Main Application -------- */

public class BookMyApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("            Version 7.1          ");
        System.out.println("=================================");

        // Example reservation ID from previous booking
        String reservationId = "RES-101";

        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservationId, new Service("Breakfast", 20));
        manager.addService(reservationId, new Service("Airport Pickup", 40));
        manager.addService(reservationId, new Service("Spa Access", 50));

        // Display services
        manager.displayServices(reservationId);

        // Calculate additional cost
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: $" + totalCost);

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}