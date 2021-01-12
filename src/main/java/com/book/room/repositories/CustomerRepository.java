package com.book.room.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.room.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	public Customer findByName(String name);
}
