package com.book.room.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.book.room.model.RoomBooking;

@Repository
public class HotelBookingDAOImpl {
	
//	@Autowired
//	private HotelBookingDAO hotelBookingDAO;
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
	public List<RoomBooking> getHotelRoomBookings(int hotelId) {
		List<RoomBooking> hotelRoomBookings = new ArrayList<>();
//		List<Map<String, Object>> hotelRoomBookings = jdbcTemplate.queryForList("select * from HOTELROOMBOOKING");
//		hotelBookingDAO.findAll().forEach(hotelRoomBookings::add);
		return hotelRoomBookings;
	}
	
//	public List<Map<String, Object>> getHotelRoomBookings(int hotelId) {
////		List<HotelRoomBooking> hotelRoomBookings = new ArrayList<>();
//		List<Map<String, Object>> hotelRoomBookings = jdbcTemplate.queryForList("select * from HOTELROOMBOOKING");
////		hotelBookingDAO.findAll().forEach(hotelRoomBookings::add);
//		return hotelRoomBookings;
//	}

}
