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

import com.anqili.application.bean.Subject;
import com.anqili.application.service.SubjectService;

@RestController
public class SubjectController {
	@Resource
	private SubjectService subjectService;
	
	//get all subjects
	@GetMapping("/allsubjects")
	public List<Subject> getAllSubject() {
		return this.subjectService.getAllSubjects();
	}
	
	//get subjects by id
		@GetMapping("/subject")
		public Subject getSubjectById(@RequestParam("id") int subjectId) {
			return this.subjectService.getSubjectById(subjectId);
		}
	
	//get subjects by courseId
	@GetMapping("/subjects/course")
	public List<Subject> getSubjectByCourse(@RequestParam("course") int courseId) {
		return this.subjectService.getSubjectByCourse(courseId);
	}
	
	//get subjects by teacherId
	@GetMapping("/subjects/teacher")
	public List<Subject> getSubjectByTeacher(@RequestParam("teacher") int teacherId) {
		return this.subjectService.getSubjectByTeacher(teacherId);
	}
	
	//insert a new subject
	@PostMapping("/subject")
	public boolean postSubject(@RequestBody Subject newSubject) {
		return this.subjectService.insertSubject(newSubject);
	}
	
	//edit a subject by id
	@PutMapping("/subject")
	public boolean putSubject(@RequestParam("subject") int subjectId, @RequestBody Subject subject) {
		return this.subjectService.updateSubject(subjectId, subject);
	}
	
	//delete a subject by id
	@DeleteMapping("/subjects/delete")
	public boolean deleteSubject(@RequestParam("subject") int subjectId) {
		return this.subjectService.deleteOneSubject(subjectId);
	}
	
	//delete many subjects by their id
	@DeleteMapping("/subjects/deletesubjects")
	public boolean deleteManySubjects(@RequestBody List<Integer> subjectIDs) {
		return this.subjectService.deleteManySubjects(subjectIDs);
	}
}
