package com.khush.ridewise.strategy;

import com.khush.ridewise.model.Driver;
import com.khush.ridewise.model.Rider;

import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        if (drivers == null || drivers.isEmpty()) return null;

        Driver fallback = null;

        for (Driver driver : drivers) {
            if (!driver.isAvailable()) continue;

            // exact match = nearest
            if (driver.getCurrentLocation().equalsIgnoreCase(rider.getLocation())) {
                return driver;
            }

            // first available fallback
            if (fallback == null) {
                fallback = driver;
            }
        }
        return fallback;
    }
}
