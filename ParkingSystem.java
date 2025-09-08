import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

/**
 * Main class for the Smart Parking System.
 * Provides a console interface for user interaction.
 */
public class ParkingSystem {
    private ParkingLot parkingLot;
    private Scanner scanner;
    private Map<String, Vehicle> registeredVehicles;
    private boolean running;
    
    /**
     * Constructor for the parking system.
     * 
     * @param parkingLotName The name of the parking lot
     * @param capacity The total capacity of the parking lot
     */
    public ParkingSystem(String parkingLotName, int capacity) {
        String logFilePath = "parking_log.txt";
        this.parkingLot = new ParkingLot(parkingLotName, capacity, logFilePath);
        this.scanner = new Scanner(System.in);
        this.registeredVehicles = new HashMap<>();
        this.running = false;
        
        // Create log file if it doesn't exist
        try {
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (Exception e) {
            System.err.println("Error creating log file: " + e.getMessage());
        }
        
        // Initialize parking slots
        initializeSlots(capacity);
    }
    
    /**
     * Initializes the parking slots with different priority levels.
     * 
     * @param capacity The total capacity of the parking lot
     */
    private void initializeSlots(int capacity) {
        // Create slots with different priority levels based on their location
        int emergencySlots = Math.max(1, capacity / 10); // 10% for emergency
        int vipSlots = Math.max(1, capacity / 5);       // 20% for VIP
        int handicappedSlots = Math.max(1, capacity / 5); // 20% for handicapped
        int regularSlots = capacity - emergencySlots - vipSlots - handicappedSlots;
        
        // Add emergency slots (highest priority)
        for (int i = 1; i <= emergencySlots; i++) {
            parkingLot.addSlot(new Slot("E" + i, "Emergency Zone", 1));
        }
        
        // Add VIP slots
        for (int i = 1; i <= vipSlots; i++) {
            parkingLot.addSlot(new Slot("V" + i, "VIP Zone", 2));
        }
        
        // Add handicapped slots
        for (int i = 1; i <= handicappedSlots; i++) {
            parkingLot.addSlot(new Slot("H" + i, "Handicapped Zone", 3));
        }
        
        // Add regular slots
        for (int i = 1; i <= regularSlots; i++) {
            parkingLot.addSlot(new Slot("R" + i, "Regular Zone", 4));
        }
    }
    
    /**
     * Starts the parking system and displays the main menu.
     */
    public void start() {
        running = true;
        System.out.println("===== Smart Parking System =====\n");
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    registerVehicle();
                    break;
                case 2:
                    parkVehicle();
                    break;
                case 3:
                    exitVehicle();
                    break;
                case 4:
                    displayStatus();
                    break;
                case 5:
                    adminPanel();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Smart Parking System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); // Add a blank line for readability
        }
        
        scanner.close();
    }
    
    /**
     * Displays the main menu options.
     */
    private void displayMenu() {
        System.out.println("===== Main Menu =====");
        System.out.println("1. Register a Vehicle");
        System.out.println("2. Park a Vehicle");
        System.out.println("3. Exit a Vehicle");
        System.out.println("4. Display Parking Status");
        System.out.println("5. Admin Panel");
        System.out.println("0. Exit System");
    }
    
    /**
     * Registers a new vehicle in the system.
     */
    private void registerVehicle() {
        System.out.println("\n===== Register a Vehicle =====");
        
        String licensePlate = getStringInput("Enter license plate number: ");
        
        // Check if vehicle is already registered
        if (registeredVehicles.containsKey(licensePlate)) {
            System.out.println("Vehicle with this license plate is already registered.");
            return;
        }
        
        String vehicleType = getStringInput("Enter vehicle type (car, motorcycle, truck, etc.): ");
        
        System.out.println("Select user category:");
        System.out.println("1. Emergency");
        System.out.println("2. VIP");
        System.out.println("3. Handicapped");
        System.out.println("4. Regular");
        
        int categoryChoice = getIntInput("Enter choice (1-4): ");
        UserCategory userCategory;
        
        switch (categoryChoice) {
            case 1:
                userCategory = UserCategory.EMERGENCY;
                break;
            case 2:
                userCategory = UserCategory.VIP;
                break;
            case 3:
                userCategory = UserCategory.HANDICAPPED;
                break;
            case 4:
                userCategory = UserCategory.REGULAR;
                break;
            default:
                System.out.println("Invalid choice. Setting as Regular.");
                userCategory = UserCategory.REGULAR;
        }
        
        Vehicle vehicle = new Vehicle(licensePlate, vehicleType, userCategory);
        registeredVehicles.put(licensePlate, vehicle);
        
        System.out.println("Vehicle registered successfully: " + vehicle);
    }
    
    /**
     * Parks a registered vehicle in an available slot.
     */
    private void parkVehicle() {
        System.out.println("\n===== Park a Vehicle =====");
        
        if (registeredVehicles.isEmpty()) {
            System.out.println("No vehicles registered. Please register a vehicle first.");
            return;
        }
        
        String licensePlate = getStringInput("Enter license plate number: ");
        Vehicle vehicle = registeredVehicles.get(licensePlate);
        
        if (vehicle == null) {
            System.out.println("Vehicle not found. Please register the vehicle first.");
            return;
        }
        
        Slot allocatedSlot = parkingLot.allocateSlot(vehicle);
        
        if (allocatedSlot != null) {
            System.out.println("Vehicle parked successfully in slot: " + allocatedSlot.getSlotId());
            System.out.println("Location: " + allocatedSlot.getLocation());
        } else {
            System.out.println("No slots available. Vehicle added to waiting list.");
        }
    }
    
    /**
     * Processes a vehicle exiting the parking lot.
     */
    private void exitVehicle() {
        System.out.println("\n===== Exit a Vehicle =====");
        
        String licensePlate = getStringInput("Enter license plate number: ");
        
        Slot vacatedSlot = parkingLot.deallocateSlot(licensePlate);
        
        if (vacatedSlot != null) {
            System.out.println("Vehicle exited successfully from slot: " + vacatedSlot.getSlotId());
        } else {
            System.out.println("Vehicle not found in the parking lot.");
        }
    }
    
    /**
     * Displays the current status of the parking lot.
     */
    private void displayStatus() {
        System.out.println("\n===== Parking Status =====");
        System.out.println(parkingLot.getStatus());
    }
    
    /**
     * Provides admin functionality for managing the parking lot.
     */
    private void adminPanel() {
        System.out.println("\n===== Admin Panel =====");
        System.out.println("1. Add a new parking slot");
        System.out.println("2. View all registered vehicles");
        System.out.println("3. Back to main menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addNewSlot();
                break;
            case 2:
                viewRegisteredVehicles();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    /**
     * Adds a new parking slot to the parking lot.
     */
    private void addNewSlot() {
        System.out.println("\n===== Add New Slot =====");
        
        String slotId = getStringInput("Enter slot ID: ");
        String location = getStringInput("Enter slot location: ");
        
        System.out.println("Select slot priority level:");
        System.out.println("1. Emergency (Highest)");
        System.out.println("2. VIP");
        System.out.println("3. Handicapped");
        System.out.println("4. Regular (Lowest)");
        
        int priorityChoice = getIntInput("Enter choice (1-4): ");
        int priorityLevel;
        
        switch (priorityChoice) {
            case 1:
                priorityLevel = 1;
                break;
            case 2:
                priorityLevel = 2;
                break;
            case 3:
                priorityLevel = 3;
                break;
            case 4:
                priorityLevel = 4;
                break;
            default:
                System.out.println("Invalid choice. Setting as Regular priority.");
                priorityLevel = 4;
        }
        
        Slot newSlot = new Slot(slotId, location, priorityLevel);
        parkingLot.addSlot(newSlot);
        
        System.out.println("New slot added successfully: " + newSlot);
    }
    
    /**
     * Displays all registered vehicles.
     */
    private void viewRegisteredVehicles() {
        System.out.println("\n===== Registered Vehicles =====");
        
        if (registeredVehicles.isEmpty()) {
            System.out.println("No vehicles registered.");
            return;
        }
        
        for (Vehicle vehicle : registeredVehicles.values()) {
            System.out.println(vehicle);
        }
    }
    
    /**
     * Gets string input from the user.
     * 
     * @param prompt The prompt to display
     * @return The user's input as a string
     */
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Gets integer input from the user.
     * 
     * @param prompt The prompt to display
     * @return The user's input as an integer
     */
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    /**
     * Main method to start the application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        ParkingSystem parkingSystem = new ParkingSystem("Smart Parking Facility", 20);
        parkingSystem.start();
    }
}