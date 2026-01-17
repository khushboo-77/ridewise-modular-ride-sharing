package com.khush.ridewise.model;

import com.khush.ridewise.enums.RideStatus;

import java.time.LocalDateTime;

public class Ride {
    private final String id;
    private final Rider rider;
    private Driver driver;
    private final double distance;
    private RideStatus status;
    private final LocalDateTime createdAt;

    public Ride(String id, Rider rider, double distance) {
        this.id = id;
        this.rider = rider;
        this.distance = distance;
        this.status = RideStatus.REQUESTED;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public double getDistance() {
        return distance;
    }

    public RideStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void assignDriver(Driver driver) {
        this.driver = driver;
        this.status = RideStatus.ASSIGNED;
    }

    public void complete() {
        this.status = RideStatus.COMPLETED;
    }

    public void cancel() {
        this.status = RideStatus.CANCELLED;
    }

    @Override
    public String toString() {
        String driverInfo = (driver == null) ? "None" : driver.getName() + "(" + driver.getId() + ")";
        return "Ride{id='" + id + "', rider=" + rider.getName() +
                ", driver=" + driverInfo +
                ", distance=" + distance +
                ", status=" + status +
                ", createdAt=" + createdAt + "}";
    }
}
