package com.anqili.application.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anqili.application.bean.SubjectCourse;
import com.anqili.application.dao.SubjectCourseDao;
import com.anqili.application.service.SubjectCourseService;

@Service
public class SubjectCourseServiceImpl implements SubjectCourseService {
	
	@Resource
	private SubjectCourseDao subjectCourseDao;
	
	@Override
	public SubjectCourse getSubCourId(int courId, int subId) {
		SubjectCourse courSub = new SubjectCourse();
		courSub.setCourseId(courId);
		courSub.setSubjectId(subId);
		return subjectCourseDao.selectSubCourId(courSub);
	}
	
	@Override
	public List<SubjectCourse> getCourseBySubjectId(int subjectId) {
		return subjectCourseDao.selectCourseBySubject(subjectId);
	}

	@Override
	public List<SubjectCourse> getSubjectByCourse(int courseId) {
		return subjectCourseDao.selectSubjectByCourse(courseId);
	}

	@Override
	public List<SubjectCourse> getAllCourseWithSubject() {
		return subjectCourseDao.selecctAllCourseWithSubject();
	}
	
	@Override
	public boolean addSubjectToCourse(SubjectCourse subjectcourse) {
		return subjectCourseDao.insertSubjectToCourse(subjectcourse);
	}

	@Override
	public boolean removeSubjectFromCourse(int sub_courId) {
		return subjectCourseDao.deleteSubjectFromCourse(sub_courId);
	}

}
