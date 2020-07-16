package com.anqili.application.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ScheduleService {
	
	List<Entry<Integer, Double>> countCourseWeight();
	Map<Integer,Map<Integer,Integer>> coursesTime (List<Entry<Integer, Double>> sortCourseWeight, int week);
	Map<Integer,int[][]> CourseSchedule(Map<Integer,Map<Integer,Integer>> coursesTime, int weeks);
}
