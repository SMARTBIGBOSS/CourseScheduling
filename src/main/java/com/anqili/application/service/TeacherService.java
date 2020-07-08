package com.anqili.application.service;

import java.util.List;

import com.anqili.application.bean.Teacher;

public interface TeacherService {
	//get a teacher by teacherId
	Teacher getTeacherById(int teachId);
	
	//get a teacher by teacher number
	Teacher getTeacherByNum(String teachNum);
	
	//get teachers by department name
	List<Teacher> getTeacherByDept(String dept);
	
	//get teachers by status
	List<Teacher> getTeacherByStatus(int status);
	
	//get teachers by subjectId
	List<Teacher> getTeacherBysubject(int subjectId);
	
	//update a teacher by teacherId
	boolean updateTeacher(int teachId, Teacher newTeacher);
	
	//insert a new teacher
	boolean insertTeacher(Teacher teacher);
}
