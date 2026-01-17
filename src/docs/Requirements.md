# RideWise — Requirements

## 1. Project Summary
RideWise is a simplified, console-based Ride-Sharing system (Uber/Ola style).  
Users can:
- Register riders and drivers
- View available drivers
- Request a ride
- Match rides to drivers using a pluggable strategy
- Calculate fare using a pluggable pricing strategy
- Track ride status lifecycle

This project focuses on **Low Level Design (LLD)** and clean engineering practices:
- Strategy Pattern (composition over inheritance)
- SOLID Principles
- Low coupling / High cohesion
- Maintainable, extendable design

---

## 2. Functional Requirements (FR)

### FR1 — Register Rider
- System should allow registering a rider with:
    - id
    - name
    - location

### FR2 — Register Driver
- System should allow registering a driver with:
    - id
    - name
    - currentLocation
    - vehicleType
    - available (default true)

### FR3 — Show Available Drivers
- System should list only drivers where:
    - available == true

### FR4 — Request Ride
- Rider requests ride by:
    - riderId
    - distance
- Ride status begins as `REQUESTED`
- Ride gets assigned a driver using ride matching strategy
- If no driver is available → ride cancelled + exception

### FR5 — Ride Matching Strategy
- Driver assignment must be decided via:
    - RideMatchingStrategy interface
- Strategy should be injectable into RideService

### FR6 — Fare Calculation Strategy
- Fare must be calculated using:
    - FareStrategy interface
- Strategy should be injectable into RideService

### FR7 — Track Ride Status
Ride lifecycle must support:
- REQUESTED
- ASSIGNED
- COMPLETED
- CANCELLED

### FR8 — Complete Ride
- Completing ride should:
    - update ride status to COMPLETED
    - mark driver available again
    - generate FareReceipt

---

## 3. Non-Functional Requirements (NFR)

### NFR1 — Extendable Pricing Algorithm
- System should allow adding new fare strategies without modifying RideService core logic.

### NFR2 — Replaceable Driver Matching Logic
- System should allow switching between driver matching strategies at runtime.

### NFR3 — Low Coupling
- Services should depend on interfaces (not concrete implementations).

### NFR4 — Maintainability
- Clean readable code
- Proper separation of concerns using layers

---

## 4. Console Menu Requirements

Main menu in Main.java:
1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View Rides
0. Exit

Menu must:
- use service-layer only
- handle invalid inputs
- avoid tightly coupled logic
