package com.khush.ridewise.strategy;

public class LeastActiveDriverStrategy {
}
package com.khush.ridewise.strategy;

import com.khush.ridewise.model.Driver;
import com.khush.ridewise.model.Rider;

import java.util.List;
import java.util.Map;

public class LeastActiveDriverStrategy implements RideMatchingStrategy {

    private final Map<String, Integer> driverRideCounts;

    public LeastActiveDriverStrategy(Map<String, Integer> driverRideCounts) {
        this.driverRideCounts = driverRideCounts;
    }

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        Driver best = null;
        int minRides = Integer.MAX_VALUE;

        for (Driver driver : drivers) {
            if (!driver.isAvailable()) continue;

            int rides = driverRideCounts.getOrDefault(driver.getId(), 0);

            if (rides < minRides) {
                minRides = rides;
                best = driver;
            }
        }
        return best;
    }
}
