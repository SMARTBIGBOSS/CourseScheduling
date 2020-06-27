package com.anqili.application.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anqili.application.bean.Course;
import com.anqili.application.service.CourseService;

@RestController
public class CourseController {
	@Resource
	private CourseService courseService;
	
	//get all courses
	@GetMapping("/allcourses")
	public List<Course> getAllCourse() {
		return this.courseService.getAllCourse();
	}
	
	//get courses by subjectId
	@GetMapping("/courses/subject")
	public List<Course> getCourseBySubject(@RequestParam("subject") int subjectId) {
		return this.courseService.getCourseBySubjectId(subjectId);
	}
	
	//get courses by year
	@GetMapping("/courses/year")
	public List<Course> getCourseByYear(@RequestParam("year") int year) {
		return this.courseService.getCourseByYear(year);
	}
	
	//get courses by id
		@GetMapping("/course")
		public Course getCourseById(@RequestParam("id") int courseId) {
			return this.courseService.getCourseById(courseId);
		}
	
	//edit a course by id
	@PutMapping("/course")
	public boolean putCourse(@RequestParam("course") int courseId, @RequestBody Course course) {
		return this.courseService.updateCourse(courseId, course);
	}
	
	//delete a course by id
	@DeleteMapping("/courses/delete")
	public boolean deleteCourse(@RequestParam("course") int courseId) {
		return this.courseService.deleteOneCourse(courseId);
	}
	
	//delete many courses by their id
	@DeleteMapping("/courses/deletecourse")
	public boolean deleteManyCourse(@RequestBody List<Integer> courseIDs) {
		return this.courseService.deleteManyCourse(courseIDs);
	}
	
	//add a new course
	@PostMapping("/course")
	public boolean postCourse(@RequestBody Course newCourse) {
		return this.courseService.insertCourse(newCourse);
	}
	
}
