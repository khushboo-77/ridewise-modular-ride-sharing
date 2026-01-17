# Class Model — RideWise

This document describes the core domain classes and their responsibilities.

---

## 1. Domain Package: model/

### Rider
Represents a customer who requests rides.

**Fields**
- id: String
- name: String
- location: String

**Responsibilities**
- Store rider information

---

### Driver
Represents a driver available to take rides.

**Fields**
- id: String
- name: String
- currentLocation: String
- available: boolean
- vehicleType: VehicleType

**Responsibilities**
- Store driver details
- Track driver availability

---

### Ride
Represents a single ride request and its lifecycle.

**Fields**
- id: String
- rider: Rider
- driver: Driver (nullable until assigned)
- distance: double
- status: RideStatus
- createdAt: LocalDateTime

**Responsibilities**
- Track ride lifecycle
- Bind rider + driver + status

---

### FareReceipt
Represents the fare outcome for a completed ride.

**Fields**
- rideId: String
- amount: double
- generatedAt: LocalDateTime

**Responsibilities**
- Hold fare amount and generation timestamp

---

## 2. Enums Package: enums/

### RideStatus
Possible states of a ride:
- REQUESTED
- ASSIGNED
- COMPLETED
- CANCELLED

---

### VehicleType
Supported vehicle categories:
- BIKE
- AUTO
- CAR

---

## 3. Strategy Package: strategy/

### RideMatchingStrategy (interface)

```java
Driver findDriver(Rider rider, List<Driver> drivers);
```

**Purpose**
- Defines the algorithm used to match a ride request to a driver.
- Enables replacing matching logic without changing core RideService.

**Implementations**
- NearestDriverStrategy
- LeastActiveDriverStrategy

---

### NearestDriverStrategy
Matches the rider with the nearest available driver.

**Key Logic**
- Prefer driver whose `currentLocation` equals rider `location`
- Otherwise assign first available driver as fallback

**Responsibilities**
- Provide proximity-based driver assignment

---

### LeastActiveDriverStrategy
Matches the rider with the least active available driver.

**Key Logic**
- Select available driver with minimum completed ride count
- Uses `driverRideCounts` maintained in service layer

**Responsibilities**
- Ensure fair ride distribution among drivers

---

### FareStrategy (interface)

```java
double calculateFare(Ride ride);
```

**Purpose**
- Defines pricing logic for a ride.
- Allows fare calculation to be extended without modifying RideService.

**Implementations**
- DefaultFareStrategy
- PeakHourFareStrategy

---

### DefaultFareStrategy
Standard pricing algorithm.

**Key Logic**
- Fare = Base Fare + (distance × per km rate)

**Responsibilities**
- Compute default ride fare

---

### PeakHourFareStrategy
Pricing modifier for peak hours.

**Key Logic**
- Apply multiplier during peak hours (e.g., 6PM–9PM)
- Otherwise fall back to base fare

**Responsibilities**
- Increase fare dynamically for peak-hour demand
