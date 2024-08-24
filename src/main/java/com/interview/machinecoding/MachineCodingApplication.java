package com.interview.machinecoding;

import com.interview.machinecoding.business.BookingService;
import com.interview.machinecoding.business.BookingServiceImpl;
import com.interview.machinecoding.business.CenterService;
import com.interview.machinecoding.business.CenterServiceImpl;
import com.interview.machinecoding.business.CenterWorkoutService;
import com.interview.machinecoding.business.CenterWorkoutServiceImpl;
import com.interview.machinecoding.business.SlotService;
import com.interview.machinecoding.business.SlotServiceImpl;
import com.interview.machinecoding.business.UserService;
import com.interview.machinecoding.business.UserServiceImpl;
import com.interview.machinecoding.business.WorkoutService;
import com.interview.machinecoding.business.WorkoutServiceImpl;
import com.interview.machinecoding.business.strategy.BookingStrategy;
import com.interview.machinecoding.business.strategy.NormalUserBookingStrategy;
import com.interview.machinecoding.business.strategy.VIPBookingStrategy;
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
import com.interview.machinecoding.entities.Location;
import com.interview.machinecoding.util.LocationUtil;

public class MachineCodingApplication {
    public static void main(String... args){
        BookingDao bookingDao = new BookingDaoImpl();
        CenterDao centerDao = new CenterDaoImpl();
        SlotDao slotDao = new SlotDaoImpl();
        UserDao userDao = new UserDaoImpl();
        WorkoutDao workoutDao = new WorkoutDaoImpl();

        WorkoutService workoutService = new WorkoutServiceImpl(workoutDao);
        CenterService centerService = new CenterServiceImpl(centerDao);
        CenterWorkoutService centerWorkoutService = new CenterWorkoutServiceImpl(centerService,workoutService);

        UserService userService = new UserServiceImpl(userDao);

        SlotService slotService = new SlotServiceImpl(slotDao,centerService,workoutService);

        BookingStrategy vipBookingStrategy = new VIPBookingStrategy(userService,slotService);
        BookingStrategy normalBookingStrategy = new NormalUserBookingStrategy(userService,slotService);

        BookingService bookingService = new BookingServiceImpl(bookingDao,slotService,userService,centerService,vipBookingStrategy,normalBookingStrategy);

        //=================================================================================================================================================//

    }
}
