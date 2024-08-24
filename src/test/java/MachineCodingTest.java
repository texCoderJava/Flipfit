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
import com.interview.machinecoding.entities.Booking;
import com.interview.machinecoding.entities.Center;
import com.interview.machinecoding.entities.Slot;
import com.interview.machinecoding.entities.User;
import com.interview.machinecoding.entities.Workout;
import com.interview.machinecoding.model.UserType;
import com.interview.machinecoding.util.LocationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class MachineCodingTest {
    private BookingDao bookingDao;
    private CenterDao centerDao;
    private SlotDao slotDao;
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private WorkoutService workoutService;
    private CenterService centerService;
    private CenterWorkoutService centerWorkoutService;
    private UserService userService;
    private SlotService slotService;
    private BookingStrategy vipBookingStrategy;
    private BookingStrategy normalBookingStrategy;
    private BookingService bookingService;
    @BeforeEach
    public void init(){
        this.bookingDao = new BookingDaoImpl();
        this.centerDao = new CenterDaoImpl();
        this.slotDao = new SlotDaoImpl();
        this.userDao = new UserDaoImpl();
        this.workoutDao = new WorkoutDaoImpl();

        this.workoutService = new WorkoutServiceImpl(workoutDao);
        this.centerService = new CenterServiceImpl(centerDao);
        this.centerWorkoutService = new CenterWorkoutServiceImpl(centerService,workoutService);

        this.userService = new UserServiceImpl(userDao);

        this.slotService = new SlotServiceImpl(slotDao,centerService,workoutService);

        this.vipBookingStrategy = new VIPBookingStrategy(userService,slotService);
        this.normalBookingStrategy = new NormalUserBookingStrategy(userService,slotService);

        this.bookingService = new BookingServiceImpl(bookingDao,slotService,userService,centerService,vipBookingStrategy,normalBookingStrategy);
    }

    @Test
    public void addNewCenter(){
        Center center = this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());

        Assertions.assertNotNull(center);
        Assertions.assertFalse(StringUtils.isBlank(center.getId()));
    }

    @Test
    public void addNewWorkout(){
        Center center = this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());

        this.workoutService.addNewWorkOut("Yoga");

        this.centerWorkoutService.addWorkoutToCenter(center.getName(),"Yoga");

        center = this.centerService.getCenterByArea("Koramangla");

        Assertions.assertTrue(center.getWorkouts().size() > 0);
        Assertions.assertEquals("Yoga",center.getWorkouts().get(0).getWorkoutName());
    }

    @Test
    public void addSlot(){
        this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());
        this.workoutService.addNewWorkOut("Yoga");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Yoga");

        Slot slot = this.slotService.addSlot("Koramangla","Yoga", LocalTime.now(),5,false);
        Assertions.assertNotNull(slot);

        Center center = this.centerService.getCenterByArea("Koramangla");

        Assertions.assertEquals(1, center.getWorkouts().size());
    }

    @Test
    public void deleteSlot(){
        LocalTime startTime = LocalTime.now();

        this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());
        this.workoutService.addNewWorkOut("Yoga");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Yoga");

        Slot slot = this.slotService.addSlot("Koramangla","Yoga", startTime,5,false );
        Assertions.assertNotNull(slot);

        Center center = this.centerService.getCenterByArea("Koramangla");

        Assertions.assertEquals(1, center.getWorkouts().size());

        this.slotService.deleteSlot("Koramangla","Yoga", startTime);

        Center updatedCenter = this.centerService.getCenterByArea("Koramangla");

        Assertions.assertEquals(1, updatedCenter.getWorkouts().size());
    }

    @Test
    public void detailsOfWorkOutAvailableAtACenter(){
        this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());
        this.workoutService.addNewWorkOut("Yoga");
        this.workoutService.addNewWorkOut("Weights");
        this.workoutService.addNewWorkOut("Cardio");

        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Yoga");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Weights");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Cardio");

        List<Workout> workout = this.centerService.getCenterByArea("Koramangla").getWorkouts();

        Assertions.assertTrue(workout.size() > 0);
        workout.forEach(currentWorkout -> System.out.println(currentWorkout.getWorkoutName()));
    }

    @Test
    public void detailsOfSlotAcrossAllWorkoutType(){
        User samUser = this.userService.registerUser("Sam", UserType.NORMAL);
        User tomUser = this.userService.registerUser("Tom", UserType.VIP);


        this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());
        this.workoutService.addNewWorkOut("Yoga");
        this.workoutService.addNewWorkOut("Weights");
        this.workoutService.addNewWorkOut("Cardio");

        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Yoga");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Weights");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Cardio");

        Slot yogaSlot = this.slotService.addSlot("Koramangla","Yoga", LocalTime.now(),5,false );
        Slot weightsSlot = this.slotService.addSlot("Koramangla","Weights", LocalTime.now(),5,false );
        Slot cardioSlot = this.slotService.addSlot("Koramangla","Cardio", LocalTime.now(),5,true );

        Map<String, List<Slot>> slotByWorkoutMap = this.bookingService.viewSlots("Koramangla",samUser.getUserId());

        Assertions.assertTrue(slotByWorkoutMap.size() > 0);
        Assertions.assertEquals(1,slotByWorkoutMap.get("Yoga").size());
        Assertions.assertEquals(1,slotByWorkoutMap.get("Weights").size());
        Assertions.assertEquals(0,slotByWorkoutMap.get("Cardio").size());

        Map<String, List<Slot>> slotByWorkoutMapWithVip = this.bookingService.viewSlots("Koramangla",tomUser.getUserId());

        Assertions.assertTrue(slotByWorkoutMapWithVip.size() > 0);
        Assertions.assertEquals(1,slotByWorkoutMapWithVip.get("Yoga").size());
        Assertions.assertEquals(1,slotByWorkoutMapWithVip.get("Weights").size());
        Assertions.assertEquals(1,slotByWorkoutMapWithVip.get("Cardio").size());
    }

    @Test
    public void bookSlotByCenter(){
        User samUser = this.userService.registerUser("Sam", UserType.NORMAL);
        User tomUser = this.userService.registerUser("Tom", UserType.VIP);


        this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());
        this.workoutService.addNewWorkOut("Yoga");
        this.workoutService.addNewWorkOut("Weights");
        this.workoutService.addNewWorkOut("Cardio");

        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Yoga");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Weights");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Cardio");

        Slot yogaSlot = this.slotService.addSlot("Koramangla","Yoga", LocalTime.now(),5,false );
        Slot weightsSlot = this.slotService.addSlot("Koramangla","Weights", LocalTime.now(),5,false );
        Slot cardioSlot = this.slotService.addSlot("Koramangla","Cardio", LocalTime.now(),5,true );

        boolean isSlotBookedForSam = this.bookingService.bookSlot(samUser.getUserId(),yogaSlot.getSlotId(),"Yoga","Koramangla");

        Assertions.assertTrue(isSlotBookedForSam);

        boolean isSlotBookedForTom = this.bookingService.bookSlot(tomUser.getUserId(),cardioSlot.getSlotId(),"Cardio","Koramangla");

        Assertions.assertTrue(isSlotBookedForTom);

        boolean isSlotBookedForTomForWeights = this.bookingService.bookSlot(tomUser.getUserId(),weightsSlot.getSlotId(),"Weights","Koramangla");

        Assertions.assertTrue(isSlotBookedForTomForWeights);
    }

    @Test
    public void testCancelBookingWithVipUserAssignment(){
        User samUser = this.userService.registerUser("Sam", UserType.NORMAL);
        User tomUser = this.userService.registerUser("Tom", UserType.VIP);
        User kitUser = this.userService.registerUser("kit", UserType.VIP);


        this.centerService.addCenter("Koramangla","Bangalore", LocationUtil.generateRandomLocation());
        this.workoutService.addNewWorkOut("Yoga");
        this.workoutService.addNewWorkOut("Weights");
        this.workoutService.addNewWorkOut("Cardio");

        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Yoga");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Weights");
        this.centerWorkoutService.addWorkoutToCenter("Koramangla","Cardio");

        Slot yogaSlot = this.slotService.addSlot("Koramangla","Yoga", LocalTime.now(),2,false );

        boolean isSlotBookedForSam = this.bookingService.bookSlot(samUser.getUserId(),yogaSlot.getSlotId(),"Yoga","Koramangla");

        Assertions.assertTrue(isSlotBookedForSam);

        boolean isSlotBookedForTom = this.bookingService.bookSlot(tomUser.getUserId(),yogaSlot.getSlotId(),"Yoga","Koramangla");

        Assertions.assertTrue(isSlotBookedForTom);

        boolean isSlotBookedForKit = this.bookingService.bookSlot(kitUser.getUserId(),yogaSlot.getSlotId(),"Yoga","Koramangla");

        Assertions.assertFalse(isSlotBookedForKit);

        this.bookingService.cancelBooking(tomUser.getUserId(),yogaSlot.getSlotId(),"Koramangla");

        List< Booking> bookings = this.userService.getUserById(kitUser.getUserId()).getBookings();

        Assertions.assertEquals(1, bookings.size());

    }
}
