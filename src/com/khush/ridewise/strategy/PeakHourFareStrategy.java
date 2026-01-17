package com.khush.ridewise.strategy;

import com.khush.ridewise.model.Ride;

import java.time.LocalTime;

public class PeakHourFareStrategy implements FareStrategy {

    private final FareStrategy baseFareStrategy;

    public PeakHourFareStrategy(FareStrategy baseFareStrategy) {
        this.baseFareStrategy = baseFareStrategy;
    }

    @Override
    public double calculateFare(Ride ride) {
        double baseFare = baseFareStrategy.calculateFare(ride);

        LocalTime now = LocalTime.now();
        boolean isPeak = !now.isBefore(LocalTime.of(18, 0)) && now.isBefore(LocalTime.of(21, 0));

        return isPeak ? baseFare * 1.5 : baseFare;
    }
}
