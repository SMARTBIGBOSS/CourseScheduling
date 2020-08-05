package com.anqili.application.dao;

import java.util.List;

import com.anqili.application.bean.Room;

public interface RoomDao {
	//Select all room ordering by building and descending capacity
	List<Room> selectAllRoom();
	
	//Select room by id
	Room selectRoomById(int roomId);
	
	//Select rooms by building code
	List<Room> selectRoomByBuildingCode(String buildingCode);
	
	//Select rooms which the capacity is more than course size
	List<Room> selectRoomByCapacity(int size);
	
	//Select rooms by room type
	List<Room> selectRoomByRoomType(int type);
	
	//Select rooms by room type and capacity
	List<Room> selectRoomByTypeCapacity(Room criteria);
	
	//Select rooms by building code, room type and capacity over course size
	List<Room> selectRoomByCodeTypeCapacity(Room criteria);
	
	//Update a specific room by id
	boolean updateRoom(Room room);
	
	//Insert a new room
	boolean insertRoom(Room room);
	
	//Delete a exited room by id
	boolean deleteOneRoom(int roomId);
}
