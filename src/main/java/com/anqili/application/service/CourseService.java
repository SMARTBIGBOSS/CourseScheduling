package com.anqili.application.service;

import java.util.List;

import com.anqili.application.bean.Course;

public interface CourseService {
	//get all courses
	List<Course> getAllCourse();
	
	//get course by id
	Course getCourseById(int courseId);
	
	//get course by subjectId
	List<Course> getCourseBySubjectId(int subjectId);
	
	//get course by year
	List<Course> getCourseByYear(int year);
	
	//update a course by id
	boolean updateCourse(int courseId, Course course);
	
	//delete a course
	boolean deleteOneCourse(int courseId);
	
	//delete many courses
	boolean deleteManyCourse(List<Integer> courseIDs);
	
	//insert a new course
	boolean insertCourse(Course course);
	
}
