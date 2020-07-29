package com.anqili.application.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ScheduleService {
	
	List<Entry<Integer, Double>> countCourseWeight();
	Map<Integer,Map<Integer,Integer>> countCoursesTime (List<Entry<Integer, Double>> sortCourseWeight, int week);
	Map<Integer,int[][]> generateCourseTimetableTemp(Map<Integer,Map<Integer,Integer>> coursesTime);
	Map<Integer,List<int[][]>> generateCourseTimetable(Map<Integer,int[][]> coursesTimetableTemp, Map<Integer,int[][]> teachersTimetableTemp, int week);
}
