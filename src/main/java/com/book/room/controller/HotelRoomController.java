package com.book.room.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.room.model.Room;
import com.book.room.repositories.RoomRepository;

@RestController
public class HotelRoomController {
	@Autowired
	private RoomRepository roomRepository;
	
	@PostMapping("/room")
	public void saveHotelRoomType(@RequestBody Room hotelRoom) {
		roomRepository.save(hotelRoom);
	}
	
	@GetMapping("/rooms")
	public List<Room> getHotelRooms() {
		return roomRepository.findAll();
	}
	
	@GetMapping("/room/{id}")
	public Room getHotelRoomById(@PathParam(value = "id") int id) {
		return roomRepository.findById(id).get();
	}
	
	@GetMapping("/hotelRoom/{hotelId}")
	public List<Room> getHotelRoomsByHotelId(@PathParam(value = "hotelId") int hotelId) {
		return roomRepository.findByHotelId(hotelId);
	}
}
