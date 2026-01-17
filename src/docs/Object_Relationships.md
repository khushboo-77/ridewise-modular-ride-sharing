
---

## ✅ `docs/Object_Relationships.md`

```md
# Object Relationships — RideWise

This document explains entity relationships and data flow across the system.

---

## 1. Entity Relationships

### Rider ↔ Ride
- A Rider can request many rides over time
- Each Ride has exactly one Rider

**Relationship**
- Rider (1) → Ride (many)

---

### Driver ↔ Ride
- A Driver can complete many rides over time
- Each Ride has at most one Driver
- Driver is assigned only after matching strategy runs

**Relationship**
- Driver (1) → Ride (many)
- Ride (1) → Driver (0..1)

---

### Ride ↔ FareReceipt
- A Ride generates exactly one FareReceipt when completed

**Relationship**
- Ride (1) → FareReceipt (1)

---

## 2. Lifecycle Relationships

### Driver Availability
- When ride is assigned:
  - Driver.available = false
- When ride is completed:
  - Driver.available = true

This ensures:
- no double-allocation of drivers
- accurate "available drivers" list

---

## 3. Flow for Request Ride

1. Rider requests ride (riderId + distance)
2. RideService creates Ride with status REQUESTED
3. RideService fetches available drivers from DriverService
4. RideService delegates assignment to RideMatchingStrategy
5. If driver found:
   - Ride.assignDriver(driver)
   - Ride status becomes ASSIGNED
   - Driver.available becomes false
6. If no driver:
   - Ride status becomes CANCELLED
   - throw NoDriverAvailableException

---

## 4. Flow for Complete Ride

1. RideService finds ride by rideId
2. Validates ride is in ASSIGNED state
3. Marks ride COMPLETED
4. Releases driver (available = true)
5. Calculates fare using FareStrategy
6. Generates FareReceipt

---

## 5. Strategy Relationships (Composition)

RideService does not know concrete classes. It depends on interfaces:
- RideMatchingStrategy
- FareStrategy

This enables runtime switching:
- NearestDriverStrategy → LeastActiveDriverStrategy
- DefaultFareStrategy → PeakHourFareStrategy
