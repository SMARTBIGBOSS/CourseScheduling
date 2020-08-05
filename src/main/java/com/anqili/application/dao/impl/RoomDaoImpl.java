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
	public List<Room> selectRoomByBuildingCode(String buildingCode) {
		return sqlSession.selectList("selectRoomByBuildingCode", buildingCode);
	}

	@Override
	public List<Room> selectRoomByCapacity(int size) {
		return sqlSession.selectList("selectRoomByCapacity", size);
	}

	@Override
	public List<Room> selectRoomByRoomType(int type) {
		return sqlSession.selectList("selectRoomByRoomType", type);
	}
	
	@Override
	public List<Room> selectRoomByTypeCapacity(Room criteria){
		return sqlSession.selectList("selectRoomByTypeCapacity", criteria);
	}

	@Override
	public List<Room> selectRoomByCodeTypeCapacity(Room criteria) {
		return sqlSession.selectList("selectRoomByCodeTypeCapacity", criteria);
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
