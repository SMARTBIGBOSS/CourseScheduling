package com.anqili.application.dao;

import java.util.List;

import com.anqili.application.bean.SubjectCourse;

public interface SubjectCourseDao {

	//Select sub_cour Id by courseId and subjectId
	SubjectCourse selectSubCourId(SubjectCourse courSub);
	
	//Select subjects by course ID
	List<SubjectCourse> selectSubjectByCourse(int courseId);
	
	//Select courses by subject ID
	List<SubjectCourse> selectCourseBySubject(int subjectId);
	
	//Select all courses with subjects
	List<SubjectCourse> selecctAllCourseWithSubject();
	
	//Insert a subject to a course
	boolean insertSubjectToCourse(SubjectCourse subjectcourse);
	
	//Delete a subject from course
	boolean deleteSubjectFromCourse(int sub_courId);

}
