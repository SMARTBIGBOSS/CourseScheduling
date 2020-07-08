package com.anqili.application.dao;

import java.util.List;

import com.anqili.application.bean.Course;

public interface CourseDao {
	//Select all courses
	List<Course> selectAllCourse();
	
	//Select a course by id
	Course selectCourseById(int courseId);
	
	//Select courses by subject ID
	List<Course> selectCourseBySubject(int subjectId);
	
	//Select courses by year
	List<Course> selectCourseByYear(int year);
	
	//Update a course by id
	boolean updateCourse(Course course);
	
	//Delete a course by id
	boolean deleteOneCourse(int courseId);
	
	//insert a new course
	boolean insertCourse(Course course);
}