package com.anqili.application.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anqili.application.bean.SubjectCourse;
import com.anqili.application.service.SubjectCourseService;

@RestController
public class SubjectCourseController {
	@Resource
	private SubjectCourseService subjectCourseService;
	
	//get subjects by courseId
	@GetMapping("/subjects/course")
	public List<SubjectCourse> getSubjectByCourse(@RequestParam("course") int courseId) {
		return this.subjectCourseService.getSubjectByCourse(courseId);
	}
	
	//get courses by subjectId
	@GetMapping("/courses/subject")
	public List<SubjectCourse> getCourseBySubject(@RequestParam("subject") int subjectId) {
		return this.subjectCourseService.getCourseBySubjectId(subjectId);
	}
	
	//get all courses arranged subjects
	@GetMapping("/allCoursesWithSubject")
	public List<SubjectCourse> getAllCourseWithSubject(){
		return this.subjectCourseService.getAllCourseWithSubject();
	}
	
	//add a subject to a course
	@PostMapping("/course/subject")
	public boolean postSubjectToCourse(@RequestBody SubjectCourse subjectcourse) {
		return this.subjectCourseService.addSubjectToCourse(subjectcourse);
	}
	
	//remove a subject from course
	@DeleteMapping("/course/subject")
	public boolean deleteSubjectFromCourse(@RequestParam("sub_courId") int sub_courId) {
		return this.subjectCourseService.removeSubjectFromCourse(sub_courId);
	}
}
