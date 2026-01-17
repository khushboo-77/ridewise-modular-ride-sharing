package com.khush.ridewise.service;

import com.khush.ridewise.model.Rider;

import java.util.*;

public class RiderService {

    private final Map<String, Rider> riders = new HashMap<>();

    public Rider registerRider(Rider rider) {
        riders.put(rider.getId(), rider);
        return rider;
    }

    public Rider getRiderById(String riderId) {
        return riders.get(riderId);
    }

    public List<Rider> getAllRiders() {
        return new ArrayList<>(riders.values());
    }
}
