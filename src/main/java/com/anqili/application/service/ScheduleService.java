package com.anqili.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ScheduleService {
	
	Map<Integer,int[][]> coursesTimetableTemp = new HashMap<Integer,int[][]>();//{courId:course[sid][sid]}
	Map<Integer,int[][]> teachersTimetableTemp = new HashMap<Integer,int[][]>();//{teachId:teacher[sub_courid][sub_courid]}
	
	Map<Integer,List<int[][]>> courseTimetable = new HashMap<Integer,List<int[][]>>();
	Map<Integer,List<int[][]>> teacherTimtable = new HashMap<Integer,List<int[][]>>();
	
	List<Entry<Integer, Double>> countCourseWeight();
	Map<Integer,Map<Integer,Integer>> countCoursesTime (List<Entry<Integer, Double>> sortCourseWeight, int week);
	Map<Integer,int[][]> generateCourseTimetableTemp(Map<Integer,Map<Integer,Integer>> coursesTime);
	Map<Integer,List<int[][]>> generateCourseTimetable(Map<Integer,int[][]> coursesTimetableTemp, Map<Integer,int[][]> teachersTimetableTemp, int week);
}
