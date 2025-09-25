package ridesharingappproject;

import java.util.Scanner;

// RideRequest class
class RideRequest {
    private int rideId;
    private String passengerName;
    private String pickupLocation;
    private String dropoffLocation;
    private float fare;

    public RideRequest(int rideId, String passengerName, String pickupLocation, String dropoffLocation, float fare) {
        this.rideId = rideId;
        this.passengerName = passengerName;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.fare = fare;
    }

    public int getRideId() { return rideId; }
    public String getPassengerName() { return passengerName; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public float getFare() { return fare; }

    public void display() {
        System.out.println("Ride ID: " + rideId);
        System.out.println("Passenger: " + passengerName);
        System.out.println("Pickup: " + pickupLocation);
        System.out.println("Dropoff: " + dropoffLocation);
        System.out.println("Fare: R" + fare);
        System.out.println("---------------------------");
    }
}

// Node class for linked list
class Node {
    RideRequest ride;
    Node next;

    public Node(RideRequest ride) {
        this.ride = ride;
        this.next = null;
    }
}

// RideManager class
class RideManager {
    private Node head;

    public void insertAtEnd(RideRequest ride) {
        Node newNode = new Node(ride);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }
    }

    public void deleteById(int id) {
        if (head == null) return;
        if (head.ride.getRideId() == id) {
            head = head.next;
            return;
        }
        Node temp = head;
        while (temp.next != null && temp.next.ride.getRideId() != id)
            temp = temp.next;
        if (temp.next != null)
            temp.next = temp.next.next;
    }

    public RideRequest searchById(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.ride.getRideId() == id)
                return temp.ride;
            temp = temp.next;
        }
        return null;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("No rides available.");
            return;
        }
        Node temp = head;
        while (temp != null) {
            temp.ride.display();
            temp = temp.next;
        }
    }

    public void sortByFare() {
        if (head == null) {
            System.out.println("No rides available.");
            return;
        }

        // Convert to array
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        RideRequest[] rides = new RideRequest[size];
        temp = head;
        for (int i = 0; i < size; i++) {
            rides[i] = temp.ride;
            temp = temp.next;
        }

        // Insertion sort
        for (int i = 1; i < size; i++) {
            RideRequest key = rides[i];
            int j = i - 1;
            while (j >= 0 && rides[j].getFare() > key.getFare()) {
                rides[j + 1] = rides[j];
                j--;
            }
            rides[j + 1] = key;
        }

        // Display sorted rides
        System.out.println("--- Rides Sorted by Fare (Ascending) ---");
        for (RideRequest ride : rides)
            ride.display();
    }

    public void printReverse(Node node) {
        if (node == null) return;
        printReverse(node.next);
        node.ride.display();
    }

    public void printReverse() {
        if (head == null) {
            System.out.println("No rides available.");
            return;
        }
        System.out.println("--- Rides in Reverse ---");
        printReverse(head);
    }

    public float totalFare(Node node) {
        if (node == null) return 0;
        return node.ride.getFare() + totalFare(node.next);
    }

    public float totalFare() {
        return totalFare(head);
    }
}

// Main class

/**
 *
 * @author muano
 */
public class RideSharingApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RideManager manager = new RideManager();
        int nextRideId = 1; // Auto-incremented ride IDs

        while (true) {
            System.out.println("\n--- Ride Sharing Booking Manager ---");
            System.out.println("1. Add Ride Request");
            System.out.println("2. Delete Ride by ID");
            System.out.println("3. Search Ride by ID");
            System.out.println("4. Display All Rides");
            System.out.println("5. Sort Rides by Fare");
            System.out.println("6. Print Rides in Reverse");
            System.out.println("7. Calculate Total Fare");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Passenger Name: ");
                        String name = sc.nextLine();
                        System.out.print("Pickup Location: ");
                        String pickup = sc.nextLine();
                        System.out.print("Dropoff Location: ");
                        String dropoff = sc.nextLine();
                        System.out.print("Fare: ");
                        float fare = Float.parseFloat(sc.nextLine());
                        manager.insertAtEnd(new RideRequest(nextRideId++, name, pickup, dropoff, fare));
                        System.out.println("Ride request added successfully!");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid fare. Please enter a valid number.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Ride ID to delete: ");
                    try {
                        int delId = Integer.parseInt(sc.nextLine());
                        manager.deleteById(delId);
                        System.out.println("Delete operation complete.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Ride ID to search: ");
                    try {
                        int searchId = Integer.parseInt(sc.nextLine());
                        RideRequest found = manager.searchById(searchId);
                        if (found != null) found.display();
                        else System.out.println("Ride not found.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format.");
                    }
                    break;
                case 4:
                    manager.displayAll();
                    break;
                case 5:
                    manager.sortByFare();
                    break;
                case 6:
                    manager.printReverse();
                    break;
                case 7:
                    System.out.println("Total Fare: R" + manager.totalFare());
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}