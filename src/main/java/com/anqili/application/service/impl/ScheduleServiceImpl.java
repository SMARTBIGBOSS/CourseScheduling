package com.anqili.application.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anqili.application.bean.Course;
import com.anqili.application.bean.SubjectCourse;
import com.anqili.application.dao.CourseDao;
import com.anqili.application.dao.SubjectCourseDao;
import com.anqili.application.dao.SubjectDao;
import com.anqili.application.dao.impl.CourseDaoImpl;
import com.anqili.application.dao.impl.SubjectCourseDaoImpl;
import com.anqili.application.service.CourseService;
import com.anqili.application.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	@Resource
	private CourseDao courseDao;
	@Resource
	private SubjectDao subjectDao;
	@Resource
	private SubjectCourseDao subjectCourseDao;
	
	private ArrayList<int[][]> coursesTimetable;
	private ArrayList<int[][]> teachersTimetable;
	private ArrayList<int[][]> roomsTimetable;
	
	//Calculate and sort the weight of each course. The greater weight, the higher priority.
	public List<Entry<Integer, Double>> countCourseWeight() {
		Map<Integer, Double> courseWeight = new HashMap<Integer,Double>();//{"key": courseId, "value": weight}
		
		List<SubjectCourse> courseList = subjectCourseDao.selecctAllCourseWithSubject();
//	System.out.println("专业个数："+courseList.size());
		for(int i = 0; i < courseList.size(); i++) {
			Course course = courseList.get(i).getCourse();
			int totalCourseTime = 0;
//	System.out.print("专业id号："+course.getCourId());
			List<SubjectCourse> subjects = subjectCourseDao.selectSubjectByCourse(course.getCourId());
			int subjectNum = subjects.size();
			while(!subjects.isEmpty()) {
				int lastOne = subjects.size()-1;
				totalCourseTime += (subjects.get(lastOne).getSubject().getClassTime() + subjects.get(lastOne).getSubject().getLabTime());
				subjects.remove(lastOne);
			}
//	System.out.print(" 所有课程总课时："+totalCourseTime);
			double w = 0.3*course.getCourSize() + 0.2*totalCourseTime + subjectNum;
//	System.out.println(" 专业比重："+w);
			courseWeight.put(course.getCourId(), w);
		}
		//Sort course List ordering by weight value
		List<Map.Entry<Integer, Double>> sortCourseWeight = new ArrayList<Map.Entry<Integer, Double>>(courseWeight.entrySet());
		Collections.sort(sortCourseWeight, new Comparator<Map.Entry<Integer, Double>>(){
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				return (o2.getValue()>=o1.getValue()?1:-1);
			}
		});
		return sortCourseWeight;
	}
	
	//Create matrixes for course, teacher and room
	public ArrayList<int[][]> createMultipleMatrix(int number){
		ArrayList<int[][]> matrixes = new ArrayList<int[][]>();
		while(number > 0) {
			int[][] matrix = new int[8][5];
			matrixes.add(matrix);
		}
		return matrixes;
	}
	
	//count subjects hours/week of each course
	public List<Map<Integer,int[]>> subjectsTime (List<Entry<Integer, Double>> sortCourseWeight, int week){
		List<Map<Integer,int[]>> subjectsTime = new ArrayList<Map<Integer,int[]>>();
		List<Map<Integer,int[]>> errorSubjectsTime = new ArrayList<Map<Integer,int[]>>();
		
		for(int i = 0; i < sortCourseWeight.size(); i++) {
			int courseId = sortCourseWeight.get(i).getKey();
			List<SubjectCourse> subjects = subjectCourseDao.selectSubjectByCourse(courseId);
			int[] subjectTime = new int[subjects.size()];//each subject time(hours/week)
			int sum = 0;
			for(int j = 0; j < subjectTime.length; j++) {
				subjectTime[j] = (int)Math.ceil((double)(subjects.get(j).getSubject().getClassTime()+subjects.get(j).getSubject().getLabTime())/week);
				sum += subjectTime[j];
			}
			Map<Integer,int[]> courSubMap = new HashMap<Integer,int[]>();
			courSubMap.put(courseId, subjectTime);
			subjectsTime.add(courSubMap);
			if(sum>40) {
				errorSubjectsTime.add(courSubMap);
			}
		}
		if(errorSubjectsTime.size() > 0) {
//			System.out.println("有专业排课不合理");
			return errorSubjectsTime;
		}
		return subjectsTime;
		

	}
	
	//Arrange course timetable and teacher timetable
	public void SubjectSchedule(List<Entry<Integer, Double>> sortCourseWeight, 
			List<int[]> subjectsTime, int weeks){
		

	}
}
