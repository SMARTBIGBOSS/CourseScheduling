package com.anqili.application.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.anqili.application.bean.Subject;
import com.anqili.application.dao.SubjectDao;
import com.anqili.application.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Resource
	private SubjectDao subjectDao;
	
	@Override
	public List<Subject> getAllSubjects() {
		return subjectDao.selectAllSubject();
	}
	
	@Override
	public Subject getSubjectById(int subjectId) {
		return subjectDao.selectSubjectById(subjectId);
	}

	@Override
	public List<Subject> getSubjectByCourse(int courseId) {
		return subjectDao.selectSubjectByCourse(courseId);
	}
	
	@Override
	public List<Subject> getSubjectByTeacher(int teacherId) {
		return subjectDao.selectSubjectByTeacher(teacherId);
	}

	@Override
	public boolean insertSubject(Subject subject) {
		return subjectDao.insertSubject(subject);
	}

	@Override
	public boolean deleteOneSubject(int subjectId) {
		return subjectDao.deleteOneSubject(subjectId);
	}

	@Override
	public boolean deleteManySubjects(List<Integer> subjectIDs) {
		while(!subjectIDs.isEmpty()) {
			deleteOneSubject(subjectIDs.remove(0));
		}
		return true;
	}

	@Override
	public boolean updateSubject(int subjectId, Subject subject) {
		Subject oldSubject = subjectDao.selectSubjectById(subjectId);
		oldSubject.setClassTime(subject.getClassTime());
		oldSubject.setLabTime(subject.getLabTime());
		oldSubject.setSubjName(subject.getSubjName());
		oldSubject.setTeachId(subject.getTeachId());
		return subjectDao.updateSubject(oldSubject);
	}


}
