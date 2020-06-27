package com.anqili.application.service;

import java.util.List;
import java.util.Map;

import com.anqili.application.bean.Subject;

public interface SubjectService {
	//get all subjects
	List<Subject> getAllSubjects();
	
	//get subject by id
	Subject getSubjectById(int subjectId);
	
	//get subjects by courseId
	List<Subject> getSubjectByCourse(int courseId);
	
	//get subjects by teacherId
	List<Subject> getSubjectByTeacher(int teacherId);
	
	//insert a new subject
	boolean insertSubject(Subject subject);
	
	//update a subject by id
	boolean updateSubject(int subjectId, Subject subject);
	
	//delete a subject by id
	boolean deleteOneSubject(int subjectId);
	
	//delete many subjects by id
	boolean deleteManySubjects(List<Integer> subjectIDs);
}
