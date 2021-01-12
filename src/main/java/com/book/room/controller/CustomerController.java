package com.book.room.controller;

import static com.book.room.constants.Status.BOOKED;
import static com.book.room.constants.Status.PENDING_APPROVAL;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.room.model.RoomBooking;
import com.book.room.model.RoomType;
import com.book.room.model.Customer;
import com.book.room.repositories.RoomBookingRepository;
import com.book.room.repositories.RoomTypeRepository;
import com.book.room.repositories.CustomerRepository;

@RestController
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RoomBookingRepository roomBookingRepository;
	
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	
	@PostMapping("/user")
	public void saveUser(@RequestBody Customer user) {
		customerRepository.save(user);
	}
	
	@PutMapping("/user")
	public void updateUser(@RequestBody Customer user) {
		Customer existingUser = customerRepository.findByName(user.getName());
		if(existingUser != null && existingUser.getBonusPoints() != user.getBonusPoints()) {
			existingUser.setBonusPoints(existingUser.getBonusPoints() + user.getBonusPoints());
			
			List<RoomBooking> userPendingApprovalHotelRoomBookings = roomBookingRepository.findByUserIdAndBookingStatus(existingUser.getId(), PENDING_APPROVAL.name());
			List<RoomBooking> bookingsToBeSaved = new ArrayList<>();
			for(RoomBooking booking:userPendingApprovalHotelRoomBookings) {
				RoomType roomType = roomTypeRepository.findByRoomType(booking.getHotelRoomType());
				int roomCost = roomType.getCost().intValue();
				if(roomType.getCost().intValue() <= existingUser.getBonusPoints()) {
					booking.setBookingStatus(BOOKED.name());
					bookingsToBeSaved.add(booking);
					existingUser.setBonusPoints(existingUser.getBonusPoints() - roomCost);
				} else {
					break;
				}
			}
			if(bookingsToBeSaved.size() > 0) {
				roomBookingRepository.saveAll(bookingsToBeSaved);
			}
			customerRepository.save(existingUser);
		}
	}
	
	@GetMapping("/users")
	public List<Customer> getUsers() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/user/{id}")
	public Customer getUserById(@PathParam(value = "id") int id) {
		return customerRepository.findById(id).get();
	}
}
