package com.anqili.application.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anqili.application.bean.Room;
import com.anqili.application.dao.RoomDao;
import com.anqili.application.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Resource
	private RoomDao roomDao;
	
	@Override
	public List<Room> selectAllRoom() {
		return roomDao.selectAllRoom();
	}

	@Override
	public Room selectRoomById(int roomId) {
		return roomDao.selectRoomById(roomId);
	}

	@Override
	public List<Room> selectRoomByBuilding(String building) {
		return roomDao.selectRoomByBuilding(building);
	}

	@Override
	public List<Room> selectRoomByCapacity(int size) {
		return roomDao.selectRoomByCapacity(size);
	}

	@Override
	public boolean updateRoom(int roomId, Room room) {
		Room oldRoom = roomDao.selectRoomById(roomId);
		oldRoom.setBuilding(room.getBuilding());
		oldRoom.setNumber(room.getNumber());
		oldRoom.setCapacity(room.getCapacity());
		return roomDao.updateRoom(oldRoom);
	}

	@Override
	public boolean insertRoom(Room room) {
		return roomDao.insertRoom(room);
	}

	@Override
	public boolean deleteOneRoom(int roomId) {
		return roomDao.deleteOneRoom(roomId);
	}

	@Override
	public boolean deleteManyRoom(List<Integer> roomIDs) {
		while(!roomIDs.isEmpty()) {
			roomDao.deleteOneRoom(roomIDs.remove(0));
		}
		return true;
	}

}
