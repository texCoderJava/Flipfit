package com.interview.machinecoding;

import com.interview.machinecoding.dao.BookingDao;
import com.interview.machinecoding.dao.BookingDaoImpl;
import com.interview.machinecoding.dao.CenterDao;
import com.interview.machinecoding.dao.CenterDaoImpl;
import com.interview.machinecoding.dao.SlotDao;
import com.interview.machinecoding.dao.SlotDaoImpl;
import com.interview.machinecoding.dao.UserDao;
import com.interview.machinecoding.dao.UserDaoImpl;
import com.interview.machinecoding.dao.WorkoutDao;
import com.interview.machinecoding.dao.WorkoutDaoImpl;

public class MachineCodingApplication {
    public static void main(String... args){
        BookingDao bookingDao = new BookingDaoImpl();
        CenterDao centerDao = new CenterDaoImpl();
        SlotDao slotDao = new SlotDaoImpl();
        UserDao userDao = new UserDaoImpl();
        WorkoutDao workoutDao = new WorkoutDaoImpl();
    }
}
