package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Center;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CenterDaoImpl implements CenterDao{
    Map<String,Center> centerMap = new ConcurrentHashMap<>();
    @Override
    public Center addCenter(Center center) {
        return this.centerMap.put(center.getName(),center);
    }

    @Override
    public Center getByCenterArea(String centerArea) {
        return this.centerMap.get(centerArea);
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
