package com.anqili.application.service;

import java.util.List;

import com.anqili.application.bean.Room;

public interface RoomService {
	//get all rooms
	List<Room> getAllRoom();
	
	//get room by id
	Room getRoomById(int roomId);
	
	//get rooms by building
	List<Room> getRoomByBuildingCode(String buildingCode);
	
	//get rooms which the capacity is more than course size
	List<Room> getRoomByCapacity(int size);
	
	//get rooms by room type
	List<Room> getRoomByType(int type);
	
	//get rooms by type and capacity
	List<Room> getRoomByTypeCapacity(int type, int size);
	
	//get rooms by building code, room type and capacity over course size
	List<Room> getRoomByCodeTypeCapacity(String buildingCode, int type, int size);
	
	//edit a room by id
	boolean editRoom(int roomId, Room room);
	
	//insert a new room
	boolean insertRoom(Room room);
	
	//delete a room
	boolean deleteOneRoom(int roomId);
	
	//delete multiple rooms
	boolean deleteManyRoom(List<Integer> roomIDs);
	
}
