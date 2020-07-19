package com.anqili.application.service;

import java.util.List;

import com.anqili.application.bean.SubjectCourse;

public interface SubjectCourseService {
	
	//get sub_courId by courId and subId
	SubjectCourse getSubCourId(int courIdd, int subId);
	//get course by subjectId
	List<SubjectCourse> getCourseBySubjectId(int subjectId);
	
	//get subjects by courseId
	List<SubjectCourse> getSubjectByCourse(int courseId);
	
	//get all courses with subject
	List<SubjectCourse> getAllCourseWithSubject();
	
	//add a subject to a course
	boolean addSubjectToCourse(SubjectCourse subjectcourse);
	
	//remove a subject from course
	boolean removeSubjectFromCourse(int sub_courId);
}
