package com.anqili.application.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ScheduleService {
	
	List<Entry<Integer, Double>> countCourseWeight();
	List<Map<Integer,int[]>> subjectsTime (List<Entry<Integer, Double>> sortCourseWeight, int week);
}
