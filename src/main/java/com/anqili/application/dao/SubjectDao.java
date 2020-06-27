package com.anqili.application.dao;

import java.util.List;

import com.anqili.application.bean.Subject;

public interface SubjectDao{
	//Select all subjects
	List<Subject> selectAllSubject();
	
	//select subject by id
	Subject selectSubjectById(int subjectId);
	
	//Select subjects by course ID
	List<Subject> selectSubjectByCourse(int courseId);
	
	//Select subjects by teacherID
	List<Subject> selectSubjectByTeacher(int teacherId);
	
	//Update a specific subject by its ID
	boolean updateSubject(Subject subject);
	
	//Delete a subject by its ID
	boolean deleteOneSubject(int subjectId);
	
	//Insert a new subject
	boolean insertSubject(Subject subject);
}
