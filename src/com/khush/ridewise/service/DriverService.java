package com.khush.ridewise.service;

import com.khush.ridewise.model.Driver;

import java.util.*;
import java.util.stream.Collectors;

public class DriverService {

    private final Map<String, Driver> drivers = new HashMap<>();

    public Driver registerDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
        return driver;
    }

    public Driver getDriverById(String driverId) {
        return drivers.get(driverId);
    }

    public void updateAvailability(String driverId, boolean available) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setAvailable(available);
        }
    }

    public List<Driver> getAvailableDrivers() {
        return drivers.values()
                .stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }
}
