package com.anqili.application.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
	public Map<Integer,Map<Integer,Integer>> coursesTime (List<Entry<Integer, Double>> sortCourseWeight, int week){
		Map<Integer,Map<Integer,Integer>> coursesTime = new HashMap<Integer,Map<Integer,Integer>>();
		Map<Integer,Map<Integer,Integer>> errorCoursesTime = new HashMap<Integer,Map<Integer,Integer>>();
		
		for(int i = 0; i < sortCourseWeight.size(); i++) {
			int courseId = sortCourseWeight.get(i).getKey();//get course
			List<SubjectCourse> subjects = subjectCourseDao.selectSubjectByCourse(courseId);//all subjects of the course
			Map<Integer, Integer> subjectTime = new HashMap<Integer,Integer>();//save each subject time(hours/week){subId:time}
			int sum = 0;//use to check if the course total learning time over 40h.
			
			//count each subject total time(class time + lab time)
			for(int j = 0; j < subjects.size(); j++) {
				int subId = subjects.get(j).getSubjectId();
				int time = (int)Math.ceil((double)(subjects.get(j).getSubject().getClassTime()+subjects.get(j).getSubject().getLabTime())/week);
				subjectTime.put(subId, time);
				sum += time;
			}
			coursesTime.put(courseId, subjectTime);
			if(sum>40) {
				errorCoursesTime.put(courseId, subjectTime);
			}
		}
		if(errorCoursesTime.size() > 0) {
//			System.out.println("有专业排课不合理，一周总课时超过40小时");
			return errorCoursesTime;
		}
		return coursesTime;
	}
	
	//Arrange course timetable and teacher timetable
	public Map<Integer,int[][]> CourseSchedule(Map<Integer,Map<Integer,Integer>> coursesTime, int weeks){
		Map<Integer,int[][]> coursesTimetable = new HashMap<Integer,int[][]>();
		for(Entry<Integer,Map<Integer,Integer>> c : coursesTime.entrySet()) {
			int courseId = c.getKey();
			Map<Integer,Integer> subjectsMap = c.getValue();
			int[][] courseTimetable = new int[8][5];
			int segment = 0,date = 0;
			courseTimetable =  SubjectArrangement(subjectsMap,courseTimetable,segment,date);
			coursesTimetable.put(courseId, courseTimetable);
		}
		return coursesTimetable;
	}

	public int[][] SubjectArrangement(Map<Integer,Integer> subjectsMap, int[][] timetable, int segment, int date) {
		if(!subjectsMap.isEmpty()) {
			Iterator<Map.Entry<Integer, Integer>> entries = subjectsMap.entrySet().iterator();
			while(entries.hasNext()) {
				Entry<Integer, Integer> subject = entries.next();
				int subId = subject.getKey();
				int subTime = subject.getValue();
					if(subTime>=2) {
						while(timetable[segment][date] != 0) {
							if(date<4) {
								date += 1;
								continue;
							}
							date = 0 ;
							segment += 2;
						}
						timetable[segment][date] = subId;
						timetable[segment+1][date] = subId;
						subject.setValue(subTime-2);
					}else if(subTime>=1) {
						while(timetable[segment][date] != 0) {
							if(date<4) {
								date += 1;
								continue;
							}
							date = 0 ;
							segment += 2;
						}
							timetable[segment][date] = subId;
							subject.setValue(subTime-1);
						
					}else {
						entries.remove();
					}
			}
			SubjectArrangement(subjectsMap,timetable,segment,date);
		}
		return timetable;
	}
}
