import java.util.HashMap;
 import java.util.LinkedList;
 import java.util.Map;
 import java.util.PriorityQueue;
 import java.util.Queue;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.text.SimpleDateFormat;
 import java.util.Date;

/**
 * Manages the parking lot, including slot allocation and deallocation.
 * Uses priority queues for efficient slot management based on priority.
 */
public class ParkingLot {
    private String name;
    private int capacity;
    private PriorityQueue<Slot> availableSlots;
    private Map<String, Slot> occupiedSlots; // Maps license plate to slot
    private Queue<Vehicle> waitingList;
    private String logFilePath;
    
    /**
     * Constructor for creating a new parking lot.
     * 
     * @param name The name of the parking lot
     * @param capacity The total capacity of the parking lot
     * @param logFilePath The file path for storing logs
     */
    public ParkingLot(String name, int capacity, String logFilePath) {
        this.name = name;
        this.capacity = capacity;
        this.availableSlots = new PriorityQueue<>();
        this.occupiedSlots = new HashMap<>();
        this.waitingList = new LinkedList<>();
        this.logFilePath = logFilePath;
        
        // Log the creation of the parking lot
        logEvent("Parking lot created: " + name + " with capacity: " + capacity);
    }
    
    /**
     * Adds a new slot to the parking lot.
     * 
     * @param slot The slot to add
     */
    public void addSlot(Slot slot) {
        if (!slot.isOccupied()) {
            availableSlots.add(slot);
            logEvent("Slot added: " + slot.getSlotId() + " with priority level: " + slot.getPriorityLevel());
        }
    }
    
    /**
     * Allocates a parking slot to a vehicle based on priority.
     * 
     * @param vehicle The vehicle to allocate a slot for
     * @return The allocated slot, or null if no suitable slot is available
     */
    public Slot allocateSlot(Vehicle vehicle) {
        if (availableSlots.isEmpty()) {
            // Add to waiting list if no slots available
            waitingList.add(vehicle);
            logEvent("No slots available. Vehicle " + vehicle.getLicensePlate() + " added to waiting list.");
            return null;
        }
        
        // Find the highest priority available slot
        Slot allocatedSlot = availableSlots.poll();
        allocatedSlot.occupy(vehicle);
        occupiedSlots.put(vehicle.getLicensePlate(), allocatedSlot);
        
        logEvent("Slot allocated: " + allocatedSlot.getSlotId() + " to vehicle: " + 
                vehicle.getLicensePlate() + " (" + vehicle.getUserCategory() + ")");
        
        return allocatedSlot;
    }
    
    /**
     * Deallocates a parking slot when a vehicle exits.
     * 
     * @param licensePlate The license plate of the exiting vehicle
     * @return The vacated slot, or null if the vehicle was not found
     */
    public Slot deallocateSlot(String licensePlate) {
        Slot slot = occupiedSlots.get(licensePlate);
        if (slot == null) {
            logEvent("Vehicle not found: " + licensePlate);
            return null;
        }
        
        Vehicle vehicle = slot.vacate();
        occupiedSlots.remove(licensePlate);
        availableSlots.add(slot);
        
        logEvent("Slot deallocated: " + slot.getSlotId() + " from vehicle: " + 
                vehicle.getLicensePlate() + ". Duration: " + calculateDuration(vehicle.getEntryTime()));
        
        // Check waiting list and allocate slot if possible
        checkWaitingList();
        
        return slot;
    }
    
    /**
     * Checks the waiting list and allocates slots if available.
     */
    private void checkWaitingList() {
        if (!waitingList.isEmpty() && !availableSlots.isEmpty()) {
            Vehicle vehicle = waitingList.poll();
            allocateSlot(vehicle);
            logEvent("Vehicle from waiting list allocated a slot: " + vehicle.getLicensePlate());
        }
    }
    
    /**
     * Gets the current status of the parking lot.
     * 
     * @return A string representation of the current status
     */
    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Parking Lot Status: \n");
        status.append("Name: ").append(name).append("\n");
        status.append("Total Capacity: ").append(capacity).append("\n");
        status.append("Available Slots: ").append(availableSlots.size()).append("\n");
        status.append("Occupied Slots: ").append(occupiedSlots.size()).append("\n");
        status.append("Waiting List Size: ").append(waitingList.size()).append("\n");
        
        return status.toString();
    }
    
    /**
     * Logs an event to the log file.
     * 
     * @param event The event description to log
     */
    private void logEvent(String event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        String logEntry = timestamp + " - " + event;
        
        System.out.println(logEntry); // Also print to console for debugging
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    /**
     * Calculates the duration a vehicle spent in the parking lot.
     * 
     * @param entryTime The entry time in milliseconds
     * @return A formatted string representing the duration
     */
    private String calculateDuration(long entryTime) {
        long exitTime = System.currentTimeMillis();
        long durationMillis = exitTime - entryTime;
        
        long seconds = durationMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        minutes %= 60;
        seconds %= 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    /**
     * Gets the number of available slots.
     * 
     * @return The number of available slots
     */
    public int getAvailableSlotsCount() {
        return availableSlots.size();
    }
    
    /**
     * Gets the number of occupied slots.
     * 
     * @return The number of occupied slots
     */
    public int getOccupiedSlotsCount() {
        return occupiedSlots.size();
    }
    
    /**
     * Gets the waiting list size.
     * 
     * @return The number of vehicles in the waiting list
     */
    public int getWaitingListSize() {
        return waitingList.size();
    }
}