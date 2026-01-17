package com.khush.ridewise.service;

import com.khush.ridewise.enums.RideStatus;
import com.khush.ridewise.exception.NoDriverAvailableException;
import com.khush.ridewise.model.Driver;
import com.khush.ridewise.model.FareReceipt;
import com.khush.ridewise.model.Ride;
import com.khush.ridewise.model.Rider;
import com.khush.ridewise.strategy.FareStrategy;
import com.khush.ridewise.strategy.RideMatchingStrategy;
import com.khush.ridewise.util.IdGenerator;

import java.util.*;

public class RideService {

    private final RiderService riderService;
    private final DriverService driverService;
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;

    // Ride storage
    private final Map<String, Ride> rides = new LinkedHashMap<>();

    // For LeastActiveDriverStrategy support
    private final Map<String, Integer> driverRideCounts = new HashMap<>();

    public RideService(RiderService riderService,
                       DriverService driverService,
                       RideMatchingStrategy matchingStrategy,
                       FareStrategy fareStrategy) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    /**
     * Request a ride:
     * 1) validate rider
     * 2) create ride (REQUESTED)
     * 3) match driver using strategy
     * 4) assign ride + mark driver unavailable
     */
    public Ride requestRide(String riderId, double distance) {
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            throw new IllegalArgumentException("Invalid rider ID: " + riderId);
        }

        Ride ride = new Ride(IdGenerator.generateId("RIDE"), rider, distance);

        List<Driver> availableDrivers = driverService.getAvailableDrivers();
        Driver matchedDriver = matchingStrategy.findDriver(rider, availableDrivers);

        if (matchedDriver == null) {
            ride.cancel();
            rides.put(ride.getId(), ride);
            throw new NoDriverAvailableException("No drivers available right now.");
        }

        ride.assignDriver(matchedDriver);
        matchedDriver.setAvailable(false); // driver locked

        rides.put(ride.getId(), ride);
        return ride;
    }

    /**
     * Complete ride:
     * 1) validate ride exists
     * 2) ride must be ASSIGNED
     * 3) mark completed
     * 4) mark driver available
     * 5) generate receipt
     */
    public FareReceipt completeRide(String rideId) {
        Ride ride = rides.get(rideId);

        if (ride == null) {
            throw new IllegalArgumentException("Invalid ride ID: " + rideId);
        }

        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Ride is not in ASSIGNED state: " + ride.getStatus());
        }

        ride.complete();

        // release driver
        Driver driver = ride.getDriver();
        if (driver != null) {
            driver.setAvailable(true);
            driverRideCounts.put(driver.getId(), driverRideCounts.getOrDefault(driver.getId(), 0) + 1);
        }

        double fare = fareStrategy.calculateFare(ride);
        return new FareReceipt(ride.getId(), fare);
    }

    public Ride getRideById(String rideId) {
        return rides.get(rideId);
    }

    public List<Ride> getAllRides() {
        return new A
