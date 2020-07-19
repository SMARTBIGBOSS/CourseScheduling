package com.anqili.application.dao;

import java.util.List;

import com.anqili.application.bean.Teacher;

public interface TeacherDao {
	//select teacher by teacherId
	Teacher selectTeacherById(int teachId);
	
	//select teacher by teacher number
	Teacher selectTeacherByNum(String teachNum);
	
	//select teachers by department name
	List<Teacher> selectTeacherByDept(String dept);
	
	//select teachers by status(cancel -1; inactive 0; active 1)
	List<Teacher> selectTeacherByStatus(int status);
	
	//select teachers by subjectId
	Teacher selectTeacherBySubject(int subjectId);
	
	//update a specific teacher by teacherId
	boolean updateTeacher(Teacher newTeacher);
	
	//insert a new teacher
	boolean insertTeacher(Teacher teacher);
}
