package com.anqili.application.bean;

public class Room {
	private int roomId;
	private String building;
	private String number;
	private int capacity;
	
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getRoomId() {
		return roomId;
	}
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", building=" + building + ", number=" + number + ", capacity=" + capacity
				+ "]";
	}
	
}
