package com.anqili.application.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anqili.application.bean.Student;
import com.anqili.application.dao.StudentDao;
import com.anqili.application.service.StudentService;

@Service
public class StudentServiceImp implements StudentService {

	@Resource
	private StudentDao studentDao;
	
	@Override
	public Student getStudentById(int stuId) {
		return studentDao.selectStudentById(stuId);
	}

	@Override
	public Student getStudentByNum(String stuNum) {
		return studentDao.selectStudentByNum(stuNum);
	}

	@Override
	public List<Student> getStudentByCourse(int courseId) {
		return studentDao.selectStudentByCourse(courseId);
	}

	@Override
	public boolean updateStudent(int stuId, Student newStudent) {
		Student oldStudent = studentDao.selectStudentById(stuId);
		oldStudent.setStuNum(newStudent.getStuNum());
		oldStudent.setStuName(newStudent.getStuName());
		oldStudent.setCourseId(newStudent.getCourseId());
		return studentDao.updateStudent(oldStudent);
	}

	@Override
	public boolean insertStuden(Student student) {
		return studentDao.insertStudent(student);
	}

}
