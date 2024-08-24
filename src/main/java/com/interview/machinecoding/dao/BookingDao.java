package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Booking;

import java.util.List;

public interface BookingDao {

    Booking createBooking(Booking booking);

    Booking getBookingById(String bookingId);

    void updateBooking(Booking booking);

    List<Booking> getAllBookings();
}
