/**
 * Represents a parking slot in the parking system.
 * Each slot has an ID, location, and priority level.
 */
public class Slot implements Comparable<Slot> {
    private String slotId;
    private String location;
    private int priorityLevel;
    private boolean isOccupied;
    private Vehicle occupiedBy;
    
    /**
     * Constructor for creating a new parking slot.
     * 
     * @param slotId The unique identifier for the slot
     * @param location The location description of the slot
     * @param priorityLevel The priority level of the slot (lower number means higher priority)
     */
    public Slot(String slotId, String location, int priorityLevel) {
        this.slotId = slotId;
        this.location = location;
        this.priorityLevel = priorityLevel;
        this.isOccupied = false;
        this.occupiedBy = null;
    }
    
    /**
     * Gets the slot ID.
     * 
     * @return The slot ID
     */
    public String getSlotId() {
        return slotId;
    }
    
    /**
     * Gets the location of the slot.
     * 
     * @return The location description
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * Gets the priority level of the slot.
     * 
     * @return The priority level
     */
    public int getPriorityLevel() {
        return priorityLevel;
    }
    
    /**
     * Checks if the slot is currently occupied.
     * 
     * @return true if occupied, false otherwise
     */
    public boolean isOccupied() {
        return isOccupied;
    }
    
    /**
     * Gets the vehicle currently occupying this slot.
     * 
     * @return The vehicle occupying the slot, or null if unoccupied
     */
    public Vehicle getOccupiedBy() {
        return occupiedBy;
    }
    
    /**
     * Occupies the slot with a vehicle.
     * 
     * @param vehicle The vehicle to occupy the slot
     * @return true if successful, false if already occupied
     */
    public boolean occupy(Vehicle vehicle) {
        if (!isOccupied) {
            this.isOccupied = true;
            this.occupiedBy = vehicle;
            return true;
        }
        return false;
    }
    
    /**
     * Vacates the slot, making it available again.
     * 
     * @return The vehicle that was occupying the slot, or null if it was already vacant
     */
    public Vehicle vacate() {
        if (isOccupied) {
            Vehicle vehicle = this.occupiedBy;
            this.isOccupied = false;
            this.occupiedBy = null;
            return vehicle;
        }
        return null;
    }
    
    @Override
    public int compareTo(Slot other) {
        // Compare slots based on priority level (lower number means higher priority)
        return Integer.compare(this.priorityLevel, other.priorityLevel);
    }
    
    @Override
    public String toString() {
        return "Slot{" +
                "slotId='" + slotId + '\'' +
                ", location='" + location + '\'' +
                ", priorityLevel=" + priorityLevel +
                ", isOccupied=" + isOccupied +
                ", occupiedBy=" + (occupiedBy != null ? occupiedBy.getLicensePlate() : "none") +
                '}';
    }
}