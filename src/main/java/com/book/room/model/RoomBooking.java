package com.book.room.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roombooking")
public class RoomBooking {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "hotelid", nullable = false)
	private int hotelId;
	@Column(name = "hotelroomid", nullable = false)
	private int hotelRoomId;
	@Column(name = "hotelroomtype", nullable = false)
	private String hotelRoomType;	
	@Column(name = "bookingdate", nullable = false)
	private Date bookingDate;
	@Column(name = "bookingstatus")
	private String bookingStatus;
	@Column(name = "userid", nullable = false)
	private int userId;
	
	public String getHotelRoomType() {
		return hotelRoomType;
	}
	public void setHotelRoomType(String hotelRoomType) {
		this.hotelRoomType = hotelRoomType;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHotelRoomId() {
		return hotelRoomId;
	}
	public void setHotelRoomId(int hotelRoomId) {
		this.hotelRoomId = hotelRoomId;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	
}
