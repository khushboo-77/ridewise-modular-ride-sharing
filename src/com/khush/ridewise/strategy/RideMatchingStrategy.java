package com.khush.ridewise.strategy;

import com.khush.ridewise.model.Driver;
import com.khush.ridewise.model.Rider;

import java.util.List;

public interface RideMatchingStrategy {
    Driver findDriver(Rider rider, List<Driver> drivers);
}
