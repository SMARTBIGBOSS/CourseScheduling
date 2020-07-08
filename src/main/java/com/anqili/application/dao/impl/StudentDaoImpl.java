package com.anqili.application.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anqili.application.bean.Student;
import com.anqili.application.dao.StudentDao;

@Repository
public class StudentDaoImpl implements StudentDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Student selectStudentById(int stuId) {
		return sqlSession.selectOne("selectStudentById", stuId);
	}

	@Override
	public Student selectStudentByNum(String stuNum) {
		return sqlSession.selectOne("selectStudentByNum", stuNum);
	}

	@Override
	public List<Student> selectStudentByCourse(int courseId) {
		return sqlSession.selectList("selectStudentByCourse", courseId);
	}

	@Override
	@Transactional
	public boolean updateStudent(Student newStudent) {
		sqlSession.update("updateStudent", newStudent);
		return true;
	}

	@Override
	@Transactional
	public boolean insertStudent(Student student) {
		sqlSession.insert("insertStudent", student);
		return true;
	}

}
