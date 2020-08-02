package com.anqili.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ScheduleService {
	
	Map<Integer,int[][]> coursesTimetableTemp = new HashMap<Integer,int[][]>();//{courId:course[sid][sid]}
	Map<Integer,int[][]> teachersTimetableTemp = new HashMap<Integer,int[][]>();//{teachId:teacher[sub_courid][sub_courid]}
	
	Map<Integer,Map<Integer,int[][]>> courseTimetable = new HashMap<Integer,Map<Integer,int[][]>>();//{cId:{c[sid][sid],c[sid][sid]}}
	Map<Integer,Map<Integer,int[][]>> teacherTimetable = new HashMap<Integer,Map<Integer,int[][]>>();//{tId:{{1:t[sub_courId][sub_courId]},{2:t[sub_courId][sub_courId]}}}
	
	List<Entry<Integer, Double>> countCourseWeight();
	Map<Integer,Map<Integer,Integer>> countCoursesTime (List<Entry<Integer, Double>> sortCourseWeight, int week);
	Map<Integer,int[][]> generateCourseTimetableTemp(Map<Integer,Map<Integer,Integer>> coursesTime);
	Map<Integer,Map<Integer,int[][]>> generateCourseTimetable(Map<Integer,int[][]> coursesTimetableTemp, Map<Integer,int[][]> teachersTimetableTemp, int week);
}
