package com.khush.ridewise.model;

import com.khush.ridewise.enums.VehicleType;

public class Driver {
    private final String id;
    private String name;
    private String currentLocation;
    private boolean available;
    private VehicleType vehicleType;

    public Driver(String id, String name, String currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
        this.available = true; // default
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "Driver{id='" + id + "', name='" + name + "', currentLocation='" + currentLocation +
                "', vehicleType=" + vehicleType + ", available=" + available + "}";
    }
}
