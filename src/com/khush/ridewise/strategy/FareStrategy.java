package com.khush.ridewise.strategy;

import com.khush.ridewise.model.Ride;

public interface FareStrategy {
    double calculateFare(Ride ride);
}
