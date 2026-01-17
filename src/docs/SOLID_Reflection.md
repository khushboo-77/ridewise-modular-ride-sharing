# SOLID Reflection — RideWise

This document explains how SOLID principles are applied in RideWise.

---

## 1. S — Single Responsibility Principle (SRP)

Each class has a single reason to change:

- Rider, Driver, Ride, FareReceipt → only represent domain data
- RiderService → rider management only
- DriverService → driver management only
- RideService → orchestrates booking lifecycle only
- Strategies → matching/pricing logic only

This prevents "God classes" and improves testability.

---

## 2. O — Open/Closed Principle (OCP)

The system is extendable without modifying stable core logic:

✅ Add new driver allocation strategy:
- Create a new class implementing RideMatchingStrategy
- Inject into RideService

✅ Add new pricing algorithm:
- Create new class implementing FareStrategy
- Inject into RideService

RideService remains unchanged.

---

## 3. L — Liskov Substitution Principle (LSP)

All strategy implementations are interchangeable.

RideService can use any:
- RideMatchingStrategy implementation
- FareStrategy implementation

without behavioral breakage.

---

## 4. I — Interface Segregation Principle (ISP)

Interfaces are small and focused:

- RideMatchingStrategy contains only matching logic
- FareStrategy contains only pricing logic

No unnecessary methods are forced on implementations.

---

## 5. D — Dependency Inversion Principle (DIP)

RideService depends on abstractions, not concrete classes:

- RideMatchingStrategy
- FareStrategy

Concrete strategies are injected during construction, enabling:
- loose coupling
- easier testing
- runtime swapping of strategies

---

## Additional Design Principles

### DRY (Don't Repeat Yourself)
- Centralized ride creation & driver allocation in RideService
- Shared fare calculations via strategy composition

### KISS (Keep It Simple)
- Locations modeled as String for MVP
- Simple console I/O

### YAGNI (You Aren't Gonna Need It)
- No DB / networking / UI layer added
- No premature optimization

### Law of Demeter
- Main interacts only with services
- Services interact only with direct collaborators
- No deep chaining across objects
