package com.interview.machinecoding.util;

import com.interview.machinecoding.entities.Location;

import java.util.Random;

public class LocationUtil {

    public static Location generateRandomLocation(){
        Random random = new Random();
        return new Location(-90 + (90 - (-90)) * random.nextDouble() ,
                -180 + (180 - (-180)) * random.nextDouble());
    }

    public static double calculateDistance(Location location1,Location location2){
        double latDistance = location2.getLatitude() - location1.getLatitude();
        double longDistance = location2.getLongitude() - location1.getLongitude();

        return Math.sqrt(latDistance * latDistance + longDistance * longDistance);
    }


}
