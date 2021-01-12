package com.book.room.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.room.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	public List<Room> findByHotelId(int hotelId);
	public List<Room> findByHotelIdAndRoomTypeId(int hotelId, int roomTypeId);
}
