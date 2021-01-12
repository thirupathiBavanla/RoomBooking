package com.book.room.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.room.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
	public Hotel findByName(String name);
}
