package com.anqili.application.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anqili.application.bean.Student;
import com.anqili.application.service.StudentService;

@RestController
public class StudentController {
	@Resource
	private StudentService studentService;
	
	//get a student by studentID
	@GetMapping("/student")
	public Student getStudentByID(@RequestParam("stuId") int stuId) {
		return studentService.getStudentById(stuId);
	}
	
	//get a student by student number
	@GetMapping("/studentNum")
	public Student getStudentByNum(@RequestParam("stuNum") String stuNum) {
		return studentService.getStudentByNum(stuNum);
	}
	
	//get students by course
	@GetMapping("/student/course")
	public List<Student> getStudentByCourse(@RequestParam("courseId") int courseId) {
		return studentService.getStudentByCourse(courseId);
	}
	
	//edit a student by Id
	@PutMapping("/student")
	public boolean putStudent(@RequestParam("stuId") int stuId, @RequestBody Student newStudent) {
		return studentService.updateStudent(stuId, newStudent);
	}
	
	//insert a new student
	@PostMapping("/student")
	public boolean postStudent(@RequestBody Student student) {
		return studentService.insertStuden(student);
	}
}
