package com.anqili.application.bean;

public class Room {
	private int roomId;
	private String building;
	private String building_code;
	private String number;
	private int capacity;
	private int type;//class room:1; lab room:-1
	
	public int getRoomId() {
		return roomId;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getBuilding_code() {
		return building_code;
	}
	public void setBuilding_code(String building_code) {
		this.building_code = building_code;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", building=" + building + ", building_code=" + building_code + ", number="
				+ number + ", capacity=" + capacity + ", type=" + type + "]";
	}
	
}
