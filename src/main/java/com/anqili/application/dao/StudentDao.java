package com.anqili.application.dao;

import java.util.List;

import com.anqili.application.bean.Student;

public interface StudentDao {
	//select student by studentId
	Student selectStudentById(int stuId);
	
	//select student by student number
	Student selectStudentByNum(String stuNum);
	
	//select student by course
	List<Student> selectStudentByCourse(int courseId);
	
	//update a specific student by studentId
	boolean updateStudent(Student newstudent);
	
	//insert a new student
	boolean insertStudent(Student student);
}
