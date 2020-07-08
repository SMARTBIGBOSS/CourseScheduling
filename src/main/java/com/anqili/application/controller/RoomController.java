package com.anqili.application.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anqili.application.bean.Room;
import com.anqili.application.service.RoomService;

@RestController
public class RoomController {
	@Resource
	private RoomService roomService;
	
	//get all rooms
	@GetMapping("/allRooms")
	public List<Room> selectAllRoom(){
		return roomService.selectAllRoom();
	}
	
	//get room by id
	@GetMapping("/room")
	public Room selectRoomById(@RequestParam("roomId") int roomId) {
		return roomService.selectRoomById(roomId);
	}
	
	//get rooms by building
	@GetMapping("/roomBuilding")
	public List<Room> selectRoomByBuilding(@RequestParam("building") String building){
		return roomService.selectRoomByBuilding(building);
	}
	
	//get rooms by over course size
	@GetMapping("/roomOverSize")
	public List<Room> selectRoomByCapacity(@RequestParam("size") int size){
		return roomService.selectRoomByCapacity(size);
	}
	
	//edit room by id
	@PutMapping("/room")
	public boolean updateRoom(@RequestParam("roomId") int roomId, @RequestBody Room room) {
		return roomService.updateRoom(roomId, room);
	}
	
	//add a new room
	@PostMapping("/room")
	public boolean insertRoom(@RequestBody Room room) {
		return roomService.insertRoom(room);
	}
	
	//delete one room by id
	@DeleteMapping("/room")
	public boolean delectOneRoom(@RequestParam("roomId") int roomId) {
		return roomService.deleteOneRoom(roomId);
	}
	
	//delete many rooms by IDs
	@DeleteMapping("/rooms")
	public boolean deleteManyRoom(@RequestBody List<Integer> roomIDs) {
		return roomService.deleteManyRoom(roomIDs);
	}
}
