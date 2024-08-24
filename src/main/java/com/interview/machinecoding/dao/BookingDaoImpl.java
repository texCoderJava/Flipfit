package com.interview.machinecoding.dao;

import com.interview.machinecoding.entities.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookingDaoImpl implements BookingDao{
    private Map<String,Booking> bookingMap = new ConcurrentHashMap<>();
    @Override
    public Booking createBooking(Booking booking) {
        return this.bookingMap.put(booking.getBookingId(),booking);
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return this.bookingMap.get(bookingId);
    }

    @Override
    public void updateBooking(Booking booking) {
        this.bookingMap.put(booking.getBookingId(),booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return new ArrayList<>(this.bookingMap.values());
    }
}
