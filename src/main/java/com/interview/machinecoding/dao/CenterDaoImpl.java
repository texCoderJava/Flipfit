package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.exception.FlipFitApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CenterDaoImpl implements CenterDao{
    Map<String,Center> centerMap = new ConcurrentHashMap<>();
    @Override
    public Center addCenter(Center center) {
        Optional.ofNullable(this.centerMap.get(center.getName())).ifPresent(existingCenter -> {
            throw new FlipFitApplicationException("Center Already Exist");
        });

        this.centerMap.put(center.getName(),center);
        return center;
    }

    @Override
    public Center getByCenterArea(String centerArea) {
        return Optional.ofNullable(this.centerMap.get(centerArea))
                .orElseThrow(() -> new FlipFitApplicationException("The center does not exist"));
    }

    @Override
    public List<Center> getAllCenters() {
        return new ArrayList<>(this.centerMap.values());
    }

    @Override
    public void updateCenter(Center center) {
        this.centerMap.put(center.getName(),center);
    }
}
