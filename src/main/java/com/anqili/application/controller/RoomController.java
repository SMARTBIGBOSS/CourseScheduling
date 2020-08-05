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
	public List<Room> getAllRoom(){
		return roomService.getAllRoom();
	}
	
	//get room by id
	@GetMapping("/room")
	public Room getRoomById(@RequestParam("roomId") int roomId) {
		return roomService.getRoomById(roomId);
	}
	
	//get rooms by building code
	@GetMapping("/roomBuilding")
	public List<Room> getRoomByBuilding(@RequestParam("buildingCode") String buildingCode){
		return roomService.getRoomByBuildingCode(buildingCode);
	}
	
	//get rooms by over course size
	@GetMapping("/roomOverSize")
	public List<Room> getRoomByCapacity(@RequestParam("size") int size){
		return roomService.getRoomByCapacity(size);
	}
	
	//get rooms by room type
	@GetMapping("/roomType")
	public List<Room> getRoomByType(@RequestParam("type") int type){
		return roomService.getRoomByType(type);
	}
	
	//get rooms by type and capacity
	@GetMapping("/roomTypeSize")
	public List<Room> getRoomByTypeCapacity(@RequestParam("type") int type, @RequestParam("size") int size){
		return roomService.getRoomByTypeCapacity(type, size);
	}
	
	//get rooms by building code, room type and capacity over course size
	@GetMapping("/roomBuildingTypeSize")
	public List<Room> getRoomByCodeTypeCapacity(
			@RequestParam("buildindCode") String buildingCode, @RequestParam("type") int type, @RequestParam("size") int size){
		return roomService.getRoomByCodeTypeCapacity(buildingCode, type, size);
	}
	
	//edit room by id
	@PutMapping("/room")
	public boolean editRoom(@RequestParam("roomId") int roomId, @RequestBody Room room) {
		return roomService.editRoom(roomId, room);
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
