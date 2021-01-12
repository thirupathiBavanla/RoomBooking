package com.book.room.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.room.model.Hotel;
import com.book.room.repositories.HotelRepository;

@RestController
public class HotelController {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@PostMapping("/hotel")
	public void saveHotel(@RequestBody Hotel hotel) {
		hotelRepository.save(hotel);
	}
	
	@GetMapping("/hotels")
	public List<Hotel> getHotels() {
		return hotelRepository.findAll();
	}
	
	@GetMapping("/hotel/{id}")
	public Hotel getHotelById(@PathParam(value = "id") int id) {
		return hotelRepository.findById(id).get();
	}
}
