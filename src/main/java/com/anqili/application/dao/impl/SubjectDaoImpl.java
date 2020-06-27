package com.anqili.application.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.anqili.application.bean.Subject;
import com.anqili.application.dao.SubjectDao;

@Repository
public class SubjectDaoImpl implements SubjectDao {
	@Autowired
    private SqlSession sqlSession;
	
	@Override
	public List<Subject> selectAllSubject() {
		return sqlSession.selectList("selectAllSubject");
	}
	
	@Override
	public Subject selectSubjectById(int subjectId) {
		return sqlSession.selectOne("selectBySubjectId",subjectId);
	}

	@Override
	public List<Subject> selectSubjectByCourse(int courseId) {
		return sqlSession.selectList("selectSubjectByCourse", courseId);
	}
	
	@Override
	public List<Subject> selectSubjectByTeacher(int teacherId) {
		return sqlSession.selectList("selectSubjectByTeacher", teacherId);
	}
	
	@Override
	@Transactional
	public boolean updateSubject(Subject subject) {
		sqlSession.update("updateSubject", subject);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteOneSubject(int subjectId) {
		sqlSession.delete("deleteOneSubject", subjectId);
		return true;
	}
	
	@Override
	@Transactional
	public boolean insertSubject(Subject subject) {		
			sqlSession.insert("insertSubject", subject);
			return true;
	}

}
