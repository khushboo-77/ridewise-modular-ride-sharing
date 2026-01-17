package com.khush.ridewise;

import com.khush.ridewise.enums.VehicleType;
import com.khush.ridewise.exception.NoDriverAvailableException;
import com.khush.ridewise.model.Driver;
import com.khush.ridewise.model.FareReceipt;
import com.khush.ridewise.model.Ride;
import com.khush.ridewise.model.Rider;
import com.khush.ridewise.service.DriverService;
import com.khush.ridewise.service.RideService;
import com.khush.ridewise.service.RiderService;
import com.khush.ridewise.strategy.DefaultFareStrategy;
import com.khush.ridewise.strategy.FareStrategy;
import com.khush.ridewise.strategy.NearestDriverStrategy;
import com.khush.ridewise.strategy.PeakHourFareStrategy;
import com.khush.ridewise.strategy.RideMatchingStrategy;
import com.khush.ridewise.util.IdGenerator;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Services
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        // Strategies
        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();

        // we can swap strategies easily (OCP + DIP)
        FareStrategy fareStrategy = new DefaultFareStrategy();
        // FareStrategy fareStrategy = new PeakHourFareStrategy(new DefaultFareStrategy());

        RideService rideService = new RideService(
                riderService,
                driverService,
                matchingStrategy,
                fareStrategy
        );

        while (true) {
            System.out.println("\n=== RideWise Menu ===");
            System.out.println("1. Add Rider");
            System.out.println("2. Add Driver");
            System.out.println("3. View Available Drivers");
            System.out.println("4. Request Ride");
            System.out.println("5. Complete Ride");
            System.out.println("6. View Rides");
            System.out.println("0. Exit");

            System.out.print("Choose: ");
            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    addRider(sc, riderService);
                    break;
                case 2:
                    addDriver(sc, driverService);
                    break;
                case 3:
                    viewAvailableDrivers(driverService);
                    break;
                case 4:
                    requestRide(sc, rideService);
                    break;
                case 5:
                    completeRide(sc, rideService);
                    break;
                case 6:
                    viewRides(rideService);
                    break;
                case 0:
                    System.out.println("Exiting RideWise...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addRider(Scanner sc, RiderService riderService) {
        System.out.print("Enter rider name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter rider location: ");
        String location = sc.nextLine().trim();

        String id = IdGenerator.generateId("RIDER");
        Rider rider = new Rider(id, name, location);
        riderService.registerRider(rider);

        System.out.println("Rider registered: " + rider);
    }

    private static void addDriver(Scanner sc, DriverService driverService) {
        System.out.print("Enter driver name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter driver current location: ");
        String location = sc.nextLine().trim();

        VehicleType vehicleType = readVehicleType(sc);

        String id = IdGenerator.generateId("DRIVER");
        Driver driver = new Driver(id, name, location, vehicleType);
        driverService.registerDriver(driver);

        System.out.println("Driver registered: " + driver);
    }

    private static void viewAvailableDrivers(DriverService driverService) {
        List<Driver> available = driverService.getAvailableDrivers();

        if (available.isEmpty()) {
            System.out.println("No available drivers right now.");
            return;
        }

        System.out.println("\n--- Available Drivers ---");
        for (Driver d : available) {
            System.out.println(d);
        }
    }

    private static void requestRide(Scanner sc, RideService rideService) {
        try {
            System.out.print("Enter rider ID: ");
            String riderId = sc.nextLine().trim();

            System.out.print("Enter distance (km): ");
            double distance = readDouble(sc);

            Ride ride = rideService.requestRide(riderId, distance);
            System.out.println("Ride requested successfully!");
            System.out.println("Ride Details: " + ride);

        } catch (NoDriverAvailableException e) {
            System.out.println("Error" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void completeRide(Scanner sc, RideService rideService) {
        try {
            System.out.print("Enter ride ID to complete: ");
            String rideId = sc.nextLine().trim();

            FareReceipt receipt = rideService.completeRide(rideId);

            System.out.println("Ride completed!");
            System.out.println("Receipt: " + receipt);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewRides(RideService rideService) {
        List<Ride> rides = rideService.getAllRides();

        if (rides.isEmpty()) {
            System.out.println("No rides yet.");
            return;
        }

        System.out.println("\n--- Ride History ---");
        for (Ride r : rides) {
            System.out.println(r);
        }
    }

    //INPUT HELPERS
    private static int readInt(Scanner sc) {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.print("Invalid number. Enter again: ");
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Double.parseDouble(input);
            } catch (Exception e) {
                System.out.print("Invalid number. Enter again: ");
            }
        }
    }

    private static VehicleType readVehicleType(Scanner sc) {
        while (true) {
            System.out.println("Select Vehicle Type:");
            System.out.println("1. BIKE");
            System.out.println("2. AUTO");
            System.out.println("3. CAR");
            System.out.print("Choose: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    return VehicleType.BIKE;
                case 2:
                    return VehicleType.AUTO;
                case 3:
                    return VehicleType.CAR;
                default:
                    System.out.println("Invalid vehicle type. Try again.");
            }
        }
    }
}
