package com.anqili.application.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anqili.application.bean.Teacher;
import com.anqili.application.dao.TeacherDao;

@Repository
public class TeacherDaoimpl implements TeacherDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Teacher selectTeacherById(int teachId) {
		return sqlSession.selectOne("selectTeacherById", teachId);
	}

	@Override
	public Teacher selectTeacherByNum(String teachNum) {
		return sqlSession.selectOne("selectTeacherByNum", teachNum);
	}

	@Override
	public List<Teacher> selectTeacherByDept(String dept) {
		return sqlSession.selectList("selectTeacherByDept", dept);
	}

	@Override
	public List<Teacher> selectTeacherByStatus(int status) {
		return sqlSession.selectList("selectTeacherByStatus", status);
	}

	@Override
	public Teacher selectTeacherBySubject(int subjectId) {
		return sqlSession.selectOne("selectTeacherBySubject", subjectId);
	}

	@Override
	@Transactional
	public boolean updateTeacher(Teacher newTeacher) {
		sqlSession.update("updateTeacher", newTeacher);
		return true;
	}

	@Override
	@Transactional
	public boolean insertTeacher(Teacher teacher) {
		sqlSession.insert("insertTeacher", teacher);
		return true;
	}

}
