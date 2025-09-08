# Smart Parking Allocation System

A Java-based Smart Parking System that manages real-time allocation and deallocation of parking slots using a priority-based approach. The system assigns parking slots to vehicles based on user categories and priority levels.

## Features

- **Priority-based Slot Allocation**: Vehicles are assigned parking slots based on user categories (VIP, Handicapped, Regular, Emergency)
- **Entry/Exit Management**: Efficient tracking of vehicles entering and exiting the parking lot
- **Real-time Slot Availability**: Up-to-date information on available parking slots
- **User-based Priority Assignment**: Different priority levels for different user categories
- **Waitlist System**: Vehicles can be added to a waiting list when no slots are available
- **Logging System**: Comprehensive logging of all parking activities
- **Admin Panel**: Administrative functions for managing the parking lot

## Core Data Structures & Algorithms

- **Priority Queue**: Used to assign the highest-priority slot to incoming vehicles
- **HashMap**: Maps vehicle numbers to slot details for quick lookup
- **LinkedList**: Maintains waitlist for vehicles when no slots are available
- **OOP Principles**: Encapsulation, inheritance, and polymorphism for clean code organization
- **File Handling**: Stores parking logs persistently

## Class Structure

- **UserCategory**: Enum representing different user categories with priority levels
- **Vehicle**: Represents a vehicle with license plate, type, and user category
- **Slot**: Represents a parking slot with ID, location, and priority level
- **ParkingLot**: Manages slots and implements priority queue for allocation
- **ParkingSystem**: Main class providing console interface for user interaction

## How to Run

1. Ensure you have Java Development Kit (JDK) installed on your system
2. Compile all Java files:
   ```
   javac *.java
   ```
3. Run the ParkingSystem class:
   ```
   java ParkingSystem
   ```
4. Follow the on-screen instructions to interact with the system

## System Workflow

1. **Register a Vehicle**: Add a new vehicle to the system with its details
2. **Park a Vehicle**: Allocate a parking slot to a registered vehicle
3. **Exit a Vehicle**: Process a vehicle leaving the parking lot
4. **Display Status**: View the current status of the parking lot
5. **Admin Panel**: Access administrative functions

## Future Enhancements

- Graphical User Interface (GUI) using JavaFX
- Database integration for persistent storage
- Mobile application for remote slot booking
- Payment system integration
- Automated license plate recognition

## Project Structure

```
├── ParkingSystem.java    # Main class with console UI
├── ParkingLot.java       # Core logic for slot management
├── Slot.java             # Parking slot representation
├── Vehicle.java          # Vehicle representation
├── UserCategory.java     # User categories enum
├── parking_log.txt       # Log file for parking activities
└── README.md             # Project documentation
```