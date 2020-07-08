package com.anqili.application.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anqili.application.bean.Room;
import com.anqili.application.dao.RoomDao;

@Repository
public class RoomDaoImpl implements RoomDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Room> selectAllRoom() {
		return sqlSession.selectList("selectAllRoom");
	}
	
	@Override
	public Room selectRoomById(int roomId) {
		return sqlSession.selectOne("selectRoomById", roomId);
	}

	@Override
	public List<Room> selectRoomByBuilding(String building) {
		return sqlSession.selectList("selectRoomByBuilding", building);
	}

	@Override
	public List<Room> selectRoomByCapacity(int size) {
		return sqlSession.selectList("selectRoomByCapacity", size);
	}

	@Override
	public boolean updateRoom(Room room) {
		sqlSession.update("updateRoom", room);
		return true;
	}

	@Override
	public boolean insertRoom(Room room) {
		sqlSession.insert("insertRoom", room);
		return true;
	}

	@Override
	public boolean deleteOneRoom(int roomId) {
		sqlSession.delete("deleteOneRoom", roomId);
		return true;
	}


}
