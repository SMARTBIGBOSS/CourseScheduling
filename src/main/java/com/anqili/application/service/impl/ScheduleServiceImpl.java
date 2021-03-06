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
import com.anqili.application.bean.Teacher;
import com.anqili.application.service.ScheduleService;
import com.anqili.application.service.SubjectCourseService;
import com.anqili.application.service.TeacherService;

@Service
public class ScheduleServiceImpl implements ScheduleService{

	@Resource
	private TeacherService teacherService;
	@Resource
	private SubjectCourseService subjectCourseService;
	
	//Calculate and sort the weight of each course. The greater weight, the higher priority.
	public List<Entry<Integer, Double>> countCourseWeight() {
		Map<Integer, Double> courseWeight = new HashMap<Integer,Double>();//{"key": courseId, "value": weight}
		List<SubjectCourse> courseList = subjectCourseService.getAllCourseWithSubject();//[{sub_courId,courseId,course}]
		
		for(int i = 0; i < courseList.size(); i++) {
			Course course = courseList.get(i).getCourse();
			int totalCourseTime = 0;
			List<SubjectCourse> subjects = subjectCourseService.getSubjectByCourse(course.getCourId());//[[{sub_courId,courseId,subjecctId,course,subject,teachId,teachName}]]
			int subjectNum = subjects.size();
			while(!subjects.isEmpty()) {
				int lastOne = subjects.size()-1;
				totalCourseTime += (subjects.get(lastOne).getSubject().getClassTime() + subjects.get(lastOne).getSubject().getLabTime());
				subjects.remove(lastOne);
			}
			double w = 0.3*course.getCourSize() + 0.2*totalCourseTime + subjectNum;
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
			List<SubjectCourse> subjects = subjectCourseService.getSubjectByCourse(courseId);//all subjects of the course [[{sub_courId,courseId,subjecctId,course,subject,teachId,teachName}]]
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
				System.out.println("over learning time");
			}
		}
		if(errorCoursesTime.size() > 0) {
			return errorCoursesTime;
		}
		return coursesTime;//{courseId:{sid:total time,sid:total time,..}}
	}
	
	//Arrange course timetable template as per max hours per week of each subject
	public Map<Integer,int[][]> generateCourseTimetableTemp(Map<Integer,Map<Integer,Integer>> coursesTime){
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
				
				Teacher teacher = teacherService.getTeacherBysubject(subId);
				SubjectCourse subCour = subjectCourseService.getSubCourId(courseId, subId);
				boolean isAvailable = checkTeacherTimetable(teacher,segment,date);
				
				if(subTime == 0) {
					entries.remove();
				}else if(subTime == 1) {
					while(courseTimetable[segment][date] != 0 || !isAvailable) {
						if(date<4) {
							date += 1;
						}else if(segment<6){
							date = 0 ;
							segment += 2;
						}else {
								date = 0;
								segment = 0;
						}
						isAvailable = checkTeacherTimetable(teacher,segment,date);
					}
					courseTimetable[segment][date] = subId;
					if(teachersTimetableTemp.get(teacher.getTeachId()) != null) {
						int[][] teacherTimetable = teachersTimetableTemp.get(teacher.getTeachId());
						teacherTimetable[segment][date] = subCour.getSub_courId();
						teacherTimetable[segment+1][date] = subCour.getSub_courId();
						teachersTimetableTemp.put(teacher.getTeachId(), teacherTimetable);
					}else {
						int[][] teacherTimetable = new int[8][5];
						teacherTimetable[segment][date] = subCour.getSub_courId();
						teacherTimetable[segment+1][date] = subCour.getSub_courId();
						teachersTimetableTemp.put(teacher.getTeachId(), teacherTimetable);
					}
					subject.setValue(subTime-1);
				}else {
					while(courseTimetable[segment][date] != 0 || !isAvailable) {
						if(date<4) {
							date += 1;
						}else if(segment<6){
							date = 0 ;
							segment += 2;
						}else {
								date = 0;
								segment = 0;
						}
						isAvailable = checkTeacherTimetable(teacher,segment,date);
					}
					courseTimetable[segment][date] = subId;
					courseTimetable[segment+1][date] = subId;

					if(teachersTimetableTemp.get(teacher.getTeachId()) != null) {
						int[][] teacherTimetable = teachersTimetableTemp.get(teacher.getTeachId());
						teacherTimetable[segment][date] = subCour.getSub_courId();
						teacherTimetable[segment+1][date] = subCour.getSub_courId();
						teachersTimetableTemp.put(teacher.getTeachId(), teacherTimetable);
					}else {
						int[][] teacherTimetable = new int[8][5];
						teacherTimetable[segment][date] = subCour.getSub_courId();
						teacherTimetable[segment+1][date] = subCour.getSub_courId();
						teachersTimetableTemp.put(teacher.getTeachId(), teacherTimetable);
					}
					subject.setValue(subTime-2);
				}
			}
			arrangeSubject(courseId,subjectsMap,courseTimetable,segment,date);
		}
		return courseTimetable;
	}

	//Check teacher timetable template to confirm teacher is available
	public boolean checkTeacherTimetable(Teacher teacher, int segment, int date) {
		int[][] teacherTimetable = teachersTimetableTemp.get(teacher.getTeachId());
		if(teacherTimetable == null || teacherTimetable[segment][date] == 0)
			return true;
		return false;
	}
	
	//Generate course schedule for whole semester
	public Map<Integer, Map<Integer, int[][]>> generateCourseTimetable(Map<Integer,int[][]> coursesTimetableTemp, Map<Integer,int[][]> teachersTimetableTemp, int week) {
		for(Entry<Integer,int[][]> ctt : coursesTimetableTemp.entrySet()) {
			int cId = ctt.getKey();
			int[][] ct = ctt.getValue();//course timetable
			List<SubjectCourse> subjects = subjectCourseService.getSubjectByCourse(cId);
			Map<Integer,int[]> subjectMap = new HashMap<Integer,int[]>();//{sid:[classTime,labTime,flag]} flag=1:class; flag=-1:lab
			HashMap<Integer,int[]> arrangeMap = new HashMap<Integer,int[]>();//{sid:[classAmong,labAmong]} max time per week
			
			//initialize subjectMap(store total class time, total lab time,flag) and arrangeMap(store max classTime, max labTime per week)
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
			Map<Integer,int[][]> scheduleC = new HashMap<Integer,int[][]>();
			
			//go though each week
			while(counter <= week) {
				Map<Integer,int[]> copyArrangeMap = new HashMap<Integer,int[]>();
				copyArrangeMap = deepCloneMap(arrangeMap);
				int [][] newCT = new int[8][5];
				//go though each time segment and date
				for(int column = 0; column < 5; column++) {
					for(int row = 0; row < 7; row++) {
						int sid = ct[row][column];
						if(sid != 0) {
							int[] temp = subjectMap.get(sid);//total class time, lab time, flag of a subject
							int[] check = copyArrangeMap.get(sid);//max class time, max lab time per week of a subject
							if(temp != null) {
								switch(temp[2]) {
								case 1:
									if(temp[0] > 1) {
										if(ct[row][column] == ct[row+1][column] && check[0]>1) {
											newCT[row][column] = newCT[row+1][column] = sid;
												int tid = teacherService.getTeacherBysubject(sid).getTeachId();
												int sub_courId = subjectCourseService.getSubCourId(cId, sid).getSub_courId();
												updateTeacherTimetable(tid,counter,row,column,sub_courId);
												updateTeacherTimetable(tid,counter,row+1,column,sub_courId);
											temp[0] =  temp[0] - 2;
											check[0] -= 2;		
											subjectMap.put(sid, temp);
										}else {
											newCT[row][column] = ct[row][column];
												int sub_courId = subjectCourseService.getSubCourId(cId, sid).getSub_courId();
												int tid = teacherService.getTeacherBysubject(sid).getTeachId();
												updateTeacherTimetable(tid,counter,row,column,sub_courId);
											temp[0] =  temp[0] - 1;
											check[0] -= 1;
											subjectMap.put(sid, temp);
										}
									} else if(temp[0] == 1) {
										newCT[row][column] = ct[row][column];
											int sub_courId = subjectCourseService.getSubCourId(cId, sid).getSub_courId();
											int tid = teacherService.getTeacherBysubject(sid).getTeachId();
											updateTeacherTimetable(tid,counter,row,column,sub_courId);	
										temp[0] =  temp[0] - 1;
										check[0] -= 1;
										subjectMap.put(sid, temp);
									} else {
										continue;
									}
									if(temp[1] != 0 && check[0] < check[1]) {
										temp[2] = -1;
										subjectMap.put(ct[row][column], temp);
									}
									row++;
									continue;
								case -1:
									if(temp[1] > 1) {
										if(ct[row][column] == ct[row+1][column]) {
											newCT[row][column] = newCT[row+1][column] = 0-sid;
												int sub_courId = 0-subjectCourseService.getSubCourId(cId, sid).getSub_courId();
												int tid = teacherService.getTeacherBysubject(sid).getTeachId();
												updateTeacherTimetable(tid,counter,row,column,sub_courId);
												updateTeacherTimetable(tid,counter,row+1,column,sub_courId);
											temp[1] =  temp[1] - 2;
											check[1] -= 2;
											subjectMap.put(0-sid, temp);
										}else {
											newCT[row][column] = 0-ct[row][column];
												int sub_courId = 0-subjectCourseService.getSubCourId(cId, sid).getSub_courId();
												int tid = teacherService.getTeacherBysubject(sid).getTeachId();
												updateTeacherTimetable(tid,counter,row,column,sub_courId);
											temp[1] =  temp[1] - 1;
											check[1] -= 1;
											subjectMap.put(sid, temp);
										}
									} else if(temp[1] == 1) {
										newCT[row][column] = 0-ct[row][column];
											int sub_courId = 0-subjectCourseService.getSubCourId(cId, sid).getSub_courId();
											int tid = teacherService.getTeacherBysubject(sid).getTeachId();
											updateTeacherTimetable(tid,counter,row,column,sub_courId);
										temp[1] =  temp[1] - 1;
										check[1] -= 1;
										subjectMap.put(sid, temp);
									} else {
										continue;
									}
									if(temp[0] != 0 && check[0] >= check[1]) {
										temp[2] = 1;
										subjectMap.put(ct[row][column], temp);
									}
									row++;
									continue;
								}
							}
							copyArrangeMap.put(ct[row][column], check);
						}
					}
				}
				if(scheduleC.put(counter,newCT) != null) {
					for(Map.Entry<Integer,int[]> entry:subjectMap.entrySet()){
						int[] reset = entry.getValue();
						int key = entry.getKey();
						reset[2] = 1;
						subjectMap.put(key, reset);
					}
					counter++;
				}
			}
			courseTimetable.put(cId, scheduleC);
		}
		return courseTimetable;
	}
	
	//update the teacher schedule of whole semester
	public void updateTeacherTimetable(int tId, int week,int segment,int date, int sub_courId) {
		Map <Integer,int[][]> scheduleT = teacherTimetable.get(tId);//a teacher timetable
		int[][] oneTimetable = new int[8][5];
		if(scheduleT == null) {
			scheduleT = new HashMap<Integer,int[][]>();
			oneTimetable[segment][date] = sub_courId;
		} else {
			oneTimetable = scheduleT.get(week);
			if(oneTimetable == null) {
				oneTimetable = new int[8][5];
				oneTimetable[segment][date] = sub_courId;
			} else {
				oneTimetable[segment][date] = sub_courId;
			}
		}
		scheduleT.put(week, oneTimetable);
		teacherTimetable.put(tId, scheduleT);
	}
	
	//deep copy Map
	public Map<Integer, int[]> deepCloneMap(Map<Integer,int[]> m){
		Map<Integer, int[]> result = new HashMap<Integer,int[]>();
            for(Map.Entry<Integer, int[]> e : m.entrySet()) {
            	result.put(e.getKey(), e.getValue().clone());
            }
		return result;
	}
}
