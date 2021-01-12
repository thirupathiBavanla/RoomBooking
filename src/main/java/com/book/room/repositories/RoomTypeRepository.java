package com.book.room.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.room.model.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
	public RoomType findByRoomType(String roomType);
}
