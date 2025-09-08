/**
 * Represents a vehicle in the parking system.
 * Each vehicle has a license plate number, type, and associated user category.
 */
public class Vehicle {
    private String licensePlate;
    private String vehicleType;
    private UserCategory userCategory;
    private long entryTime;
    
    /**
     * Constructor for creating a new vehicle.
     * 
     * @param licensePlate The unique license plate number of the vehicle
     * @param vehicleType The type of vehicle (e.g., car, motorcycle, truck)
     * @param userCategory The category of the user (determines priority)
     */
    public Vehicle(String licensePlate, String vehicleType, UserCategory userCategory) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.userCategory = userCategory;
        this.entryTime = System.currentTimeMillis();
    }
    
    /**
     * Gets the license plate number of the vehicle.
     * 
     * @return The license plate number
     */
    public String getLicensePlate() {
        return licensePlate;
    }
    
    /**
     * Gets the type of the vehicle.
     * 
     * @return The vehicle type
     */
    public String getVehicleType() {
        return vehicleType;
    }
    
    /**
     * Gets the user category associated with this vehicle.
     * 
     * @return The user category
     */
    public UserCategory getUserCategory() {
        return userCategory;
    }
    
    /**
     * Gets the entry time of the vehicle into the parking system.
     * 
     * @return The entry time in milliseconds
     */
    public long getEntryTime() {
        return entryTime;
    }
    
    @Override
    public String toString() {
        return "Vehicle{" +
                "licensePlate='" + licensePlate + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", userCategory=" + userCategory +
                ", entryTime=" + entryTime +
                '}';
    }
}