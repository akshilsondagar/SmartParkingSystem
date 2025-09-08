/**
 * Enum representing different user categories with their priority levels.
 * Higher priority users get preferential parking slot allocation.
 */
public enum UserCategory {
    EMERGENCY(1),   // Highest priority for emergency vehicles
    VIP(2),         // Second highest priority for VIP users
    HANDICAPPED(3), // Third highest priority for handicapped users
    REGULAR(4);     // Lowest priority for regular users
    
    private final int priorityLevel;
    
    UserCategory(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    
    public int getPriorityLevel() {
        return priorityLevel;
    }
}