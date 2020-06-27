package com.anqili.application.service;

import java.util.List;

import com.anqili.application.bean.Student;

public interface StudentService {
	//get a student by studentID
	Student getStudentById(int stuId);
	
	//get a student by student number
	Student getStudentByNum(String stuNum);
	
	//get students by a course
	List<Student> getStudentByCourse(int courseId);
	
	//update a student by studentID
	boolean updateStudent(int stuId, Student newStudent);
	
	//insert a new student
	boolean insertStuden(Student student);
}
