package com.anqili.application.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anqili.application.service.CourseService;
import com.anqili.application.service.ScheduleService;
import com.anqili.application.service.SubjectService;

@RestController
public class ScheduleController {
	@Resource
	private CourseService courseService;
	@Resource
	private SubjectService subjectService;
	@Resource
	private ScheduleService scheduleService;
	
	@GetMapping("/s")
	public List<Map<Integer, int[]>> check() {
		List<Entry<Integer, Double>> sortWeight = this.scheduleService.countCourseWeight();
		List<Map<Integer, int[]>> subjectsTime = this.scheduleService.subjectsTime(sortWeight, 16);
		return subjectsTime;
	}
	
}
