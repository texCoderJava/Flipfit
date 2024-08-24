package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Center;

import java.util.List;

public interface CenterDao {
    Center addCenter(Center center);

    Center getByCenterArea(String centerArea);
    List<Center> getAllCenters();
    void updateCenter(Center center);
}
