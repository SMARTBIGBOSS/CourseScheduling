package com.anqili.application.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
import com.anqili.application.bean.Teacher;
import com.anqili.application.dao.CourseDao;
import com.anqili.application.dao.SubjectCourseDao;
import com.anqili.application.dao.SubjectDao;
import com.anqili.application.dao.TeacherDao;
import com.anqili.application.dao.impl.CourseDaoImpl;
import com.anqili.application.dao.impl.SubjectCourseDaoImpl;
import com.anqili.application.service.CourseService;
import com.anqili.application.service.RequiredCloneable;
import com.anqili.application.service.ScheduleService;
import com.anqili.application.service.SubjectCourseService;

@Service
public class ScheduleServiceImpl implements ScheduleService{
//	@Resource
//	private CourseDao courseDao;
//	@Resource
//	private SubjectDao subjectDao;
	@Resource
	private SubjectCourseDao subjectCourseDao;
	@Resource
	private TeacherDao teacherDao;
	@Resource
	private SubjectCourseService subjectCourseService;

	Map<Integer,int[][]> coursesTimetableTemp = new HashMap<Integer,int[][]>();//{courId:course[sid][sid]}
	Map<Integer,int[][]> teachersTimetableTemp = new HashMap<Integer,int[][]>();//{teachId:teacher[sub_courid][sub_courid]}
	
	Map<Integer,List<int[][]>> courseTimetable = new HashMap<Integer,List<int[][]>>();
	
	//Calculate and sort the weight of each course. The greater weight, the higher priority.
	public List<Entry<Integer, Double>> countCourseWeight() {
		Map<Integer, Double> courseWeight = new HashMap<Integer,Double>();//{"key": courseId, "value": weight}
		List<SubjectCourse> courseList = subjectCourseService.getAllCourseWithSubject();//[{sub_courId,courseId,course}]
//	System.out.println("专业个数："+courseList.size());
		
		for(int i = 0; i < courseList.size(); i++) {
			Course course = courseList.get(i).getCourse();
			int totalCourseTime = 0;
//	System.out.print("专业id号："+course.getCourId());
			List<SubjectCourse> subjects = subjectCourseDao.selectSubjectByCourse(course.getCourId());//[[{sub_courId,courseId,subjecctId,course,subject,teachId,teachName}]]
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
	
	//Count max hours/week of subjects of each course to insure subject can be finish during the semester 
	public Map<Integer,Map<Integer,Integer>> countCoursesTime (List<Entry<Integer, Double>> sortCourseWeight, int week){
		Map<Integer,Map<Integer,Integer>> coursesTime = new HashMap<Integer,Map<Integer,Integer>>();
		Map<Integer,Map<Integer,Integer>> errorCoursesTime = new HashMap<Integer,Map<Integer,Integer>>();
		
		for(int i = 0; i < sortCourseWeight.size(); i++) {
			int courseId = sortCourseWeight.get(i).getKey();//get course
			List<SubjectCourse> subjects = subjectCourseDao.selectSubjectByCourse(courseId);//all subjects of the course [[{sub_courId,courseId,subjecctId,course,subject,teachId,teachName}]]
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
		return coursesTime;//{courseId:{sid:int,sid:int,..}}
	}
	
	//Arrange course timetable template as per max hours per week of each subject
	public Map<Integer,int[][]> generateCourseTimetableTemp(Map<Integer,Map<Integer,Integer>> coursesTime){
//		Map<Integer,int[][]> coursesTimetable = new HashMap<Integer,int[][]>();
		for(Entry<Integer,Map<Integer,Integer>> c : coursesTime.entrySet()) {
			int courseId = c.getKey();
			Map<Integer,Integer> subjectsMap = c.getValue();
			int[][] courseTimetable = new int[8][5];
			int segment = 0,date = 0;
			courseTimetable =  arrangeSubject(courseId,subjectsMap,courseTimetable,segment,date);
			coursesTimetableTemp.put(courseId, courseTimetable);
		}
		return coursesTimetableTemp;
	}

	//Arrange subjects and generate teacher timetable template
	public int[][] arrangeSubject(int courseId, Map<Integer,Integer> subjectsMap, int[][] courseTimetable, int segment, int date) {
		if(!subjectsMap.isEmpty()) {
			Iterator<Map.Entry<Integer, Integer>> entries = subjectsMap.entrySet().iterator();
			while(entries.hasNext()) {
				Entry<Integer, Integer> subject = entries.next();
				int subId = subject.getKey();
				int subTime = subject.getValue();
				
				Teacher teacher = teacherDao.selectTeacherBySubject(subId);
				SubjectCourse subCour = subjectCourseService.getSubCourId(courseId, subId);
				int[][] teacherTimetable = new int[8][5];
				
				if(subTime>=2) {
					while((courseTimetable[segment][date] == 0 && !checkTeacherTimetable(teacher,teacherTimetable,segment,date)) 
							|| courseTimetable[segment][date] != 0) {
						if(date<4) {
							date += 1;
							continue;
						}
						date = 0 ;
						segment += 2;
					}
					courseTimetable[segment][date] = subId;
					courseTimetable[segment+1][date] = subId;
					teacherTimetable[segment][date] = subCour.getSub_courId();
					teacherTimetable[segment+1][date] = subCour.getSub_courId();
					teacherTimetable = teachersTimetableTemp.get(teacher.getTeachId());
					subject.setValue(subTime-2);
				}else if(subTime>=1) {
					while((courseTimetable[segment][date] == 0 && !checkTeacherTimetable(teacher,teacherTimetable,segment,date)) 
							|| courseTimetable[segment][date] != 0) {
						if(date<4) {
							date += 1;
							continue;
						}
						date = 0 ;
						segment += 2;
					}
					courseTimetable[segment][date] = subId;
					teacherTimetable[segment][date] = subCour.getSub_courId();
					teacherTimetable = teachersTimetableTemp.get(teacher.getTeachId());
					subject.setValue(subTime-1);
					
				}else {
					entries.remove();
				}
			}
			arrangeSubject(courseId,subjectsMap,courseTimetable,segment,date);
		}
		return courseTimetable;
	}

	//Check teacher timetable to confirm teacher is available
	public boolean checkTeacherTimetable(Teacher teacher, int[][] teacherTimetable, int segment, int date) {
		teacherTimetable = teachersTimetableTemp.get(teacher.getTeachId());
		if(teacherTimetable == null || teacherTimetable[segment][date] == 0)
			return true;
		return false;
	}
	
	//Generate course timetable for whole week
	public Map<Integer,List<int[][]>> generateCourseTimetable(Map<Integer,int[][]> coursesTimetableTemp, Map<Integer,int[][]> teachersTimetableTemp, int week) {
		for(Entry<Integer,int[][]> ctt : coursesTimetableTemp.entrySet()) {
			int cId = ctt.getKey();
			int[][] ct = ctt.getValue();
			List<SubjectCourse> subjects = subjectCourseService.getSubjectByCourse(cId);
			Map<Integer,int[]> subjectMap = new HashMap<Integer,int[]>();//{sid:[classTime,labTime,flag]} flag=1:class; flag=-1:lab
			HashMap<Integer,int[]> arrangeMap = new HashMap<Integer,int[]>();//{sid:[classAmong,labAmong]}
			
			//initialize subjectMap and arrangeMap
			subjects.forEach(sub->{
				int sId = sub.getSubjectId();
				int sClassTime = sub.getSubject().getClassTime();
				int sLabTime = sub.getSubject().getLabTime();
				int[] arrSub = {sClassTime,sLabTime,1};
				subjectMap.put(sId, arrSub);
				double ctw = (double)sClassTime/week;
				double ltw = (double)sLabTime/week;
				int[] arrArr = {(int) (ctw>1?Math.floor(ctw):Math.ceil(ctw)),(int) (ltw>1?Math.floor(ltw):Math.ceil(ltw))};
				arrangeMap.put(sId, arrArr);
			});
			
			//generate the whole timetable
			int counter = 1;
			List<int[][]> schedule = new ArrayList<int[][]>();
			while(counter <= week) {
				Map<Integer,int[]> copyArrangeMap = new HashMap<Integer,int[]>();

				copyArrangeMap = deepCloneMap(arrangeMap);
				for(Map.Entry<Integer,int[]> entry:copyArrangeMap.entrySet()){

					System.out.println(entry.getValue()[0]+" "+entry.getValue()[1]);
				}
				int [][] newCT = new int[8][5];
				for(int column = 0; column < 5; column++) {
					for(int row = 0; row < 8; row++) {
						int[] temp = subjectMap.get(ct[row][column]);
						int[] check = copyArrangeMap.get(ct[row][column]);
						if(temp != null) {
							switch(temp[2]) {
							case 1:
								if(temp[0] > 1) {
									if(ct[row][column] == ct[row+1][column] && check[0]>1) {
										newCT[row][column] = newCT[row+1][column] = ct[row][column];
										temp[0] =  temp[0] - 2;
										check[0] -= 2;		
										subjectMap.put(ct[row][column], temp);
										row++;
									}else {
										newCT[row][column] = ct[row][column];
										temp[0] =  temp[0] - 1;
										check[0] -= 1;
										subjectMap.put(ct[row][column], temp);
//										row --;
									}
								} else if(temp[0] == 1) {
									newCT[row][column] = ct[row][column];
									temp[0] =  temp[0] - 1;
									check[0] -= 1;
									subjectMap.put(ct[row][column], temp);
								} else {
									continue;
								}
								if(temp[1] != 0 && check[0] < check[1]) {
									temp[2] = -1;
									subjectMap.put(ct[row][column], temp);
								}
								continue;
							case -1:
								if(temp[1] > 1) {
									if(ct[row][column] == ct[row+1][column]) {
										newCT[row][column] = newCT[row+1][column] = 0-ct[row][column];
										temp[1] =  temp[1] - 2;
										check[1] -= 2;
										subjectMap.put(ct[row][column], temp);
										row++;
									}else {
										newCT[row][column] = 0-ct[row][column];
										temp[1] =  temp[1] - 1;
										check[1] -= 1;
										subjectMap.put(ct[row][column], temp);
//										row --;
									}
								} else if(temp[1] == 1) {
									newCT[row][column] = 0-ct[row][column];
									temp[1] =  temp[1] - 1;
									check[1] -= 1;
									subjectMap.put(ct[row][column], temp);
								} else {
									continue;
								}
								if(temp[0] != 0 && check[0] >= check[1]) {
									temp[2] = 1;
									subjectMap.put(ct[row][column], temp);
								}
								continue;
							}
						}
						copyArrangeMap.put(ct[row][column], check);
					}
				}
				if(schedule.add(newCT)) {
					for(Map.Entry<Integer,int[]> entry:subjectMap.entrySet()){
						int[] reset = entry.getValue();
						int key = entry.getKey();
						reset[2] = 1;
						subjectMap.put(key, reset);
//						System.out.println(reset[0]+" "+reset[1]+" "+reset[2]);
					}
					
					counter++;
				}
			}
			
			courseTimetable.put(cId, schedule);
		}
		return courseTimetable;
	}
	
	public Map<Integer, int[]> deepCloneMap(Map<Integer,int[]> m){
		Map<Integer, int[]> result = new HashMap<Integer,int[]>();
            for(Map.Entry<Integer, int[]> e : m.entrySet()) {
            	int[] v = e.getValue().clone();
            	
            	result.put(e.getKey(), e.getValue().clone());
            }
		return result;
		
	}
}
