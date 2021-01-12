package com.book.room.controller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.room.constants.Status;
import com.book.room.exception.CustomerNotFoundException;
import com.book.room.exception.HotelNotFoundException;
import com.book.room.exception.RoomTypeNotFoundException;
import com.book.room.model.BookingRoomRequest;
import com.book.room.model.Customer;
import com.book.room.model.Hotel;
import com.book.room.model.Room;
import com.book.room.model.RoomBooking;
import com.book.room.model.RoomType;
import com.book.room.repositories.CustomerRepository;
import com.book.room.repositories.HotelRepository;
import com.book.room.repositories.RoomBookingRepository;
import com.book.room.repositories.RoomRepository;
import com.book.room.repositories.RoomTypeRepository;

@RestController
public class BookingRoomController {
	
	@Autowired
	private RoomBookingRepository roomBookingRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping("/hotel/booking")
	public void bookHotelRoom(@Valid @RequestBody BookingRoomRequest hotelBookingRequest) 
			throws HotelNotFoundException, RoomTypeNotFoundException, CustomerNotFoundException, Exception {
		// check hotel with name exists
		String hotelName = hotelBookingRequest.getHotelName();
		Hotel hotel = hotelRepository.findByName(hotelName);
		if(hotel == null) {
			throw new HotelNotFoundException("Hotel "+hotelName+" not found.");
		}
		// check hotel room type exists
		String hotelRoomType = hotelBookingRequest.getRoomType();
		RoomType roomType = roomTypeRepository.findByRoomType(hotelRoomType);
		if(roomType == null) {
			throw new RoomTypeNotFoundException("Room type " + hotelRoomType + " not found.");
		}
		// check user exists in the db
		int userId = hotelBookingRequest.getUserId();
		Optional<Customer> userObj = customerRepository.findById(userId);
		if(!userObj.isPresent()) {
			throw new CustomerNotFoundException("User with id "+userId+" not found.");
		}
		Customer user = userObj.get();
		// check booking date is not older date than today
		Date bookingDate = hotelBookingRequest.getBookingDate();
		long millis = System.currentTimeMillis();
		Date currentDate = new Date(millis);
		if(bookingDate.toLocalDate().isBefore(currentDate.toLocalDate())) {
			throw new Exception("Date should be greater than now or current");
		}
		
		RoomBooking hotelRoomBooking = new RoomBooking();
		hotelRoomBooking.setBookingDate(bookingDate);
		String bookingStatus = (roomType.getCost().intValue() <= user.getBonusPoints()) ?
				Status.BOOKED.name() : Status.PENDING_APPROVAL.name();
		hotelRoomBooking.setBookingStatus(bookingStatus);
		hotelRoomBooking.setHotelId(hotel.getId());
		hotelRoomBooking.setHotelRoomType(hotelRoomType);
		hotelRoomBooking.setUserId(userId);
		
		Room hotelRoom = null;
		// check vacant hotel rooms
		List<Room> hotelRoomsByHotelId = roomRepository.findByHotelIdAndRoomTypeId(hotel.getId(), roomType.getId());
		List<RoomBooking> hotelRoomBookings = roomBookingRepository.findByHotelIdAndBookingDateAndHotelRoomType(hotel.getId(), bookingDate, hotelRoomType);
		List<Room> vacantHotelRooms = new ArrayList<>();
		vacantHotelRooms.addAll(hotelRoomsByHotelId);
		for(RoomBooking booking:hotelRoomBookings) {
			if(Status.BOOKED.name().equals(booking.getBookingStatus()) || Status.PENDING_APPROVAL.name().equals(booking.getBookingStatus())) {
				vacantHotelRooms.removeAll(hotelRoomsByHotelId.parallelStream().filter((room) -> booking.getHotelRoomId() == room.getId()).collect(Collectors.toList()));
			}
		}
		
		// check if hotel rooms are empty and probable booking status is booked.
		if((vacantHotelRooms == null || vacantHotelRooms.isEmpty()) && Status.BOOKED.name().equals(bookingStatus)) {
			// check pending approval hotel rooms and cancel the current booking and assign it to this booking.
			List<RoomBooking> pendingApprovalHotelRoomBookings = hotelRoomBookings.parallelStream().filter((booking) -> Status.PENDING_APPROVAL.name().equals(booking.getBookingStatus())).collect(Collectors.toList()); //hotelBookingDAO.findByHotelIdAndBookingDateAndBookingStatusAndHotelRoomType(hotel.getId(), bookingDate, PENDING_APPROVAL.name(), hotelRoomType);
			if(pendingApprovalHotelRoomBookings != null && pendingApprovalHotelRoomBookings.size() > 0) {
				RoomBooking pendingApprovalHotelRoomBooking = pendingApprovalHotelRoomBookings.get(0);
				hotelRoom = hotelRoomsByHotelId.parallelStream().filter((room) -> pendingApprovalHotelRoomBooking.getHotelRoomId() == room.getId()).collect(Collectors.toList()).get(0);
				pendingApprovalHotelRoomBooking.setBookingStatus(Status.FAILED.name());
				roomBookingRepository.save(pendingApprovalHotelRoomBooking);
			} else {
				throw new Exception("Hotel rooms in " + hotelName +" of type " + roomType.getRoomType() +" are not available.");
			}
		} else {
			hotelRoom = vacantHotelRooms.get(0);
		}
		hotelRoomBooking.setHotelRoomId(hotelRoom.getId());
		hotelRoomBooking = roomBookingRepository.save(hotelRoomBooking);
		
		// assign a hotel room and update booking object
		// deduct the bonus points from user for this booking if status is BOOKED.
		if(Status.BOOKED.name().equals(bookingStatus)) {
			int remainingUserBonusPoints = user.getBonusPoints() - roomType.getCost().intValue();
			user.setBonusPoints(remainingUserBonusPoints);
			customerRepository.save(user);
		}
	}
}
