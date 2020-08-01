package com.anqili.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Map<Integer,Map<Integer,Integer>> check() {
		List<Entry<Integer, Double>> sortWeight = this.scheduleService.countCourseWeight();
		Map<Integer,Map<Integer,Integer>> subjectsTime = this.scheduleService.countCoursesTime(sortWeight, 16);
		return subjectsTime;
	}
	
	@GetMapping("/c")
	public Map<Integer, int[][]> checkCourseSchedule() {
//		Map<Integer,Integer> subjectsTime = new HashMap<Integer,Integer>();
//		subjectsTime.put(29, 5);
//		subjectsTime.put(30, 5);
//		subjectsTime.put(31, 1);
//		Map<Integer,Map<Integer,Integer>> coursesTime = new HashMap<Integer,Map<Integer,Integer>>();
//		coursesTime.put(8, subjectsTime);
		List<Entry<Integer, Double>> sortWeight = this.scheduleService.countCourseWeight();
		Map<Integer,Map<Integer,Integer>> coursesTime = this.scheduleService.countCoursesTime(sortWeight, 16);
		return this.scheduleService.generateCourseTimetableTemp(coursesTime);
//		return ScheduleService.teachersTimetableTemp;
	}
	
	@GetMapping("/w")
	public Map<Integer,List<int[][]>> generateWholeSchedule(@RequestParam("week") int week){
//		Map<Integer,Integer> subjectsTime = new HashMap<Integer,Integer>();
//		subjectsTime.put(29, 5);
//		subjectsTime.put(30, 5);
//		subjectsTime.put(31, 1);
//		Map<Integer,Map<Integer,Integer>> coursesTime = new HashMap<Integer,Map<Integer,Integer>>();
//		coursesTime.put(8, subjectsTime);
		List<Entry<Integer, Double>> sortWeight = this.scheduleService.countCourseWeight();
		Map<Integer,Map<Integer,Integer>> coursesTime = this.scheduleService.countCoursesTime(sortWeight, week);
		Map<Integer,int[][]> coursesTimetableTemp = this.scheduleService.generateCourseTimetableTemp(coursesTime);
		
		Map<Integer,int[][]> teachersTimetableTemp = new HashMap<Integer,int[][]>();
		return this.scheduleService.generateCourseTimetable(coursesTimetableTemp, teachersTimetableTemp, week);
	}
}
