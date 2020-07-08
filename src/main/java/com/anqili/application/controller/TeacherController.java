package com.anqili.application.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anqili.application.bean.Teacher;
import com.anqili.application.service.TeacherService;

@RestController
public class TeacherController {

	@Resource
	private TeacherService teacherService;
	
	//get a teacher by teacherId
	@GetMapping("/teacher")
	public Teacher getTeacherById(@RequestParam("teachId") int teachId) {
		return teacherService.getTeacherById(teachId);
	}
	
	//get a teacher by teacher number
	@GetMapping("/teacherNum")
	public Teacher getTeacherByNum(@RequestParam("teachNum") String teachNum) {
		return teacherService.getTeacherByNum(teachNum);
	}
	
	//get teachers by department name
	@GetMapping("/teacherDept")
	public List<Teacher> getTeacherByDept(@RequestParam("deptName") String dept){
		return teacherService.getTeacherByDept(dept);
	}
	
	//get teachers by status
	@GetMapping("/teacherStatus")
	public List<Teacher> getTeacherByStatus(@RequestParam("status") int status){
		return teacherService.getTeacherByStatus(status);
	}
	
	//get teachers by subjectId
	@GetMapping("teacher/subject")
	public List<Teacher> getTeacherBySubject(@RequestParam("subId") int subjectId){
		return teacherService.getTeacherBysubject(subjectId);
	}
	
	//edit a teacher by teacherId
	@PutMapping("/teacher")
	public boolean putTeacher(@RequestParam("teachId") int teachId, @RequestBody Teacher newTeacher) {
		return teacherService.updateTeacher(teachId, newTeacher);
	}
	
	//insert a new teacher
	@PostMapping("/teacher")
	public boolean postTeacher(@RequestBody Teacher teacher) {
		return teacherService.insertTeacher(teacher);
	}
	
}
