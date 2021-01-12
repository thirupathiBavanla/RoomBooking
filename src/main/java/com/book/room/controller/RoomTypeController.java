package com.book.room.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.room.model.RoomType;
import com.book.room.repositories.RoomTypeRepository;

@RestController
public class RoomTypeController {
	@Autowired
	private RoomTypeRepository roomTypeRepository;
	
	@PostMapping("/roomtype")
	public void saveRoomType(@RequestBody RoomType hotelRoomType) {
		roomTypeRepository.save(hotelRoomType);
	}
	
	@GetMapping("/roomtypes")
	public List<RoomType> getRoomTypes() {
		return roomTypeRepository.findAll();
	}
	
	@GetMapping("/roomtype/{id}")
	public RoomType getRoomTypeById(@PathParam(value = "id") int id) {
		return roomTypeRepository.findById(id).get();
	}
}
