package com.anqili.application.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anqili.application.bean.Teacher;
import com.anqili.application.dao.TeacherDao;
import com.anqili.application.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Resource
	private TeacherDao teacherDao;
	
	@Override
	public Teacher getTeacherById(int teachId) {
		return teacherDao.selectTeacherById(teachId);
	}

	@Override
	public Teacher getTeacherByNum(String teachNum) {
		return teacherDao.selectTeacherByNum(teachNum);
	}

	@Override
	public List<Teacher> getTeacherByDept(String dept) {
		return teacherDao.selectTeacherByDept(dept);
	}

	@Override
	public List<Teacher> getTeacherByStatus(int status) {
		return teacherDao.selectTeacherByStatus(status);
	}

	@Override
	public Teacher getTeacherBysubject(int subjectId) {
		return teacherDao.selectTeacherBySubject(subjectId);
	}

	@Override
	public boolean updateTeacher(int teachId, Teacher newTeacher) {
		Teacher oldTeacher = teacherDao.selectTeacherById(teachId);
		oldTeacher.setTeachName(newTeacher.getTeachName());
		oldTeacher.setTeachNum(newTeacher.getTeachNum());
		oldTeacher.setDepartment(newTeacher.getDepartment());
		oldTeacher.setStatus(newTeacher.getStatus());
		return teacherDao.updateTeacher(oldTeacher);
	}

	@Override
	public boolean insertTeacher(Teacher teacher) {
		return teacherDao.insertTeacher(teacher);
	}

}
