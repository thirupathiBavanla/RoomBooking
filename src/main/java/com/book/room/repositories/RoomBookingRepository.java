package com.book.room.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.room.model.RoomBooking;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer> {
	public RoomBooking findByHotelIdAndBookingDateAndUserIdAndHotelRoomType(int hotelId, Date bookingDate, int userId, String hotelRoomType);
	public List<RoomBooking> findByHotelIdAndBookingDateAndHotelRoomType(int hotelId, Date bookingDate, String hotelRoomType);
	public List<RoomBooking> findByHotelId(int hotelId);
	public List<RoomBooking> findByUserIdAndBookingStatus(int userId, String bookingStatus);
}
