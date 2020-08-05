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
	public List<Room> getAllRoom() {
		return roomDao.selectAllRoom();
	}

	@Override
	public Room getRoomById(int roomId) {
		return roomDao.selectRoomById(roomId);
	}

	@Override
	public List<Room> getRoomByBuildingCode(String buildingCode) {
		return roomDao.selectRoomByBuildingCode(buildingCode);
	}

	@Override
	public List<Room> getRoomByCapacity(int size) {
		return roomDao.selectRoomByCapacity(size);
	}
	
	@Override
	public List<Room> getRoomByType(int type) {
		return roomDao.selectRoomByRoomType(type);
	}
	
	@Override
	public List<Room> getRoomByTypeCapacity(int type, int size){
		Room criteria = new Room();
		criteria.setCapacity(size);
		criteria.setType(type);
		return roomDao.selectRoomByTypeCapacity(criteria);
	}

	@Override
	public List<Room> getRoomByCodeTypeCapacity(String buildingCode, int type, int size) {
		Room criteria = new Room();
		criteria.setBuilding_code(buildingCode);
		criteria.setType(type);
		criteria.setCapacity(size);
		return roomDao.selectRoomByCodeTypeCapacity(criteria);
	}

	@Override
	public boolean editRoom(int roomId, Room room) {
		Room oldRoom = roomDao.selectRoomById(roomId);
		oldRoom.setBuilding(room.getBuilding());
		oldRoom.setBuilding_code(room.getBuilding_code());
		oldRoom.setNumber(room.getNumber());
		oldRoom.setCapacity(room.getCapacity());
		oldRoom.setType(room.getType());
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
