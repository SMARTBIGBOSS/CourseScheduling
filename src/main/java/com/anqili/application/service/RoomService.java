package com.anqili.application.service;

import java.util.List;

import com.anqili.application.bean.Room;

public interface RoomService {
	//get all rooms
	List<Room> selectAllRoom();
	
	//get room by id
	Room selectRoomById(int roomId);
	
	//get room by building
	List<Room> selectRoomByBuilding(String building);
	
	//get room which the capacity is more than course size
	List<Room> selectRoomByCapacity(int size);
	
	//update a room by id
	boolean updateRoom(int roomId, Room room);
	
	//insert a new room
	boolean insertRoom(Room room);
	
	//delete a room
	boolean deleteOneRoom(int roomId);
	
	//delete multiple rooms
	boolean deleteManyRoom(List<Integer> roomIDs);
	
}
